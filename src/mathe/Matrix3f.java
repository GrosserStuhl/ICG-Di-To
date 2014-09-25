package mathe;

import ogl.vecmath.Matrix;

public class Matrix3f {

	private float[][]m;

	public Matrix3f() {

	m = new float[3][3];
		
	}
	
	public Matrix3f initIdentity(){
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	
	
		return this;
	}
	
	
	
	public Matrix3f toMatrix3f(Matrix o){
		
		float[] m4f = new float[16];
		m4f = o.asArray();
		
		
		
		m[0][0] = m4f[0];	m[0][1] = m4f[4];	m[0][2] = m4f[8];	
		m[1][0] = m4f[1];	m[1][1] = m4f[5];	m[1][2] = m4f[9];	
		m[2][0] = m4f[2];	m[2][1] = m4f[6];	m[2][2] = m4f[10];	
	
		return this;
	}
	
//	public Matrix3f initTranslation(float x, float y, float z){
//		
//		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
//		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
//		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
//		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
//	
//		return this;
//	}
	
//	public Matrix4f mul(Matrix4f r){
//		Matrix4f res = new Matrix4f();
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				res.set(i, j,   m[i][0] * r.get(0, j) +
//								m[i][1] * r.get(1, j) +
//								m[i][2] * r.get(2, j) +
//								m[i][3] * r.get(3,j));
//			}
//		}
//		return res;
//		
//	}

//	public float[][] getM() {
//		
//		float[][] res = new float[4][4];
//		
//		for (int i = 0; i < 4; i++) 
//			for (int j = 0; j < 4; j++)
//				res[i][j] = m[i][j];
//				
//		
//		return res;
//	}
	
	public float get(int x , int y){
		return m[x][y];
	}

	public void setM(float[][] m) {
		this.m = m;
	}
	
	public void set(int x, int y, float value){
		m[x][y] = value;
	}
	
	
}
