package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;

import shader.PhongShader;
import shader.Shader;

public class OBJMesh extends ShapeNode implements App {

	private FloatBuffer positionData;
	private FloatBuffer colorData;

	public OBJMesh(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);

	}

	
	@Override
	public void init() {
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		finalizeBuffer(positionData, colorData, vertices);
		
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_B))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;

	}


	public void finalizeBuffer(FloatBuffer position, FloatBuffer color,
			Vertex[] vertices) {
		for (Vertex v : vertices) {
			positionData.put(v.getPosition().asArray());
			colorData.put(v.getColor().asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

	
	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		
		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		Matrix viewMatrix = getShader().getViewMatrixUniform().getMatrix();
		Matrix normalMatrix = (viewMatrix.mult(modelMatrix).invertRigid().transpose());
		getShader().getNormalMatrixUniform().set(normalMatrix);
		
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		
		// color of cube
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0, colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());


		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
	}

}
