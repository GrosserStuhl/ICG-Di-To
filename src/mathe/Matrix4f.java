package mathe;

public class Matrix4f {

	private float[][] m = new float[4][4];;
	  
	public Matrix4f(float value1, float value2, float value3, float value4,
			float value5, float value6, float value7, float value8,
			float value9, float value10, float value11, float value12,
			float value13, float value14, float value15, float value16) {
		m[0][0] = value1;
		m[0][1] = value2;
		m[0][2] = value3;
		m[0][3] = value4;

		m[1][0] = value5;
		m[1][1] = value6;
		m[1][2] = value7;
		m[1][3] = value8;

		m[2][0] = value9;
		m[2][1] = value10;
		m[2][2] = value11;
		m[2][3] = value12;

		m[3][0] = value13;
		m[3][1] = value14;
		m[3][2] = value15;
		m[3][3] = value16;
	}
	  
	public Matrix4f(float[] valueArray) {
		m[0][0] = valueArray[0];
		m[0][1] = valueArray[1];
		m[0][2] = valueArray[2];
		m[0][3] = valueArray[3];
		m[1][0] = valueArray[4];
		m[1][1] = valueArray[5];
		m[1][2] = valueArray[6];
		m[1][3] = valueArray[7];
		m[2][0] = valueArray[8];
		m[2][1] = valueArray[9];
		m[2][2] = valueArray[10];
		m[2][3] = valueArray[11];
		m[3][0] = valueArray[12];
		m[3][1] = valueArray[13];
		m[3][2] = valueArray[14];
		m[3][3] = valueArray[15];
	}
	  
	public Matrix4f(Matrix4f matrix) {
		m[0][0] = matrix.get(0, 0);
		m[0][1] = matrix.get(0, 1);
		m[0][2] = matrix.get(0, 2);
		m[0][3] = matrix.get(0, 3);
		m[1][0] = matrix.get(1, 0);
		m[1][1] = matrix.get(1, 1);
		m[1][2] = matrix.get(1, 2);
		m[1][3] = matrix.get(1, 3);
		m[2][0] = matrix.get(2, 0);
		m[2][1] = matrix.get(2, 1);
		m[2][2] = matrix.get(2, 2);
		m[2][3] = matrix.get(2, 3);
		m[3][0] = matrix.get(3, 0);
		m[3][1] = matrix.get(3, 1);
		m[3][2] = matrix.get(3, 2);
		m[3][3] = matrix.get(3, 3);
	}
	
	
	public Matrix4f() {
	}
	
	public Matrix4f(float[][] m){
		this.m = m;
	}
	
	public static Matrix4f identityMatrix(){
		float[][] temp = new float[4][4];
		
		temp[0][0] = 1;    temp[0][1] = 0;   temp[0][2] = 0;   temp[0][3] = 0;
		temp[1][0] = 0;    temp[1][1] = 1;   temp[1][2] = 0;   temp[1][3] = 0;
		temp[2][0] = 0;    temp[2][1] = 0;   temp[2][2] = 1;   temp[2][3] = 0;
		temp[3][0] = 0;    temp[3][1] = 0;   temp[3][2] = 0;   temp[3][3] = 1;
		
		return new Matrix4f(temp);
	}
	
	public static Matrix4f translationMatrix(float x, float y , float z){
		float[][] m = new float[4][4];
		
		m[0][0] = 1;    m[0][1] = 0;   m[0][2] = 0;   m[0][3] = x;
		m[1][0] = 0;    m[1][1] = 1;   m[1][2] = 0;   m[1][3] = y;
		m[2][0] = 0;    m[2][1] = 0;   m[2][2] = 1;   m[2][3] = z;
		m[3][0] = 0;    m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
		
		return new Matrix4f(m);
		
	}
	
