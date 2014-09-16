package general;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import static ogl.vecmathimp.FactoryDefault.vecmath;

public class RowNode extends Node {

	private int rowIndex;
	private float newNodePosition = 0;

	public RowNode(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void addNode(Node node) {
		node.setTranslation(vecmath.vector(newNodePosition, 0, 0));
		newNodePosition += 3;
		getChildNodes().add(node);
	}

	@Override
	public void init() {

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		if (getChildNodes().isEmpty())
			newNodePosition = 0;

		for (Node child : getChildNodes()) {
			child.simulate(elapsed, input);
		}
	}

	@Override
	public void display(int width, int height) {

	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		setTransformation(vecmath.translationMatrix(0, 0, -rowIndex * 20));

		for (Node child : getChildNodes()) {
			child.display(width, height, getTransformation());
		}
	}
}
