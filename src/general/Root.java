package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;
import mathe.Vector3f;

import org.lwjgl.util.vector.Vector2f;







import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.app.Texture;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shader.BaseLight;
import shader.PhongShader;
import shader.Shader;
import shapes.Cube;
import shapes.OBJModel;
import shapes.Pyramid;
import util.ResourceLoader;

public class Root extends Node implements App {

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Root()).start();
	}

	private Shader shader;
	private Shader textureShader;
	private Camera cam;
	private InputManager manager;

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

	private Vector2f v2f(float x, float y) {
		return new Vector2f(x, y);
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

	// The colors of the cube vertices.
	private Color[] c = { col(1, 0, 0), col(1, 0, 0), col(1, 0, 0),
			col(1, 0, 0), col(0, 1, 0), col(0, 1, 0), col(0, 1, 0),
			col(0, 1, 0) };

	// Pyramid

	private Vector[] t = {
			// hinten links-unten
			vec(-w2, -h2, -d2),
			// hinten rechts-unten
			vec(w2, -h2, -d2),
			// vorne rechts-unten
			vec(w2, -h2, d2),
			// vorne links-unten
			vec(-w2, -h2, d2),

			// nach oben
			vec(0, d2, 0)

	};

	// The colors of the Pyramid vertices.
	private Color[] colorT = { col(1, 0, 0), col(1, 0, 0), col(1, 0, 0),
			col(1, 0, 0), col(0, 1, 0) };

	public Root() {

	}

	@Override
	public void init() {

		
		PhongShader.setAmbientLight(new Vector3f(0.2f,0.2f,0.2f));
		
		//BaseLight(Farbe, diffuse Intensität des Lichtes), direktionale Lichtrichtung 
		PhongShader.setDirectionalLight(new BaseLight(vecmath.color(1,1,1),0.8f),vecmath.vector(1,1,1));
		
		shader = new Shader();
		textureShader = new Shader("originalVertex.vs", "originalFragment.fs");

		RowNode row_one = new RowNode(0);
		RowNode row_two = new RowNode(1);
		RowNode row_three = new RowNode(2);
		addNode(row_one);
		addNode(row_two);
		addNode(row_three);

		setTransformation(vecmath.identityMatrix());
		cam = new Camera(getTransformation());
		addNode(cam);

		vertices = Vertex.cubeVertices(p, c);
		// cube mit color ohne textur
		// Vertex[] vertices_cube2 = Vertex.cubeVertices(p_row2, c);
		// Vertex[] vertices_cube3 = Vertex.cubeVertices(p_row3, c);

		verticesT = Vertex.triangleVertices(t, colorT);

		
		
		//Phong extra diffuse:
		Vertex.calcNormals(vertices, cubeIndices());
		
//		for (int i = 0; i < vertices.length; i++) {
//			System.out.println("Normal: x("+vertices[i].getNormal().x()+"), y("+vertices[i].getNormal().y()+"), z("+vertices[i].getNormal().z()+")");
//		}
		
		
		Cube cube = new Cube(vertices, shader, vecmath.vector(0f, 0f, 0f));
		row_one.addNode(cube);

		Cube cube2 = new Cube(vertices, shader, vecmath.vector(0, 0, 0));
		row_one.addNode(cube2);
		Cube cube3 = new Cube(vertices, shader, vecmath.vector(0f, 0, 0));
		row_one.addNode(cube3);
		
		
		float cubeWidth = 1;
		float cubeHeight = 1;
		cube.setHeight(cubeHeight);
		cube.setWidth(cubeWidth);
		cube2.setHeight(cubeHeight);
		cube2.setWidth(cubeWidth);
		cube3.setHeight(cubeHeight);
		cube3.setWidth(cubeWidth);
		
		String no = "#bob\"mop";
		String[] lo = no.split("\"");
		System.out.println(lo[0]);

		Mesh m = ResourceLoader.loadOBJModel("jupiter.obj");
		Texture t = ResourceLoader.loadTexture("MoonMap2.jpg");
		OBJModel moon = new OBJModel(m.getVertices(), textureShader, t,
				vecmath.vector(0, 0, 0));
		
		
		

		Mesh m2 = ResourceLoader.loadOBJModel("start.obj");
		Texture t2 = ResourceLoader.loadTexture("stark.png");
		OBJModel crate = new OBJModel(m2.getVertices(), textureShader, t2,
				vecmath.vector(0, 0, 0));
		moon.addNode(crate);
		row_three.addNode(crate);

		Pyramid pyr = new Pyramid(verticesT, shader, vecmath.vector(0, 0, 0));
		crate.addNode(moon);
		row_three.addNode(moon);
		
		Mesh m5 = ResourceLoader.loadOBJModel("jupiter.obj");
		Texture t5 = ResourceLoader.loadTexture("jupiter.jpg");
		OBJModel jupiter = new OBJModel(m5.getVertices(), textureShader, t5,
				vecmath.vector(0, 0, 0));
		crate.addNode(jupiter);
		row_three.addNode(jupiter);
		
		
		Mesh m3 = ResourceLoader.loadOBJModel("backFirst.obj");
		Texture t3 = ResourceLoader.loadTexture("stars.jpg");
		OBJModel plane = new OBJModel(m3.getVertices(), textureShader, t3,
				vecmath.vector(0, 0, -5));
		addNode(plane);
		

		OBJModel plane2 = new OBJModel(m3.getVertices(), textureShader, t3,
				vecmath.vector(0, 0, -25));
		addNode(plane2);
		
		OBJModel plane3 = new OBJModel(m3.getVertices(), textureShader, t3,
				vecmath.vector(0, 0, -45));
		addNode(plane3);
		
		// Mesh m2 = ResourceLoader.loadOBJModel("optimus.obj");
		// Texture t2 = ResourceLoader.loadTexture("optimus.png");
		// OBJModel monkeyMod2 = new OBJModel(m2.getVertices(), shader,t2);
		// row_one.addNode(monkeyMod2);

		row_one.setName("Row One");
		row_two.setName("Row Two");
		row_three.setName("Row Three");
		cam.setName("Camera");
//		cube.setName("Cube1");
//		cube2.setName("Cube2");
//		cube3.setName("Cube3");
//		pyr.setName("Pyramid");
		moon.setName("Moon");
		crate.setName("Crate");
		
		manager = new InputManager(cam, getChildNodes());
		
		for (Node child : getChildNodes()) {
			child.init();
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		manager.update(elapsed, input);
		for (Node child : getChildNodes()) {
			child.simulate(elapsed, input);
		}
	}

	@Override
	public void display(int width, int height) {
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
		Matrix viewMatrix = cam.getTransformation();
		
		// The inverse camera transformation. World space to camera
		// space.
		// Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 10f),
		// vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));

		// Activate the shader program and set the transformation
		// matricies to
		// the
		// uniform variables.

		shader.activate();
		shader.getProjectionMatrixUniform().set(projectionMatrix);
		shader.getViewMatrixUniform().set(viewMatrix);

		// getChildNodes().get(0).display(width, height, getTransformation());
		for (Node child : getChildNodes()) {
			if (child.getShader() != null && child.getShader().equals(shader))
				child.display(width, height, getTransformation());
			else child.display(width, height, getTransformation());
		}
		
		textureShader.activate();
		textureShader.getProjectionMatrixUniform().set(projectionMatrix);
		textureShader.getViewMatrixUniform().set(viewMatrix);


		for (Node child : getChildNodes()) {
			if (child.getShader() != null
					&& child.getShader().equals(textureShader))
				child.display(width, height, getTransformation());
			else child.display(width, height, getTransformation());
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
	}
	
	
	public int[] cubeIndices(){
		int[] res = new int[24];
		
		res[0] = 0; res[1] = 1; res[2] = 2; res[3] = 3;
		
		res[4] = 4; res[5] = 5; res[6] = 6; res[7] = 7;
		
		res[8] = 1; res[9] = 4; res[10] = 7; res[11] = 2;
		
		res[12] = 3; res[13] = 2; res[14] = 7; res[15] = 6;
		
		res[16] = 5; res[17] = 0; res[18] = 3; res[19] = 6;
		
		res[20] = 5; res[21] = 4; res[22] = 1; res[23] = 0;
		
		return res;
	}
	
	
//	public ArrayList<OBJIndex> generateCubeIndices(){
//		ArrayList<OBJIndex> cubeIndices = new ArrayList<OBJIndex>();
//		
//		cubeIndices.add(cubeIndex(0));
//		cubeIndices.add(cubeIndex(1));
//		cubeIndices.add(cubeIndex(2));
//		cubeIndices.add(cubeIndex(3));
//		
//		cubeIndices.add(cubeIndex(4));
//		cubeIndices.add(cubeIndex(5));
//		cubeIndices.add(cubeIndex(6));
//		cubeIndices.add(cubeIndex(7));
//		
//		cubeIndices.add(cubeIndex(1));
//		cubeIndices.add(cubeIndex(4));
//		cubeIndices.add(cubeIndex(7));
//		cubeIndices.add(cubeIndex(2));
//		
//		cubeIndices.add(cubeIndex(3));
//		cubeIndices.add(cubeIndex(2));
//		cubeIndices.add(cubeIndex(7));
//		cubeIndices.add(cubeIndex(6));
//		
//		cubeIndices.add(cubeIndex(5));
//		cubeIndices.add(cubeIndex(0));
//		cubeIndices.add(cubeIndex(3));
//		cubeIndices.add(cubeIndex(6));
//		
//		cubeIndices.add(cubeIndex(5));
//		cubeIndices.add(cubeIndex(4));
//		cubeIndices.add(cubeIndex(1));
//		cubeIndices.add(cubeIndex(0));
//		
//		
//		return cubeIndices;
//		
//	}
//	
//	private int[] cubeIndicesToIntArray(ArrayList<OBJIndex> cubeIndices) {
//
//		int[] res = new int[cubeIndices.size()];
//		
//		// hier egal man bekommt immer die gleichen int wert 
//		for (int i = 0; i < cubeIndices.size(); i++) {
//			res[i] = cubeIndices.get(i).vertexIndex;
//		}
//		
//		return res;
//		
//	}
	
//	public OBJIndex cubeIndex(int i){
//		
//		return new OBJIndex(i,i);
//		
//	}

}
