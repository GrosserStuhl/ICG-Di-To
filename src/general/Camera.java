package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.app.Input;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;

public class Camera extends Node {

	public Camera() {

	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			System.out.println("KEy W pressed");
			// Vector zoom = vertices[0].position.add(vec(0, 0, 8));
			// shader.getViewMatrixUniform().set(
			// viewMatrix.mult(vecmath.translationMatrix(zoom)));
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(int width, int height) {
		// TODO Auto-generated method stub
		
	}
}
