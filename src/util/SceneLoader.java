package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import general.Scene;

public class SceneLoader {

	public static Scene loadScene(String fileName) {
		try {
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new FileReader("./res/scenes/"
						+ fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			int rowCount = 1;
			ArrayList<ShapePlan> shapes = new ArrayList<>();

			String line = "";
			while (!line.equals("</scene>")) {
				line = reader.readLine();
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
						System.out.println("shape through if: " + shape);
						shape = new ShapePlan(planet[1], children, scale);
					} else {
						shape = new ShapePlan(planet[1], scale);
						System.out.println("shape through else: " + shape);
					}
					shapes.add(shape);
				}
			}
			reader.close();
			return new Scene(shapes, rowCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
