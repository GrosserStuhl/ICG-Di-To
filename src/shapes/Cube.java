package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import general.Mesh;
import general.ResourceLoader;
import general.Shader;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Cube extends ShapeNode implements App {

	public Cube(Vertex[] vertices, Shader shader) {
		super(vertices, shader);
	}

	private FloatBuffer positionData2;

	private FloatBuffer positionDataT;
	private FloatBuffer colorDataT;

	// Mesh DATA
	// private Vertex[] meshVertices;
	// private FloatBuffer positionDataM;
	// private FloatBuffer colorDataM;

	public void init() {

		// Prepare the vertex data arrays.
		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficently.
		positionData = positionBuffer(vertices.length);
		colorData = colorBuffer(vertices.length);
		finalizeBuffers(positionData, colorData, vertices);

		// Mesh blenderCube = ResourceLoader.loadMesh("monkeyMod.obj");
		// meshVertices = Vertex.testVertices(blenderCube.getPositionData(),
		// blenderCube.getColorData(), blenderCube.getFaceData());
		// positionDataM = positionBuffer(meshVertices.length);
		// colorDataM = colorBuffer(meshVertices.length);
		// finalizeBuffers(positionDataM, colorDataM, meshVertices);
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;
	}

	@Override
	public void display(int width, int height) {

		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1),
				angle);

		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		getShader().getModelMatrixUniform().set(modelMatrix);

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

		// getShader().getModelMatrixUniform().set(
		// modelMatrix.mult(vecmath.translationMatrix(-2, 0, 0)));
		// glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
		// positionDataT);
		// glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		// glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
		// colorDataT);
		// glEnableVertexAttribArray(Shader.getColorAttribIdx());

		// glDrawArrays(GL_TRIANGLES, 0, verticesT.length);

		// getShader().getModelMatrixUniform().set(
		// modelMatrix.mult(vecmath.translationMatrix(2, 0, 0)));
		// glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
		// positionDataM);
		// glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		// glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
		// colorDataM);
		// glEnableVertexAttribArray(Shader.getColorAttribIdx());
		//
		// glDrawArrays(GL_TRIANGLES, 0, meshVertices.length);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}

	}

}
