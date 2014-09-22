package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Ray2 {

	private Vector origin;
	private Vector direction;

	public Ray2(Camera cam, float mouseX, float mouseY) {
		Matrix view = cam.getTransformation();
		view = view.invertRigid();

		calcMouseInWorldPosition(mouseX, mouseY, view);

		System.out.println("origin: " + origin);
		System.out.println("direction: " + direction);
	}

	private void calcMouseInWorldPosition(float mouseX, float mouseY,
			Matrix view) {
		Vector start = vecmath.vector(0, 0, 0);

		FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
		FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);

		FloatBuffer startBuffer = BufferUtils.createFloatBuffer(16);
		IntBuffer viewBuffer = BufferUtils.createIntBuffer(16);

		glGetFloat(GL_MODELVIEW_MATRIX, modelBuffer);
		glGetFloat(GL_PROJECTION_MATRIX, projBuffer);
		glGetInteger(GL_VIEWPORT, viewBuffer);

		gluUnProject(mouseX, mouseY, 0.0f, modelBuffer, projBuffer, viewBuffer,
				startBuffer);

		start = vecmath.vector(startBuffer.get(0), startBuffer.get(1),
				startBuffer.get(2));

		float height = 600;
		float width = 600;

		float tempX = (float) (start.x() * 0.1f * Math.tan(Math.PI * 60f / 360));
		float tempY = (float) (start.y() * 0.1f * Math.tan(Math.PI * 60f / 360)
				* height / width);
		float tempZ = -0.1f;

		direction = vecmath.vector(tempX, tempY, tempZ);

		// tempX = view.get(0, 0) * direction.x() + view.get(1, 0) *
		// direction.y() + view.get(2, 0) * direction.z();
		// tempY = view.get(0, 1) * direction.x() + view.get(1, 1) *
		// direction.y() + view.get(2, 1) * direction.z();
		// tempZ = view.get(0, 2) * direction.x() + view.get(1, 2) *
		// direction.y() + view.get(2, 2) * direction.z();

		direction = view.transformDirection(direction);
		// direction = vecmath.vector(tempX, tempY, tempZ);
		System.out.println("FINAL DIRECTION: " + direction);

		origin = view.getPosition();
	}

	public Vector getDirection() {
		return direction;
	}

	public Vector getOrigin() {
		return origin;
	}

	public void setOrigin(Vector origin) {
		this.origin = origin;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public boolean intersects(Vector[] vertices) {
		float x1 = vertices[0].x();
		float x2 = vertices[1].x();
		float x3 = vertices[2].x();

		float y1 = vertices[0].y();
		float y2 = vertices[1].y();
		float y3 = vertices[2].y();

		float z1 = vertices[0].z();
		float z2 = vertices[1].z();
		float z3 = vertices[2].z();

		float A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
		float B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
		float C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
		float D = -x1 * (y2 * z3 - y3 * z2) - x2 * (y3 * z1 - y1 * z3) - x3
				* (y1 * z2 - y2 * z1);

		float top = -(A * origin.x() + B * origin.y() + C * origin.z() + D);
		float bot = (A * direction.x() + B * direction.y() + C * direction.z());
		float t = top / bot;

		if (t <= 0)
			return false;
		// System.out.println("t: " + t);

		Vector intersection = vecmath.vector(origin.x() + direction.x() * t,
				origin.y() + direction.y() * t, origin.z() + direction.z() * t);
		// System.out.println("inetrsection: " + intersection);

		// Vektor aus P1 und P2
		Vector V1 = vecmath.vector(vertices[1].x() - vertices[0].x(),
				vertices[1].y() - vertices[0].y(), vertices[1].z()
						- vertices[0].z());
		// Vektor aus P4 und P1
		Vector V2 = vecmath.vector(vertices[0].x() - vertices[3].x(),
				vertices[0].y() - vertices[3].y(), vertices[0].z()
						- vertices[3].z());
		// Vektor aus P3 und P4
		Vector V3 = vecmath.vector(vertices[3].x() - vertices[2].x(),
				vertices[3].y() - vertices[2].y(), vertices[3].z()
						- vertices[2].z());
		// Vektor aus P1 und intersection
		Vector V4 = vecmath.vector(intersection.x() - vertices[0].x(),
				intersection.y() - vertices[0].y(), intersection.z()
						- vertices[0].z());
		// Vektor aus P3 und intersection
		Vector V5 = vecmath.vector(intersection.x() - vertices[2].x(),
				intersection.y() - vertices[2].y(), intersection.z()
						- vertices[2].z());
		// Vektor aus P4 und intersection
		Vector V6 = vecmath.vector(intersection.x() - vertices[3].x(),
				intersection.y() - vertices[3].y(), intersection.z()
						- vertices[3].z());
		// Vektor aus P2 und P3
		Vector V7 = vecmath.vector(vertices[2].x() - vertices[1].x(),
				vertices[2].y() - vertices[1].y(), vertices[2].z()
						- vertices[1].z());
		// Vektor aus P2 und intersection
		Vector V8 = vecmath.vector(intersection.x() - vertices[1].x(),
				intersection.y() - vertices[1].y(), intersection.z()
						- vertices[1].z());

		V1 = V1.normalize();
		V2 = V2.normalize();
		V3 = V3.normalize();
		V4 = V4.normalize();
		V5 = V5.normalize();
		V6 = V6.normalize();
		V7 = V7.normalize();
		V8 = V8.normalize();

		float dotProduct1v4 = V4.dot(V1);
		float dotProduct3v5 = V3.dot(V5);
		float dotProduct2v6 = V2.dot(V6);
		float dotProduct7v8 = V7.dot(V8);

		if (dotProduct1v4 > 0 && dotProduct3v5 > 0 && dotProduct2v6 > 0
				&& dotProduct7v8 > 0)
			return true;

		return false;
	}
}
