package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public abstract class ShapeNode extends Node {

	protected FloatBuffer positionData;
	protected FloatBuffer colorData;
	protected Vertex[] vertices;
	private Shader shader;

	public ShapeNode(Vertex[] vertices, Shader shader) {
		this.positionData = positionBuffer(vertices.length);
		this.colorData = colorBuffer(vertices.length);
		this.vertices = vertices;
		this.shader = shader;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public FloatBuffer getPositionData() {
		return positionData;
	}

	public FloatBuffer getColorData() {
		return colorData;
	}

	protected FloatBuffer positionBuffer(int verticesLength) {
		return BufferUtils.createFloatBuffer(verticesLength
				* vecmath.vectorSize());
	}

	protected FloatBuffer colorBuffer(int verticesLength) {
		return BufferUtils.createFloatBuffer(verticesLength
				* vecmath.colorSize());
	}

	protected void finalizeBuffers(FloatBuffer positionData,
			FloatBuffer colorData, Vertex[] vertices) {
		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
		}
		positionData.rewind();
		colorData.rewind();
	}

}
