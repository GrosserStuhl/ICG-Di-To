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
import ogl.vecmath.Matrix;

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
	public static int textureAttribIdx = 2;
	public static int baseAttribIdx = 3;

	public static int ambientAttribIdx = 4;

	public static int normalAttribIdx = 5;
	public ColorUniform directionalLightColor;
	public FloatUniform directionalLightIntensity;
	public VectorUniform directionalLightDirection;

	// public static int diffuseColorAttribIdx = 6;
	// public static int diffuseIntensityAttribIdx = 7;
	// public static int diffuseDirectionAttribIdx = 8;

	public Shader() {

		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL11.GL_DEPTH_TEST);

		// Create and compile the vertex shader.
		int vs = glCreateShader(GL20.GL_VERTEX_SHADER);
		// load vertexShader
		glShaderSource(vs, ResourceLoader.loadShader("phongAmbDiffVertex.vs"));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL20.GL_FRAGMENT_SHADER);
		// load fragmentShader
		glShaderSource(fs, ResourceLoader.loadShader("phongAmbDiffFragment.fs"));
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
		glBindAttribLocation(program, baseAttribIdx, "baseColor");
		glBindAttribLocation(program, ambientAttribIdx, "ambientLight");
		glBindAttribLocation(program, textureAttribIdx, "textureCoord");

		glBindAttribLocation(program, normalAttribIdx, "normal");

		// glBindAttribLocation(program, diffuseColorAttribIdx,
		// "directionalLight.base.color");
		// glBindAttribLocation(program, diffuseIntensityAttribIdx,
		// "directionalLight.base.intensity");
		// glBindAttribLocation(program, diffuseDirectionAttribIdx,
		// "directionalLight.base.direction");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This
		// needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");

		directionalLightColor = new ColorUniform(program,
				"directionalLight.base.color");
		directionalLightIntensity = new FloatUniform(program,
				"directionalLight.base.intensity");
		directionalLightDirection = new VectorUniform(program,
				"directionalLight.direction");
	}

	public void activate() {
		// Activate the shader program and set the transformation matricies to
		// the
		// uniform variables.
		glUseProgram(program);
	}

	public void deactivate() {

		glUseProgram(0);
	}

	public Shader(String fileNameVS, String fileNameFS) {

		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL11.GL_DEPTH_TEST);

		// Create and compile the vertex shader.
		int vs = glCreateShader(GL20.GL_VERTEX_SHADER);
		// load vertexShader
		glShaderSource(vs, ResourceLoader.loadShader(fileNameVS));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL20.GL_FRAGMENT_SHADER);
		// load fragmentShader
		glShaderSource(fs, ResourceLoader.loadShader(fileNameFS));
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
		// glBindAttribLocation(program, colorAttribIdx, "color");
		glBindAttribLocation(program, textureAttribIdx, "textureCoord");
		glBindAttribLocation(program, ambientAttribIdx, "ambientLight");

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

	public static int getTextureAttribIdx() {
		return textureAttribIdx;
	}

	public static void setTextureAttribIdx(int textureAttribIdx) {
		Shader.textureAttribIdx = textureAttribIdx;
	}

	public int getProgram() {
		return program;
	}

	public static int getBaseAttribIdx() {
		return baseAttribIdx;
	}

	public static int getAmbientAttribIdx() {
		return ambientAttribIdx;
	}

	// public static int getDiffuseColorAttribIdx() {
	// return diffuseColorAttribIdx;
	// }
	//
	// public static int getDiffuseDirectionAttribIdx() {
	// return diffuseDirectionAttribIdx;
	// }
	//
	// public static int getDiffuseIntensityAttribIdx() {
	// return diffuseIntensityAttribIdx;
	// }

	public static int getNormalAttribIdx() {
		return normalAttribIdx;
	}

	public void setDirectionalLightColor(ColorUniform directionalLightColor) {
		this.directionalLightColor = directionalLightColor;
	}

	public void setDirectionalLightDirection(
			VectorUniform directionalLightDirection) {
		this.directionalLightDirection = directionalLightDirection;
	}

	public void setDirectionalLightIntensity(
			FloatUniform directionalLightIntensity) {
		this.directionalLightIntensity = directionalLightIntensity;
	}

	public ColorUniform getDirectionalLightColor() {
		return directionalLightColor;
	}

	public VectorUniform getDirectionalLightDirection() {
		return directionalLightDirection;
	}

	public FloatUniform getDirectionalLightIntensity() {
		return directionalLightIntensity;
	}

	@Override
	public void init() {

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

	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {

		for (Node child : getChildNodes()) {
			child.display(width, height, getTransformation());
		}
	}

	
	
	
	
	
	
	
	
	
	// @Override
	// public void init() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void simulate(float elapsed, Input input) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void display(int width, int height) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	//
	// @Override
	// public void display(int width, int height, Matrix parentMatrix) {
	//
	//
	// }

	
	
	
	
	
	
	
	
//	public void SetUniformf(String uniformName, float value) {
//		glUniform1f(m_resource.GetUniforms().get(uniformName), value);
//	}
//
//	public void SetUniform(String uniformName, Vector3f value) {
//		glUniform3f(m_resource.GetUniforms().get(uniformName), value.GetX(),
//				value.GetY(), value.GetZ());
//	}
//
//	public void SetUniform(String uniformName, Matrix4f value) {
//		glUniformMatrix4(m_resource.GetUniforms().get(uniformName), true,
//				Util.CreateFlippedBuffer(value));
//	}
//
//	public void SetUniformBaseLight(String uniformName, BaseLight baseLight) {
//		SetUniform(uniformName + ".color", baseLight.GetColor());
//		SetUniformf(uniformName + ".intensity", baseLight.GetIntensity());
//	}
//
//	public void SetUniformDirectionalLight(String uniformName,
//			DirectionalLight directionalLight) {
//		SetUniformBaseLight(uniformName + ".base", directionalLight);
//		SetUniform(uniformName + ".direction", directionalLight.GetDirection());
//	}

}
