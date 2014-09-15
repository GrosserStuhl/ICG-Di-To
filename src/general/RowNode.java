package general;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import static ogl.vecmathimp.FactoryDefault.vecmath;

public class RowNode extends Node {

	private int rowIndex;
	
	//ddd

	public RowNode(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void init() {

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		for (Node child : getChildNodes()) {
			child.simulate(elapsed, input);
		}
	}

	@Override
	public void display(int width, int height) {

	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		setTransformation(vecmath.translationMatrix(0, 0, -rowIndex*20));
		
		for (Node child : getChildNodes()) {
			child.display(width, height, getTransformation());
		}
	}
}
