import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL12;


public class Test3D {

	float x,y;
	
	public Test3D() {
		this.x = 100;
		this.y = 200;
	}
	
	public static void main(String args[]){
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("3DTest");
			Display.create();
			
			Test3D t = new Test3D();
			t.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		        //glEnable bringt, dass eine bestimmte Sache aktiv wird. In diesem Fall der Depth Test.
				//Der Depth Test bringt, mach das die Depth aktiv wird.
				//Den MUSS man aktivieren wenn man das in 3D machen will. Mehr dazu unten im Code.
				glEnable(GL_DEPTH_TEST);
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				//gulPerspective bringt, dass 3D aktiv.
				//Parameter: fov, aspect, zNear, zFar
				//FOV sollte glaube ich jeder kennen! (http://de.wikipedia.org/wiki/FOV)
				//Der aspect ist einfach nur width/height.
				//zNear ist wie Nahe ein Objekt minimal sein muss um es zu rendern.
				//zNear ist wie Nahe ein Objekt maximal sein muss um es zu rendern.
				gluPerspective(45, (float)Display.getWidth()/(float)Display.getHeight(), 0.3f, 1000);
				glMatrixMode(GL_MODELVIEW);
				glTranslatef(0, 0, -10);
				
				//Sys.getTime() ist das selbe wie System.currentTimeMillis().
				long last = Sys.getTime();
				
				while(!Display.isCloseRequested()) {
					
					long now = Sys.getTime();
					//Was timeSinceLastFrame (tslf) ist hat Brotcrunsher schon erklärt.
					float tslf = ((float)now-last)/1000f;
					//Hier müssen wir ein | machen (Strg+Alt+>) und dann noch die Pixel die in der Depth (Tiefe) sind zu löschen.
					//Ups.. mir fällt gerade auf das ich diesen Befehl noch nicht erklärt habe. Das hole ich mal nach.
					//glClear mach das die Pixel gelöscht werden also auf Schwarz gesetzt werden.
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					glColor3f(1,1,1);
					//Jetzt kommt wieder was neues. Rotation!
					//mit glRotatef kann man etwas Rotieren lassen.
					//Das erste Parameter ist der speed.
					//Das zweite in welcher Richtung y rotiert werden soll (0 = gar nicht, 1 = im Uhrzeigersinn, -1 = gegen den Uhrzeigersinn)
					//Das dritte in welcher Richtung x rotiert werden soll.
					//Das vierte in welcher Richtung z rotiert werden soll.
					glRotatef(100f*tslf, 0, 1, -1);
					//Und hier kommt das eigentliche. Ein Würfel!
					//Diese Werde bei glVertex.. muss man leider auswändig lernen..
					glBegin(GL_QUADS);
					glColor3f(1,0,0);
					//Vordere Seite
					glVertex3f(-1,-1,1);
					glVertex3f(-1,1,1);
					glVertex3f(1,1,1);
					glVertex3f(1,-1,1);
					glColor3f(0,1,0);
					//Linke Seite
					glVertex3f(-1,-1,-1);
					glVertex3f(-1,-1,1);
					glVertex3f(-1,1,1);
					glVertex3f(-1,1,-1);
					glColor3f(0,0,1);
					//Rechte Seite
					glVertex3f(1,-1,-1);
					glVertex3f(1,-1,1);
					glVertex3f(1,1,1);
					glVertex3f(1,1,-1);
					glColor3f(1, 1, 0);
					//Untere Seite
					glVertex3f(-1,-1,-1);
					glVertex3f(1,-1,-1);
					glVertex3f(1,-1,1);
					glVertex3f(-1,-1,1);
					glColor3f(0, 1, 1);
					//Obere Seite
					glVertex3f(-1,1,-1);
					glVertex3f(1,1,-1);
					glVertex3f(1,1,1);
					glVertex3f(-1,1,1);
					glColor3f(1, 1, 1);
					//Hintere Seite
					glVertex3f(-1,-1,-1);
					glVertex3f(-1,1,-1);
					glVertex3f(1,1,-1);
					glVertex3f(1,-1,-1);
					;
					glEnd();	
					Display.update();
					last = now;
					
				}
 
		// close Display and stop internal update thread
		Display.destroy();
	}
	
	
}
