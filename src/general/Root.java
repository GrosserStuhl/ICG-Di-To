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
import util.SceneLoader;

public class Root extends Node implements App {

	static public void main(String[] args) {
		new OpenGLApp("Cube - OpenGL ES 2.0 (lwjgl)", new Root()).start();
	}

	private Shader shader;
	private Shader textureShader;
	private Shader planeShader;

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
	private Vector[] p = { vec(-w2, -h2, -d2), vec(w2, -h2, -d2),
			vec(w2, h2, -d2), vec(-w2, h2, -d2), vec(w2, -h2, d2),
			vec(-w2, -h2, d2), vec(-w2, h2, d2), vec(w2, h2, d2) };

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
	private Color[] colorT = 
		{ col(1, 0, 0), col(1, 0, 0), col(1, 0, 0),
			col(1, 0, 0), col(0, 1, 0) };

	public Root() {

	}

	@Override
	public void init() {

		
		PhongShader.setAmbientLight(new Vector3f(0.5f,0.5f,0.5f));
		
		//BaseLight(Farbe, diffuse Intensitšt des Lichtes), direktionale Lichtrichtung 
		PhongShader.setDirectionalLight(new BaseLight(vecmath.color(0.5f,0.5f,0.5f),0.8f),vecmath.vector(1,1,1));
		
		shader = new Shader();
		textureShader = new Shader("phongTAmbVertex.vs", "phongTAmbFragment.fs");
//		textureShader = new Shader("TAmbDiffSpecVertex.vs", "TAmbDiffSpecFragment.fs");
		planeShader = new Shader("originalVertex.vs","originalFragment.fs");

//		Scene scene = SceneLoader.createScene("scene1.xml", textureShader);

		 RowNode row_one = new RowNode(0);
		 RowNode row_two = new RowNode(1);
		 RowNode row_three = new RowNode(2);
		 addNode(row_one);
		 addNode(row_two);
		 addNode(row_three);

//		for (RowNode row : scene.getNodes())
//			addNode(row);

		setTransformation(vecmath.identityMatrix());
		cam = new Camera(getTransformation());
		addNode(cam);

		
		// Phong extra diffuse:
		Vector3f[] v3fArray = toV3f(t);
		verticesT = Vertex.triangleVertices(v3fArray, colorT);
		Vertex.calcNormals(verticesT, pyramidIndices());
		// ENd Phong diffuse component

		Vector eyePosition = cam.getTransformation().getPosition();
		 Pyramid pyr = new Pyramid(verticesT, shader, cam.getEye(),
		 vecmath.vector(0, 0, 0));
		 row_one.addNode(pyr);
		
		 Pyramid pyr2 = new Pyramid(verticesT, shader, cam.getEye(),
		 vecmath.vector(0, 0, 0));
		 row_one.addNode(pyr2);
		
		 Pyramid pyr3 = new Pyramid(verticesT, shader, cam.getEye(),
		 vecmath.vector(0, 0, 0));
		 row_one.addNode(pyr3);
		
		 // Cube cube = new Cube(vertices, shader, vecmath.vector(0f, 0f,
//		 0f));
		 // row_one.addNode(cube);
		 //
		 // Cube cube2 = new Cube(vertices, shader, vecmath.vector(0, 0, 0));
		 // row_one.addNode(cube2);
		 // Cube cube3 = new Cube(vertices, shader, vecmath.vector(0f, 0, 0));
		 // row_one.addNode(cube3);
		
		
		 float cubeWidth = 1;
		 float cubeHeight = 1;
		 // cube.setHeight(cubeHeight);
		 // cube.setWidth(cubeWidth);
		 // cube2.setHeight(cubeHeight);
		 // cube2.setWidth(cubeWidth);
		 // cube3.setHeight(cubeHeight);
		 // cube3.setWidth(cubeWidth);
		
//		 Mesh m = ResourceLoader.loadOBJModel("jupiter.obj");
//		 Texture t = ResourceLoader.loadTexture("MoonMap2.jpg");
//		 OBJModel moon = new OBJModel(m.getVertices(), textureShader, t,
//		 vecmath.vector(0, 0, 0));
//		
//		 Mesh m2 = ResourceLoader.loadOBJModel("crateTest.obj");
//		 Texture t2 = ResourceLoader.loadTexture("stark.png");
//		 OBJModel crate = new OBJModel(m2.getVertices(), textureShader, t2,
//		 vecmath.vector(0, 0, 0));
//		 row_three.addNode(crate);
//		 pyr2.addNode(crate);
//		
//		  Pyramid pyr = new Pyramid(verticesT, shader, vecmath.vector(0, 0,
//		 0));
//		 crate.addNode(moon);
//		 row_three.addNode(moon);
//		
		Mesh m = ResourceLoader.loadOBJModel("jupiter.obj");
		Texture t = ResourceLoader.loadTexture("MoonMap2.jpg");
		OBJModel moon = new OBJModel(m.getVertices(), textureShader, t,vecmath.vector(0, 0, 0), eyePosition);
//
		Mesh m2 = ResourceLoader.loadOBJModel("crateTest.obj");
		Texture t2 = ResourceLoader.loadTexture("stark.png");
		OBJModel crate = new OBJModel(m2.getVertices(), textureShader, t2,vecmath.vector(0, 0, 0), eyePosition);
		row_three.addNode(crate);
		pyr2.addNode(crate);

//		Pyramid pyr = new Pyramid(verticesT, shader, vecmath.vector(0, 0, 0));
		crate.addNode(moon);
		row_three.addNode(moon);
		
		Mesh m5 = ResourceLoader.loadOBJModel("jupiter.obj");
		Texture t5 = ResourceLoader.loadTexture("jupiter.jpg");
		OBJModel jupiter = new OBJModel(m5.getVertices(), textureShader, t5,vecmath.vector(0, 0, 0), eyePosition);
		crate.addNode(jupiter);
		row_three.addNode(jupiter);
		
		
		Mesh m3 = ResourceLoader.loadOBJModel("backFirst.obj");
		Texture t3 = ResourceLoader.loadTexture("stars.jpg");
		OBJModel plane = new OBJModel(m3.getVertices(), planeShader, t3,vecmath.vector(0, 0, -5), eyePosition);
		addNode(plane);

		OBJModel plane2 = new OBJModel(m3.getVertices(), planeShader, t3,vecmath.vector(0, 0, -25), eyePosition);
		addNode(plane2);
		
		OBJModel plane3 = new OBJModel(m3.getVertices(), planeShader, t3,vecmath.vector(0, 0, -45), eyePosition);
		addNode(plane3);
		

		
		

		 row_one.setName("Row One");
		 row_two.setName("Row Two");
		 row_three.setName("Row Three");
		 cam.setName("Camera");
		// cube.setName("Cube1");
		// cube2.setName("Cube2");
		// cube3.setName("Cube3");
		// pyr.setName("Pyramid");
		// moon.setName("Moon");
		// crate.setName("Crate");

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
			else
				child.display(width, height, getTransformation());
		}

