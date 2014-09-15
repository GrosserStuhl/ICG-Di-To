package general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import ogl.app.Input;

public class InputManager {

	private Camera cam;
	private ArrayList<Node> nodes;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private Set<Integer> keysUp = new HashSet<Integer>();

	public InputManager(Camera cam, ArrayList<Node> children) {
		//nbnn
		this.cam = cam;
		nodes = children;

		for (int i = 0; i <= 220; i++) {
			keysUp.add(i);
		}
	}

	public void update(float elapsed, Input input) {
		if (input.isKeyDown(Keyboard.KEY_W)) {
			cam.moveForward(elapsed * 5);
		}
	}

	private void setSelection() {
		System.out.println("row: " + rowIndex);
		System.out.println("selection: " + selectionIndex);
		if (nodes.get(0).getChildNodes().get(0) == null)
			System.out.println("node at 0 is null");
		nodes.get(rowIndex).getChildNodes().get(selectionIndex).setSelected();
	}

	private boolean isKeyUp(int key) {
		return keysUp.contains(key);
	}
}
