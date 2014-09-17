package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

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
		
		diffuseColor = BufferUtils.createFloatBuffer(vertices.length * 3);
		diffuseIntensity = BufferUtils.createFloatBuffer(vertices.length * 1);
		diffuseDirection = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeDiffuseBuffer(diffuseColor, diffuseIntensity, diffuseDirection, vertices);
		
		
	}
	
	FloatBuffer ambientData;
	
	FloatBuffer diffuseColor;
	FloatBuffer diffuseIntensity;
	FloatBuffer diffuseDirection;

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
		
		
		// ambient Lightning
		glVertexAttribPointer(Shader.getBaseAttribIdx(), 3, false, 0,
				colorData);
		glEnableVertexAttribArray(Shader.getBaseAttribIdx());
		
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());
		
		
		glVertexAttribPointer(Shader.getDiffuseColorAttribIdx(), 3, false, 0,
				diffuseColor);
		glEnableVertexAttribArray(Shader.getDiffuseColorAttribIdx());
		
		glVertexAttribPointer(Shader.getDiffuseIntensityAttribIdx(), 1, false, 0,
				diffuseIntensity);
		glEnableVertexAttribArray(Shader.getDiffuseIntensityAttribIdx());
		
		
		glVertexAttribPointer(Shader.getDiffuseDirectionAttribIdx(), 3, false, 0,
				diffuseDirection);
		glEnableVertexAttribArray(Shader.getDiffuseDirectionAttribIdx());
		
		


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
	
	protected void finalizeDiffuseBuffer(FloatBuffer diffC, FloatBuffer diffI, FloatBuffer diffD, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			diffC.put(PhongShader.diffuseColorToArray());
			diffI.put(PhongShader.diffuseIntensityToArray());
			diffD.put(PhongShader.diffuseDirectionToArray());
		}
		diffC.rewind();
		diffI.rewind();
		diffD.rewind();
	}
	
	
	
}
