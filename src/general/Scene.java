package general;

import java.util.ArrayList;

import ogl.app.Texture;
import shader.Shader;
import shapes.OBJModel;
import util.ResourceLoader;
import util.ShapePlan;
import static ogl.vecmathimp.FactoryDefault.vecmath;

public class Scene {

	private ArrayList<ShapePlan> shapes;
	private int rowCount;
	private ArrayList<RowNode> nodes = new ArrayList<>();
	private Shader shader;
	private Camera cam;

	public Scene(ArrayList<ShapePlan> shapes, int rowCount) {
		this.shapes = shapes;
		this.rowCount = rowCount;
	}
	
	public Scene(ArrayList<ShapePlan> shapes, int rowCount, Camera cam) {
		this.shapes = shapes;
		this.rowCount = rowCount;
		this.cam = cam;
	}

	public void createNodes() {
		for (int i = 0; i < rowCount; i++) {
			RowNode row = new RowNode(i);
			row.setName("RowNode" + i);
			System.out.println("row added: " + row);
			nodes.add(row);
		}

		Mesh small = ResourceLoader.loadOBJModel("small.obj");
		Mesh medium = ResourceLoader.loadOBJModel("jupiterSmoothShaded.obj");
		Mesh large = ResourceLoader.loadOBJModel("xlarge.obj");
		Texture moonT = ResourceLoader.loadTexture("MoonMap2.jpg");
		Texture jupiterT = ResourceLoader.loadTexture("jupiter.jpg");
		Texture merkurT = ResourceLoader.loadTexture("earth.jpg");

		for (ShapePlan shape : shapes) {
			System.out.println("for loop started");
			ShapeNode node = null;
			if (shape.getName().equals("Moon")) {
				System.out.println("moon found");
				switch ((int) shape.getScale()) {
				case 1:
					node = new OBJModel(small.getVertices(), shader, moonT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				case 2:
					node = new OBJModel(medium.getVertices(), shader, moonT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;

				default:
					node = new OBJModel(large.getVertices(), shader, moonT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				}
				node.setName("Moon");
			} else if (shape.getName().equals("Jupiter")) {
				System.out.println("jupiter found");
				switch ((int) shape.getScale()) {
				case 1:
					node = new OBJModel(small.getVertices(), shader, jupiterT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				case 2:
					node = new OBJModel(medium.getVertices(), shader, jupiterT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;

				default:
					node = new OBJModel(large.getVertices(), shader, jupiterT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				}
				node.setName("Jupiter");
			} else {
				System.out.println("else found");
				System.out.println(shape.getName() + "   PIEP");
				switch ((int) shape.getScale()) {
				case 1:
					node = new OBJModel(small.getVertices(), shader, merkurT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				case 2:
					node = new OBJModel(medium.getVertices(), shader, merkurT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;

				default:
					node = new OBJModel(large.getVertices(), shader, merkurT,
							vecmath.vector(0, 0, 0), shape.getScale());
					break;
				}
				node.setName("Merkur");
			}
			if (!shape.getChildren().isEmpty()) {
				for (ShapePlan child : shape.getChildren()) {
					ShapeNode childNode = null;
					if (child.getName().equals("Moon")) {
						System.out.println("moon2 found");
						switch ((int) child.getScale()) {
						case 1:
							childNode = new OBJModel(small.getVertices(),
									shader, moonT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						case 2:
							childNode = new OBJModel(medium.getVertices(),
									shader, moonT, vecmath.vector(0, 0, 0), shape.getScale());
							break;

						default:
							childNode = new OBJModel(large.getVertices(),
									shader, moonT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						}
						childNode.setName("Moon");
						System.out.println("childnode new name: "
								+ childNode.getName());
					} else if (child.getName().equals("Jupiter")) {
						System.out.println("jupiter2 found");
						switch ((int) child.getScale()) {
						case 1:
							childNode = new OBJModel(small.getVertices(),
									shader, jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						case 2:
							childNode = new OBJModel(medium.getVertices(),
									shader, jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
							break;

						default:
							childNode = new OBJModel(large.getVertices(),
									shader, jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						}
						childNode.setName("Jupiter");
						System.out.println("childnode new name: "
								+ childNode.getName());
					} else {
						System.out.println("else2 found");
						System.out.println(child.getName() + "   PIEP2");
						switch ((int) child.getScale()) {
						case 1:
							childNode = new OBJModel(small.getVertices(),
									shader, merkurT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						case 2:
							childNode = new OBJModel(medium.getVertices(),
									shader, merkurT, vecmath.vector(0, 0, 0), shape.getScale());
							break;

						default:
							childNode = new OBJModel(large.getVertices(),
									shader, merkurT, vecmath.vector(0, 0, 0), shape.getScale());
							break;
						}
						childNode.setName("Merkur");
						System.out.println("childnode new name: "
								+ childNode.getName());
					}
					if (!child.getChildren().isEmpty()) {
						for (ShapePlan child2 : child.getChildren()) {
							ShapeNode childNode2 = null;
							if (child2.getName().equals("Moon")) {
								System.out.println("moon3 found");
								switch ((int) child2.getScale()) {
								case 1:
									childNode2 = new OBJModel(
											small.getVertices(), shader, moonT,
											vecmath.vector(0, 0, 0), shape.getScale());
									break;
								case 2:
									childNode2 = new OBJModel(
											medium.getVertices(), shader,
											moonT, vecmath.vector(0, 0, 0), shape.getScale());
									break;

								default:
									childNode2 = new OBJModel(
											large.getVertices(), shader, moonT,
											vecmath.vector(0, 0, 0), shape.getScale());
									break;
								}
								childNode2.setName("Moon");
							} else if (child2.getName().equals("Jupiter")) {
								System.out.println("jupiter3 found");
								switch ((int) child2.getScale()) {
								case 1:
									childNode2 = new OBJModel(
											small.getVertices(), shader,
											jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
									break;
								case 2:
									childNode2 = new OBJModel(
											medium.getVertices(), shader,
											jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
									break;

								default:
									childNode2 = new OBJModel(
											large.getVertices(), shader,
											jupiterT, vecmath.vector(0, 0, 0), shape.getScale());
									break;
								}
								childNode2.setName("Jupiter");
							} else {
								System.out.println("else3 found");
								System.out.println(child2.getName()
										+ "    PIEP3");
								switch ((int) child2.getScale()) {
								case 1:
									childNode2 = new OBJModel(
											small.getVertices(), shader,
											merkurT, vecmath.vector(0, 0, 0), shape.getScale());
									break;
								case 2:
									childNode2 = new OBJModel(
											medium.getVertices(), shader,
											merkurT, vecmath.vector(0, 0, 0), shape.getScale());
									break;

								default:
									childNode2 = new OBJModel(
											large.getVertices(), shader,
											merkurT, vecmath.vector(0, 0, 0), shape.getScale());
									break;
								}
								childNode2.setName("Merkur");
							}
							childNode.addNode(childNode2);
							System.out.println("child2 added: " + childNode2);
						}
					}
					node.addNode(childNode);
					System.out.println("child added: " + childNode);
				}
			}
			nodes.get(0).addNode(node);
			System.out.println("node added: " + node);
		}
	}

	public ArrayList<RowNode> getNodes() {
		return nodes;
	}

	public int getRowCount() {
		return rowCount;
	}

	public ArrayList<ShapePlan> getShapes() {
		return shapes;
	}
	
	public Camera getCamera() {
		return cam;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
}
