package shader;



/**
 * Mirroring GLSL Structure ( its a wrapper class)
 * 
 * @author TomTheBomb
 *
 */

public class BaseLight {

	private mathe.Color color;
	private float intensity;
	
	
	public BaseLight(mathe.Color c, float intensity) {
		this.color = c;
		this.intensity = intensity;
	
	}
	
	public mathe.Color getColor() {
		return color;
	}
	
	public float getIntensity() {
		return intensity;
	}
	
	public void setColor(mathe.Color color) {
		this.color = color;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
}
