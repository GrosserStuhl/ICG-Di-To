package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.Vector;

public class Ray {

	private Vector clickPosInWorld = vecmath.vector(0, 0, 0);
	private Vector direction = vecmath.vector(0, 0, 0);

	public Ray() {

	}

	/**
	 * Computes the intersection of this ray with the X-Y Plane (where Z = 0)
	 * and writes it back to the provided vector.
	 */
	public void intersectionWithXyPlane(float[] worldPos) {
		float s = -clickPosInWorld.z() / direction.z();
		worldPos[0] = clickPosInWorld.x() + direction.x() * s;
		worldPos[1] = clickPosInWorld.y() + direction.y() * s;
		worldPos[2] = 0;
	}

	public Vector getClickPosInWorld() {
		return clickPosInWorld;
	}

	public Vector getDirection() {
		return direction;
	}
	
	public void setClickPosInWorld(Vector clickPosInWorld) {
		this.clickPosInWorld = clickPosInWorld;
	}
	
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
