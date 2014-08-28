package tom;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLApplication {

	public static void main(String args[]){
		
		initDisplay();
		gameLoop();
		cleanUp();
		
	}
	
	public static void gameLoop(){
		
		Camera cam = new Camera(70,(float) Display.getWidth()/ (float)Display.getHeight(),0.3f,1000);
		float x = 0;
		
		boolean temp = false;
		
		while(!Display.isCloseRequested()){
			
			boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
			boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
			boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
			boolean right =  Keyboard.isKeyDown(Keyboard.KEY_D);
			
			
			
			if(forward)
				cam.move(0.01f,1);
			if(backward)
				cam.move(-0.01f,1);
			if(left)
				cam.move(0.01f,0);//cam.rotateY(-0.1f);
			if(right)
				cam.move(-0.01f,0);//cam.rotateY(0.1f);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				cam.rotateY(-0.1f);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				cam.rotateY(0.1f);
			
			
			//Clears the Color Buffer and the Depth Buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			//resets the ModelViewMatrix
			glLoadIdentity();
			cam.useView();
			
			// put it in a matrix
			// this will make sure that the translation and rotation
			// will only affect anything between both commands:
			// glPushMatrix(){rotation,...} glPopMatrix()
			glPushMatrix();
			{
					glColor3f(1.0f,0.5f,0f);
					glTranslatef(0,0,-10);
					//glRotatef(x,1,1,0);
					
					// enables diagonal movement  and rotates obejct
					if((forward && left) || (forward && right) || (backward && left) || (backward && right) ){
						temp = true;
					}
					
					if(temp)
						glRotatef(45,0,1,0);
					
					glBegin(GL_QUADS);
					{
						// FrontFace (drawn from x to y)
						glColor3f(1f,0f,0f);
						glVertex3f(-1,-1,1);
						glVertex3f(-1,1,1);
						glVertex3f(1,1,1);
						glVertex3f(1,-1,1);
						
						// BackFace (same thing, only negative z)
						glColor3f(0f,1f,0f);
						glVertex3f(-1,-1,-1);
						glVertex3f(-1,1,-1);
						glVertex3f(1,1,-1);
						glVertex3f(1,-1,-1);
						
						// BottomFace (drawn from y to z)
						glColor3f(0f,0f,1f);
						glVertex3f(-1,-1,-1);
						glVertex3f(-1,-1,1);
						glVertex3f(-1,1,1);
						glVertex3f(-1,1,-1);
						
						// TopFace (drawn from y to z)
						glColor3f(1f,1f,0f);
						glVertex3f(1,-1,-1);
						glVertex3f(1,-1,1);
						glVertex3f(1,1,1);
						glVertex3f(1,1,-1);
						
						//LeftFace (drawn from x to z)
						glColor3f(0f,1f,1f);
						glVertex3f(-1,-1,-1);
						glVertex3f(1,-1,-1);
						glVertex3f(1,-1,1);
						glVertex3f(-1,-1,1);
						
						//RightFace
						glColor3f(1f,0f,1f);
						glVertex3f(-1,1,-1);
						glVertex3f(1,1,-1);
						glVertex3f(1,1,1);
						glVertex3f(-1,1,1);
						
						
					}
					glEnd();
			}
			glPopMatrix();
			
			x+=1f;
			
			Display.update();
			
			
		}
		
		
	}
	
    public static void cleanUp(){
		
		Display.destroy();
		
	}
	
	public static void initDisplay(){
		
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			
		}
		catch(LWJGLException ex){
			Logger.getLogger(LWJGLApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
