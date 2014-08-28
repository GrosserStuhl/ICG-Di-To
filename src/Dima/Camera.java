package Dima;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera {
	private float x, y, z;
	private float rx, ry, rz; // Rotation

	private float fov; // Field of view
	private float aspect; // aspect ratio (Verhätnis Breite-Höhe)
	private float zNear;
	private float zFar;

	public Camera(float fov, float aspect, float zNear, float zFar) {
		x = 0;
		y = 0;
		z = 0;
		rx = 0;
		ry = 0;
		rz = 0;

		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;

		initProjection();
	}

	private void initProjection() {

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, aspect, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
	}

	public void useView() {
		glRotatef(rx, 1, 0, 0);
		glRotatef(ry, 0, 1, 0);
		glRotatef(rz, 0, 0, 1);

		glTranslatef(x, y, z);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public void move(float amt, float dir) {
		z += amt * Math.sin(Math.toRadians(ry + 90 * dir));
		x += amt * Math.cos(Math.toRadians(ry + 90 * dir));
	}

	public void moveOnZ(float amt) {
		z += amt * Math.sin(Math.toRadians(ry + 90));
		x += amt * Math.cos(Math.toRadians(ry + 90));
	}

	public void moveOnX(float amt) {
		z += amt * Math.sin(Math.toRadians(ry));
		x += amt * Math.cos(Math.toRadians(ry));

	}
	
	public void moveOnY(float amt) {
		y += amt;
	}

	public void rotateY(float amt) {
		ry += amt;
	}

	public void rotateX(float amt) {
		rx += amt;
	}
}