		textureShader.activate();
		textureShader.getProjectionMatrixUniform().set(projectionMatrix);
		textureShader.getViewMatrixUniform().set(viewMatrix);

		for (Node child : getChildNodes()) {
			if (child.getShader() != null
					&& child.getShader().equals(textureShader)){
				child.display(width, height, getTransformation());
			}
			else
				child.display(width, height, getTransformation());
		}

		planeShader.activate();
		planeShader.getProjectionMatrixUniform().set(projectionMatrix);
		planeShader.getViewMatrixUniform().set(viewMatrix);

		for (Node child : getChildNodes()) {
			if (child.getShader() != null
					&& child.getShader().equals(planeShader))
				child.display(width, height, getTransformation());
			else
				child.display(width, height, getTransformation());
		}
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {
	}

	public int[] cubeIndices() {
		int[] res = new int[24];

		res[0] = 0;
		res[1] = 1;
		res[2] = 2;
		res[3] = 3;

		res[4] = 4;
		res[5] = 5;
		res[6] = 6;
		res[7] = 7;

		res[8] = 1;
		res[9] = 4;
		res[10] = 7;
		res[11] = 2;

		res[12] = 3;
		res[13] = 2;
		res[14] = 7;
		res[15] = 6;

		res[16] = 5;
		res[17] = 0;
		res[18] = 3;
		res[19] = 6;

		res[20] = 5;
		res[21] = 4;
		res[22] = 1;
		res[23] = 0;

		return res;
	}

	public int[] pyramidIndices() {
		int[] res = new int[15];

		res[0] = 0;
		res[1] = 1;
		res[2] = 4;
		res[3] = 2;
		res[4] = 4;
		res[5] = 3;
		res[6] = 4;
		res[7] = 0;
		res[8] = 3;
		res[9] = 1;
		res[10] = 2;
		res[11] = 4;
		res[12] = 3;
		res[13] = 2;
		res[14] = 1;

		return res;
	}

	private Vector3f[] toV3f(Vector[] p) {

		Vector3f[] res = new Vector3f[p.length];

		for (int i = 0; i < p.length; i++) {
			res[i] = new Vector3f(p[i].x(), p[i].y(), p[i].z());
		}

		return res;
	}

}
