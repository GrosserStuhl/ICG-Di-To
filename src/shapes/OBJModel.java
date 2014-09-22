package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import mathe.Vector3f;
import ogl.app.App;
import ogl.app.Texture;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import shader.PhongShader;
import shader.Shader;

public class OBJModel extends ShapeNode implements App {

	private Texture t;

	public OBJModel(Vertex[] vertices, Shader shader, Texture t,  Vector translation, float scale) {
		super(vertices, shader, translation, scale);
		
		this.t = t;
		
		positionData = createFloatBuffer(vertices.length * 3);
		textureData = createFloatBuffer(vertices.length * 2);
		normalData = createFloatBuffer(vertices.length * 3);
		ambientData = createFloatBuffer(vertices.length * 3);

		finalizeBuffers(positionData, textureData, normalData, vertices);
	}

	FloatBuffer positionData;
	FloatBuffer textureData;
	FloatBuffer normalData;
	
	FloatBuffer ambientData;
	
	Vector eyePosition;
	

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// ohne t.bind() funktioniert es nicht!!!
		t.bind();

		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
//		modelMatrix = modelMatrix.mult(vecmath.scaleMatrix(0.5f, 0.5f, 0.5f));
		
		setTransformation(modelMatrix);

		getShader().getModelMatrixUniform().set(modelMatrix);
		
//		getShader().getLightVectorMatrixUniform().set(vecmath.vector(1, 0, 0));
		
		
		Matrix viewMatrix = getShader().getViewMatrixUniform().getMatrix();
		Matrix normalMatrix = (viewMatrix.mult(modelMatrix).invertRigid().transpose());

		getShader().getNormalMatrixUniform().set(normalMatrix);
		
		
		
		//////////LIGHTNING SECTION ////////////////////
		// ambient light
		getShader().getAmbientLightVectorUniform().set(PhongShader.getAmbientLight());
				
		// diffuse Lightning
		getShader().getLightPositionVectorUniform().set(new Vector3f(0, 0, 16));
		
		// specular Lightning
		getShader().getSpecularIntensityFloatUniform().set(2);
		getShader().getSpecularExponentFloatUniform().set(32);
		////////////////////////////////////////////////
		
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// last number offset
		glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0,
				textureData);
		glEnableVertexAttribArray(Shader.getTextureAttribIdx());
		
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);

		
	}
	

	private void finalizeBuffers(FloatBuffer p, FloatBuffer t,FloatBuffer n, Vertex[] vertices) {
		for (Vertex v : vertices) {
				p.put(v.getPosition().asArray());
				t.put(v.getTextureCoord().asArray());
				n.put(v.getNormal().asArray());
		}
		p.rewind();
		t.rewind();
		n.rewind();
	}


}
