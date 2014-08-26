import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class popo {

	private int x = 0;
	private int y = 0;
	private boolean clicked = false;

	public popo() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("PIEP");
			Display.create();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// initialization code
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		while (!Display.isCloseRequested()) {

			glClear(GL_COLOR_BUFFER_BIT);

			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				System.exit(0);
			}

			int mouseY = Display.getHeight() - Mouse.getY() - 1;
			int mouseX = Mouse.getX();
			System.out.println("X: " + mouseX + ", Y: " + mouseY);
			if (Mouse.isButtonDown(0)) {
				if (mouseX >= x && mouseX <= x + 50 && mouseY >= y
						&& mouseY <= y + 50)
					clicked = true;
			}
			if (Mouse.isButtonDown(1) && clicked == true) {
				clicked = false;
			}
			// Geschwindigkeit der Bewegung
			int dx = Mouse.getDX();
			int dy = -Mouse.getDY(); // Aus irgendeinem Grund muss man
										// invertieren, da snst nicht korrekt
										// ist

			// render Code
			glBegin(GL_LINES);
			glVertex2i(100, 100);
			glVertex2i(200, 200);
			glEnd();

			glBegin(GL_QUADS);
			glVertex2i(x, y);
			glVertex2i(x + 50, y);
			glVertex2i(x + 50, y + 50);
			glVertex2i(x, y + 50);
			glEnd();

			if (clicked) {
				// x = Mouse.getX();
				// y = Display.getHeight() - Mouse.getY() - 1;
				x = x + dx;
				y = y + dy;
			}
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new popo();
	}
}
