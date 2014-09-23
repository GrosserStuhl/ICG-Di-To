package shader;

import mathe.Color;
import mathe.Vector3f;


public class PhongShader {

	public PhongShader() {
		
	}

	private static Color ambientLight;
	private static DirectionalLight directionalLight;

	public static Color getAmbientLight() {
		return ambientLight;
	}

	public static void setAmbientLight(Color ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}

	public static void setDirectionalLight(DirectionalLight directionalLight) {
		PhongShader.directionalLight = directionalLight;
	}


	
	public static DirectionalLight getDirectionalLight() {
		return directionalLight;
	}
	
	
	public static void setDirectionalLight(BaseLight baseLight,
			Vector3f direction) {
		PhongShader.directionalLight = new DirectionalLight(baseLight,
				direction);

	}

	

}
