package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import general.Node;
import general.ShapeNode;
import general.Vertex;
import mathe.Matrix4f;
import ogl.app.App;
import ogl.app.MatrixUniform;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;

import shader.PhongShader;
import shader.Shader;

public class Cube extends ShapeNode implements App {

	public Cube(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);

		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeAmbientBuffer(ambientData, vertices);

		normalData = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeNormalBuffer(normalData, vertices);
		
		
		
		
//		modelViewBuffer = BufferUtils.createFloatBuffer(16);
//		matModelView = new float[16];
//		modelViewBuffer.put(matModelView);
//		modelViewBuffer.rewind();
		
		
		
	}

	FloatBuffer ambientData;
	
	
	FloatBuffer modelViewBuffer;
	float [] matModelView;
	
	FloatBuffer normalData;



	@Override
	public void display(int width, int height, Matrix parentMatrix) {

		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		getShader().getLightVectorMatrixUniform().set(vecmath.vector(0, 0, 0));
		
		
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
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());

		// vectornormalData
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
	}

	protected void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}

	protected void finalizeNormalBuffer(FloatBuffer n, Vertex[] vertices) {
		for (Vertex v : vertices) {
			n.put(v.getNormal().asArray());
		}
		n.rewind();
	}
	
	
	public Matrix3f toMatrix3f(Matrix o){
		
		Matrix3f m = new Matrix3f();
		
		float[] m4f = new float[16];
		m4f = o.asArray();
		
		
		
		m.m00 = m4f[0];	m.m10 = m4f[4];	m.m20 = m4f[8];	
		m.m01 = m4f[1];	m.m11 = m4f[5];	m.m21 = m4f[9];	
		m.m02 = m4f[2];	m.m12 = m4f[6];	m.m22 = m4f[10];	
	
		return m;
	}
	
	
	/**
	 * Transforms a float array of length 16 to a two dimensional
	 * float array of new float[4][4]
	 * 
	 * @param f
	 * @return
	 */
	
	private float[][] toTwoDimensionalFloat(float[] f){
		
		float[][] res = new float[4][4];
		
		res[0][0] = f[0];  res[0][1] = f[1];  res[0][2] = f[2];  res[0][3] = f[3];
		res[1][0] = f[4];  res[1][1] = f[5];  res[1][2] = f[6];  res[1][3] = f[7];
		res[2][0] = f[8];  res[2][1] = f[9];  res[2][2] = f[10];  res[2][3] = f[11];
		res[3][0] = f[12];  res[3][1] = f[13];  res[3][2] = f[14];  res[3][3] = f[15];
		
		return res;
		
	}

	
	
	
}
