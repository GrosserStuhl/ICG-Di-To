package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Camera extends Node {

	private Vector eye, center, up;
	private Vector oldEye;
	private Vector oldCenter;
	private boolean animationFor = false;
	private boolean animationBack = false;
	private float totalPan = -3f;
	private float totalTilt = 0;
	private boolean freeMode;
	private Vector targetPos;

	public Camera(Matrix parentMatrix) {
		setTransformation(parentMatrix.mult(vecmath.translationMatrix(3, 0, 5)));
	}

	public Camera(Vector eye, Vector center, boolean freeMode) {
		this.eye = eye;
		this.center = center;
		this.freeMode = freeMode;
	}

	@Override
	public void init() {
		if (eye == null && center == null) {
			center = getTransformation().getPosition();
			eye = center.add(vecmath.vector(0, 0, 10f));
		}
		up = vecmath.vector(0f, 1f, 0f);
		oldCenter = vecmath.vector(0, 0, 5f);
		oldEye = oldCenter.add(vecmath.vector(0, 0, 10f));
		
		setTransformation(vecmath.lookatMatrix(eye, center, up));
	}

	@Override
	public void simulate(float elapsed, Input input) {
		if (animationFor) {
			if (center.z() < targetPos.z()) {
				animationFor = false;
			} else {
				center = center.sub(vecmath.vector(0, 0, elapsed * 20f));
				eye = center.add(vecmath.vector(0, 0, 10f));
			}
		} else if (animationBack) {
			if (center.z() > targetPos.z()) {
				animationBack = false;
			} else {
				center = center.add(vecmath.vector(0, 0, elapsed * 20f));
				eye = center.add(vecmath.vector(0, 0, 10f));
			}
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		setTransformation(vecmath.lookatMatrix(eye, center, up));
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
			totalPan = -3f;
			totalTilt = 0;
			freeMode = false;
		}
	}

	public void setFree() {
		oldEye = eye;
		oldCenter = center;
		freeMode = true;
		System.out.println("free set");
	}
	
	public void setFixed() {
		eye = oldEye;
		center = oldCenter;
		totalPan = -3f;
		totalTilt = 0;
		freeMode = false;
		System.out.println("fixed set");
	}
	
	public boolean isAnimationActive() {
		if (animationFor || animationBack)
			return true;
		else
			return false;
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

	public void moveRowForward(Vector pos) {
		animationBack = false;
		animationFor = true;
		targetPos = pos.add(vecmath.vector(0, 0, 4.5f));
	}

	public void moveRowBack(Vector pos) {
		animationFor = false;
		animationBack = true;
		targetPos = pos.add(vecmath.vector(0, 0, 4.5f));
	}
	
	public boolean isInFreeMode() {
		return freeMode;
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
