package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import mathe.Vector3f;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.opengl.GL20;

import shader.PhongShader;
import shader.Shader;

public class Pyramid extends ShapeNode {
	
	
	
	public Pyramid(Vertex[] vertices, Vector translation, float scale) {
		super(vertices, translation, scale);
		
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		normalData = createFloatBuffer(vertices.length * 3);
		
		finalizePyramidBuffers(positionData, colorData, normalData,vertices);
	}
	
	FloatBuffer positionData;
	FloatBuffer colorData;
	FloatBuffer normalData;

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		
		Matrix modelMatrix = parentMatrix.mult(vecmath.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(0, 1, 0, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		
		
		Matrix viewMatrix = getShader().getViewMatrixUniform().getMatrix();
		Matrix normalMatrix = (viewMatrix.mult(modelMatrix).invertRigid().transpose());
		getShader().getNormalMatrixUniform().set(normalMatrix);
		
		
		////////// LIGHTNING SECTION ////////////////////
		// ambient light
		getShader().getAmbientLightVectorUniform().set(PhongShader.getAmbientLight());
				
		// diffuse Lightning
		getShader().getLightPositionVectorUniform().set(new Vector3f(1, 1, 1));
		
		// specular Lightning
		getShader().getSpecularIntensityFloatUniform().set(2);
		getShader().getSpecularExponentFloatUniform().set(32);
		////////////////////////////////////////////////
		
		
		
		
		// vertex position of the pyramid
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		// color Data of the pyramid
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
				colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());
		
		// vertex position of the pyramid
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());
		
		
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		
		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		
	}
	
	private void finalizePyramidBuffers(FloatBuffer pos, FloatBuffer col, FloatBuffer norm, Vertex[] vertices) {
		for (Vertex v : vertices) {
			pos.put(v.getPosition().asArray());
			col.put(v.getColor().asArray());
			norm.put(v.getNormal().asArray());
		}
		pos.rewind();
		col.rewind();
		norm.rewind();
	}

}
