package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.Vector;

public class Ray2 {

	private Vector origin;
	private Vector direction;

	public Ray2(Vector origin, Vector direction) {
		super();
		this.origin = origin;
		this.direction = direction.normalize();
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
		float x4 = vertices[3].x();

		float y1 = vertices[0].y();
		float y2 = vertices[1].y();
		float y3 = vertices[2].y();
		float y4 = vertices[3].y();

		float z1 = vertices[0].z();
		float z2 = vertices[1].z();
		float z3 = vertices[2].z();
		float z4 = vertices[3].z();

		// Vektor aus P1 und P2
		Vector V1 = vecmath.vector(vertices[1].x() - vertices[0].x(),
				vertices[1].y() - vertices[0].y(), vertices[1].z()
						- vertices[0].z());
		// Vektor aus P1 und P4
		Vector V2 = vecmath.vector(vertices[3].x() - vertices[0].x(),
				vertices[3].y() - vertices[0].y(), vertices[3].z()
						- vertices[0].z());
		Vector normal = V1.cross(V2);

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
		System.out.println("t: "+t);

		Vector intersection = vecmath.vector(origin.x() + direction.x() * t,
				origin.y() + direction.y() * t, origin.z() + direction.z() * t);
		System.out.println("inetrsection: " +intersection);

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

		V1 = V1.normalize();
		V2 = V2.normalize();
		V3 = V3.normalize();
		V4 = V4.normalize();
		V5 = V5.normalize();

		float dotProduct1v4 = V1.dot(V4);
		float dotProduct3v5 = V3.dot(V5);

		if (dotProduct1v4 > 0 && dotProduct3v5 > 0)
			return true;

		return false;
	}
}
