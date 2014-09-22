package shader;

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
import general.Node;
import mathe.ColorUniform;
import mathe.FloatUniform;
import mathe.MatrixUniform3f;
import mathe.VectorUniform;
import ogl.app.Input;
import ogl.app.MatrixUniform;
import ogl.app.Util;
import ogl.vecmath.Matrix;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import util.ResourceLoader;

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

	public static int normalAttribIdx = 5;
	
	private MatrixUniform normalMatrixUniform;
	private VectorUniform lightPositionVectorUniform;
	private FloatUniform specularIntensityFloatUniform;
	private FloatUniform specularExponentFloatUniform;
	private VectorUniform eyePositionVectorUniform;
	
	private VectorUniform ambientLightVectorUniform;
	
	
	private MatrixUniform pLPosLightUniform;
	private VectorUniform vpositionUniform;
	private ColorUniform vcolorUniform;
	private FloatUniform fambientUniform;
	private FloatUniform constantAttenuationUniform;
	private FloatUniform linearAttenuationUniform;
	private FloatUniform exponentAttenuationUniform;

	
	
	public Shader() {

		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL_DEPTH_TEST);

		// Create and compile the vertex shader.
		int vs = glCreateShader(GL_VERTEX_SHADER);
		// load vertexShader
		glShaderSource(vs, ResourceLoader.loadShader("AmbDiffSpecVertex.vs"));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		// load fragmentShader
		glShaderSource(fs, ResourceLoader.loadShader("AmbDiffSpecFragment.fs"));
		glCompileShader(fs);
		Util.checkCompilation(fs);

		// Create the shader program and link vertex and fragment shader
		// together.
		program = glCreateProgram();
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		// Bind the vertex attribute data locations for this shader program. The
		// shader expects to get vertex and color data from the mesh. This needs to
		// be done *before* linking the program.
		glBindAttribLocation(program, vertexAttribIdx, "vertex");
		glBindAttribLocation(program, colorAttribIdx, "color");
		glBindAttribLocation(program, normalAttribIdx, "normal");
		
		
		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");

		ambientLightVectorUniform = new VectorUniform(program, "ambient");
		
		lightPositionVectorUniform = new VectorUniform(program,
				"lightPosition");
		
		specularIntensityFloatUniform = new FloatUniform(program,"specIntensity");
		specularExponentFloatUniform = new FloatUniform(program, "specExponent");
		eyePositionVectorUniform = new VectorUniform(program,"eyePosition");
		
		
		normalMatrixUniform = new MatrixUniform(program, "normalMatrix");
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
		glEnable(GL_DEPTH_TEST);
		
		
		//cull faces (back), clockwise
//		glFrontFace(GL_CCW);
//		glCullFace(GL_FRONT);
//		glEnable(GL_CULL_FACE);
		

		// Create and compile the vertex shader.
		int vs = glCreateShader(GL_VERTEX_SHADER);
		// load vertexShader
		glShaderSource(vs, ResourceLoader.loadShader(fileNameVS));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
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
		// shader expects to get vertex and color data from the mesh. This needs to
		// be done *before* linking the program.
		glBindAttribLocation(program, vertexAttribIdx, "vertex");
		glBindAttribLocation(program, textureAttribIdx, "textureCoord");
		glBindAttribLocation(program, normalAttribIdx, "normal");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");
		
		
		normalMatrixUniform = new MatrixUniform(program, "transformMatrix");
		
		ambientLightVectorUniform = new VectorUniform(program, "ambient");
		
		lightPositionVectorUniform = new VectorUniform(program,
				"lightPosition");
		
		specularIntensityFloatUniform = new FloatUniform(program,"specIntensity");
		specularExponentFloatUniform = new FloatUniform(program, "specExponent");
		eyePositionVectorUniform = new VectorUniform(program,"eyePosition");
		
		
		normalMatrixUniform = new MatrixUniform(program, "normalMatrix");
		
		
		pLPosLightUniform = new MatrixUniform(program, "pLPosLight");
		
		vpositionUniform = new VectorUniform(program, "pointLight.vposition");
		vcolorUniform = new ColorUniform(program, "pointLight.vcolor");
		fambientUniform = new FloatUniform(program, "fambient");
		constantAttenuationUniform = new FloatUniform(program, "pointLight.fconstantAtt");
		linearAttenuationUniform = new FloatUniform(program, "pointLight.flinearAtt");
		exponentAttenuationUniform = new FloatUniform(program, "pointLight.fexpAtt");
		
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


	
	public static int getNormalAttribIdx() {
		return normalAttribIdx;
	}

	
	public VectorUniform getLightPositionVectorUniform() {
		return lightPositionVectorUniform;
	}
	
	public FloatUniform getSpecularExponentFloatUniform() {
		return specularExponentFloatUniform;
	}
	
	public FloatUniform getSpecularIntensityFloatUniform() {
		return specularIntensityFloatUniform;
	}
	
	public VectorUniform getEyePositionVectorUniform() {
		return eyePositionVectorUniform;
	}
	
	public MatrixUniform getpLPosLightUniform() {
		return pLPosLightUniform;
	}
	
	
	public MatrixUniform getNormalMatrixUniform() {
		return normalMatrixUniform;
	}
	
	public VectorUniform getAmbientLightVectorUniform() {
		return ambientLightVectorUniform;
	}
	
	
	
	public FloatUniform getConstantAttenuationUniform() {
		return constantAttenuationUniform;
	}
	public FloatUniform getExponentAttenuationUniform() {
		return exponentAttenuationUniform;
	}
	public FloatUniform getLinearAttenuationUniform() {
		return linearAttenuationUniform;
	}
	public FloatUniform getFambientUniform() {
		return fambientUniform;
	}
	public ColorUniform getVcolorUniform() {
		return vcolorUniform;
	}
	public VectorUniform getVpositionUniform() {
		return vpositionUniform;
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

}
