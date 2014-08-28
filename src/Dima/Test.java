package Dima;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Test {

	public static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void gameLoop() {
		Camera cam = new Camera(70, (float) Display.getWidth()
				/ (float) Display.getHeight(), 0.3f, 1000);
		float translation = 0;
		Mouse.setGrabbed(true);
		boolean forward, back, left, right;

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();

			forward = Keyboard.isKeyDown(Keyboard.KEY_W);
			back = Keyboard.isKeyDown(Keyboard.KEY_S);
			left = Keyboard.isKeyDown(Keyboard.KEY_A);
			right = Keyboard.isKeyDown(Keyboard.KEY_D);

			if (forward)
				cam.moveOnZ(0.01f);
			if (back)
				cam.moveOnZ(-0.01f);
			if (left)
				cam.moveOnX(0.01f);
			if (right)
				cam.moveOnX(-0.01f);
			if (Keyboard.isKeyDown(Keyboard.KEY_E))
				cam.rotateY(0.05f);
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
				cam.rotateY(-0.05f);
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				cleanUp();
			if (Keyboard.isKeyDown(Keyboard.KEY_1))
				gluLookAt(1, 1, 1, 2, 2, 2, 1, 1, 1);

			cam.rotateY((float) Mouse.getDX() / 3);
			cam.rotateX((float) -Mouse.getDY() / 3);

			glPushMatrix();
			{
				glColor3f(1.0f, 0.5f, 0f);
				glTranslatef(0, 0, -10);
				glRotatef(translation, 1, 1, 0);
				glBegin(GL_QUADS);
				{
					glColor3f(1f, 0f, 0f);
					glVertex3f(-1, -1, 1);
					glVertex3f(1, -1, 1);
					glVertex3f(1, 1, 1);
					glVertex3f(-1, 1, 1);

					// BackFace
					glColor3f(0f, 1f, 0f);
					glVertex3f(-1, -1, -1);
					glVertex3f(-1, 1, -1);
					glVertex3f(1, 1, -1);
					glVertex3f(1, -1, -1);

					// BottomFace
					glColor3f(0f, 0f, 1f);
					glVertex3f(-1, -1, -1);
					glVertex3f(-1, -1, 1);
					glVertex3f(-1, 1, 1);
					glVertex3f(-1, 1, -1);

					// TopFace
					glColor3f(1f, 1f, 0f);
					glVertex3f(1, -1, -1);
					glVertex3f(1, -1, 1);
					glVertex3f(1, 1, 1);
					glVertex3f(1, 1, -1);

					// LeftFace
					glColor3f(0f, 1f, 1f);
					glVertex3f(-1, -1, -1);
					glVertex3f(1, -1, -1);
					glVertex3f(1, -1, 1);
					glVertex3f(-1, -1, 1);

					// Right Face
					glColor3f(1f, 0f, 1f);
					glVertex3f(-1, 1, -1);
					glVertex3f(1, 1, -1);
					glVertex3f(1, 1, 1);
					glVertex3f(-1, 1, 1);
				}
				glEnd();
			}
			glPopMatrix();

			glColor3f(1.0f, 0.5f, 1f);
			glBegin(GL_QUADS);
			glVertex3f(-10, -10, -10);
			glVertex3f(10, -10, -10);
			glVertex3f(10, -10, 10);
			glVertex3f(-10, -10, 10);
			glEnd();

			translation += 0.01f;
			Display.update();
		}
	}

	public static void cleanUp() {
		Display.destroy();
		System.exit(0);
	}

	public static void main(String[] args) {
		initDisplay();
		gameLoop();
		cleanUp();
	}
}