	public static Matrix4f translationMatrix(Vector3f r){
		float[][] m = new float[4][4];
		
		m[0][0] = 1;    m[0][1] = 0;   m[0][2] = 0;   m[0][3] = r.x;
		m[1][0] = 0;    m[1][1] = 1;   m[1][2] = 0;   m[1][3] = r.y;
		m[2][0] = 0;    m[2][1] = 0;   m[2][2] = 1;   m[2][3] = r.z;
		m[3][0] = 0;    m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
		
		return new Matrix4f(m);
		
	}
	
//	public static Matrix4f rotationMatrix(float x, float y , float z){
//		float[][] m = new float[4][4];
//		
//		Matrix4f rx = new Matrix4f();
//		Matrix4f ry = new Matrix4f();
//		Matrix4f rz = new Matrix4f();
//		
//		x = (float)Math.toRadians(x);
//		y = (float)Math.toRadians(y);
//		z = (float)Math.toRadians(z);
//		
//		
//		rz.m[0][0] = (float)Math.cos(z);    rz.m[0][1] = -(float) Math.sin(z);   rz.m[0][2] = 0;   					 rz.m[0][3] = 0;
//		rz.m[1][0] = (float)Math.sin(z);    rz.m[1][1] = (float)Math.cos(z);   	 rz.m[1][2] = 0;   					 rz.m[1][3] = 0;
//		rz.m[2][0] = 0;    					rz.m[2][1] = 0;   					 rz.m[2][2] = 1;   					 rz.m[2][3] = 0;
//		rz.m[3][0] = 0;    					rz.m[3][1] = 0;   					 rz.m[3][2] = 0;   					 rz.m[3][3] = 1;
//		
//		rx.m[0][0] = 1;    					rx.m[0][1] = 0;                      rx.m[0][2] = 0;   					 rx.m[0][3] = 0;
//		rx.m[1][0] = 0;   				    rx.m[1][1] = (float)Math.cos(x);     rx.m[1][2] = -(float) Math.sin(x);  rx.m[1][3] = 0;
//		rx.m[2][0] = 0;                     rx.m[2][1] = (float)Math.cos(x);     rx.m[2][2] = (float)Math.cos(x);    rx.m[2][3] = 0;
//		rx.m[3][0] = 0;                     rx.m[3][1] = 0;                      rx.m[3][2] = 0;   					 rx.m[3][3] = 1;
//		
//		ry.m[0][0] = (float)Math.cos(y);    ry.m[0][1] = 0;                      ry.m[0][2] = -(float)Math.sin(y);   ry.m[0][3] = 0;
//		ry.m[1][0] = 0;    					ry.m[1][1] = 1;                      ry.m[1][2] = 0;   				     ry.m[1][3] = 0;
//		ry.m[2][0] = (float)Math.sin(y);    ry.m[2][1] = 0;                      ry.m[2][2] = (float)Math.cos(y);    ry.m[2][3] = 0;
//		ry.m[3][0] = 0;    					ry.m[3][1] = 0;                      ry.m[3][2] = 0;   				     ry.m[3][3] = 1;
//		
//		m = rz.mult(ry.mult(rx)).getM();
//		
//		return new Matrix4f(m);
//	}
	
	public static Matrix4f rotationMatrix(Vector3f axis, float angle)
	  {
	    Matrix4f m = new Matrix4f();
	    float rad = (float) (angle / 180.0F * Math.PI);
	    float cos = (float)Math.cos(rad);
	    float sin = (float)Math.sin(rad);
	    Vector3f axis_norm = axis.normalize();
	    float rx = axis_norm.x;
	    float ry = axis_norm.y;
	    float rz = axis_norm.z;
	    float icos = 1.0F - cos;
	    
	    m.set(0, 0, icos * rx * rx + cos);
	    m.set(0, 1, icos * rx * rz - ry * sin);
	    m.set(0, 2, icos * rx * ry + rz * sin);
	    
	    m.set(1, 0, icos * ry * rz + rx * sin);
	    m.set(1, 1, icos * ry * ry + cos);
	    m.set(1, 2, icos * rx * ry - rz * sin);
	    
	    m.set(2, 0, icos * ry * rz - rx * sin);
	    m.set(2, 1, icos * rx * rz + ry * sin);
	    m.set(2, 2, icos * rz * rz + cos);
	    return m;
	  }
	
