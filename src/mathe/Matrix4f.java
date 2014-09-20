package mathe;

public class Matrix4f {

	private float[][] m;
	
	
	public Matrix4f() {
		m = new float[4][4];
		
	}
	
	public Matrix4f initIdentity(){
		
		m[0][0] = 1;    m[0][1] = 0;   m[0][2] = 0;   m[0][3] = 0;
		m[1][0] = 0;    m[1][1] = 1;   m[1][2] = 0;   m[1][3] = 0;
		m[2][0] = 0;    m[2][1] = 0;   m[2][2] = 1;   m[2][3] = 0;
		m[3][0] = 0;    m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
		
		
		return this;
		
	}
	
	public Matrix4f initTranslation(float x, float y , float z){
		
		m[0][0] = 1;    m[0][1] = 0;   m[0][2] = 0;   m[0][3] = x;
		m[1][0] = 0;    m[1][1] = 1;   m[1][2] = 0;   m[1][3] = y;
		m[2][0] = 0;    m[2][1] = 0;   m[2][2] = 1;   m[2][3] = z;
		m[3][0] = 0;    m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;
		
		return this;
		
	}
	
	public Matrix4f initRotation(float x, float y , float z){
		
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		
		rz.m[0][0] = (float)Math.cos(z);    rz.m[0][1] = -(float) Math.sin(z);   rz.m[0][2] = 0;   					 rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z);    rz.m[1][1] = (float)Math.cos(z);   	 rz.m[1][2] = 0;   					 rz.m[1][3] = 0;
		rz.m[2][0] = 0;    					rz.m[2][1] = 0;   					 rz.m[2][2] = 1;   					 rz.m[2][3] = 0;
		rz.m[3][0] = 0;    					rz.m[3][1] = 0;   					 rz.m[3][2] = 0;   					 rz.m[3][3] = 1;
		
