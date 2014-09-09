package general;

import ogl.app.Input;

public class RowNode extends Node {


//KOmmentar für TOM
	public RowNode() {

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
		for (Node child : getChildNodes()) {
			child.display(width, height);
		}
	}

}
