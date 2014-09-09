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
	private int rowIndex = 0;
	private int selectionIndex = 0;
	private final int ROW_DISTANCE = 10;
	private final int OBJ_DISTANCE = 5;

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
		if (input.isKeyToggled(Keyboard.KEY_M)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				// moveOnZ(0.02f);
				Vector temp = vecmath.vector(center.x() - eye.x(), center.y()
						- eye.y(), center.z() - eye.z());
				center = center.add(temp.normalize().mult(0.008f));
				eye = eye.add(temp.normalize().mult(0.008f));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				// moveOnZ(-0.02f);
				Vector temp = vecmath.vector(eye.x() - center.x(), eye.y()
						- center.y(), eye.z() - center.z());
				center = center.add(temp.normalize().mult(0.008f));
				eye = eye.add(temp.normalize().mult(0.008f));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				// moveOnX(-0.02f);
				float eyeRightX = (eye.x() * (float) Math.cos(90)) + (eye.z() * (float) -Math.sin(90));
				float eyeRightY = eye.y();
				float eyeRightZ = (eye.x() * (float) Math.sin(90)) + (eye.z() * (float)Math.cos(90));
				
				Vector eyeRight = vecmath.vector(eyeRightX, eyeRightY, eyeRightZ);

				float centerRightX = (center.x() * (float) Math.cos(90)) + (center.z() * (float) -Math.sin(90));
				float centerRightY = center.y();
				float centerRightZ = (center.x() * (float) Math.sin(90)) + (center.z() * (float)Math.cos(90));
				
				Vector centerRight = vecmath.vector(centerRightX, centerRightY, centerRightZ);
				
				center = center.add(eyeRight.normalize().mult(0.008f));
				eye = eye.add(centerRight.normalize().mult(0.008f));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				// moveOnX(0.02f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				// rotateY(0.2f);
				center = center.add(vecmath.vector(-0.02f, 0, 0));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				center = center.add(vecmath.vector(0.02f, 0, 0));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				center = center.add(vecmath.vector(0, 0.02f, 0));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				center = center.add(vecmath.vector(0, -0.02f, 0));
			}

			// updateCamera();
			viewMatrix = vecmath.lookatMatrix(eye, center,
					vecmath.vector(0f, 1f, 0f));
			shader.getViewMatrixUniform().set(viewMatrix);
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				if (rowIndex < rootChildren.size())
					rowIndex++;
				// Bewege eine Reihe hinter
				eye = vecmath.vector(0, 0, (rowIndex * ROW_DISTANCE)
						- ROW_DISTANCE);
				center = vecmath.vector(0, 0, rowIndex * ROW_DISTANCE);
				setSelection();

				System.out.println(eye);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if (rowIndex > 0)
					rowIndex--;
				// Bewege eine Reihe hinter
				eye = vecmath.vector(0, 0, (rowIndex * -ROW_DISTANCE)
						- ROW_DISTANCE);
				center = vecmath.vector(0, 0, rowIndex * -ROW_DISTANCE);
				setSelection();

				System.out.println(eye);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				// eye = eye.add(vecmath.vector(0.002f, 0, 0));
				// center = center.add(vecmath.vector(0.002f, 0, 0));
				// eye = vecmath.vector(-2, 0, -10);
				// center = vecmath.vector(-2, 0, 0);
				// viewMatrix = rootChildren.get(0).getTransformation();
				// System.out.println(viewMatrix);
				// shader.getViewMatrixUniform().set(
				// viewMatrix.mult(vecmath.translationMatrix(vecmath
				// .vector(0, 0, -10))));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (selectionIndex > 0)
					selectionIndex--;
				// Bewege eine Reihe hinter
				eye = vecmath.vector(selectionIndex * OBJ_DISTANCE, 0, eye.z());
				center = vecmath.vector(selectionIndex * OBJ_DISTANCE, 0,
						center.z());
				setSelection();

				System.out.println(eye);
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

		setSelection();
	}

	@Override
	public void simulate(float elapsed, Input input) {
		this.input = input;
	}

	@Override
	public void display(int width, int height) {
		update();
	}

	private void setSelection() {
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
}
