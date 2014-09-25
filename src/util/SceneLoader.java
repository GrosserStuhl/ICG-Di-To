package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.Vector;
import general.Camera;
import general.Node;
import general.Scene;

public class SceneLoader {

	public static Scene createScene(String fileName) {
		Scene scene = loadScene(fileName);
		scene.createNodes();

		return scene;
	}

	private static Scene loadScene(String fileName) {
		try {
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new FileReader("./res/scenes/"
						+ fileName));
			} catch (FileNotFoundException e) {
				System.err
						.println("Angegebene Szene-Datei konnte nicht gefunden werden, Programm wird beendet");
				System.exit(0);
			}

			int rowCount = 1;
			ArrayList<ShapePlan> shapes = new ArrayList<>();
			Vector eye = null;
			Vector center = null;
			boolean freeMode = false;

			String line = "";
			while (!line.equals("</scene>")) {
				line = reader.readLine();
				if (line.contains("<camera")) {
					System.out.println("camera line" + line);
					String[] vectors = line.split("#");
					String[] coords = vectors[1].split("\"");
					String[] floatsEye = coords[1].split(",");
					float x = 0, y = 0, z = 0;
					try {
						x = Float.parseFloat(floatsEye[0]);
						y = Float.parseFloat(floatsEye[1]);
						z = Float.parseFloat(floatsEye[2]);
					} catch (NumberFormatException ne) {
						System.err
								.println("Kamerakoordinaten konnten nicht ausgelesen werden");
					}
					eye = vecmath.vector(x, y, z);
					String[] coords2 = vectors[2].split("\"");
					String[] floatsCenter = coords2[1].split(",");
					try {
						x = Float.parseFloat(floatsCenter[0]);
						y = Float.parseFloat(floatsCenter[1]);
						z = Float.parseFloat(floatsCenter[2]);
					} catch (NumberFormatException ne) {
						System.err
								.println("Kamerakoordinaten konnten nicht ausgelesen werden");
					}
					center = vecmath.vector(x, y, z);

					String[] modePart = vectors[3].split("\"");
					String mode = modePart[1];
					if (mode.equals("free"))
						freeMode = true;
					else
						freeMode = false;
				}
				if (line.contains("<shape")) {
					System.out.println("first line" + line);
					ShapePlan shape = null;
					String[] attributes = line.split("#");
					String[] planet = attributes[1].split("\"");
					float scale = 0;
					if (attributes.length > 2) {
						String[] scale1 = attributes[2].split("=");
						String[] scale2 = scale1[1].split(">");
						String scale3 = scale2[0];
						scale = Float.parseFloat(scale3);
					}

					if (!line.endsWith("</shape>")) {
						ArrayList<ShapePlan> children = new ArrayList<>();
						rowCount++;
						reader.setLineNumber(reader.getLineNumber() - 1);
						ShapePlan child = null;
						line = reader.readLine();
						while (!line.trim().equals("</shape>")) {
							System.out.println("zweite while: " + line);
							String[] attributes2 = line.split("#");
							String[] planet2 = attributes2[1].split("\"");
							float scaleTwo = 0;
							if (attributes2.length > 2) {
								String[] scale1 = attributes2[2].split("=");
								String[] scale2 = scale1[1].split(">");
								String scale3 = scale2[0];
								scaleTwo = Float.parseFloat(scale3);
							}
							if (!line.endsWith("</shape>")) {
								ArrayList<ShapePlan> children2 = new ArrayList<>();
								rowCount++;
								reader.setLineNumber(reader.getLineNumber() - 1);
								ShapePlan child2 = null;
								line = reader.readLine();
								while (!line.trim().equals("</shape>")) {
									System.out.println("dritte while: " + line);
									String[] attributes3 = line.split("#");
									String[] planet3 = attributes3[1]
											.split("\"");
									float scaleThree = 0;
									if (attributes3.length > 2) {
										String[] scale1 = attributes3[2]
												.split("=");
										String[] scale2 = scale1[1].split(">");
										String scale3 = scale2[0];
										scaleThree = Float.parseFloat(scale3);
									}
									child2 = new ShapePlan(planet3[1],
											scaleThree);
									System.out.println("child2: " + child2);
									children2.add(child2);
									line = reader.readLine();
								}
								child = new ShapePlan(planet2[1], children2,
										scaleTwo);
								System.out
										.println("child through if: " + child);
							} else {
								child = new ShapePlan(planet2[1], scaleTwo);
								System.out.println("child through else: "
										+ child);
							}
							children.add(child);
							line = reader.readLine();
						}
						shape = new ShapePlan(planet[1], children, scale);
						System.out.println("shape through if: " + shape);
					} else {
						shape = new ShapePlan(planet[1], scale);
						System.out.println("shape through else: " + shape);
					}
					shapes.add(shape);
				}
			}
			reader.close();
			if (eye != null && center != null)
				return new Scene(shapes, rowCount, new Camera(eye, center,
						freeMode));
			else
				return new Scene(shapes, rowCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void saveScene(String fileName, ArrayList<Node> nodes,
			Camera cam) {
		File f = new File("res\\scenes\\" + fileName + ".xml");
		StringBuilder builder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

		builder.append("\n<scene>\n");
		builder.append("<camera #eye=\"" + cam.getEye().x() + ","
				+ cam.getEye().y() + "," + cam.getEye().z() + "\" #center=\""
				+ cam.getCenter().x() + "," + cam.getCenter().y() + ","
				+ cam.getCenter().z() + "\"");
		String mode = "";
		if (cam.isInFreeMode())
			mode = "free";
		else
			mode = "fixed";
		builder.append(" #mode=\"" + mode + "\"></camera>\n");
		for (Node node : nodes.get(0).getChildNodes()) {
			builder.append("<shape #planet=\"" + node.getName() + "\" #scale="
					+ node.getScale() + ">");
			if (node.getChildNodes().isEmpty()) {
				builder.append("</shape>\n");
			} else {
				builder.append("\n");
				for (Node child : node.getChildNodes()) {
					builder.append("	<shape #planet=\"" + child.getName()
							+ "\" #scale=" + child.getScale() + ">");
					if (child.getChildNodes().isEmpty()) {
						builder.append("</shape>\n");
					} else {
						builder.append("\n");
						for (Node child2 : node.getChildNodes()) {
							builder.append("		<shape #planet=\""
									+ child2.getName() + "\" #scale="
									+ child2.getScale() + "></shape>");
						}
						builder.append("\n	</shape>\n");
					}
				}
				builder.append("</shape>\n");
			}
		}
		builder.append("</scene>");
		System.out.println(builder.toString());

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(f));
			writer.write(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(fileName);
	}
}
