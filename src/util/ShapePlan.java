package util;

import java.util.ArrayList;

public class ShapePlan {
	
	private String planet;
	private ArrayList<String> children;
	private float scale;
	
	public ShapePlan(String type, ArrayList<String> children, float scale) {
		this.planet = type;
		this.children = children;
		this.scale = scale;
	}
	
	public ShapePlan(String type, float scale) {
		this.planet = type;
		this.scale = scale;
	}
	
	public ShapePlan(String type) {
		this.planet = type;
		this.children = null;
		scale = 1;
	}

	public String getName() {
		return planet;
	}

	public void setName(String name) {
		this.planet = name;
	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
	
}