	public static Matrix4f rotationMatrix(float x, float y, float z, float angle) {
		return Matrix4f.rotationMatrix(new Vector3f(x, y, z), angle);
	}
	
	public static Matrix4f scaleMatrix(float x, float y, float z)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return new Matrix4f(m);
	}
	
	public static Matrix4f scaleMatrix(Vector3f vec)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = vec.x;	m[0][1] = 0;		m[0][2] = 0;		m[0][3] = 0;
		m[1][0] = 0;		m[1][1] = vec.y;	m[1][2] = 0;		m[1][3] = 0;
		m[2][0] = 0;		m[2][1] = 0;		m[2][2] = vec.z;	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;
		
		return new Matrix4f(m);
	}
	
//	public static Matrix4f projectionMatrix(float fov, float aspectRatio, float zNear, float zFar){
//		float[][] m = new float[4][4];
//		
//		float tanHalfFOV = (float)Math.tan(fov / 2);
//		float zRange = zNear - zFar;
//		
//		m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
//		m[1][0] = 0;	m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	m[1][3] = 0;
//		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
//		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 1;	m[3][3] = 0;
//		
//		return new Matrix4f(m);
//	}
	
	public static Matrix4f projectionMatrix(float fov, float aspectRatio,
			float zNear, float zFar) {
		float ymax, xmax;
		ymax = (float) (zNear * Math.tan(fov * Math.PI / 360.0));
		// ymin = -ymax;
		// xmin = -ymax * aspectRatio;
		xmax = ymax * aspectRatio;
		return frustumMatrix(-xmax, xmax, -ymax, ymax, zNear, zFar);
	}
	
	private static Matrix4f frustumMatrix(float left, float right, float bottom,
			float top, float zNear, float zFar) {
		float temp, temp2, temp3, temp4;
		temp = 2.0f * zNear;
		temp2 = right - left;
		temp3 = top - bottom;
		temp4 = zFar - zNear;
		
		float[] matrix = new float[16];
		matrix[0] = temp / temp2;
		matrix[1] = 0.0f;
		matrix[2] = 0.0f;
		matrix[3] = 0.0f;
		matrix[4] = 0.0f;
		matrix[5] = temp / temp3;
		matrix[6] = 0.0f;
		matrix[7] = 0.0f;
		matrix[8] = (right + left) / temp2;
		matrix[9] = (top + bottom) / temp3;
		matrix[10] = (-zFar - zNear) / temp4;
		matrix[11] = (-temp * zFar) / temp4;
		matrix[12] = 0.0f;
		matrix[13] = 0.0f;
		matrix[14] = -1.0f;
		matrix[15] = 0.0f;
		
		return new Matrix4f(matrix);
	}
	
