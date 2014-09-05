package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;
import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shapes.Cube;

public class Root extends Node implements App {

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Root()).start();
	}

	private Shader shader;

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	private Vertex[] vertices;
	private Vertex[] vertices2;

	private Vertex[] verticesT;

	// Make construction of vectors easy on the eyes.
	private Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}

	// Make construction of colors easy on the eyes.
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
	}

	//
	// 6 ------- 7
	// / | / |
	// 3 ------- 2 |
	// | | | |
	// | 5 ----- |- 4
	// | / | /
	// 0 ------- 1
	//

	// The positions of the cube vertices.
	private Vector[] p = { vec(-w2, -h2, -d2), vec(w2, -h2, -d2),
			vec(w2, h2, -d2), vec(-w2, h2, -d2), vec(w2, -h2, d2),
			vec(-w2, -h2, d2), vec(-w2, h2, d2), vec(w2, h2, d2) };

	private Vector[] s = {
			// hinten links-unten
			vec(-w2, -h2, -d2),
			// hinten rechts-unten
			vec(w2, -h2, -d2),
			// hinten rechts-oben
			vec(w2, h2, -d2),
			// hinten links-oben
			vec(-w2, h2, -d2),

			// vorne rechts-unten
			vec(w2, -h2, d2),
			// vorne links-unten
			vec(-w2, -h2, d2),
			// vorne links-oben
			vec(-w2, h2, d2),
			// vorne rechts-oben
			vec(w2, h2, d2) };

	// The colors of the cube vertices.
	private Color[] c = { col(1, 0, 0), col(1, 0, 0), col(1, 0, 0),
			col(1, 0, 0), col(0, 1, 0), col(0, 1, 0), col(0, 1, 0),
			col(0, 1, 0) };

	private Vector[] t = {
			// hinten links-unten
			vec(-w2, -h2, -d2),
			// hinten rechts-unten
			vec(w2, -h2, -d2),
			// vorne rechts-unten
			vec(w2, -h2, d2),
			// vorne links-unten
			vec(-w2, -h2, d2),

			// nach oben
			vec(0, d2, 0)

	};

	// The colors of the cube vertices.
	private Color[] colorT = { col(1, 0, 0), col(1, 0, 0), col(1, 0, 0),
			col(1, 0, 0), col(0, 1, 0) };

	public Root() {

	}

	@Override
	public void init() {

		shader = new Shader();

		vertices = Vertex.cubeVertices(p, c);
		vertices2 = Vertex.cubeVertices(s, c);

		verticesT = Vertex.triangleVertices(t, colorT);

		Cube cube = new Cube(vertices, shader);
		this.addNode(cube);

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		for (Node child : getChildNodes()) {
			child.simulate(elapsed, input);
		}
	}

	@Override
	public void display(int width, int height) {
		// Adjust the the viewport to the actual window size. This
		// makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to
		// all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f,
				100f);

		// The inverse camera transformation. World space to camera
		// space.
		Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 10f),
				vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));

		// Activate the shader program and set the transformation
		// matricies to
		// the
		// uniform variables.
		shader.activate();
		shader.getViewMatrixUniform().set(viewMatrix);
		shader.getProjectionMatrixUniform().set(projectionMatrix);

		for (Node child : getChildNodes()) {
			child.display(width, height);
		}
	}

}
