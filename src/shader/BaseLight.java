package shader;


import mathe.Vector3f;
import ogl.vecmath.Color;

/**
 * Mirroring GLSL Structure ( its a wrapper class)
 * 
 * @author TomTheBomb
 *
 */

public class BaseLight {

	private Color color;
	private float intensity;
	
	
	public BaseLight(Color c, float intensity) {
		this.color = c;
		this.intensity = intensity;
	
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getIntensity() {
		return intensity;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
}
