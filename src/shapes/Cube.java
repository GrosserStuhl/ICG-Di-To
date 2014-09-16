package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import general.Shader;
import general.ShapeNode;
import general.Vertex;
import ogl.app.App;
import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;

public class Cube extends ShapeNode implements App {

	private Vector translation;
	
	public Cube(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader);
		
		this.translation = translation;
		
	}
	
	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		
		
		Matrix modelMatrix = parentMatrix.mult(vecmath.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);
		
		
		
		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
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
		
		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());

	}
}
