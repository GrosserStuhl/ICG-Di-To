package general;

import ogl.vecmath.Vector;
//import mathe.Vector3f;



public class DirectionalLight {

	private BaseLight base;
	Vector direction;
	
	public DirectionalLight(BaseLight base, Vector direction) {

		this.base = base;
		this.direction = direction.normalize();
	}
	
	public BaseLight getBase() {
		return base;
	}
	
	public Vector getDirection() {
		return direction;
	}
	
	public void setBase(BaseLight base) {
		this.base = base;
	}
	
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
	
	
	
}
