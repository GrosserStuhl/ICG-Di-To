package util;

import java.util.ArrayList;

public class ShapePlan {

	private String name;
	private ArrayList<ShapePlan> children = new ArrayList<>();
	private float scale;

	public ShapePlan(String name, ArrayList<ShapePlan> children, float scale) {
		this.name = name;
		this.children = children;
		this.setScale(scale);
	}

	public ShapePlan(String name, float scale) {
		this.name = name;
		this.setScale(scale);
	}

	public ShapePlan(String name) {
		this.name = name;
		setScale(1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ShapePlan> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ShapePlan> children) {
		this.children = children;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "name: " + name + ", scale: " + scale;
	}

}
