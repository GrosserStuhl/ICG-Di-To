package general;

import java.util.Vector;

import ogl.app.App;
import ogl.vecmath.Matrix;

public abstract class Node implements App {

	private Vector<Node> childNodes = new Vector<Node>();
	private String name;
	private Matrix transformation;

	public void setTransformation(Matrix transformation) {
		this.transformation = transformation;
	}

	public Matrix getTransformation() {
		return transformation;
	}

	public Vector<Node> getChildNodes() {
		return childNodes;
	}

	public void addNode(Node node) {
		childNodes.add(node);
	}

	public void removeNode(Node node) {
		childNodes.remove(node);
	}

	public void update() {

	}

	@Override
	public String toString() {
		return "Node: " + name;
	}

}
