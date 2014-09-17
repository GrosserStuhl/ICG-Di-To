package general;

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

	public static float[] diffuseColorToArray() {

		float[] res = new float[3];
		res[0] = directionalLight.getBase().getColor().getX();
		res[1] = directionalLight.getBase().getColor().getY();
		res[2] = directionalLight.getBase().getColor().getZ();

		return res;
	}

	public static float[] diffuseIntensityToArray() {

		float[] res = new float[1];
		res[0] = directionalLight.getBase().getIntensity();

		return res;
	}
	
	public static float[] diffuseDirectionToArray() {

		float[] res = new float[3];
		res[0] = directionalLight.getDirection().getX();
		res[1] = directionalLight.getDirection().getY();
		res[2] = directionalLight.getDirection().getZ();

		return res;
	}
	
	
	
	
	

	public static void setDirectionalLight(BaseLight baseLight,
			Vector3f direction) {
		PhongShader.directionalLight = new DirectionalLight(baseLight,
				direction);

	}

}
