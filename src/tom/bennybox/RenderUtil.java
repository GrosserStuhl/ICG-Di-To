package tom.bennybox;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderUtil {

	public static void clearScreen(){
		
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static void initGraphics(){
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		
		
		//tells cull_face you draw everything clockwise
		glFrontFace(GL_CW);
		//get rid of back face
		glCullFace(GL_BACK);
		//enables FaceCulling
		glEnable(GL_CULL_FACE);
		// important for drawing more than one thing
		// gl gets to know which object to draw on top of which
		glEnable(GL_DEPTH_TEST);
		
		// TODO: Depth clamp for later;
		
		
		// 3 gamma correction
		glEnable(GL_FRAMEBUFFER_SRGB);
		
		
	}
	
}
