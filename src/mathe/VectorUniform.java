package mathe;

import ogl.app.Uniform;
import ogl.vecmath.Vector;

import static org.lwjgl.opengl.GL20.glUniform3f;

public class VectorUniform extends Uniform {

	public VectorUniform(int program, String name) {
		super(program, name);
	}
	
	public void set(Vector3f value){
		glUniform3f(location, value.getX(), value.getY(), value.getZ());
	}

}
