package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import mathe.Vector3f;

import org.lwjgl.BufferUtils;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Ray2 {

	private Vector origin;
	private Vector direction;

	public Ray2(Camera cam, float mouseX, float mouseY) {
//		origin = cam.getEye();
		direction = cam.getDirection();
		direction = direction.normalize();
		float height = 600;
		float width = 600;
		float aspect = (float) width / (float) height;
		Matrix proj = vecmath.perspectiveMatrix(60f, aspect, 0.1f, 100f);
		proj = proj.invertRigid();
		Matrix view = cam.getTransformation();
		view = view.invertRigid();

//		Vector3f vector = new Vector3f(0.89f, 0.1f, 0.0001f);
//		System.out.println("old " + vecmath.vector(0.89f, 0.1f, 0.0001f).dot(vecmath.vector(0.5f, 0.5f, 1f)));
//		System.out.println("before: "+ vector);
////		vector = vector.cross(new Vector3f(0.5f, 0.5f, 1f));
//		System.out.println("after: "  + vector.dot(new Vector3f(0.5f, 0.5f, 1f)));
		
		mouseY = height - mouseY - 1;
//		FloatBuffer worldPos = getMousePosition(mouseX, mouseY, proj, view);
//		origin = vecmath.vector(worldPos.get(0), worldPos.get(1), worldPos.get(2));
//		System.out.println(direction);
//		System.out.println(cam.getTransformation());
		calcMouseInWorldPosition(mouseX, mouseY, proj, view);
		
		
//		float tempX = proj.get(0, 0) * direction.x() + proj.get(1, 0) * direction.y()
//				+ proj.get(2, 0) * direction.z();
//		float tempY = proj.get(0, 1) * direction.x() + proj.get(1, 1) * direction.y()
//				+ proj.get(2, 1) * direction.z();
//		float tempZ = proj.get(0, 2) * direction.x() + proj.get(1, 2) * direction.y()
//				+ proj.get(2, 2) * direction.z();
//		
//		direction = vecmath.vector(tempX, tempY, tempZ);
//		
//		tempX = view.get(0, 0) * direction.x() + view.get(1, 0) * direction.y()
//				+ view.get(2, 0) * direction.z();
//		tempY = view.get(0, 1) * direction.x() + view.get(1, 1) * direction.y()
//				+ view.get(2, 1) * direction.z();
//		tempZ = view.get(0, 2) * direction.x() + view.get(1, 2) * direction.y()
//				+ view.get(2, 2) * direction.z();
//		
//		direction = vecmath.vector(tempX, tempY, tempZ);
//		direction = direction.normalize();
		
//		float tempX = view.get(0, 0) * mouseX + view.get(1, 0) * mouseY;
//		float tempY = view.get(0, 1) * mouseX + view.get(1, 1) * mouseY;
//		float tempZ = view.get(0, 2) * mouseX + view.get(1, 2) * mouseY;
//		
//		origin = vecmath.vector(tempX, tempY, tempZ);
//		direction = cam.getDirection();
		
//		direction = vecmath.vector(direction.x()-origin.x(), direction.y()-origin.y(), direction.z()-origin.z());
		
		System.out.println("origin: " + direction); 
		System.out.println("direction: " + direction); 
		
//		float x = (2.0f * mouseX) / width - 1.0f;
//		float y = 1.0f - (2.0f * mouseX) / height;
//		float z = 1.0f;
//		Vector ray_nds = vecmath.vector(x, y, z);
//
//		Vector4f clip = new Vector4f(ray_nds.x(), ray_nds.y(), -1.0f, 1.0f);
//
//		float tempX = proj.get(0, 0) * clip.x + proj.get(1, 0) * clip.y
//				+ proj.get(2, 0) * clip.z + proj.get(3, 0) * clip.w;
//		float tempY = proj.get(0, 1) * clip.x + proj.get(1, 1) * clip.y
//				+ proj.get(2, 1) * clip.z + proj.get(3, 1) * clip.w;
//		float tempZ = proj.get(0, 2) * clip.x + proj.get(1, 2) * clip.y
//				+ proj.get(2, 2) * clip.z + proj.get(3, 2) * clip.w;
//		float tempW = proj.get(0, 3) * clip.x + proj.get(1, 3) * clip.y
//				+ proj.get(2, 3) * clip.z + proj.get(3, 3) * clip.w;
//
//		Vector4f ray_eye = new Vector4f(tempX, tempY, tempZ, tempW);
//		ray_eye = new Vector4f(ray_eye.x, ray_eye.y, -1.0f, 0.0f);
//
//		tempX = view.get(0, 0) * ray_eye.x + view.get(1, 0) * ray_eye.y
//				+ view.get(2, 0) * ray_eye.z + view.get(3, 0) * ray_eye.w;
//		tempY = view.get(0, 1) * ray_eye.x + view.get(1, 1) * ray_eye.y
//				+ view.get(2, 1) * ray_eye.z + view.get(3, 1) * ray_eye.w;
//		tempZ = view.get(0, 2) * ray_eye.x + view.get(1, 2) * ray_eye.y
//				+ view.get(2, 2) * ray_eye.z + view.get(3, 2) * ray_eye.w;
//		tempW = view.get(0, 3) * ray_eye.x + view.get(1, 3) * ray_eye.y
//				+ view.get(2, 3) * ray_eye.z + view.get(3, 3) * ray_eye.w;
//
//		Vector ray_wor = vecmath.vector(tempX, tempY, tempZ);
//		// don't forget to normalise the vector at some point
//		ray_wor = ray_wor.normalize();
//		direction = ray_wor;
	}

	public FloatBuffer getMousePosition(float mouseX, float mouseY, Matrix proj, Matrix view) {
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		modelview.put(view.asArray());
		modelview.rewind();
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		projection.put(proj.asArray());
		projection.rewind();
		FloatBuffer winZ = BufferUtils.createFloatBuffer(1);
		float winX, winY;
		FloatBuffer position = BufferUtils.createFloatBuffer(3);
		
		glGetFloat(GL_MODELVIEW_MATRIX, modelview);
		glGetFloat(GL_PROJECTION_MATRIX, projection);
		glGetInteger(GL_VIEWPORT, viewport);
		
		winX = mouseX;
		winY = mouseY;
		glReadPixels((int) mouseX, (int) winY, 1, 1, GL_DEPTH_COMPONENT,
				GL_FLOAT, winZ);
		gluUnProject(winX, winY, winZ.get(), modelview, projection,
				viewport, position);
		return position;
	}
	
	private void calcMouseInWorldPosition(float mouseX, float mouseY, Matrix proj, Matrix view) {
	    Vector start = vecmath.vector(0, 0, 0);
	    Vector end = vecmath.vector(0, 0, 0);
	    
	    FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
		modelBuffer.put(view.asArray());
		modelBuffer.rewind();
		FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
		projBuffer.put(proj.asArray());
		projBuffer.rewind();

//	    FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
//	    FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
	    FloatBuffer startBuffer = BufferUtils.createFloatBuffer(16);
	    FloatBuffer endBuffer = BufferUtils.createFloatBuffer(16);
	    IntBuffer viewBuffer = BufferUtils.createIntBuffer(16);

	    glGetFloat(GL_MODELVIEW_MATRIX, modelBuffer);
	    glGetFloat(GL_PROJECTION_MATRIX, projBuffer);
	    glGetInteger(GL_VIEWPORT, viewBuffer);
	    
	    gluUnProject(mouseX, mouseY, 0.0f, modelBuffer, projBuffer, viewBuffer, startBuffer);
	    gluUnProject(mouseX, mouseY, 1.0f, modelBuffer, projBuffer, viewBuffer, endBuffer);

	    start = vecmath.vector(startBuffer.get(0), startBuffer.get(1), startBuffer.get(2));
	    end = vecmath.vector(endBuffer.get(0), endBuffer.get(1), endBuffer.get(2));

	    origin = vecmath.vector(end.x()-start.x(), end.y()-start.y(), end.z()-start.z());
	    System.out.println("FRESH ORIGIN: " + origin);

	    System.out.println("Mouse Coords: " + mouseX + ", " + mouseY);
	    System.out.println("start: " + start);
	    System.out.println("end: " + end);
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
//		System.out.println("t: " + t);

		Vector intersection = vecmath.vector(origin.x() + direction.x() * t,
				origin.y() + direction.y() * t, origin.z() + direction.z() * t);
//		System.out.println("inetrsection: " + intersection);

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
		// float winkel1 = (float) Math.toDegrees((float)
		// Math.acos(dotProduct1v4));
		// float winkel2 = (float) Math.toDegrees((float)
		// Math.acos(dotProduct3v5));
		// float winkel3 = (float) Math.toDegrees((float)
		// Math.acos(dotProduct2v6));
		// float winkel4 = (float) Math.toDegrees((float)
		// Math.acos(dotProduct7v8));
		// System.out.println("Winkel1: " + winkel1);
		// System.out.println("Winkel2: " + winkel2);
		// System.out.println("Winkel3: " + winkel3);
		// System.out.println("Winkel4: " + winkel4);

		if (dotProduct1v4 > 0 && dotProduct3v5 > 0 && dotProduct2v6 > 0
				&& dotProduct7v8 > 0)
			return true;
		// if (winkel1 < 91 && winkel2 < 91 && winkel3 < 91
		// && winkel4 < 91)
		// return true;

		return false;
	}
}
