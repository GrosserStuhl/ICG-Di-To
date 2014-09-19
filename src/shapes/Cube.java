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
import general.PhongShader;
import general.Shader;
import general.ShapeNode;
import general.Vertex;
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
		
		modelViewBuffer = BufferUtils.createFloatBuffer(16);
		matModelView = new float[16];
		modelViewBuffer.put(matModelView);
		modelViewBuffer.rewind();
		
//		diffuseColor = BufferUtils.createFloatBuffer(vertices.length * 3);
//		diffuseIntensity = BufferUtils.createFloatBuffer(vertices.length * 1);
//		diffuseDirection = BufferUtils.createFloatBuffer(vertices.length * 3);
//		finalizeDiffuseBuffer(diffuseColor, diffuseIntensity, diffuseDirection, vertices);
		
		
	}

	FloatBuffer ambientData;
	
	
	FloatBuffer modelViewBuffer;
	float [] matModelView;
	
	FloatBuffer normalData;

	// FloatBuffer diffuseColor;
	// FloatBuffer diffuseIntensity;
	// FloatBuffer diffuseDirection;

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
		
		
		
		MatrixUniform viewEncoder = getShader().getViewMatrixUniform();
		
		float[] elements = new float[16];
		
		for (int i = 0; i < viewEncoder.getBuffer().capacity(); i++) {
			elements[i] = viewEncoder.getBuffer().get(i);
		}
		
		Matrix m = vecmath.matrix(elements);
		System.out.println(m);
		
		Matrix modelView4f = modelMatrix.mult(m);

		// for normalMatrix: invertRigid(); transpose() (upper -left: mat3())
		Matrix3f modelView3f = toMatrix3f(modelView4f);

		org.lwjgl.util.vector.Matrix modelViewMatrix = modelView3f.invert().transpose();
		
		FloatBuffer modelViewBuffer = BufferUtils.createFloatBuffer(9);
		modelViewMatrix.store(modelViewBuffer);
		modelViewBuffer.rewind();
		
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

		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());

//		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0,
//				colorData);
//		glEnableVertexAttribArray(Shader.getColorAttribIdx());
		
		
		

		
		
		// ambient Lightning
		glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0, colorData);
		glEnableVertexAttribArray(Shader.getColorAttribIdx());

		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());

		// vectornormalData
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// glVertexAttribPointer(Shader.getDiffuseColorAttribIdx(), 3, false, 0,
		// diffuseColor);
		// glEnableVertexAttribArray(Shader.getDiffuseColorAttribIdx());
		//
		// glVertexAttribPointer(Shader.getDiffuseIntensityAttribIdx(), 1,
		// false, 0,
		// diffuseIntensity);
		// glEnableVertexAttribArray(Shader.getDiffuseIntensityAttribIdx());
		//
		//
		// glVertexAttribPointer(Shader.getDiffuseDirectionAttribIdx(), 3,
		// false, 0,
		// diffuseDirection);
		// glEnableVertexAttribArray(Shader.getDiffuseDirectionAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);

		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getBaseAttribIdx());
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

	
	
	
}
