package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import general.ResourceLoader;
import general.Shader;
import general.ShapeNode;
import general.Vertex;
import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

public class Cube extends ShapeNode implements App {

	
	private Shader s;
	
	public Cube(Vertex[] vertices, Shader shader) {
		super(vertices, shader);
		this.s = shader;
	}
	

	FloatBuffer textureData;
	Texture t;
	
	public void init() {

		// Prepare the vertex data arrays.
		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficently.
		positionData = positionBuffer(vertices.length);
		textureData = BufferUtils.createFloatBuffer(vertices.length * 2); // because of Vector2f
		
		colorData = colorBuffer(vertices.length);
		
		
		t = ResourceLoader.loadTexture("wood.jpg");
		
		finalizeTextured(positionData,textureData,vertices);
		
//		finalizeBuffers(positionData, colorData, vertices);

	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(Keyboard.KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 150 * elapsed;
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1),
				angle);
		this.setTransformation(parentMatrix.mult(modelMatrix));

		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		getShader().getModelMatrixUniform().set(getTransformation());

		
		
		// Enable the vertex data arrays (with indices 0 and 1). We use a vertex
		// position and a vertex color.
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		
		glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0,
				textureData);
		glEnableVertexAttribArray(Shader.getTextureAttribIdx());

		// Draw the triangles that form the cube from the vertex data arrays.
		glDrawArrays(GL_QUADS, 0, vertices.length);
		
		GL20.glDisableVertexAttribArray(Shader.getVertexAttribIdx());
		GL20.glDisableVertexAttribArray(Shader.getTextureAttribIdx());

		// getShader().getModelMatrixUniform().set(
		// modelMatrix.mult(vecmath.translationMatrix(-2, 0, 0)));
	}
	
	private void finalizeTextured(FloatBuffer positionData, FloatBuffer textureData, Vertex[] vertices) {
		for (Vertex v : vertices) {
			positionData.put(v.getPosition().asArray());
			textureData.put(asArray(v.getTextureCoord()));
			
		}
		positionData.rewind();
		textureData.rewind();
	}
	
	public float[] asArray(Vector2f t){

		float[] res = new float[2];
		res[0] = t.getX();
		res[1] = t.getY();
		
		return res;
		
	}
	
	
	

	public Vector getLookAtVector() {
		// Vordere linke Ecke
		Vector edge = vertices[3].getPosition();
		//Position für Kamera 
		edge.add(vecmath.vector(0.5f, 0, 10f));
		return edge;
	}

}
