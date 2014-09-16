package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.nio.FloatBuffer;

import ogl.app.Input;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

public abstract class ShapeNode extends Node {

	protected FloatBuffer positionData;
	protected FloatBuffer colorData;
	protected Vertex[] vertices;
	protected Vector translation;
	private Shader shader;
	private boolean selected;

	// Initialize the rotation angle of the cube.
	protected float angle = 0;

	public ShapeNode(Vertex[] vertices, Shader shader, Vector translation) {
		this.positionData = positionBuffer(vertices.length);
		this.colorData = colorBuffer(vertices.length);
		this.vertices = vertices;
		this.shader = shader;
		this.translation = translation;
	}

	@Override
	public void init() {
		positionData = positionBuffer(vertices.length);
		colorData = colorBuffer(vertices.length);
		finalizeBuffers(positionData, colorData, vertices);

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (isSelected())
			// Increase the angle with a speed of 90 degrees per second.
			angle += 90 * elapsed;
	}

	@Override
	public void display(int width, int height) {

	}

	@Override
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

	public Vertex[] getVertices() {
		return vertices;
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

	public void setTranslation(Vector translation) {
		this.translation = translation;
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected() {
		System.out.println(getName() + " got selected");
		selected = true;
		// Hier shader ändern um farbe zu ändern
	}

	@Override
	public void deselect() {
		selected = false;
		angle = 0;
	}
}
