package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

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

	public OBJModel(Vertex[] vertices, Shader shader, Texture t,
			Vector translation) {
		super(vertices, shader, translation);
		this.t = t;
	}

	FloatBuffer positionData;
	FloatBuffer textureData;
//	FloatBuffer ambientData;
	

	@Override
	public void init() {

		positionData = createFloatBuffer(vertices.length * 3);
		textureData = createFloatBuffer(vertices.length * 2);

		finalizeTextured(positionData, textureData, vertices);
		
		
//		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
//		finalizeAmbientBuffer(ambientData, vertices);

	}
	

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
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());

		// last number offset
		glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0,
				textureData);
		glEnableVertexAttribArray(Shader.getTextureAttribIdx());
		
//		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
//				ambientData);
//		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);

		glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		glDisableVertexAttribArray(Shader.getTextureAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
	}
	
	protected void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}

	private void finalizeTextured(FloatBuffer positionData,
			FloatBuffer textureData, Vertex[] vertices) {
		for (Vertex v : vertices) {
			if(v.getPosition() != null)
				positionData.put(v.getPosition().asArray());
			if(v.getTextureCoord() != null)
				textureData.put(asArray(v.getTextureCoord()));
		}
		positionData.rewind();
		textureData.rewind();
	}

	public float[] asArray(Vector2f t) {

		float[] res = new float[2];
		res[0] = t.getX();
		res[1] = t.getY();

		return res;

	}

}
