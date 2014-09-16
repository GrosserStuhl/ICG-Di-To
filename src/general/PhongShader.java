package general;

import org.lwjgl.util.vector.Vector3f;

public class PhongShader {

	public PhongShader() {
		// TODO Auto-generated constructor stub
	}
	
	private static Vector3f ambientLight;
	
	public static Vector3f getAmbientLight() {
		return ambientLight;
	}
	
	public static void setAmbientLight(Vector3f ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}
	
}
