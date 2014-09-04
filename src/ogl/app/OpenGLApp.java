/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;
import ogl.vecmath.Matrix;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * A simple framework for OpenGL applications.
 */
public final class OpenGLApp {

	private int width = 600;
	private int height = 600;

	private StopWatch time = new StopWatch();
	private Input input;
	private App application;
	private String title;
	private boolean multisampling = false;
	
//	private Shader shader;

	/**
	 * Create an OpenGL application with one window. The application behavior is
	 * controlled by the <code>App</code> object.
	 * 
	 * @param title
	 *            The string that is displayed in the title bar of the
	 *            application window.
	 * @param application
	 *            The application object.
	 */
	public OpenGLApp(String title, App application) {
		this(title, application, false);
	}

	/**
	 * Create an OpenGL application with one window. The application behavior is
	 * controlled by the <code>App</code> object.
	 * 
	 * @param title
	 *            The string that is displayed in the title bar of the
	 *            application window.
	 * @param multisampling
	 *            Multisampling is used if true. May not work on all platforms.
	 * @param application
	 *            The application object.
	 */
	public OpenGLApp(String title, App application, boolean multisampling) {
		this.title = title;
		this.application = application;
		this.multisampling = multisampling
				&& System.getProperty("os.name").equals("Mac OS X");

		System.out.println("LWJGL version " + Sys.getVersion() + " running on "
				+ System.getProperty("os.name") + " version "
				+ System.getProperty("os.version") + ".");

	}

	/**
	 * Start the application. The window is opened and the main loop is entered.
	 * This call does not return until the window is closed or an exception was
	 * caught.
	 */
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			if (multisampling)
				Display.create(new PixelFormat().withSamples(8));
			else
				Display.create();

			// Sync buffer swap with vertical sync. Results in 60 fps on my Mac
			// Book.
			Display.setSwapInterval(1);
			Display.setVSyncEnabled(true);

			input = new Input();
			application.init();
//			shader = new Shader();

			while (!Display.isCloseRequested()) {
				input.update();
				input.setWindowSize(width, height);
				
//				// Adjust the the viewport to the actual window size. This makes the
//				// rendered image fill the entire window.
//				glViewport(0, 0, width, height);
//
//				// Clear all buffers.
//				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//
//				// Assemble the transformation matrix that will be applied to all
//				// vertices in the vertex shader.
//				float aspect = (float) width / (float) height;
//
//				// The perspective projection. Camera space to NDC.
//				Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f,
//						100f);
//
//				// The inverse camera transformation. World space to camera space.
//				Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 10f),
//						vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));
//				
//				// Activate the shader program and set the transformation matricies to
//				// the
//				// uniform variables.
//				shader.activate();
//				shader.getViewMatrixUniform().set(viewMatrix);
//				shader.getProjectionMatrixUniform().set(projectionMatrix);
				
				
				application.simulate(time.elapsed(), input);
				application.display(width, height);
				Display.update();
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		} finally {
			Display.destroy();
		}
	}
}
