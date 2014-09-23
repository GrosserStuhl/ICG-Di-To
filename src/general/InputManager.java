package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import ogl.app.Input;
import ogl.vecmath.Vector;
import util.SceneLoader;

public class InputManager {

	private Camera cam;
	private ArrayList<Node> nodes;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private int rowOneSelection = 0;
	private int rowTwoSelection = 0;
	private Set<Integer> keysUp = new HashSet<Integer>();
	private boolean firstRun = true;
	private Node selectedNode;

	public InputManager(Camera cam, ArrayList<Node> children) {
		this.cam = cam;
		nodes = children;

		setSelection();

		for (int i = 0; i <= 220; i++) {
			keysUp.add(i);
		}
	}

	public void update(float elapsed, Input input) {
		if (firstRun) {
			if (cam.isInFreeMode()) {
				input.setKeyToggeled(Keyboard.KEY_M);
				Mouse.setGrabbed(true);
			}
			firstRun = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}

		if (input.isKeyToggled(Keyboard.KEY_M) && !cam.isAnimationActive()) {

			if (!cam.isInFreeMode()) {
				cam.setFree();
				Mouse.setGrabbed(true);
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
			cam.rotateY((float) -Mouse.getDX() / 500);
			cam.rotateX((float) Mouse.getDY() / 500);
		} else if (!cam.isAnimationActive()) {
			if (cam.isInFreeMode()) {
				cam.setFixed();
				Mouse.setGrabbed(false);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				if (keysUp.contains(Keyboard.KEY_W) == true) {
					keysUp.remove(Keyboard.KEY_W);
					if (!selectedNode.getChildNodes().isEmpty()
							&& nodes.get(rowIndex + 1).getClass()
									.equals(RowNode.class)) {
						if (rowIndex == 0)
							rowOneSelection = selectionIndex;
						else if (rowIndex == 1)
							rowTwoSelection = selectionIndex;
						rowIndex++;
						selectionIndex = 0;
						nodes.get(rowIndex).getChildNodes().clear();
						for (Node child : selectedNode.getChildNodes())
							nodes.get(rowIndex).addNode(child);
						cam.moveRowForward(nodes.get(rowIndex)
								.getTransformation().getPosition());
						setSelection();
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_W)
					&& !Keyboard.isKeyDown(Keyboard.KEY_W))
				keysUp.add(Keyboard.KEY_W);
			if (input.isKeyDown(Keyboard.KEY_S)) {
				if (rowIndex > 0) {
					if (rowIndex == 2)
						selectionIndex = rowTwoSelection;
					else if (rowIndex == 1)
						selectionIndex = rowOneSelection;
					rowIndex--;
					nodes.get(rowIndex + 1).getChildNodes().clear();
					cam.moveRowBack(nodes.get(rowIndex).getTransformation()
							.getPosition());
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

			// Als erledigt markieren
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				if (keysUp.contains(Keyboard.KEY_RETURN) == true) {
					keysUp.remove(Keyboard.KEY_RETURN);
					if (!selectedNode.isCompleted()) {
						selectedNode.setCompleted();
						System.out.println("node " + selectedNode.getName()
								+ "  set completed");
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_RETURN)
					&& !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
				keysUp.add(Keyboard.KEY_RETURN);

			// Als unerledigt markieren
			if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				if (keysUp.contains(Keyboard.KEY_BACK) == true) {
					keysUp.remove(Keyboard.KEY_BACK);
					if (selectedNode.isCompleted()) {
						selectedNode.setUnCompleted();
						System.out.println("node " + selectedNode.getName()
								+ "  set uncompleted");
					}
				}
			} else if (!keysUp.contains(Keyboard.KEY_BACK)
					&& !Keyboard.isKeyDown(Keyboard.KEY_BACK))
				keysUp.add(Keyboard.KEY_BACK);

			if (Mouse.isButtonDown(0)) {
				executeRayCalculation();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				&& Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			if (keysUp.contains(Keyboard.KEY_S) == true
					&& keysUp.contains((Keyboard.KEY_LCONTROL))) {
				keysUp.remove(Keyboard.KEY_S);
				keysUp.remove(Keyboard.KEY_LCONTROL);
				SimpleDateFormat sdf = new SimpleDateFormat("MMM ddyyyy HHmmss");

				Date resultdate = new Date(System.currentTimeMillis());
				SceneLoader.saveScene("scene " + sdf.format(resultdate), nodes,
						cam);
			}
		} else if (!keysUp.contains(Keyboard.KEY_S)
				&& !keysUp.contains(Keyboard.KEY_LCONTROL)) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_S)
					&& !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				keysUp.add(Keyboard.KEY_S);
				keysUp.add(Keyboard.KEY_LCONTROL);
			}
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

	private void executeRayCalculation() {
		Ray2 ray = new Ray2(cam, Mouse.getX(), Mouse.getY());
		Vector position;
		if (nodes.get(0).getChildNodes().get(0).getTransformation() != null) {
			for (int i = 0; i < nodes.get(rowIndex).getChildNodes().size(); i++) {
				Node child = nodes.get(rowIndex).getChildNodes().get(i);
				position = child.getTransformation().getPosition();
				Vector[] vertices = new Vector[4];
				vertices[0] = position.sub(vecmath.vector(
						child.getWidth() / 2 + 0.25f,
						child.getHeight() / 2 + 0.25f, 0));
				vertices[1] = position.sub(vecmath.vector(
						child.getWidth() / 2 + 0.25f,
						-child.getHeight() / 2 + 0.25f, 0));
				vertices[2] = position.add(vecmath.vector(
						child.getWidth() / 2 + 0.25f,
						child.getHeight() / 2 + 0.25f, 0));
				vertices[3] = position.add(vecmath.vector(
						child.getWidth() / 2 + 0.25f,
						-child.getHeight() / 2 + 0.25f, 0));
				boolean inters = ray.intersects(vertices);
				if (inters == true && !selectedNode.equals(child)) {
					selectionIndex = i;
					setSelection();
					break;
				}
			}
		}
	}
}
