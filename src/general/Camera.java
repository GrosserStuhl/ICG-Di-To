package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;

public class Camera extends Node {

	private float x = 0.1f;
	private float y = 0.1f;
	private float z = -10f;
	private float roll = 0f;// The rotation along the z axis
	private float pitch = 0f;// The rotation along the x axis
	private float yaw = 0f;// The rotation along the y axis
	private Vector eye, center;

	private Shader shader;
	private Matrix viewMatrix;

	public Camera(Shader shader) {
		this.shader = shader;
	}

	public void update() {
		Vector eyeAdd = vecmath.vector(0, 0, 0);
		Vector centerAdd = vecmath.vector(0, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			eyeAdd = vecmath.vector(center.x() / 10, center.y() / 10,
					center.z() / 10);
			centerAdd = vecmath.vector(0, 0, 0.0000002f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			z -= 0.002f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x -= 0.002f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			pitch += 0.02f;
		}

		eye = eye.add(eyeAdd);
		center = vecmath.vector((float) x * pitch, (float) y * yaw, (float) z
				* roll);
		center = center.add(centerAdd);

		System.out.println("E: " + eye.toString());
		System.out.println("C: " + center.toString());

		// updateCamera();
		viewMatrix = vecmath.lookatMatrix(eye, center,
				vecmath.vector(0f, 1f, 0f));
		shader.getViewMatrixUniform().set(viewMatrix);
		// viewMatrix = lookAtMatrix(vecmath.vector(x, y, z), vecmath.vector(0f,
		// 0f, 0f), vecmath.vector(0f, 1f, 0f));
		// shader.getViewMatrixUniform().set(viewMatrix);
	}

	@Override
	public void init() {
		eye = vecmath.vector(x, y, z);
		center = vecmath.vector(0f, 0f, 0f);
		viewMatrix = vecmath.lookatMatrix(eye, center,
				vecmath.vector(0f, 1f, 0f));
		shader.getViewMatrixUniform().set(viewMatrix);
	}

	@Override
	public void simulate(float elapsed, Input input) {

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
}
