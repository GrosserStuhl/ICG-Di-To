package general;

import java.util.ArrayList;
import ogl.app.App;
import ogl.vecmath.Matrix;

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

}
