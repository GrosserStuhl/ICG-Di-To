package general;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import ogl.app.Input;
import ogl.app.MatrixUniform;
import ogl.app.Util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader extends Node {

	// The shader program.
	private int program;

	// The location of the "mvpMatrix" uniform variable.
	private MatrixUniform modelMatrixUniform;
	private MatrixUniform viewMatrixUniform;
	private MatrixUniform projectionMatrixUniform;

	// The attribute indices for the vertex data.
	public static int vertexAttribIdx = 0;
	public static int colorAttribIdx = 1;

	public Shader() {

		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL11.GL_DEPTH_TEST);

		// Create and compile the vertex shader.
		int vs = glCreateShader(GL20.GL_VERTEX_SHADER);
		// load vertexShader
		glShaderSource(vs, ResourceLoader.loadShader("originalVertex.vs"));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL20.GL_FRAGMENT_SHADER);
		//load fragmentShader
		glShaderSource(fs,  ResourceLoader.loadShader("originalFragment.fs"));
		glCompileShader(fs);
		Util.checkCompilation(fs);

		// Create the shader program and link vertex and fragment shader
		// together.
		program = glCreateProgram();
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		// Bind the vertex attribute data locations for this shader program. The
		// shader expects to get vertex and color data from the mesh. This needs
		// to
		// be done *before* linking the program.
		glBindAttribLocation(program, vertexAttribIdx, "vertex");
		glBindAttribLocation(program, colorAttribIdx, "color");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This
		// needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");
	}

	public static int getVertexAttribIdx() {
		return vertexAttribIdx;
	}

	public MatrixUniform getModelMatrixUniform() {
		return modelMatrixUniform;
	}

	public MatrixUniform getViewMatrixUniform() {
		return viewMatrixUniform;
	}

	public MatrixUniform getProjectionMatrixUniform() {
		return projectionMatrixUniform;
	}

	public static int getColorAttribIdx() {
		return colorAttribIdx;
	}

	public void activate() {
		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		glUseProgram(program);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
