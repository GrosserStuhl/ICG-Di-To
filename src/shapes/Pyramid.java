package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import general.Shader;
import general.ShapeNode;
import general.Vertex;

public class Pyramid extends ShapeNode {

	public Pyramid(Vertex[] vertices, Shader shader) {
		super(vertices, shader);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_P))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 80 * elapsed;
	}

	@Override
	public void display(int width, int height) {

		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 0, 1),
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

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
	}

}