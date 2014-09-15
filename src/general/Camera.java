package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Camera extends Node {

	private float x = 0f;
	private float y = 0f;
	private float z = -10f;
	private float roll = 0f;// The rotation along the z axis
	private float pitch = 0f;// The rotation along the x axis
	private float yaw = 0f;// The rotation along the y axis
	private Vector eye, center, up;

	private ArrayList<Node> rootChildren;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private final int ROW_DISTANCE = -15;
	private final int OBJ_DISTANCE = 5;
	private Vector oldEye;
	private Vector oldCenter;
	private Set<Integer> keysUp = new HashSet<Integer>();
	private boolean animation = false;
	private int animDirection;

	public Camera(ArrayList<Node> rootChildren, Matrix parentMatrix) {
		this.rootChildren = rootChildren;
		setTransformation(parentMatrix.mult(vecmath
				.translationMatrix(0, 0, -20)));
	}

	// (i*m1*m2)^-1
	// ====================
	// disp() {
	// viewM = root.findCam();
	// setVM(viewM)
	// root.display(transMatrix) }

	@Override
	public void init() {
		eye = vecmath.vector(x, y, z);
		center = vecmath.vector(0f, 0f, 0f);
		up = vecmath.vector(0f, 1f, 0f);
		// setTransformation(vecmath.lookatMatrix(eye, center, up));

		setSelection();

		for (int i = 0; i <= 220; i++) {
			keysUp.add(i);
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// System.out.println(eye);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}
		if (animation == false) {
			if (input.isKeyToggled(Keyboard.KEY_M)) {
				if (oldEye == null) {
					oldEye = eye;
					oldCenter = center;
				}
				float moveSpeed = elapsed*10;
				float turnSpeed = elapsed*5;
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					// moveOnZ(moveSpeed);
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());
					center = center.add(temp.normalize().mult(moveSpeed));
					eye = eye.add(temp.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					// moveOnZ(-moveSpeed);
					Vector temp = vecmath.vector(eye.x() - center.x(), eye.y()
							- center.y(), eye.z() - center.z());
					center = center.add(temp.normalize().mult(moveSpeed));
					eye = eye.add(temp.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					// moveOnX(-moveSpeed);
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());
					Vector right = temp.cross(up);

					center = center.add(right.normalize().mult(moveSpeed));
					eye = eye.add(right.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					// moveOnX(moveSpeed);
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());

					Vector right = temp.cross(up);

					center = center.sub(right.normalize().mult(moveSpeed));
					eye = eye.sub(right.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());

					float vecX = (temp.x() * (float) Math.cos(-turnSpeed))
							+ (temp.z() * (float) Math.sin(-turnSpeed));
					float vecY = temp.y();
					float vecZ = (temp.x() * (float) -Math.sin(-turnSpeed))
							+ (temp.z() * (float) Math.cos(-turnSpeed));

					center = vecmath.vector(vecX, vecY, vecZ);
					// rotateY(turnSpeed);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());

					float vecX = (temp.x() * (float) Math.cos(turnSpeed))
							+ (temp.z() * (float) Math.sin(turnSpeed));
					float vecY = temp.y();
					float vecZ = (temp.x() * (float) -Math.sin(turnSpeed))
							+ (temp.z() * (float) Math.cos(turnSpeed));

					center = vecmath.vector(vecX, vecY, vecZ);
					// rotateY(-turnSpeed);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					// Vector temp = vecmath.vector(center.x() - eye.x(),
					// center.y() - eye.y(), center.z() - eye.z());
					//
					// float vecX = temp.x();
					// float vecY = (temp.y() * (float) Math.cos(turnSpeed))
					// + (temp.z() * (float) -Math.sin(turnSpeed));
					// float vecZ = (temp.y() * (float) Math.sin(turnSpeed))
					// + (temp.z() * (float) Math.cos(turnSpeed));
					//
					// // center = vecmath.vector(vecX, vecY, vecZ);
					// center = center.add(vecmath.vector(0, moveSpeed, 0));
					// eye =eye.add(vecmath.vector(0, moveSpeed, 0));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					// // center = center.add(vecmath.vector(0, -turnSpeed, 0));
					// Vector temp = vecmath.vector(center.x() - eye.x(),
					// center.y() - eye.y(), center.z() - eye.z());
					//
					// float vecX = temp.x();
					// float vecY = (temp.y() * (float) Math.cos(-turnSpeed))
					// + (temp.z() * (float) -Math.sin(-turnSpeed));
					// float vecZ = (temp.y() * (float) Math.sin(-turnSpeed))
					// + (temp.z() * (float) Math.cos(-turnSpeed));
					//
					// // center = center.add(vecmath.vector(vecX, vecY, vecZ));
					// center = vecmath.vector(vecX, vecY, vecZ);
				}
			} else {
				if (oldEye != null) {
					eye = oldEye;
					center = oldCenter;
				}
				oldEye = null;
				oldCenter = null;

				if (input.isKeyDown(Keyboard.KEY_W)) {
					// if (isKeyUp(Keyboard.KEY_W) == true) {
					// keysUp.remove(Keyboard.KEY_W);
					//
					// if (animation == false) {
					// if (rowIndex < 2) {
					// rowIndex++;
					//
					// // Bewege eine Reihe hinter
					// // center = center.add(vecmath.vector(0, 0,
					// // ROW_DISTANCE));
					// // eye = center.sub(vecmath.vector(0, 0, -10));
					// animation = true;
					// animDirection = 0;
					// setSelection();
					// }
					// }
					// }
					z += (float) ((elapsed / 10) * Math.sin(Math
							.toRadians(yaw + 90)));
					x += (float) ((elapsed / 10) * Math.cos(Math
							.toRadians(yaw + 90)));
					setTransformation((vecmath.translationMatrix(x, 0, z)
							.mult(getTransformation())));
				} else if (!keysUp.contains(Keyboard.KEY_W))
					keysUp.add(Keyboard.KEY_W);
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					// if (isKeyUp(Keyboard.KEY_S) == true) {
					// keysUp.remove(Keyboard.KEY_S);
					//
					// if (animation == false) {
					// if (rowIndex > 0) {
					// rowIndex--;
					// // // Bewege eine Reihe hinter
					// // center = center.sub(vecmath.vector(0, 0,
					// // ROW_DISTANCE));
					// // eye = center.sub(vecmath.vector(0, 0, -10));
					// animation = true;
					// animDirection = 1;
					// setSelection();
					// }
					// }
					// }
					z -= (float) ((elapsed / 10) * Math.sin(Math
							.toRadians(yaw + 90)));
					x -= (float) ((elapsed / 10) * Math.cos(Math
							.toRadians(yaw + 90)));
					setTransformation((vecmath.translationMatrix(x, 0, z)
							.mult(getTransformation())));
				} else if (!keysUp.contains(Keyboard.KEY_S))
					keysUp.add(Keyboard.KEY_S);
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					if (isKeyUp(Keyboard.KEY_D) == true) {
						keysUp.remove(Keyboard.KEY_D);

						if (animation == false) {
							if (selectionIndex < 2) {
								selectionIndex++;

								// // Bewege eine Position nach rechts
								// center =
								// center.add(vecmath.vector(OBJ_DISTANCE, 0,
								// 0));
								// eye = center.sub(vecmath.vector(0, 0, -10));
								animation = true;
								animDirection = 2;
								setSelection();
							}
						}
					}
				} else if (!keysUp.contains(Keyboard.KEY_D))
					keysUp.add(Keyboard.KEY_D);
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					if (isKeyUp(Keyboard.KEY_A) == true) {
						keysUp.remove(Keyboard.KEY_A);

						if (animation == false) {
							if (selectionIndex > 0) {
								selectionIndex--;

								// // Bewege eine Position nach links
								// center =
								// center.sub(vecmath.vector(OBJ_DISTANCE, 0,
								// 0));
								// eye = center.sub(vecmath.vector(0, 0, -10));
								animation = true;
								animDirection = 3;
								setSelection();
							}
						}
					}
				} else if (!keysUp.contains(Keyboard.KEY_A))
					keysUp.add(Keyboard.KEY_A);
				if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
					// center = center.add(vecmath.vector(-0.02f, 0, 0));
					Matrix trans = getTransformation().getTranslation();
					setTransformation(getTransformation().mult(
							trans.invertRigid()));
					yaw += elapsed;
					setTransformation(vecmath.rotationMatrix(0, 1, 0, yaw)
							.mult(getTransformation()));
					setTransformation(getTransformation().mult(trans));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					// center = center.add(vecmath.vector(0.02f, 0, 0));
					Matrix trans = getTransformation().getTranslation();
					setTransformation(getTransformation().mult(
							trans.invertRigid()));
					yaw -= elapsed;
					setTransformation(vecmath.rotationMatrix(0, 1, 0, yaw)
							.mult(getTransformation()));
					setTransformation(getTransformation().mult(trans));
				}
			}
		} else {
			// if (animation == true)
			// animate(elapsed, animDirection);
		}
	}

	private void setSelection() {
		System.out.println("row: " + rowIndex);
		System.out.println("selection: " + selectionIndex);
		if (rootChildren.get(0).getChildNodes().get(0) == null)
			System.out.println("rootchild at 0 is null");
		rootChildren.get(rowIndex).getChildNodes().get(selectionIndex)
				.setSelected();
	}

	private void animate(float elapsed, int direction) {
		// System.out.println("animating");
		float distance;
		switch (direction) {
		case 0: // Vorw�rts
			distance = elapsed * 10;
			center = center.sub(vecmath.vector(0, 0, distance));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.z() < ROW_DISTANCE * rowIndex)
				animation = false;
			break;

		case 1: // R�ckw�rts
			distance = elapsed * 10;
			center = center.add(vecmath.vector(0, 0, distance));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.z() > ROW_DISTANCE * rowIndex)
				animation = false;
			break;

		case 2: // Rechts
			distance = elapsed * 5;
			center = center.add(vecmath.vector(distance, 0, 0));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.x() > OBJ_DISTANCE * selectionIndex)
				animation = false;
			break;

		default: // Links
			distance = elapsed * 5;
			center = center.sub(vecmath.vector(distance, 0, 0));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.x() < OBJ_DISTANCE * selectionIndex)
				animation = false;
			break;
		}
	}

	private void updateCamera() {
		setTransformation(getTransformation().mult(
				vecmath.rotationMatrix(vecmath.vector(1, 0, 0), pitch)));
		setTransformation(getTransformation().mult(
				vecmath.rotationMatrix(vecmath.vector(0, 1, 0), yaw)));
		setTransformation(getTransformation().mult(
				vecmath.rotationMatrix(vecmath.vector(0, 0, 1), roll)));
		setTransformation(getTransformation().mult(
				vecmath.translationMatrix(vecmath.vector(x, y, z))));
	}

	private void moveOnZ(float amt) {
		z += amt * Math.sin(Math.toRadians(yaw + 90));
		x += amt * Math.cos(Math.toRadians(yaw + 90));
		updateCamera();
	}

	private void moveOnX(float amt) {
		z += amt * Math.sin(Math.toRadians(yaw));
		x += amt * Math.cos(Math.toRadians(yaw));
		updateCamera();
	}

	public void rotateY(float amt) {
		yaw += amt;
		updateCamera();
	}

	public void rotateX(float amt) {
		pitch += amt;
		updateCamera();
	}

	private boolean isKeyUp(int key) {
		return keysUp.contains(key);
	}

	public void moveForward(float amt) {

	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		 setTransformation(vecmath.lookatMatrix(eye, center, up));
//		System.out.println(getTransformation());
		 System.out.println("center: " + center);
		 System.out.println("eye: " + eye);
		// updateCamera();
	}

	@Override
	public void display(int width, int height) {
	}
}
