import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.MatrixUniform;
import ogl.app.OpenGLApp;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Cube extends Node implements App {

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	private Shader shader;

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Cube()).start();
	}

	// Auxillary class to represent a single vertex.
	private class Vertex {
		Vector position;
		Color color;

		Vertex(Vector p, Color c) {
			position = p;
			color = c;
		}
	}

	// Make construction of vertices easy on the eyes.
	private Vertex v(Vector p, Color c) {
		return new Vertex(p, c);
	}

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
	private Vector[] p = { 
			vec(-w2, -h2, -d2), 
			vec(w2, -h2, -d2),
			vec(w2, h2, -d2), 
			vec(-w2, h2, -d2), 
			vec(w2, -h2, d2),
			vec(-w2, -h2, d2), 
			vec(-w2, h2, d2), 
			vec(w2, h2, d2) };
	
	
	
	// The colors of the cube vertices.
	private Color[] c = { 
			col(1, 0, 0), 
			col(1, 0, 0), 
			col(1, 0, 0),
			col(1, 0, 0), 
			col(0, 1, 0), 
			col(0, 1, 0), 
			col(0, 1, 0),
			col(0, 1, 0) };

	private Vertex[] vertices = {
			// front
			v(p[0], c[0]), v(p[1], c[1]), v(p[2], c[2]), v(p[3], c[3]),
			// back
			v(p[4], c[4]), v(p[5], c[5]), v(p[6], c[6]), v(p[7], c[7]),
			// right
			v(p[1], c[1]), v(p[4], c[4]), v(p[7], c[7]), v(p[2], c[2]),
			// top
			v(p[3], c[3]), v(p[2], c[2]), v(p[7], c[7]), v(p[6], c[6]),
			// left
			v(p[5], c[5]), v(p[0], c[0]), v(p[3], c[3]), v(p[6], c[6]),
			// bottom
			v(p[5], c[5]), v(p[4], c[4]), v(p[1], c[1]), v(p[0], c[0]) };

	private FloatBuffer positionData;
	private FloatBuffer colorData;

	// Initialize the rotation angle of the cube.
	private float angle = 0;

	public void init() {

		shader = new Shader();

		// Prepare the vertex data arrays.
		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficently.
		positionData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.vectorSize());
		colorData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.colorSize());

		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 90 * elapsed;
	}

	@Override
	public void display(int width, int height) {

		// Adjust the the viewport to the actual window size. This makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f,
				100f);

		// The inverse camera transformation. World space to camera space.
		Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 3f),
				vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));

		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1),
				angle);

		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		shader.activate();
		shader.getViewMatrixUniform().set(viewMatrix);
		shader.getProjectionMatrixUniform().set(projectionMatrix);
		shader.getModelMatrixUniform().set(modelMatrix);

		// Enable the vertex data arrays (with indices 0 and 1). We use a vertex
		// position and a vertex color.
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
				colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL11.GL_QUADS, 0, vertices.length);
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

}
