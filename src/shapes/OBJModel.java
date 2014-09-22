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
import org.lwjgl.util.vector.Vector2f;

import shader.PhongShader;
import shader.Shader;

public class OBJModel extends ShapeNode implements App {

	private Texture t;

	public OBJModel(Vertex[] vertices, Shader shader, Texture t,  Vector translation) {
		super(vertices, shader, translation);
		
		this.t = t;
		
		positionData = createFloatBuffer(vertices.length * 3);
		textureData = createFloatBuffer(vertices.length * 2);
		normalData = createFloatBuffer(vertices.length * 3);
		ambientData = createFloatBuffer(vertices.length * 3);

		finalizeBuffers(positionData, textureData, normalData, ambientData, vertices);
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

		getShader().activate();
		getShader().getModelMatrixUniform().set(getTransformation());
		
//		getShader().getLightVectorMatrixUniform().set(vecmath.vector(1, 0, 0));
		getShader().getTransformMatrixUniform().set(modelMatrix.invertRigid().transpose());
		
		
		
		//////////LIGHTNING SECTION ////////////////////
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
		
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());

		// last number offset
		glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0,
				textureData);
		glEnableVertexAttribArray(Shader.getTextureAttribIdx());
		
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);

		
	}
	
	protected void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}

	private void finalizeBuffers(FloatBuffer p, FloatBuffer t,FloatBuffer n, FloatBuffer a, Vertex[] vertices) {
		for (Vertex v : vertices) {
			if(v.getPosition3f() != null)
				p.put(v.getPosition3f().asArray());
			if(v.getTextureCoord() != null)
				t.put(asArray(v.getTextureCoord()));
			if(v.getNormal3f() != null)
				n.put(v.getNormal3f().asArray());
			
				a.put(PhongShader.ambientToArray());
		}
		p.rewind();
		t.rewind();
		n.rewind();
		a.rewind();
	}

	public float[] asArray(Vector2f t) {

		float[] res = new float[2];
		res[0] = t.getX();
		res[1] = t.getY();

		return res;

	}

}
