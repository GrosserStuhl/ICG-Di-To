package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import general.ShapeNode;
import general.Vertex;
import mathe.Vector3f;
import ogl.app.App;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.opengl.GL20;
import shader.PhongShader;
import shader.Shader;

public class Cube extends ShapeNode implements App {

	public Cube(Vertex[] vertices, Shader shader, Vector translation, Vector eye, float scale) {
		super(vertices, shader, translation, scale);

		this.eyePosition = eye;
		
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		normalData = createFloatBuffer(vertices.length * 3);
		
		finalizeCubeBuffers(positionData, colorData,normalData, vertices);
		
		
		
	}

	FloatBuffer positionData;
	FloatBuffer colorData;
	FloatBuffer normalData;
	
	Vector eyePosition;



	@Override
	public void display(int width, int height, Matrix parentMatrix) {

		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		getShader().getLightPositionVectorUniform().set(new Vector3f(0, 0, 0));
		
		
		Matrix viewMatrix = getShader().getViewMatrixUniform().getMatrix();
		Matrix normalMatrix = (viewMatrix.mult(modelMatrix).invertRigid().transpose());
		getShader().getNormalMatrixUniform().set(normalMatrix);
		
		
		//////////LIGHTNING SECTION ////////////////////
		// ambient light
		getShader().getAmbientLightVectorUniform().set(PhongShader.getAmbientLight());
				
		// diffuse Lightning
		getShader().getLightPositionVectorUniform().set(new Vector3f(1, 1, 1));
		
		// specular Lightning
		getShader().getSpecularIntensityFloatUniform().set(2);
		getShader().getSpecularExponentFloatUniform().set(32);
		////////////////////////////////////////////////
		
		

		// vertex positions of cube
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		
		// color of cube
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0, colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());


		// vectornormalData
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
	}


	private void finalizeCubeBuffers(FloatBuffer pos, FloatBuffer col,FloatBuffer norm, Vertex[] vertices) {
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
