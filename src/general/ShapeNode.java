package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.nio.FloatBuffer;

import ogl.app.Input;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;

import shader.Shader;

public abstract class ShapeNode extends Node {

	protected Vertex[] vertices;
	protected Vector translation;
	private Shader shader;
	private boolean selected;
	private boolean completed;
	private float height = 1;
	private float width = 1;
	private float scale = MEDIUM;
	
	// Initialize the rotation angle
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
		selected = true;
	}

	@Override
	public void deselect() {
		selected = false;
		angle = 0;
	}

	@Override
	public void setCompleted() {
		completed = true;
	}

	@Override
	public void setUnCompleted() {
		completed = false;
	}
	
	public boolean isCompleted() {
		return completed;
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
	
	@Override
	public float getScale() {
		return scale;
	}

	public static final float SMALL = 1;
	public static final float MEDIUM = 2;
	public static final float LARGE = 3;
}
