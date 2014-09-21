package general;

import java.util.ArrayList;

import util.ShapePlan;

public class Scene {

	private ArrayList<ShapePlan> shapes;
	private int rowCount;
	private ArrayList<Node> nodes = new ArrayList<>();

	public Scene(ArrayList<ShapePlan> shapes, int rowCount) {
		this.shapes = shapes;
		this.rowCount = rowCount;
		
		System.out.println("FERTIG EHEHHEHEH");
	}

	public int getRowCount() {
		return rowCount;
	}

	public ArrayList<ShapePlan> getShapes() {
		return shapes;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
