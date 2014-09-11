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
	private float z = 10f;
	private float roll = 0f;// The rotation along the z axis
	private float pitch = 0f;// The rotation along the x axis
	private float yaw = 0f;// The rotation along the y axis
	private Vector eye, center, up;

	private Shader shader;
	private Matrix viewMatrix;
	private ArrayList<Node> rootChildren;
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private final int ROW_DISTANCE = -15;
	private final int OBJ_DISTANCE = 5;
	private Vector oldEye;
	private Vector oldCenter;
	private Set<Integer> keysUp = new HashSet<Integer>();
	private boolean animation = false;

	public Camera(ArrayList<Node> rootChildren, Shader shader) {
		this.shader = shader;
		this.rootChildren = rootChildren;
	}

	public void update() {
		// Vector eyeAdd = vecmath.vector(0, 0, 0);
		// Vector centerAdd = vecmath.vector(0, 0, 0);
		// eye = eye.add(eyeAdd);
		// center = vecmath.vector((float) x * pitch, (float) y * yaw, (float) z
		// * roll);
		// center = center.add(centerAdd);

		// updateCamera();
		// viewMatrix = vecmath.lookatMatrix(eye, center,
		// vecmath.vector(0f, 1f, 0f));
		// shader.getViewMatrixUniform().set(viewMatrix);
		// viewMatrix = lookAtMatrix(vecmath.vector(x, y, z), vecmath.vector(0f,
		// 0f, 0f), vecmath.vector(0f, 1f, 0f));
		// shader.getViewMatrixUniform().set(viewMatrix);
	}

	@Override
	public void init() {
		eye = vecmath.vector(x, y, z);
		center = vecmath.vector(0f, 0f, 0.1f);
		up = vecmath.vector(0f, 1f, 0f);
		viewMatrix = vecmath.lookatMatrix(eye, center,
				vecmath.vector(0f, 1f, 0f));
		shader.getViewMatrixUniform().set(viewMatrix);

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
				float moveSpeed = elapsed * 10;
				float turnSpeed = elapsed * 15;
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					// moveOnZ(0.02f);
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());
					center = center.add(temp.normalize().mult(moveSpeed));
					eye = eye.add(temp.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					// moveOnZ(-0.02f);
					Vector temp = vecmath.vector(eye.x() - center.x(), eye.y()
							- center.y(), eye.z() - center.z());
					center = center.add(temp.normalize().mult(moveSpeed));
					eye = eye.add(temp.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					// moveOnX(-0.02f);

					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());

					float tempRightX = (temp.x() * (float) Math.cos(90))
							+ (temp.z() * (float) -Math.sin(90));
					float tempRightY = temp.y();
					float tempRightZ = (temp.x() * (float) Math.sin(90))
							+ (temp.z() * (float) Math.cos(90));

					Vector tempRight = vecmath.vector(tempRightX, tempRightY,
							tempRightZ);

					center = center.add(tempRight.normalize().mult(moveSpeed));
					eye = eye.add(tempRight.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					// moveOnX(0.02f);
					Vector temp = vecmath.vector(center.x() - eye.x(),
							center.y() - eye.y(), center.z() - eye.z());

					float tempRightX = (temp.x() * (float) Math.cos(90))
							+ (temp.z() * (float) -Math.sin(90));
					float tempRightY = temp.y();
					float tempRightZ = (temp.x() * (float) Math.sin(90))
							+ (temp.z() * (float) Math.cos(90));

					Vector tempRight = vecmath.vector(tempRightX, tempRightY,
							tempRightZ);

					center = center.sub(tempRight.normalize().mult(moveSpeed));
					eye = eye.sub(tempRight.normalize().mult(moveSpeed));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
					// rotateY(0.2f);
					center = center.add(vecmath.vector(turnSpeed, 0, 0));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					center = center.add(vecmath.vector(-turnSpeed, 0, 0));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					center = center.add(vecmath.vector(0, turnSpeed, 0));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					center = center.add(vecmath.vector(0, -turnSpeed, 0));
				}

				// updateCamera();
			} else {
				if (oldEye != null) {
					eye = oldEye;
					center = oldCenter;
				}
				oldEye = null;
				oldCenter = null;

				if (input.isKeyDown(Keyboard.KEY_W)) {
					if (isKeyUp(Keyboard.KEY_W) == true) {
						keysUp.remove(Keyboard.KEY_W);

						 if (rowIndex < 2) {
						 rowIndex++;
						
						 // Bewege eine Reihe hinter
//						 center = center.add(vecmath.vector(0, 0,
//						 ROW_DISTANCE));
//						 eye = center.sub(vecmath.vector(0, 0, -10));
						 animation = true;
						 setSelection();
						 }
					}
				} else if (!keysUp.contains(Keyboard.KEY_W))
					keysUp.add(Keyboard.KEY_W);
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					if (isKeyUp(Keyboard.KEY_S) == true) {
						keysUp.remove(Keyboard.KEY_S);

						if (rowIndex > 0) {
							rowIndex--;
							// Bewege eine Reihe hinter
							center = center.sub(vecmath.vector(0, 0,
									ROW_DISTANCE));
							eye = center.sub(vecmath.vector(0, 0, -10));
							setSelection();
						}
					}
				} else if (!keysUp.contains(Keyboard.KEY_S))
					keysUp.add(Keyboard.KEY_S);
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					if (isKeyUp(Keyboard.KEY_D) == true) {
						keysUp.remove(Keyboard.KEY_D);

						if (selectionIndex < 2) {
							selectionIndex++;

							// Bewege eine Position nach rechts
							center = center.add(vecmath.vector(OBJ_DISTANCE, 0,
									0));
							eye = center.sub(vecmath.vector(0, 0, -10));
							setSelection();
						}
					}
				} else if (!keysUp.contains(Keyboard.KEY_D))
					keysUp.add(Keyboard.KEY_D);
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					if (isKeyUp(Keyboard.KEY_A) == true) {
						keysUp.remove(Keyboard.KEY_A);

						if (selectionIndex > 0) {
							selectionIndex--;

							// Bewege eine Position nach links
							center = center.sub(vecmath.vector(OBJ_DISTANCE, 0,
									0));
							eye = center.sub(vecmath.vector(0, 0, -10));
							setSelection();
						}
					}
				} else if (!keysUp.contains(Keyboard.KEY_A))
					keysUp.add(Keyboard.KEY_A);
				if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
					center = center.add(vecmath.vector(-0.02f, 0, 0));
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					center = center.add(vecmath.vector(0.02f, 0, 0));
				}
				// setView();
			}
		} else {
			float distance = elapsed * 10;
			center = center.sub(vecmath.vector(0, 0, distance));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.z() < -20)
				animation = false;
			if (animation == true)
				System.out.println("animating");
		}
	}

	@Override
	public void display(int width, int height) {
		viewMatrix = vecmath.lookatMatrix(eye, center,
				vecmath.vector(0f, 1f, 0f));
		shader.getViewMatrixUniform().set(viewMatrix);
	}

	private void setSelection() {
		System.out.println("row: " + rowIndex);
		System.out.println("selection: " + selectionIndex);
		if (rootChildren.get(0).getChildNodes().get(0) == null)
			System.out.println("rootchild at 0 is null");
		rootChildren.get(rowIndex).getChildNodes().get(selectionIndex)
				.setSelected();
	}

	private Matrix lookAtMatrix(Vector eye, Vector target, Vector up) {
		Vector zaxis = eye.sub(target);
		zaxis = zaxis.normalize();
		Vector xaxis = up.cross(zaxis);
		xaxis = xaxis.normalize();
		Vector yaxis = zaxis.cross(xaxis);

		Matrix orientation = vecmath.matrix(xaxis.asArray()[0],
				yaxis.asArray()[0], zaxis.asArray()[0], 0, xaxis.asArray()[1],
				yaxis.asArray()[1], zaxis.asArray()[1], 0, xaxis.asArray()[2],
				yaxis.asArray()[2], zaxis.asArray()[2], 0, 0, 0, 0, 1);

		Matrix translation = vecmath.matrix(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0,
				-eye.asArray()[0], -eye.asArray()[1], -eye.asArray()[2], 1);

		return orientation.mult(translation);
	}

	private void updateCamera() {
		viewMatrix = vecmath.rotationMatrix(vecmath.vector(1, 0, 0), pitch);
		viewMatrix = vecmath.rotationMatrix(vecmath.vector(0, 1, 0), yaw);
		viewMatrix = vecmath.rotationMatrix(vecmath.vector(0, 0, 1), roll);
		viewMatrix = vecmath.translationMatrix(vecmath.vector(x, y, z));
		shader.getViewMatrixUniform().set(viewMatrix);
	}

	private void setView() {
		viewMatrix = vecmath.lookatMatrix(
				vecmath.vector(eye.x(), eye.y(), eye.z()),
				vecmath.vector(eye.x() + center.x(), eye.y() + center.y(),
						eye.z() + center.z()),
				vecmath.vector(up.x(), up.y(), up.z()));
		shader.getViewMatrixUniform().set(viewMatrix);
	}

	private void moveOnZ(float amt) {
		z += amt * Math.sin(Math.toRadians(yaw + 90));
		x += amt * Math.cos(Math.toRadians(yaw + 90));
	}

	private void moveOnX(float amt) {
		z += amt * Math.sin(Math.toRadians(yaw));
		x += amt * Math.cos(Math.toRadians(yaw));

	}

	public void rotateY(float amt) {
		yaw += amt;
	}

	public void rotateX(float amt) {
		pitch += amt;
	}

	private boolean isKeyUp(int key) {
		return keysUp.contains(key);
	}
}
