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

	private int rowIndex = 0;
	private int selectionIndex = 0;
	private final int ROW_DISTANCE = -15;
	private final int OBJ_DISTANCE = 5;
	private Vector oldEye;
	private Vector oldCenter;
	private boolean animation = false;
	private int animDirection;
	private float totalPan = -3f;
	private float totalTilt = 0;
	private boolean freeMode;
	private Vector target;

	public Camera(Matrix parentMatrix) {
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
		setTransformation(vecmath.lookatMatrix(eye, center, up));
	}

	@Override
	public void simulate(float elapsed, Input input) {
		if (animation) {
			Vector temp = vecmath.vector(target.x() - center.x(), target.y()
					- center.y(), target.z() - center.z());
			center = center.add(temp.normalize().mult(elapsed * 20));
			eye = center.sub(vecmath.vector(0, 0, -10));
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		setTransformation(vecmath.lookatMatrix(eye, center, up));
		// System.out.println(getTransformation());
		System.out.println("center: " + center);
		System.out.println("eye: " + eye);
		System.out.println("tilt: " + totalTilt + "   pan: " + totalPan);
	}

	@Override
	public void display(int width, int height) {
	}

	public void changeMode() {
		if (freeMode == false) {
			oldEye = eye;
			oldCenter = center;
			freeMode = true;
		} else {
			eye = oldEye;
			center = oldCenter;
			freeMode = false;
		}
	}

	public void animateMovement(Vector target) {
		this.target = target;
		if (!center.equals(target))
			animation = true;
		else
			animation = false;
	}

	public boolean isAnimationActive() {
		return animation;
	}

	public void moveOnZ(float moveSpeed) {
		Vector temp = vecmath.vector(center.x() - eye.x(),
				center.y() - eye.y(), center.z() - eye.z());
		center = center.add(temp.normalize().mult(moveSpeed));
		eye = eye.add(temp.normalize().mult(moveSpeed));
	}

	public void moveOnX(float moveSpeed) {
		Vector temp = vecmath.vector(center.x() - eye.x(),
				center.y() - eye.y(), center.z() - eye.z());
		Vector right = temp.cross(up);

		center = center.add(right.normalize().mult(moveSpeed));
		eye = eye.add(right.normalize().mult(moveSpeed));
	}

	public void rotateY(float turnSpeed) {
		totalPan += turnSpeed;

		updateOrientation();
	}

	public void rotateX(float turnSpeed) {
		totalTilt += turnSpeed;

		updateOrientation();
	}

	private void updateOrientation() {
		float afterTiltY = (float) Math.sin(totalTilt);
		float afterTiltZ = (float) Math.cos(totalTilt);

		float vecX = (float) Math.sin(totalPan) * afterTiltZ;
		float vecY = afterTiltY;
		float vecZ = (float) Math.cos(totalPan) * afterTiltZ;

		center = eye.add(vecmath.vector(vecX, vecY, vecZ));
	}

	private void animate(float elapsed, int direction) {
		// System.out.println("animating");
		float distance;
		switch (direction) {
		case 0: // Vorwärts
			distance = elapsed * 10;
			center = center.sub(vecmath.vector(0, 0, distance));
			System.out.println(center);
			eye = center.sub(vecmath.vector(0, 0, -10));
			if (center.z() < ROW_DISTANCE * rowIndex)
				animation = false;
			break;

		case 1: // Rückwärts
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
}
