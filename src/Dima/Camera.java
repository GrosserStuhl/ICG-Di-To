package Dima;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

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
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT1);

		float orgParamsAmb[] = { 0.5f, 0.5f, 0.5f, 1.0f };
		FloatBuffer paramsAmb = BufferUtils
				.createFloatBuffer(orgParamsAmb.length);
		paramsAmb.put(orgParamsAmb);
		paramsAmb.flip();
		glLight(GL_LIGHT1, GL_AMBIENT, paramsAmb);
		float orgParamsDif[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		FloatBuffer paramsDif = BufferUtils
				.createFloatBuffer(orgParamsDif.length);
		paramsDif.put(orgParamsDif);
		paramsDif.flip();
		glLight(GL_LIGHT1, GL_DIFFUSE, paramsDif);
		float orgParamsPos[] = { 0.0f, 0.0f, 2.0f, 1.0f };
		FloatBuffer paramsPos = BufferUtils
				.createFloatBuffer(orgParamsPos.length);
		paramsPos.put(orgParamsPos);
		paramsPos.flip();
		glLight(GL_LIGHT1, GL_POSITION, paramsPos);

		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);

		// glFrontFace(GL_CW);
		// glCullFace(GL_BACK);
		// glEnable(GL_CULL_FACE);

//		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//		glEnable(GL_BLEND);
//		glDisable(GL_DEPTH_TEST);
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
