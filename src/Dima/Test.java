package Dima;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

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
		Texture wood = null;
		try {
			wood = TextureLoader.getTexture("jpg", new FileInputStream(
					new File("res/wood.jpg")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Camera cam = new Camera(70, (float) Display.getWidth()
				/ (float) Display.getHeight(), 0.3f, 1000);
		float translation = 0;
		boolean forward, back, left, right;

		Mouse.setGrabbed(true);
		Font awtFont = new Font("Arial", Font.BOLD, 40);
		TrueTypeFont font = new TrueTypeFont(awtFont, true);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			System.out.println(cam.getX() + " " + cam.getZ());

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

//			glPushMatrix();
//			glLoadIdentity();
//			glDisable(GL_LIGHTING);
//			font.drawString(20, 10, "bobo", Color.yellow);
//			glEnable(GL_LIGHTING);
//			glPopMatrix();

			glPushMatrix();
			{
				glColor3f(1.0f, 0.5f, 0f);
				glTranslatef(0, 0, -10);
				glRotatef(translation, 1, 1, 0);

				wood.bind();

				glBegin(GL_QUADS);
				{
					// glColor3f(1f, 0f, 0f);
					glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
					glTexCoord2f(0, 0);
					glVertex3f(-1, -1, 1);
					glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
					glTexCoord2f(0, 1);
					glVertex3f(1, -1, 1);
					glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
					glTexCoord2f(1, 1);
					glVertex3f(1, 1, 1);
					glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
					glTexCoord2f(1, 0);
					glVertex3f(-1, 1, 1);

					// BackFace
					// glColor3f(0f, 1f, 0f);
					glTexCoord2f(0, 0);
					glVertex3f(-1, -1, -1);
					glTexCoord2f(0, 1);
					glVertex3f(-1, 1, -1);
					glTexCoord2f(1, 1);
					glVertex3f(1, 1, -1);
					glTexCoord2f(1, 0);
					glVertex3f(1, -1, -1);

					// BottomFace
					// glColor3f(0f, 0f, 1f);
					glTexCoord2f(0, 0);
					glVertex3f(-1, -1, -1);
					glTexCoord2f(0, 1);
					glVertex3f(-1, -1, 1);
					glTexCoord2f(1, 1);
					glVertex3f(-1, 1, 1);
					glTexCoord2f(1, 0);
					glVertex3f(-1, 1, -1);

					// TopFace
					// glColor3f(1f, 1f, 0f);
					glTexCoord2f(0, 0);
					glVertex3f(1, -1, -1);
					glTexCoord2f(0, 1);
					glVertex3f(1, -1, 1);
					glTexCoord2f(1, 1);
					glVertex3f(1, 1, 1);
					glTexCoord2f(1, 0);
					glVertex3f(1, 1, -1);

					// LeftFace
					// glColor3f(0f, 1f, 1f);
					glTexCoord2f(0, 0);
					glVertex3f(-1, -1, -1);
					glTexCoord2f(0, 1);
					glVertex3f(1, -1, -1);
					glTexCoord2f(1, 1);
					glVertex3f(1, -1, 1);
					glTexCoord2f(1, 0);
					glVertex3f(-1, -1, 1);

					// Right Face
					// glColor3f(1f, 0f, 1f);
					glTexCoord2f(0, 0);
					glVertex3f(-1, 1, -1);
					glTexCoord2f(0, 1);
					glVertex3f(1, 1, -1);
					glTexCoord2f(1, 1);
					glVertex3f(1, 1, 1);
					glTexCoord2f(1, 0);
					glVertex3f(-1, 1, 1);
				}
				glEnd();
			}
			glPopMatrix();

			// String text = "bobo";
			// ByteBuffer buffer = BufferUtils.createByteBuffer(text.length());
			// buffer.put(text.getBytes());
			// buffer.flip();
			// glCallLists(buffer);
			// glRasterPos2f(30.0f, 10.0f);

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
