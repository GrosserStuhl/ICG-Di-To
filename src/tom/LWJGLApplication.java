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
		
		while(!Display.isCloseRequested()){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
				cam.moveForward();
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
				cam.moveBackward();
			
			//Clears the Color Buffer
			glClear(GL_COLOR_BUFFER_BIT);
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
					// moves back 10 on z-axis
					glTranslatef(0,0,-10);
					glRotatef(x,1,1,0);
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
			Display.sync(60);
			
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
