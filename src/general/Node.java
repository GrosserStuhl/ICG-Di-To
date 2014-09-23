package general;

import java.util.ArrayList;
import java.util.Arrays;

import ogl.app.App;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shader.Shader;

public abstract class Node implements App {

	private ArrayList<Node> childNodes = new ArrayList<Node>();
	private String name;
	private Matrix transformation;

	public void setTransformation(Matrix transformation) {
		this.transformation = transformation;
	}

	public Matrix getTransformation() {
		return transformation;
	}

	public ArrayList<Node> getChildNodes() {
		return childNodes;
	}

	public void addNode(Node node) {
		childNodes.add(node);
	}

	public void removeNode(Node node) {
		childNodes.remove(node);
	}

	public abstract void display(int width, int height, Matrix parentMatrix);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Node: " + name;
	}

	public void setSelected() {
	}

	public void deselect() {
	}
	
	public boolean isSelected() {
		return false;
	}

	public Vertex[] getVertices() {
		return null;
	}

	public Shader getShader() {
		return null;
	}

	public void setTranslation(Vector translation) {
	}

	public float getHeight() {
		return 0;
	}

	public float getWidth() {
		return 0;
	}

	public void setHeight(float height) {
	}

	public void setWidth(float width) {
	}

	public float getScale() {
		return 0;
	}
	
	public void setCompleted() {
	}

	public void setUnCompleted() {
	}
	
	public boolean isCompleted() {
		return false;
	}
	

	public void setThreePointLights(){}
	
	public void setTwoPointLights(){}
	
	public void setOnePointLight(){}
	

}
