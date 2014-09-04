import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

public class Cube extends Node implements App{

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	float w = 0.5f;
	float h = 0.5f;
	float d = 0.5f;

	private Shader shader;
	

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Cube()).start();
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
			vec(w2, h2, d2)}; 

	private Vector[] s = { 
			//hinten links-unten
			vec(-w2, -h2, -d2), 
			//hinten rechts-unten
			vec(w2, -h2, -d2),
			//hinten rechts-oben
			vec(w2, h2, -d2), 
			//hinten links-oben
			vec(-w2, h2, -d2), 
			
			//vorne rechts-unten
			vec(w2, -h2, d2),
			//vorne links-unten
			vec(-w2, -h2, d2),
			//vorne links-oben
			vec(-w2, h2, d2),
			//vorne rechts-oben
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
	
	private Vector[] t = {
			//hinten links-unten
			vec(-w2, -h2, -d2),
			//hinten rechts-unten
			vec(w2, -h2, -d2),
			//vorne rechts-unten
			vec(w2, -h2, d2),
			//vorne links-unten
			vec(-w2, -h2, d2),
			
			//nach oben
			vec(0,d2,0)
			
	};
	
	// The colors of the cube vertices.
		private Color[] colorT = { 
				col(1, 0, 0), 
				col(1, 0, 0), 
				col(1, 0, 0),
				col(1, 0, 0), 
				col(0, 1, 0) };

	

	private FloatBuffer positionData;
	private FloatBuffer positionData2;
	
	private FloatBuffer positionDataT;
	private FloatBuffer colorData;
	private FloatBuffer colorDataT;
	private Vertex[] vertices;
	private Vertex[] vertices2;
	
	private Vertex[] verticesT;

	// Initialize the rotation angle of the cube.
	private float angle = 0;

	public void init() {

		shader = new Shader();
		vertices = Vertex.cubeVertices(p, c);
		vertices2 = Vertex.cubeVertices(s, c);
		
		verticesT = Vertex.triangleVertices(t, colorT);

		// Prepare the vertex data arrays.
		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficently.
		positionData = positionBuffer(vertices.length);
		colorData = colorBuffer(vertices.length);
		finalizeBuffers(positionData, colorData, vertices);
		
		

		positionData2 = positionBuffer(vertices2.length);
		finalizeBuffers(positionData2, colorData, vertices2);
		
		positionDataT = positionBuffer(verticesT.length);
		colorDataT = colorBuffer(verticesT.length);
		finalizeBuffers(positionDataT, colorDataT, verticesT);
		
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f,
				100f);

		// The inverse camera transformation. World space to camera space.
		Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 10f),
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
		glDrawArrays(GL_QUADS, 0, vertices.length);

		shader.getModelMatrixUniform().set(
				modelMatrix.mult(vecmath.translationMatrix(2, 0, 0)));
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionDataT);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
				colorDataT);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());

		glDrawArrays(GL_QUADS, 0, verticesT.length);
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	public FloatBuffer positionBuffer(int verticesLength){
		return BufferUtils.createFloatBuffer(verticesLength
				* vecmath.vectorSize());
	}
	
	public FloatBuffer colorBuffer(int verticesLength){
		return BufferUtils.createFloatBuffer(verticesLength
				* vecmath.colorSize());
	}
	
	public void finalizeBuffers(FloatBuffer positionData, FloatBuffer colorData, Vertex[] vertices){
		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

}
