package mathe;

public class Vector3f {

	public float x;
	public float y;
	public float z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	public Vector3f normalize() {
		float length = length();

		// this will set the overall vectorlength to 1
		float x = this.x/length;
		float y = this.y/length;
		float z = this.z/length;

		return new Vector3f(x, y, z);
	}

	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);
		return this.cross(axis.mult(sinAngle)).add( // Rotation on local X
				(this.mult(cosAngle)).add( // Rotation on local Z
						axis.mult(this.dot(axis.mult(1 - cosAngle))))); // Rotation
																		// on
																		// local
																		// Y
	}

	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}

	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}

	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	public Vector3f mult(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	public Vector3f mult(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * returns the Vector3f as a float Array 
	 * @return float[ X, Y, Z];
	 */
	
	public float[] asArray(){
		float[] res = new float[3];
		res[0] = x;
		res[1] = y;
		res[2] = z;
		
		return res;
	}
	
	

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
}
