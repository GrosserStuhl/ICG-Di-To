package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

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
	
	public Pyramid(Vertex[] vertices, Shader shader, Vector translation) {
		super(vertices, shader, translation);
		
		
		positionData = createFloatBuffer(vertices.length * 3);
		colorData = createFloatBuffer(vertices.length * 3);
		finalizeBuffer(positionData, colorData, vertices);
		
		
		ambientData = BufferUtils.createFloatBuffer(vertices.length * 3);
		finalizeAmbientBuffer(ambientData, vertices);
		
		normalData = createFloatBuffer(vertices.length * 3);
		finalizeNormalBuffer(normalData,vertices);
	}
	
	FloatBuffer positionData;
	FloatBuffer colorData;
	
	FloatBuffer ambientData;
	FloatBuffer normalData;

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		
		getShader().activate();
		Matrix modelMatrix = parentMatrix.mult(vecmath.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		setTransformation(modelMatrix);

		// Activate the shader program and set the transformation matricies to the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());
		
		
//		MatrixUniform viewEncoder = getShader().getViewMatrixUniform();
//		float[] elements = new float[16];
//		for (int i = 0; i < viewEncoder.getBuffer().capacity(); i++) {
//			elements[i] = viewEncoder.getBuffer().get(i);
//		}
//		Matrix m = vecmath.matrix(elements);
//		System.out.println(m);
//		Matrix modelView4f = modelMatrix.mult(m);
//		// for normalMatrix: invertRigid(); transpose() (upper -left: mat3())
//		Matrix3f modelView3f = toMatrix3f(modelView4f);
//		org.lwjgl.util.vector.Matrix modelViewMatrix = modelView3f.invert().transpose();
//		FloatBuffer modelViewBuffer = BufferUtils.createFloatBuffer(9);
//		modelViewMatrix.store(modelViewBuffer);
//		modelViewBuffer.rewind();
		
		getShader().getTransformMatrixUniform().set(modelMatrix.invertRigid().transpose());
		
		
		getShader().getLightPositionMatrixUniform().set(vecmath.vector(1, 0, 0));

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
		
		
		// ambient light
		glVertexAttribPointer(Shader.getAmbientAttribIdx(), 3, false, 0,
				ambientData);
		glEnableVertexAttribArray(Shader.getAmbientAttribIdx());

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		
		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getColorAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getAmbientAttribIdx());
		
	}
	
	private void finalizeBuffer(FloatBuffer p,FloatBuffer c, Vertex[] vertices){
		for (Vertex v : vertices) {
			p.put(v.getPosition3f().asArray());
			c.put(v.getColor().asArray());
		}
		p.rewind();
		c.rewind();
	}
	
	
	
	private void finalizeAmbientBuffer(FloatBuffer a, Vertex[] vertices){
		for (int i = 0; i<vertices.length;i++) {
			a.put(PhongShader.ambientToArray());
		}
		a.rewind();
	}
	
	private void finalizeNormalBuffer(FloatBuffer n, Vertex[] vertices) {
		for (Vertex v : vertices) {
			n.put(v.getNormal3f().asArray());
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
