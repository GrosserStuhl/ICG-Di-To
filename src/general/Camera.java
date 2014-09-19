package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Camera extends Node {

	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	private float roll = 0f;// The rotation along the z axis
	private float pitch = 0f;// The rotation along the x axis
	private float yaw = 0f;// The rotation along the y axis
	private Vector eye, center, up;

	private Vector oldEye;
	private Vector oldCenter;
	private boolean animationFor = false;
	private boolean animationBack = false;
	private float totalPan = -3f;
	private float totalTilt = 0;
	private boolean freeMode;
	private float animationStartZ = 0;

	public Camera(Matrix parentMatrix) {
		setTransformation(parentMatrix.mult(vecmath.translationMatrix(3, 0, 5)));
	}

	@Override
	public void init() {
		center = getTransformation().getPosition();
		eye = center.add(vecmath.vector(0, 0, 10f));
		up = vecmath.vector(0f, 1f, 0f);
		setTransformation(vecmath.lookatMatrix(eye, center, up));
	}

	@Override
	public void simulate(float elapsed, Input input) {
		if (animationFor) {
			if (center.z() < animationStartZ - 20) {
				animationFor = false;
			} else {
				center = center.sub(vecmath.vector(0, 0, elapsed*20f));
				eye = center.add(vecmath.vector(0, 0, 10f));
			}
		} else if (animationBack) {
			if (center.z() > animationStartZ + 20) {
				animationBack = false;
			} else {
				center = center.add(vecmath.vector(0, 0, elapsed*20f));
				eye = center.add(vecmath.vector(0, 0, 10f));
			}
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		setTransformation(vecmath.lookatMatrix(eye, center, up));
		// System.out.println(getTransformation());
//		 System.out.println("center: " + center);
//		 System.out.println("eye: " + eye);
		// System.out.println("tilt: " + totalTilt + "   pan: " + totalPan);
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

	public boolean isAnimationActive() {
		if(animationFor || animationBack)
			return true;
		else return false;
	}

	public void moveOnZ(float moveSpeed) {
		Vector temp = getDirection();
		center = center.add(temp.normalize().mult(moveSpeed));
		eye = eye.add(temp.normalize().mult(moveSpeed));
	}

	public void moveOnX(float moveSpeed) {
		Vector temp = getDirection();
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

	public Vector getDirection() {
		Vector temp = vecmath.vector(center.x() - eye.x(),
				center.y() - eye.y(), center.z() - eye.z());
		return temp;
	}

	public void moveRowForward() {
		animationBack = false;
		animationFor = true;
		animationStartZ = center.z();
	}

	public void moveRowBack() {
		animationFor = false;
		animationBack = true;
		animationStartZ = center.z();
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
	
	public Vector getEye() {
		return eye;
	}
	
	public Vector getCenter() {
		return center;
	}
	
	public Vector getUp() {
		return up;
	}
}
