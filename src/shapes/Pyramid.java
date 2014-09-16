package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;

import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import general.PhongShader;
import general.Shader;
import general.ShapeNode;
import general.Vertex;

public class Pyramid extends ShapeNode {
	
	public Pyramid(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);
		
		
		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeAmbientBuffer(ambientData, vertices);
	}
	
	FloatBuffer ambientData;

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_P))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 120 * elapsed;
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		// Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 0, 1),
		// angle);
		Matrix modelMatrix = parentMatrix.mult(vecmath.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
//		getShader().activate();
		getShader().getModelMatrixUniform().set(getTransformation());

		// Enable the vertex data arrays (with indices 0 and 1). We use a vertex
		// position and a vertex color.
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		glVertexAttribPointer(Shader.getBaseAttribIdx(), 3, false, 0,
				colorData);
		glEnableVertexAttribArray(Shader.getBaseAttribIdx());
		
		
		
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		
		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		
		
	}
	
	protected void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}

}
