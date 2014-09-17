package general;


import mathe.Vector3f;
import ogl.vecmath.Color;

/**
 * Mirroring GLSL Structure ( its a wrapper class)
 * 
 * @author TomTheBomb
 *
 */

public class BaseLight {

	private Vector3f color;
	private float intensity;
	
	
	public BaseLight(Vector3f vector3f, float intensity) {
		this.color = vector3f;
		this.intensity = intensity;
	
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public float getIntensity() {
		return intensity;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
}
