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
	private boolean modeChanged;
	private Node selectedNode;

	private Vector horizontal;
	private Vector vertical;
	private Ray pickingRay;
	int viewportHeight = 600;
	int viewportWidth = 600;

	public InputManager(Camera cam, ArrayList<Node> children) {
		this.cam = cam;
		nodes = children;

		setSelection();

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
				// Mouse.setGrabbed(true);
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
			if (modeChanged == true) {
				cam.changeMode();
				modeChanged = false;
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
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				&& Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			if (keysUp.contains(Keyboard.KEY_S) == true
					&& keysUp.contains((Keyboard.KEY_LCONTROL))) {
				keysUp.remove(Keyboard.KEY_S);
				keysUp.remove(Keyboard.KEY_LCONTROL);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"MMM ddyyyy HHmmss");

				Date resultdate = new Date(System.currentTimeMillis());
				SceneLoader.saveScene("scene " + sdf.format(resultdate), nodes);
			}
		} else if (!keysUp.contains(Keyboard.KEY_S)
				&& !keysUp.contains(Keyboard.KEY_LCONTROL)) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_S)
					&& !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				keysUp.add(Keyboard.KEY_S);
				keysUp.add(Keyboard.KEY_LCONTROL);
			}
		}
		if (Mouse.isButtonDown(0)) {
			executeRayCalculation();
		}
		// Ray2 ray = new Ray2(cam.getEye(), cam.getLookDirection(),
		// Mouse.getX(),
		// viewportHeight - Mouse.getY());
		// Ray2 ray = new Ray2(cam, Mouse.getX(), Mouse.getY());
		// Vector position;
		// if (nodes.get(0).getChildNodes().get(0).getTransformation() != null)
		// {
		// for (Node child : nodes.get(rowIndex).getChildNodes()) {
		// position = child.getTransformation().getPosition();
		// Vector[] vertices = new Vector[4];
		// vertices[0] = position.sub(vecmath.vector(
		// child.getWidth() / 2 + 0.25f,
		// child.getHeight() / 2 + 0.25f, 0));
		// vertices[1] = position.sub(vecmath.vector(
		// child.getWidth() / 2 + 0.25f,
		// -child.getHeight() / 2 + 0.25f, 0));
		// vertices[2] = position.add(vecmath.vector(
		// child.getWidth() / 2 + 0.25f,
		// child.getHeight() / 2 + 0.25f, 0));
		// vertices[3] = position.add(vecmath.vector(
		// child.getWidth() / 2 + 0.25f,
		// -child.getHeight() / 2 + 0.25f, 0));
		// boolean inters = ray.intersects(vertices);
		// if (inters == true && !selectedNode.equals(child)) {
		// selectedNode.deselect();
		// selectedNode = child;
		// selectedNode.setSelected();
		// break;
		// }
		// }
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
		// Ray2 ray = new Ray2(cam.getEye(), cam.getLookDirection(),
		// Mouse.getX(), viewportHeight - Mouse.getY());
		Ray2 ray = new Ray2(cam, Mouse.getX(), Mouse.getY());
		// Ray ray = new Ray(cam, Mouse.getX(), viewportHeight - Mouse.getY());
		Vector position;
		if (nodes.get(0).getChildNodes().get(0).getTransformation() != null) {
			for (Node child : nodes.get(rowIndex).getChildNodes()) {
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
					selectedNode.deselect();
					selectedNode = child;
					selectedNode.setSelected();
					System.out.println("SELECTED");
					break;
				}
			}
		}
	}

	private void clickAction() {
		// look direction
		Vector viewDir = vecmath.vector(cam.getCenter().x() - cam.getEye().x(),
				cam.getCenter().y() - cam.getEye().y(), cam.getCenter().z()
						- cam.getEye().z());
		viewDir = viewDir.normalize();

		// screenX
		horizontal = viewDir.cross(cam.getUp()).normalize();

		// screenY
		vertical = horizontal.cross(viewDir).normalize();

		final float radians = (float) (60f * Math.PI / 180f);
		float halfHeight = (float) (Math.tan(radians / 2) * 0.1f);
		float halfScaledAspectRatio = halfHeight
				* (viewportWidth / viewportHeight);

		vertical = vertical.mult(halfHeight);
		horizontal = horizontal.mult(halfScaledAspectRatio);
		pickingRay = new Ray(cam, halfScaledAspectRatio, halfScaledAspectRatio);
		picking(Mouse.getX(), viewportHeight - Mouse.getY(), viewDir,
				pickingRay);
	}

	private void picking(float screenX, float screenY, Vector direction,
			Ray pickingRay) {

		pickingRay.setOrigin(cam.getEye());
		pickingRay.getOrigin().add(direction);

		screenX -= (float) viewportWidth / 2f;
		screenY -= (float) viewportHeight / 2f;

		// normalize to 1
		screenX /= ((float) viewportWidth / 2f);
		screenY /= ((float) viewportHeight / 2f);

		pickingRay.setOrigin(pickingRay.getOrigin().add(
				vecmath.vector(horizontal.x() * screenX + vertical.x()
						* screenY, 0, 0)));
		System.out.println(horizontal);
		System.out.println(vertical);
		System.out.println("PIEEP:   "
				+ (horizontal.y() * screenX + vertical.y() * screenY));
		pickingRay.setOrigin(pickingRay.getOrigin().add(
				vecmath.vector(0, horizontal.y() * screenX + vertical.y()
						* screenY, 0)));
		pickingRay.setOrigin(pickingRay.getOrigin().add(
				vecmath.vector(0, 0, horizontal.z() * screenX + vertical.z()
						* screenY)));

		pickingRay.setDirection(pickingRay.getOrigin().sub(cam.getEye()));
	}

	private void checkForIntersection(Ray ray) {
		Vector planeN = vecmath.vector(0, 0, 1);
		Vector planeDist = vecmath.vector(0, 0, 5);
		float distance = 5;

		System.out.println("origin " + ray.getOrigin());
		System.out.println("direction: " + ray.getDirection());
		System.out.println("eye " + cam.getEye());

		Vector top = ray.getOrigin().mult(planeN);
		float topF = top.x() + top.y() + top.z() + distance;
		Vector bot = ray.getDirection().mult(planeN);
		float botF = bot.x() + bot.y() + bot.z();
		Vector divided = vecmath.vector(top.x() / bot.x(), top.y() / bot.y(),
				top.z() / bot.z());

		System.out.println("top:" + top);

		float t = -(topF / botF);
		System.out.println(t);
		if (t <= 0)
			System.out.println("missed");
		else {
			System.out.println("hit");
			System.out.println(ray.getOrigin().add(ray.getDirection().mult(t)));
		}
	}
}
