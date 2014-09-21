package general;

import java.util.ArrayList;

import util.ShapePlan;

public class Scene {

	private ArrayList<ShapePlan> shapes;
	private int rowCount;

	public Scene(ArrayList<ShapePlan> shapes, int rowCount) {
		this.shapes = shapes;
		this.rowCount = rowCount;

		System.out.println("FERTIG EHEHHEHEH");
		for (ShapePlan shape : shapes) {
			System.out.println("shapes: " + shape);
			if (shape.getChildren() != null) {
				for (ShapePlan child : shape.getChildren()) {
					System.out.println("children: " + child);
					if (child.getChildren() != null) {
						for (ShapePlan child2 : child.getChildren()) {
							System.out.println("children2: " + child2);
						}
					}
				}
			}
		}
		
		
		
	}

	public int getRowCount() {
		return rowCount;
	}

	public ArrayList<ShapePlan> getShapes() {
		return shapes;
	}
}
