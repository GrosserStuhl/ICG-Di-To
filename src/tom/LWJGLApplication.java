package tom;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
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
		
		while(!Display.isCloseRequested()){
			
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			
			
			glPushMatrix();
			{
			
			glBegin(GL_QUADS);
			{
				glVertex3f(0,0,0);
				glVertex3f(0,1,0);
				glVertex3f(1,1,0);
				glVertex3f(1,0,0);
				
				
			}
			glEnd();
			}
			
			
			
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
