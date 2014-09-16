package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import general.Node;
import general.PhongShader;
import general.Shader;
import general.ShapeNode;
import general.Vertex;
import ogl.app.App;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Cube extends ShapeNode implements App {

	public Cube(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);
		
		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeAmbientBuffer(ambientData, vertices);
	}
	
	FloatBuffer ambientData;

	@Override
	public void display(int width, int height, Matrix parentMatrix) {

		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
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

//		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
//				colorData);
//		glEnableVertexAttribArray(Shader.getColorAttribIdx());
		
		
		
		glVertexAttribPointer(Shader.getBaseAttribIdx(), 3, false, 0,
				colorData);
		glEnableVertexAttribArray(Shader.getBaseAttribIdx());
		
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());


		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getBaseAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
	}
	
	protected void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}
	
	
	
}
