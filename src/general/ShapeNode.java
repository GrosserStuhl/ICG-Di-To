package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.nio.FloatBuffer;

import ogl.app.Input;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;

import shader.Shader;

public abstract class ShapeNode extends Node {

	protected FloatBuffer positionData;
	protected FloatBuffer colorData;
	protected Vertex[] vertices;
	protected Vector translation;
	private Shader shader;
	private boolean selected;
	private float height;
	private float width;

	// Initialize the rotation angle of the cube.
	protected float angle = 0;

	public ShapeNode(Vertex[] vertices, Shader shader, Vector translation) {
		this.vertices = vertices;
		this.shader = shader;
		this.translation = translation;
	}

	@Override
	public void init() {

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

	@Override
	public Vertex[] getVertices() {
		return vertices;
	}

	protected FloatBuffer createFloatBuffer(int verticesLength) {
		return BufferUtils.createFloatBuffer(verticesLength
				* vecmath.vectorSize());
	}

	@Override
	public void setTranslation(Vector translation) {
		this.translation = translation;
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected() {
//		System.out.println(getName() + " got selected");
		selected = true;
		// Hier shader �ndern um farbe zu �ndern
	}

	@Override
	public void deselect() {
		selected = false;
		angle = 0;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}
	
	@Override
	public void setHeight(float height) {
		this.height = height;
	}
	
	@Override
	public void setWidth(float width) {
		this.width = width;
	}
}
