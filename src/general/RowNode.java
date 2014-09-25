package general;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import static ogl.vecmathimp.FactoryDefault.vecmath;

public class RowNode extends Node {

	private int rowIndex;
	private float newNodePositionX = -5;
	private float newNodePositionY = 2;

	public RowNode(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void addNode(Node node) {
		if (newNodePositionX > 5 && newNodePositionX % 5 == 0) {
			newNodePositionY -= 4;
			newNodePositionX = -5;
		}
		node.setTranslation(vecmath.vector(newNodePositionX, newNodePositionY,
				0));
		newNodePositionX += 5;
		super.addNode(node);
	}

	@Override
	public void init() {

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		if (getChildNodes().isEmpty()) {
			newNodePositionX = -5;
			newNodePositionY = 2;
		}
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
