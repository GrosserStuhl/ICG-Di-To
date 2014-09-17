package general;

import ogl.app.Uniform;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class FloatUniform extends Uniform{

	public FloatUniform(int program, String name) {
		super(program, name);
		
	}
	
	public void set(float f){
		glUniform1f(location, f);
	}

}
