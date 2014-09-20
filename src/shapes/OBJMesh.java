package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;

import shader.PhongShader;
import shader.Shader;

public class OBJMesh extends ShapeNode implements App {

	private FloatBuffer positionData;
	private FloatBuffer colorData;

	public OBJMesh(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);

	}

	
	@Override
	public void init() {
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		finalizeBuffer(positionData, colorData, vertices);
		
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_B))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;

	}


	public void finalizeBuffer(FloatBuffer position, FloatBuffer color,
			Vertex[] vertices) {
		for (Vertex v : vertices) {
			positionData.put(v.getPosition().asArray());
			colorData.put(v.getColor().asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

	
	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		
		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		
		
//		MatrixUniform viewEncoder = getShader().getViewMatrixUniform();
//		
//		float[] elements = new float[16];
//		
//		for (int i = 0; i < viewEncoder.getBuffer().capacity(); i++) {
//			elements[i] = viewEncoder.getBuffer().get(i);
//		}
//		
//		Matrix m = vecmath.matrix(elements);
//		Matrix modelView4f = modelMatrix.mult(m);
//		
//		Matrix3f modelView3f = toMatrix3f(modelView4f);
//
//		org.lwjgl.util.vector.Matrix modelViewMatrix = modelView3f.invert().transpose();
//		
//		FloatBuffer modelViewBuffer = BufferUtils.createFloatBuffer(9);
//		modelViewMatrix.store(modelViewBuffer);
//		modelViewBuffer.rewind();
		
		getShader().getTransformMatrixUniform().set(modelMatrix.invertRigid().transpose());
		//getShader().getTransformMatrixUniform().set(modelViewBuffer);
		
		
		getShader().getDirectionalLightIntensity().set(
				PhongShader.getDirectionalLight().getBase().getIntensity());
		getShader().getDirectionalLightColor().set(
				PhongShader.getDirectionalLight().getBase().getColor());
		getShader().getDirectionalLightDirection().set(
				PhongShader.getDirectionalLight().getDirection());

		// Enable the vertex data arrays (with indices 0 and 1). We use a vertex
		// position and a vertex color.

		// vertex positions of cube
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		
		// color of cube
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0, colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());

		// ambient Lightning
//		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
//				ambientData);
//		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());

		// vectornormalData
//		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
//				normalData);
//		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
	}

}
