package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import mathe.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;

import ogl.app.MatrixUniform;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shader.PhongShader;
import shader.Shader;
import general.Node;
import general.ShapeNode;
import general.Vertex;

public class Pyramid extends ShapeNode {
	
	
	
	public Pyramid(Vertex[] vertices, Shader shader, Vector translation, float scale) {
		super(vertices, shader, translation, scale);
		
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
		normalData = createFloatBuffer(vertices.length * 3);
		
		finalizePyramidBuffers(positionData, colorData, ambientData, normalData,vertices);
		
		
	}
	
	FloatBuffer positionData;
	FloatBuffer colorData;
	
	FloatBuffer ambientData;
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
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());
				
		// diffuse Lightning
		getShader().getLightPositionVectorUniform().set(vecmath.vector(1, 1, 1));
		
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
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
		
	}
	
	private void finalizePyramidBuffers(FloatBuffer pos, FloatBuffer col, FloatBuffer amb, FloatBuffer norm, Vertex[] vertices) {
		for (Vertex v : vertices) {
			pos.put(v.getPosition().asArray());
			col.put(v.getColor().asArray());
			amb.put(PhongShader.ambientToArray());
			norm.put(v.getNormal().asArray());
		}
		pos.rewind();
		col.rewind();
		amb.rewind();
		norm.rewind();
	}

}
