package tom;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera {

	
	
	private float x;
	private float y;
	private float z;
	private float rx;
	private float ry;
	private float rz;
	
	private float fov;
	private float aspect;
	private float near;
	private float far;
	
	
	//near = nearclip plane
	public Camera(float fov, float aspect, float near, float far){
		
		x = 0;
		y = 0;
		z = 0;
		
		rx = 0;
		ry = 0;
		rz = 0;
		
		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;
		initProjection();
	}
	
	private void initProjection(){
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov,aspect,near,far);
		glMatrixMode(GL_MODELVIEW);
		
		// will enable GL Depth Calculation,
		// without it GL doesnt know which shape to draw 
		// in front of which other
		// this uses Depth Buffers, we have to clear every frame
		glEnable(GL_DEPTH_TEST);
		
	}
	
	public void useView(){
		
		glRotatef(rx,1,0,0);
		glRotatef(ry,0,1,0);
		glRotatef(rz,0,0,1);
		glTranslatef(x,y,z);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * Should move the camera in a direction
	 * 
	 */
	public void move(float amt, float dir){
		
		z+= amt * Math.sin(Math.toRadians(ry + 90 *dir));
		x += amt * Math.cos(Math.toRadians(ry + 90 * dir));
	}
	
	
	public void rotateY(float amt){
		
		ry +=amt;
		
	}
	
}
