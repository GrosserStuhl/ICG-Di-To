package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Matrix;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class OBJModel extends ShapeNode implements App {

	private Texture t;
	private Shader s;

	public OBJModel(Vertex[] vertices, Shader shader,Texture t) {
		super(vertices, shader);
		this.t = t;
		this.s = shader;
	}

	FloatBuffer positionData;
	FloatBuffer textureData;

	@Override
	public void init() {

//		t = ResourceLoader.loadTexture("MoonMap2.jpg");
		
		
		positionData  = positionBuffer(vertices.length);
		textureData = BufferUtils.createFloatBuffer(vertices.length * 2);

		finalizeTextured(positionData, textureData, vertices);

	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_B))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;

	}

	// Buffers mit TextureInfo (wurden schon in Texture gemacht)
	public void finalizeBuffer(FloatBuffer positionData, FloatBuffer colorData,
			Vertex[] vertices) {
		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		// int location = GL20.glGetUniformLocation(s.getProgram(), "img");
		// GL20.glUniform1i(location, 0);
		// // 0 because it is to use texture unit 0
		//

		// ohne t.bind() funktioniert es nicht!!!
		t.bind();

		Matrix modelMatrix = parentMatrix.mult(vecmath.translationMatrix(-5, 0,
				0));

		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(vecmath.vector(1, 1, 1), angle));
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

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);

		glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		glDisableVertexAttribArray(Shader.getTextureAttribIdx());
		
		getShader().deactivate();
	}

	private void finalizeTextured(FloatBuffer positionData,
			FloatBuffer textureData, Vertex[] vertices) {
		for (Vertex v : vertices) {

			if (v.getPosition() != null)
				positionData.put(v.getPosition().asArray());

			if (v.getTextureCoord() != null)
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
