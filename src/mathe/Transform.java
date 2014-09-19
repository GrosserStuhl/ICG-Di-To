package mathe;

public class Transform {

	
	private static float zNear; // close
	private static float zFar; // far clipping pane
	private static float width; // screen-width
	private static float height; // screen-height
	private static float fov; // field of view
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	
	public Transform() {
		translation = new Vector3f(0,0,0);
	}
	
	public Matrix4f getTransformation(){
		Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(),translation.getY(),translation.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(),rotation.getY(),rotation.getZ());
		Matrix4f scaleMatrix = new Matrix4f().InitScale(scale.getX(), scale.getY(), scale.getZ());
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public Matrix4f getProjectedTransformation(){
		
		Matrix4f transformationMatrix = getTransformation();
		Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar);
		
		return projectionMatrix.mul(transformationMatrix);
	}
	
	
	
	public static void setProjection(float fov, float width, float height, float zNear, float zFar){
		
		Transform.fov = fov;
		Transform.width = width;
		Transform.height = height;
		Transform.zNear = zNear;
		Transform.zFar = zFar;
		
	}
	
	public Vector3f getTranslation() {
		return translation;
	}
	
	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}
	
	public void setTranslation(float x , float y, float z) {
		this.translation = new Vector3f(x,y,z);
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	
}
