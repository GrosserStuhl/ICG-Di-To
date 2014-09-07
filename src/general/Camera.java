package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.util.ArrayList;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Camera extends Node {

	private float x = 0.1f;
	private float y = 0.1f;
	private float z = -10f;
	private float roll = 0f;// The rotation along the z axis
	private float pitch = 0f;// The rotation along the x axis
	private float yaw = 0f;// The rotation along the y axis
	private Vector eye, center, up;

	private Shader shader;
	private Matrix viewMatrix;
	private ArrayList<Node> rootChildren;
	private Input input;

	public Camera(ArrayList<Node> rootChildren, Shader shader) {
		this.shader = shader;
		this.rootChildren = rootChildren;
	}

	public void update() {
		Vector eyeAdd = vecmath.vector(0, 0, 0);
		Vector centerAdd = vecmath.vector(0, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}
		System.out.println(z);
		if (input.isKeyToggled(Keyboard.KEY_M)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				moveOnZ(0.02f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				moveOnZ(-0.02f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				moveOnX(-0.02f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				moveOnX(0.02f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				rotateY(0.2f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				rotateY(-0.2f);
			}

			updateCamera();
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				// eye = eye.add(vecmath.vector(0, 0, 0.02f));
				// center = center.add(vecmath.vector(0, 0, 0.02f));
				// Vector test = vecmath.vector(center.normalize().x() / 10,
				// center
				// .normalize().y() / 10, center.normalize().z() / 10);
				// eye = eye.add(test);
				// center = center.add(test);
				center = center.mult(0.002f);
				eye = eye.add(center);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				eye = eye.sub(vecmath.vector(0, 0, 0.02f));
				center = center.sub(vecmath.vector(0, 0, 0.02f));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				// eye = eye.add(vecmath.vector(0.002f, 0, 0));
				// center = center.add(vecmath.vector(0.002f, 0, 0));
				// eye = vecmath.vector(-2, 0, -10);
				// center = vecmath.vector(-2, 0, 0);
				viewMatrix = rootChildren.get(0).getTransformation();
				shader.getViewMatrixUniform().set(
						viewMatrix.mult(vecmath.translationMatrix(vecmath
								.vector(0, 0, -10))));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				// eye = eye.add(vecmath.vector(0.002f, 0, 0));
				// center = center.add(vecmath.vector(0.002f, 0, 0));
				eye = vecmath.vector(2, 0, -10);
				center = vecmath.vector(2, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				center = center.add(vecmath.vector(-0.02f, 0, 0));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				center = center.add(vecmath.vector(0.02f, 0, 0));
			}
			viewMatrix = vecmath.lookatMatrix(eye, center,
					vecmath.vector(0f, 1f, 0f));
			shader.getViewMatrixUniform().set(viewMatrix);
			// setView();
		}

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
		center = vecmath.vector(0f, 0f, 0.01f);
		up = vecmath.vector(0f, 1f, 0f);
		viewMatrix = vecmath.lookatMatrix(eye, center,
				vecmath.vector(0f, 1f, 0f));
		shader.getViewMatrixUniform().set(viewMatrix);
	}

	@Override
	public void simulate(float elapsed, Input input) {
		this.input = input;
	}

	@Override
	public void display(int width, int height) {
		update();
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
}
