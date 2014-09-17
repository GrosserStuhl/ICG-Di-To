package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.Matrix;
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

}
