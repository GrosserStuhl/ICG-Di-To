package general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import ogl.app.Input;
import ogl.vecmath.Vector;

public class InputManager {

	private Camera cam;
	private ArrayList<Node> nodes;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private Set<Integer> keysUp = new HashSet<Integer>();
	private boolean modeChanged;

	public InputManager(Camera cam, ArrayList<Node> children) {
		// nbnn
		this.cam = cam;
		nodes = children;
		

		for (int i = 0; i <= 220; i++) {
			keysUp.add(i);
		}
	}

	public void update(float elapsed, Input input) {

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}

		if (input.isKeyToggled(Keyboard.KEY_M) && !cam.isAnimationActive()) {

			if (modeChanged == false) {
				cam.changeMode();
				modeChanged = true;
			}

			float moveSpeed = elapsed * 10;
			float turnSpeed = elapsed * 1.5f;
			if (input.isKeyDown(Keyboard.KEY_W)) {
				cam.moveOnZ(moveSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_S)) {
				cam.moveOnZ(-moveSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_D)) {
				cam.moveOnX(moveSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_A)) {
				cam.moveOnX(-moveSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_E)) {
				cam.rotateY(-turnSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_Q)) {
				cam.rotateY(turnSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_UP)) {
				cam.rotateX(turnSpeed);
			}
			if (input.isKeyDown(Keyboard.KEY_DOWN)) {
				cam.rotateX(-turnSpeed);
			}
		} else {
			if (modeChanged == true) {
				cam.changeMode();
				modeChanged = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				if (keysUp.contains(Keyboard.KEY_W) == true) {
					keysUp.remove(Keyboard.KEY_W);

					Vector target = nodes.get(1).getChildNodes().get(0)
							.getChildNodes().get(0).getTransformation()
							.getPosition();
					cam.animateMovement(target);
				}
			} else if (!keysUp.contains(Keyboard.KEY_W)
					&& !Keyboard.isKeyDown(Keyboard.KEY_W))
				keysUp.add(Keyboard.KEY_W);
			if (input.isKeyDown(Keyboard.KEY_S)) {
			}
			if (input.isKeyDown(Keyboard.KEY_D)) {
			}
			if (input.isKeyDown(Keyboard.KEY_A)) {
			}
		}
	}

	private void setSelection() {
		System.out.println("row: " + rowIndex);
		System.out.println("selection: " + selectionIndex);
		if (nodes.get(0).getChildNodes().get(0) == null)
			System.out.println("node at 0 is null");
		nodes.get(rowIndex).getChildNodes().get(selectionIndex).setSelected();
	}
}