//	public static Matrix4f lookAtMatrix(Vector3f eye, Vector3f center, Vector3f upV) {
//		    float[] forwardF = new float[3];
//		 
//		    forwardF[0] = center.x - eye.x;
//		    forwardF[1] = center.y - eye.y;
//		    forwardF[2] = center.z - eye.z;
//		 
//		    Vector3f forward = new Vector3f(forwardF[0], forwardF[0], forwardF[0]).normalize();
//		 
//		    /* Side = forward x up */
//		   Vector3f side =  forward.cross(upV).normalize();
//		 
//		    /* Recompute up as: up = side x forward */
//		    Vector3f up = side.cross(forward);
//		 
//			Matrix4f m = Matrix4f.identityMatrix();
//		    m.set(0,0, side.x);
//		    m.set(1,0, side.y);
//		    m.set(2,0, side.z);
//		 
//		    m.set(0,1, up.x);
//		    m.set(1,1, up.y);
//		    m.set(2,1, up.z);
//		 
//		    m.set(0,2, -forward.x);
//		    m.set(1,2, -forward.y);
//		    m.set(2,2, -forward.z);
//		 
////		    glMultMatrixf(&amp;m[0][0]);
//		    return Matrix4f.translationMatrix(-eye.x, -eye.y, -eye.z);
//		}
	
	public static Matrix4f lookAtMatrix(Vector3f eye, Vector3f center,
			Vector3f up) {
		Matrix4f viewMatrix = new Matrix4f();
		Vector3f dir = new Vector3f(center.x - eye.x, center.y - eye.y,
				center.z - eye.z);
		Vector3f right = new Vector3f();
		dir = dir.normalize();

		right = dir.cross(up).normalize();

		up = right.cross(dir).normalize();

		viewMatrix = new Matrix4f();

		viewMatrix.set(0, 0, right.x);
		viewMatrix.set(0, 1, right.y);
		viewMatrix.set(0, 2, right.z);
		viewMatrix.set(0, 3, 0.0f);

		viewMatrix.set(1, 0, up.x);
		viewMatrix.set(1, 1, up.y);
		viewMatrix.set(1, 2, up.z);
		viewMatrix.set(1, 3, 0.0f);

		viewMatrix.set(2, 0, -dir.x);
		viewMatrix.set(2, 1, -dir.y);
		viewMatrix.set(2, 2, -dir.z);
		viewMatrix.set(2, 3, 0.0f);

		viewMatrix.set(3, 0, 0.0f);
		viewMatrix.set(3, 1, 0.0f);
		viewMatrix.set(3, 2, 0.0f);
		viewMatrix.set(3, 3, 1.0f);

		return Matrix4f.translationMatrix(-eye.x, -eye.y, -eye.z);
	}
		
	public Matrix4f inverse()
	  {
		float[] m = this.asArray();
	    float[] tmp = new float[12];
	    float[] src = new float[16];
	    float[] dst = new float[16];  

	    // Transpose matrix
	    for (int i = 0; i < 4; i++) {
	      src[i +  0] = m[i*4 + 0];
	      src[i +  4] = m[i*4 + 1];
	      src[i +  8] = m[i*4 + 2];
	      src[i + 12] = m[i*4 + 3];
	    }

	    // Calculate pairs for first 8 elements (cofactors) 
	    tmp[0] = src[10] * src[15];
	    tmp[1] = src[11] * src[14];
	    tmp[2] = src[9]  * src[15];
	    tmp[3] = src[11] * src[13];
	    tmp[4] = src[9]  * src[14];
	    tmp[5] = src[10] * src[13];
	    tmp[6] = src[8]  * src[15];
	    tmp[7] = src[11] * src[12];
	    tmp[8] = src[8]  * src[14];
	    tmp[9] = src[10] * src[12];
	    tmp[10] = src[8] * src[13];
	    tmp[11] = src[9] * src[12];
	    
	    // Calculate first 8 elements (cofactors)
	    dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
	    dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
	    dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
	    dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
	    dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
	    dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
	    dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
	    dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
	    dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
	    dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
	    dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
	    dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
	    dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
	    dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
	    dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
	    dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];
	    
	    // Calculate pairs for second 8 elements (cofactors)
	    tmp[0]  = src[2]*src[7];
	    tmp[1]  = src[3]*src[6];
	    tmp[2]  = src[1]*src[7];
	    tmp[3]  = src[3]*src[5];
	    tmp[4]  = src[1]*src[6];
	    tmp[5]  = src[2]*src[5];
	    tmp[6]  = src[0]*src[7];
	    tmp[7]  = src[3]*src[4];
	    tmp[8]  = src[0]*src[6];
	    tmp[9]  = src[2]*src[4];
	    tmp[10] = src[0]*src[5];
	    tmp[11] = src[1]*src[4];

	    // Calculate second 8 elements (cofactors)
	    dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
	    dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
	    dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
	    dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
	    dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
	    dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
	    dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
	    dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
	    dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
	    dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
	    dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
	    dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
	    dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
	    dst[14] -= tmp[10]* src[11 ] + tmp[2] * src[8]   + tmp[7] * src[9];
	    dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
	    dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

	    // Calculate determinant
	    float det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];
	    
	    // Calculate matrix inverse
	    det = 1.0f / det;
	    for (int i = 0; i < 16; i++)
	      m[i] = dst[i] * det;
	    
	    return new Matrix4f(m);
	  }
    
    
    // Determinante einer 4x4 matrix berechnen
 	// Determinante = 
 	
 	//////////////////
 	// | +  -  +  - |
 	// | -  +  -  + |
 	// | +  -  +  - |
 	// | -  +  -  + |
 	////////////////// 
    public float determinant(Matrix4f r){
    	float res;
    	res = 
    			
    	r.get(0,3) * r.get(1,2) * r.get(2,1) * r.get(3,0)-r.get(0,2) * r.get(1,3) * r.get(2,1) * r.get(3,0)-r.get(0,3) * r.get(1,1) * r.get(2,2) * r.get(3,0)+r.get(0,1) * r.get(1,3) * r.get(2,2) * r.get(3,0)+
        r.get(0,2) * r.get(1,1) * r.get(2,3) * r.get(3,0)-r.get(0,1) * r.get(1,2) * r.get(2,3) * r.get(3,0)-r.get(0,3) * r.get(1,2) * r.get(2,0) * r.get(3,1)+r.get(0,2) * r.get(1,3) * r.get(2,0) * r.get(3,1)+
        r.get(0,3) * r.get(1,0) * r.get(2,2) * r.get(3,1)-r.get(0,0) * r.get(1,3) * r.get(2,2) * r.get(3,1)-r.get(0,2) * r.get(1,0) * r.get(2,3) * r.get(3,1)+r.get(0,0) * r.get(1,2) * r.get(2,3) * r.get(3,1)+
        r.get(0,3) * r.get(1,1) * r.get(2,0) * r.get(3,2)-r.get(0,1) * r.get(1,3) * r.get(2,0) * r.get(3,2)-r.get(0,3) * r.get(1,0) * r.get(2,1) * r.get(3,2)+r.get(0,0) * r.get(1,3) * r.get(2,1) * r.get(3,2)+
        r.get(0,1) * r.get(1,0) * r.get(2,3) * r.get(3,2)-r.get(0,0) * r.get(1,1) * r.get(2,3) * r.get(3,2)-r.get(0,2) * r.get(1,1) * r.get(2,0) * r.get(3,3)+r.get(0,1) * r.get(1,2) * r.get(2,0) * r.get(3,3)+
        r.get(0,2) * r.get(1,0) * r.get(2,1) * r.get(3,3)-r.get(0,0) * r.get(1,2) * r.get(2,1) * r.get(3,3)-r.get(0,1) * r.get(1,0) * r.get(2,2) * r.get(3,3)+r.get(0,0) * r.get(1,1) * r.get(2,2) * r.get(3,3);
    	
    	return res;
    }
    
