package general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import ogl.app.Input;

public class InputManager {

	private Camera cam;
	private ArrayList<Node> nodes;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private int previousSelIndex = 0;
	private Set<Integer> keysUp = new HashSet<Integer>();
	private boolean modeChanged;
	private Node selectedNode;

	public InputManager(Camera cam, ArrayList<Node> children) {
		this.cam = cam;
		nodes = children;

		setSelection();
		System.out.println("WTF");

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
					if (!selectedNode.getChildNodes().isEmpty()) {
						rowIndex++;
						previousSelIndex = selectionIndex;
						selectionIndex = 0;
						nodes.get(rowIndex).getChildNodes().clear();
						for (Node child : selectedNode.getChildNodes())
							nodes.get(rowIndex).addNode(child);
						cam.moveRowForward();
						setSelection();
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_W)
					&& !Keyboard.isKeyDown(Keyboard.KEY_W))
				keysUp.add(Keyboard.KEY_W);
			if (input.isKeyDown(Keyboard.KEY_S)) {
				if (rowIndex>0) {
					rowIndex--;
					selectionIndex = previousSelIndex;
					nodes.get(rowIndex+1).getChildNodes().clear();
					cam.moveRowBack();
					setSelection();
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				if (keysUp.contains(Keyboard.KEY_D) == true) {
					keysUp.remove(Keyboard.KEY_D);
					if (selectionIndex < nodes.get(rowIndex).getChildNodes()
							.size() - 1) {
						selectionIndex++;
						setSelection();
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_D)
					&& !Keyboard.isKeyDown(Keyboard.KEY_D))
				keysUp.add(Keyboard.KEY_D);
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (keysUp.contains(Keyboard.KEY_A) == true) {
					keysUp.remove(Keyboard.KEY_A);
					if (selectionIndex > 0) {
						selectionIndex--;
						setSelection();
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_A)
					&& !Keyboard.isKeyDown(Keyboard.KEY_A))
				keysUp.add(Keyboard.KEY_A);
		}
	}

	private void setSelection() {
		if (selectedNode != null)
			selectedNode.deselect();
		System.out.println("row: " + rowIndex);
		System.out.println("selection: " + selectionIndex);
		selectedNode = nodes.get(rowIndex).getChildNodes().get(selectionIndex);
		selectedNode.setSelected();
	}
}