		rx.m[0][0] = 1;    					rx.m[0][1] = 0;                      rx.m[0][2] = 0;   					 rx.m[0][3] = 0;
		rx.m[1][0] = 0;   				    rx.m[1][1] = (float)Math.cos(x);     rx.m[1][2] = -(float) Math.sin(x);  rx.m[1][3] = 0;
		rx.m[2][0] = 0;                     rx.m[2][1] = (float)Math.cos(x);     rx.m[2][2] = (float)Math.cos(x);    rx.m[2][3] = 0;
		rx.m[3][0] = 0;                     rx.m[3][1] = 0;                      rx.m[3][2] = 0;   					 rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y);    ry.m[0][1] = 0;                      ry.m[0][2] = -(float)Math.sin(y);   ry.m[0][3] = 0;
		ry.m[1][0] = 0;    					ry.m[1][1] = 1;                      ry.m[1][2] = 0;   				     ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);    ry.m[2][1] = 0;                      ry.m[2][2] = (float)Math.cos(y);    ry.m[2][3] = 0;
		ry.m[3][0] = 0;    					ry.m[3][1] = 0;                      ry.m[3][2] = 0;   				     ry.m[3][3] = 1;
		
		m = rz.mul(ry.mul(rx)).getM();
		
		return this;
	}
	
	public Matrix4f InitScale(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f initProjection(float fov, float width, float height, float zNear, float zFar){
		
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		float aspectRatio = width/height;
		
		m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 1;	m[3][3] = 0;
		return this;
		
	}
	
	
	// Produkt der Adjunkte und der Determinanten
    public Matrix4f inverse(){
    	
    	   m[0][0] = m[1][2]*m[2][3]*m[3][1] - m[1][3]*m[2][2]*m[3][1] + m[1][3]*m[2][1]*m[3][2] - m[1][1]*m[2][3]*m[3][2] - m[1][2]*m[2][1]*m[3][3] + m[1][1]*m[2][2]*m[3][3];
    	   m[0][1] = m[0][3]*m[2][2]*m[3][1] - m[0][2]*m[2][3]*m[3][1] - m[0][3]*m[2][1]*m[3][2] + m[0][1]*m[2][3]*m[3][2] + m[0][2]*m[2][1]*m[3][3] - m[0][1]*m[2][2]*m[3][3];
    	   m[0][2] = m[0][2]*m[1][3]*m[3][1] - m[0][3]*m[1][2]*m[3][1] + m[0][3]*m[1][1]*m[3][2] - m[0][1]*m[1][3]*m[3][2] - m[0][2]*m[1][1]*m[3][3] + m[0][1]*m[1][2]*m[3][3];
    	   m[0][3] = m[0][3]*m[1][2]*m[2][1] - m[0][2]*m[1][3]*m[2][1] - m[0][3]*m[1][1]*m[2][2] + m[0][1]*m[1][3]*m[2][2] + m[0][2]*m[1][1]*m[2][3] - m[0][1]*m[1][2]*m[2][3];
    	   m[1][0] = m[1][3]*m[2][2]*m[3][0] - m[1][2]*m[2][3]*m[3][0] - m[1][3]*m[2][0]*m[3][2] + m[1][0]*m[2][3]*m[3][2] + m[1][2]*m[2][0]*m[3][3] - m[1][0]*m[2][2]*m[3][3];
    	   m[1][1] = m[0][2]*m[2][3]*m[3][0] - m[0][3]*m[2][2]*m[3][0] + m[0][3]*m[2][0]*m[3][2] - m[0][0]*m[2][3]*m[3][2] - m[0][2]*m[2][0]*m[3][3] + m[0][0]*m[2][2]*m[3][3];
    	   m[1][2] = m[0][3]*m[1][2]*m[3][0] - m[0][2]*m[1][3]*m[3][0] - m[0][3]*m[1][0]*m[3][2] + m[0][0]*m[1][3]*m[3][2] + m[0][2]*m[1][0]*m[3][3] - m[0][0]*m[1][2]*m[3][3];
    	   m[1][3] = m[0][2]*m[1][3]*m[2][0] - m[0][3]*m[1][2]*m[2][0] + m[0][3]*m[1][0]*m[2][2] - m[0][0]*m[1][3]*m[2][2] - m[0][2]*m[1][0]*m[2][3] + m[0][0]*m[1][2]*m[2][3];
    	   m[2][0] = m[1][1]*m[2][3]*m[3][0] - m[1][3]*m[2][1]*m[3][0] + m[1][3]*m[2][0]*m[3][1] - m[1][0]*m[2][3]*m[3][1] - m[1][1]*m[2][0]*m[3][3] + m[1][0]*m[2][1]*m[3][3];
    	   m[2][1] = m[0][3]*m[2][1]*m[3][0] - m[0][1]*m[2][3]*m[3][0] - m[0][3]*m[2][0]*m[3][1] + m[0][0]*m[2][3]*m[3][1] + m[0][1]*m[2][0]*m[3][3] - m[0][0]*m[2][1]*m[3][3];
    	   m[2][2] = m[0][1]*m[1][3]*m[3][0] - m[0][3]*m[1][1]*m[3][0] + m[0][3]*m[1][0]*m[3][1] - m[0][0]*m[1][3]*m[3][1] - m[0][1]*m[1][0]*m[3][3] + m[0][0]*m[1][1]*m[3][3];
    	   m[2][3] = m[0][3]*m[1][1]*m[2][0] - m[0][1]*m[1][3]*m[2][0] - m[0][3]*m[1][0]*m[2][1] + m[0][0]*m[1][3]*m[2][1] + m[0][1]*m[1][0]*m[2][3] - m[0][0]*m[1][1]*m[2][3];
    	   m[3][0] = m[1][2]*m[2][1]*m[3][0] - m[1][1]*m[2][2]*m[3][0] - m[1][2]*m[2][0]*m[3][1] + m[1][0]*m[2][2]*m[3][1] + m[1][1]*m[2][0]*m[3][2] - m[1][0]*m[2][1]*m[3][2];
    	   m[3][1] = m[0][1]*m[2][2]*m[3][0] - m[0][2]*m[2][1]*m[3][0] + m[0][2]*m[2][0]*m[3][1] - m[0][0]*m[2][2]*m[3][1] - m[0][1]*m[2][0]*m[3][2] + m[0][0]*m[2][1]*m[3][2];
    	   m[3][2] = m[0][2]*m[1][1]*m[3][0] - m[0][1]*m[1][2]*m[3][0] - m[0][2]*m[1][0]*m[3][1] + m[0][0]*m[1][2]*m[3][1] + m[0][1]*m[1][0]*m[3][2] - m[0][0]*m[1][1]*m[3][2];
    	   m[3][3] = m[0][1]*m[1][2]*m[2][0] - m[0][2]*m[1][1]*m[2][0] + m[0][2]*m[1][0]*m[2][1] - m[0][0]*m[1][2]*m[2][1] - m[0][1]*m[1][0]*m[2][2] + m[0][0]*m[1][1]*m[2][2];
    	
    	   // berechnet die Determinante 
    	   float det = 1/determinant(this);
    	   
    	   m[0][0] *= det;    m[0][1] *= det;   m[0][2] *= det;   m[0][3] *= det;
   		   m[1][0] *= det;    m[1][1] *= det;   m[1][2] *= det;   m[1][3] *= det;
   		   m[2][0] *= det;    m[2][1] *= det;   m[2][2] *= det;   m[2][3] *= det;
   		   m[3][0] *= det;    m[3][1] *= det;   m[3][2] *= det;   m[3][3] *= det;
    	   
    	return this;
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
				m[i][j] = res[i][j];
		
		return this;
    }
    
	public Matrix4f mul(Matrix4f r){
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
	 
	 public String toString(){
		 return	
		 	"["+m[0][0]+","+m[0][1]+","+m[0][2]+","+m[0][3]+"]\n"+
		 	"["+m[1][0]+","+m[1][1]+","+m[1][2]+","+m[1][3]+"]\n"+
		 	"["+m[2][0]+","+m[2][1]+","+m[2][2]+","+m[2][3]+"]\n"+
		    "["+m[3][0]+","+m[3][1]+","+m[3][2]+","+m[3][3]+"]\n";
		 
		 
	 }
	 
	
}
