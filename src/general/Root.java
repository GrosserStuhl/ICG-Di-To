package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.app.Texture;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shapes.Cube;
import shapes.Pyramid;

public class Root extends Node implements App {

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Root()).start();
	}

	private Shader shader;
	private Shader textureShader;

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	private Vertex[] vertices;
	private Vertex[] verticesT;

	// Make construction of vectors easy on the eyes.
	private Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}

	// Make construction of colors easy on the eyes.
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
	}
	
	private Vector2f v2f(float x, float y){
		return new Vector2f(x,y);
	}

	//
	// 6 ------- 7
	// / | / |
	// 3 ------- 2 |
	// | | | |
	// | 5 ----- |- 4
	// | / | /
	// 0 ------- 1
	//

	// The positions of the cube vertices.
	private Vector[] p = { 
			vec(-w2, -h2, -d2),
			vec(w2, -h2, -d2),
			vec(w2, h2, -d2), 
			vec(-w2, h2, -d2), 
			vec(w2, -h2, d2),
			vec(-w2, -h2, d2), 
			vec(-w2, h2, d2), 
			vec(w2, h2, d2) };
	
	// The positions of the cube vertices.
		private Vector[] p_row2 = { 
				vec(-w2, -h2, -d2 - 15), 
				vec(w2, -h2, -d2 - 15),
				vec(w2, h2, -d2 - 15), 
				vec(-w2, h2, -d2 - 15), 
				vec(w2, -h2, d2 - 15),
				vec(-w2, -h2, d2 - 15), 
				vec(-w2, h2, d2 - 15), 
				vec(w2, h2, d2 - 15) };
		
		// The positions of the cube vertices.
		private Vector[] p_row3 = { 
				vec(-w2, -h2, -d2 - 30), 
				vec(w2, -h2, -d2 - 30),
				vec(w2, h2, -d2 - 30), 
				vec(-w2, h2, -d2 - 30), 
				vec(w2, -h2, d2 - 30),
				vec(-w2, -h2, d2 - 30), 
				vec(-w2, h2, d2 - 30), 
				vec(w2, h2, d2 - 30) };

	// The colors of the cube vertices.
	private Color[] c = { 
			col(1, 0, 0), 
			col(1, 0, 0), 
			col(1, 0, 0),
			col(1, 0, 0), 
			col(0, 1, 0), 
			col(0, 1, 0),
			col(0, 1, 0),
			col(0, 1, 0) };
	
	
	private Vector2f[] cubeTexture = {
			v2f(1, 0), 
			v2f(1, 0), 
			v2f(1, 0),
			v2f(1, 0), 
			v2f(0, 1), 
			v2f(0, 1),
			v2f(0, 1),
			v2f(0, 1)
	};
	

	// Pyramid

	private Vector[] t = {
			// hinten links-unten
			vec(-w2 + 3, -h2, -d2),
			// hinten rechts-unten
			vec(w2 + 3, -h2, -d2),
			// vorne rechts-unten
			vec(w2 + 3, -h2, d2),
			// vorne links-unten
			vec(-w2 + 3, -h2, d2),

			// nach oben
			vec(0 + 3, d2, 0)

	};

	// The colors of the Pyramid vertices.
	private Color[] colorT = { 
			col(1, 0, 0), 
			col(1, 0, 0), 
			col(1, 0, 0),
			col(1, 0, 0), 
			col(0, 1, 0) };

	public Root() {

	}
	

	@Override
	public void init() {

		shader = new Shader();
		textureShader = new Shader("originalVertex.vs","originalFragment.fs");
		

		RowNode row_one = new RowNode(0);
		RowNode row_two = new RowNode(1);
		RowNode row_three = new RowNode(2);
		addNode(row_one);
		addNode(row_two);
		addNode(row_three);

		Camera cam = new Camera(getChildNodes(), shader);
		addNode(cam);

		vertices = Vertex.cubeTexture(p, cubeTexture);
		// cube mit color ohne textur
//		Vertex[] vertices_cube2 = Vertex.cubeVertices(p_row2, c);
//		Vertex[] vertices_cube3 = Vertex.cubeVertices(p_row3, c);
		
		Vertex[] vertices_cube2 = Vertex.cubeTexture(p_row2, cubeTexture);
		Vertex[] vertices_cube3 = Vertex.cubeTexture(p_row3, cubeTexture);

		verticesT = Vertex.triangleVertices(t, colorT);

		Cube cube = new Cube(vertices, textureShader);
		row_one.addNode(cube);
		
		
		Cube cube2 = new Cube(vertices_cube2, shader);
		row_two.addNode(cube2);
		Cube cube3 = new Cube(vertices_cube3, shader);
		row_three.addNode(cube3);
		Pyramid pyr = new Pyramid(verticesT, shader);
		row_one.addNode(pyr);

		Mesh m = ResourceLoader.loadMesh("smallSuper.obj");
		OBJMesh monkeyMod = new OBJMesh(m.getVertices(), shader);
		row_one.addNode(monkeyMod);

		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		for (Node child : getChildNodes()) {
			child.simulate(elapsed, input);
		}
	}

	@Override
	public void display(int width, int height) {
		setTransformation(vecmath.identityMatrix());
//		setTransformation(vecmath.matrix(
//				1, 0, 0, 0, 
//				0, 1, 0, 0, 
//				0, 0, 1, 0, 
//				0, 0, 0, 1));
		// Adjust the the viewport to the actual window size. This
		// makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to
		// all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f,
				100f);
		

		// The inverse camera transformation. World space to camera
		// space.
		// Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 10f),
		// vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));

		// Activate the shader program and set the transformation
		// matricies to
		// the
		// uniform variables.
		shader.activate();
		// shader.getViewMatrixUniform().set(viewMatrix);
		shader.getProjectionMatrixUniform().set(projectionMatrix);
		
		
		textureShader.activate();
		textureShader.getProjectionMatrixUniform().set(projectionMatrix);

		for (Node child : getChildNodes()) {
			child.display(width, height, getTransformation());
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		// TODO Auto-generated method stub
		
	}

}
