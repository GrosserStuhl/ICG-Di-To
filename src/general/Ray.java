package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.Vector;

public class Ray {

	private Vector origin = vecmath.vector(0, 0, 0);
	private Vector direction = vecmath.vector(0, 0, 0);

	public Ray(Camera cam, float mouseX, float mouseY) {

		float height = 600;
		float width = 600;

		Vector viewVec = cam.getDirection().normalize();

		Vector horizontal = viewVec.cross(cam.getUp()).normalize();
		Vector vertical = horizontal.cross(viewVec).normalize();

		final float radians = (float) (60f * Math.PI / 180f);
		float halfHeight = (float) (Math.tan(radians / 2) * 0.1f);
		float halfScaledAspectRatio = halfHeight * (width / height);

		horizontal = horizontal.mult(halfHeight);
		vertical = vertical.mult(halfScaledAspectRatio);

		origin = cam.getEye();
		origin.add(viewVec);

		float screenX = mouseX - (float) width / 2f;
		float screenY = mouseY - (float) height / 2f;

		// normalize to 1
		screenX /= ((float) width / 2f);
		screenY /= ((float) height / 2f);

		origin = vecmath.vector(origin.x() + horizontal.x() * screenX
				+ vertical.x() * screenY, origin.y() + horizontal.y() * screenX
				+ vertical.y() * screenY, origin.z() + horizontal.z() * screenX
				+ vertical.z() * screenY);

		direction = origin.sub(cam.getEye());
	}

	/**
	 * Computes the intersection of this ray with the X-Y Plane (where Z = 0)
	 * and writes it back to the provided vector.
	 */
	public void intersectionWithXyPlane(float[] worldPos) {
		float s = -origin.z() / direction.z();
		worldPos[0] = origin.x() + direction.x() * s;
		worldPos[1] = origin.y() + direction.y() * s;
		worldPos[2] = 0;
	}

	public Vector getOrigin() {
		return origin;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setOrigin(Vector rayOrigin) {
		this.origin = rayOrigin;
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
		System.out.println("t: " + t);

		Vector intersection = vecmath.vector(origin.x() + direction.x() * t,
				origin.y() + direction.y() * t, origin.z() + direction.z() * t);
		System.out.println("inetrsection: " + intersection);

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
