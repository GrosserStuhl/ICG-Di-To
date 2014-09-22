package shader;

import mathe.Vector3f;


public class PhongShader {

	public PhongShader() {
		// TODO Auto-generated constructor stub
	}

	private static Vector3f ambientLight;
	private static DirectionalLight directionalLight;

	public static Vector3f getAmbientLight() {
		return ambientLight;
	}

	public static void setAmbientLight(Vector3f ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}

	public static void setDirectionalLight(DirectionalLight directionalLight) {
		PhongShader.directionalLight = directionalLight;
	}

	public static float[] ambientToArray() {

		float[] res = new float[3];
		res[0] = ambientLight.getX();
		res[1] = ambientLight.getY();
		res[2] = ambientLight.getZ();

		return res;

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
