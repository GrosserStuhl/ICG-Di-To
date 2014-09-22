package shader;

import mathe.Vector3f;
import ogl.vecmath.Vector;
//import mathe.Vector3f;



public class DirectionalLight {

	private BaseLight base;
	Vector3f direction;
	
	public DirectionalLight(BaseLight base, Vector3f direction) {

		this.base = base;
		this.direction = direction.normalize();
	}
	
	public BaseLight getBase() {
		return base;
	}
	
	public Vector3f getDirection() {
		return direction;
	}
	
	public void setBase(BaseLight base) {
		this.base = base;
	}
	
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	
	
}
