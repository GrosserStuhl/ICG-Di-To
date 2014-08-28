package Dima;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class Starsky {

	private Point[] points;
	private float speed = 0.0f;
	private boolean flightMode = false;

	public Starsky() {
		init();
		runLoop();
	}

	private void init() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Starsky");
			Display.create();
		} catch (Exception e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45,
				(float) Display.getWidth() / (float) Display.getHeight(),
				0.001f, 100);

		glMatrixMode(GL_MODELVIEW);

		// glEnable(GL_DEPTH_TEST);

		points = new Point[1000];
		Random random = new Random();
		for (int i = 0; i < points.length; i++) {
			// x zwischen -50 und 50
			// y zwischen -50 und 50
			// z zwischen -200 und 0
			points[i] = new Point((random.nextFloat() - 0.5f) * 100,
					(random.nextFloat() - 0.5f) * 100,
					(random.nextInt(200) - 200));
		}
	}

	private void runLoop() {

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glTranslatef(0, 0, speed);

			glBegin(GL_POINTS);
			for (Point p : points) {
				glVertex3f(p.x, p.y, p.z);
			}
			glEnd();

			calculateSpeed();

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				speed = 0.0f;
				glLoadIdentity();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
				System.out.println("Mode Changed");
				flightMode = !flightMode;
			}

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
		System.exit(0);
	}

	private void calculateSpeed() {
		if (flightMode) {
			speed = speed + (float) (Mouse.getDWheel() / 100);
		} else {
			speed = (float) (Mouse.getDWheel() / 100);
		}
	}

	private class Point {
		float x, y, z;

		public Point(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public static void main(String[] args) {
		new Starsky();
	}
}
