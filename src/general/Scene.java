package general;

import java.util.ArrayList;

import util.ShapePlan;

public class Scene {

	private ArrayList<ShapePlan> shapes;
	private int rowCount;
	private ArrayList<RowNode> nodes = new ArrayList<>();

	public Scene(ArrayList<ShapePlan> shapes, int rowCount) {
		this.shapes = shapes;
		this.rowCount = rowCount;

		System.out.println("FERTIG EHEHHEHEH");
		for (ShapePlan shape : shapes) {
			System.out.println("shapes: " + shape);
			if (!shape.getChildren().isEmpty()) {
				for (ShapePlan child : shape.getChildren()) {
					System.out.println("children: " + child);
					if (!child.getChildren().isEmpty()) {
						for (ShapePlan child2 : child.getChildren()) {
							System.out.println("children2: " + child2);
						}
					}
				}
			}
		}

		for (int i = 0; i < rowCount; i++) {
			nodes.add(new RowNode(i));
		}

		for (ShapePlan shape : shapes) {
			ShapeNode node = null;
			if (shape.getName().equals("Moon")) {

			} else if (shape.getName().equals("Jupiter")) {

			}
			if (!shape.getChildren().isEmpty()) {
				for (ShapePlan child : shape.getChildren()) {
					ShapeNode childNode = null;
					if (child.getName().equals("Moon")) {

					} else if (child.getName().equals("Jupiter")) {

					}
					if (!child.getChildren().isEmpty()) {
						for (ShapePlan child2 : shape.getChildren()) {
							ShapeNode childNode2 = null;
							if (child2.getName().equals("Moon")) {

							} else if (child2.getName().equals("Jupiter")) {

							}
							childNode.addNode(childNode2);
						}
					}
					node.addNode(childNode);
				}
			}
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
}