//    public double determinant(){
//    	double res;
//    	res = 
//    			
//    	m[0][3] * m[1][2] * m[2][1] * m[3][0]-m[0][2] * m[1][3] * m[2][1] * m[3][0]-m[0][3] * m[1][1] * m[2][2] * m[3][0]+m[0][1] * m[1][3] * m[2][2] * m[3][0]+
//        m[0][2] * m[1][1] * m[2][3] * m[3][0]-m[0][1] * m[1][2] * m[2][3] * m[3][0]-m[0][3] * m[1][2] * m[2][0] * m[3][1]+m[0][2] * m[1][3] * m[2][0] * m[3][1]+
//        m[0][3] * m[1][0] * m[2][2] * m[3][1]-m[0][0] * m[1][3] * m[2][2] * m[3][1]-m[0][2] * m[1][0] * m[2][3] * m[3][1]+m[0][0] * m[1][2] * m[2][3] * m[3][1]+
//        m[0][3] * m[1][1] * m[2][0] * m[3][2]-m[0][1] * m[1][3] * m[2][0] * m[3][2]-m[0][3] * m[1][0] * m[2][1] * m[3][2]+m[0][0] * m[1][3] * m[2][1] * m[3][2]+
//        m[0][1] * m[1][0] * m[2][3] * m[3][2]-m[0][0] * m[1][1] * m[2][3] * m[3][2]-m[0][2] * m[1][1] * m[2][0] * m[3][3]+m[0][1] * m[1][2] * m[2][0] * m[3][3]+
//        m[0][2] * m[1][0] * m[2][1] * m[3][3]-m[0][0] * m[1][2] * m[2][1] * m[3][3]-m[0][1] * m[1][0] * m[2][2] * m[3][3]+m[0][0] * m[1][1] * m[2][2] * m[3][3];
//    	
//    	return res;
//    }
    
    
//    public Matrix4f transpose(){
//    	
//    	Matrix4f start = this;
//    	
//    	float[][] temp = new float[4][4];
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 4; j++)
//                temp[j][i] = m[i][j];
//        
//        
//		start.setM(temp);
//		return start;
//    }
    
    public Matrix4f transpose(){
    	float[][] temp = new float[4][4];
    	float[][] res = new float[4][4];
    	
    	res[0][0] = this.m[0][0];
		res[0][1] = this.m[1][0];
		res[0][2] = this.m[2][0];
		res[0][3] = this.m[3][0];

		res[1][0] = this.m[0][1];
		res[1][1] = this.m[1][1];
		res[1][2] = this.m[2][1];
		res[1][3] = this.m[3][1];
		
		res[2][0] = this.m[0][2];
		res[2][1] = this.m[1][2];
		res[2][2] = this.m[2][2];
		res[2][3] = this.m[3][2];
		
		res[3][0] = this.m[0][3];
		res[3][1] = this.m[1][3];
		res[3][2] = this.m[2][3];
		res[3][3] = this.m[3][3];
		
		Matrix4f transpose = new Matrix4f();
		transpose.setM(res);
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) 
				temp[i][j] = res[i][j];
		
		return new Matrix4f(temp);
    }
    
	public Matrix4f mult(Matrix4f r){
		Matrix4f res = new Matrix4f();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, m[i][0] * r.get(0,j)+
							  m[i][1] * r.get(1,j)+
							  m[i][2] * r.get(2,j)+
							  m[i][3] * r.get(3,j));
			}
		}
		
		return res;
	}
	
	public Vector3f mult(Vector3f r) {
		float tempX = m[0][0] * r.x + m[1][0] * r.y + m[2][0] * r.z;
		float tempY = m[0][1] * r.x + m[1][1] * r.y + m[2][1] * r.z;
		float tempZ = m[0][2] * r.x + m[1][2] * r.y + m[2][2] * r.z;
		
		return new Vector3f(tempX, tempY, tempZ);
	}
	
	public float get(int x, int y){
		return m[x][y];
	}
	
	 public float[][] getM() {
		return m;
	}
	 
	 public void setM(float[][] m) {
		this.m = m;
	}
	 
	 public void set(int x, int y, float value){
		 m[x][y] = value;
	 }
	 
	 public float[] asArray() {
		 float[] array = new float[16];
		 array[0] = m[0][0];
		 array[1] = m[0][1];
		 array[2] = m[0][2];
		 array[3] = m[0][3];
		 array[4] = m[1][0];
		 array[5] = m[1][1];
		 array[6] = m[1][2];
		 array[7] = m[1][3];
		 array[8] = m[2][0];
		 array[9] = m[2][1];
		 array[10] = m[2][2];
		 array[11] = m[2][3];
		 array[12] = m[3][0];
		 array[13] = m[3][1];
		 array[14] = m[3][2];
		 array[15] = m[3][3];
		 
		 return array;
	 }
	 
	 public String toString(){
		 return	
		 	"["+m[0][0]+","+m[0][1]+","+m[0][2]+","+m[0][3]+"]\n"+
		 	"["+m[1][0]+","+m[1][1]+","+m[1][2]+","+m[1][3]+"]\n"+
		 	"["+m[2][0]+","+m[2][1]+","+m[2][2]+","+m[2][3]+"]\n"+
		    "["+m[3][0]+","+m[3][1]+","+m[3][2]+","+m[3][3]+"]\n";
	 }

	public Vector3f getPosition() {
		float x = m[0][3];
		float y = m[1][3];
		float z = m[2][3];
		
		return new Vector3f(x, y, z);
	}
}
