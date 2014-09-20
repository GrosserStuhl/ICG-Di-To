package util;

import java.util.ArrayList;

public class ShapePlan {
	
	private String planet;
	private ArrayList<ShapePlan> children;
	private float scale;
	
	public ShapePlan(String type, ArrayList<ShapePlan> children, float scale) {
		this.planet = type;
		this.children = children;
		this.setScale(scale);
	}
	
	public ShapePlan(String type, float scale) {
		this.planet = type;
		this.setScale(scale);
	}
	
	public ShapePlan(String type) {
		this.planet = type;
		this.children = null;
		setScale(1);
	}

	public String getName() {
		return planet;
	}

	public void setName(String name) {
		this.planet = name;
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
	
}
