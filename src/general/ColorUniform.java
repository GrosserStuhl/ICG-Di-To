package general;



import ogl.app.Uniform;
import ogl.vecmath.Color;
import static org.lwjgl.opengl.GL20.glUniform3f;

public class ColorUniform extends Uniform{

	public ColorUniform(int program, String name) {
		super(program, name);
	}
	
	public void set(Color value)
	{
		glUniform3f(location, value.getR(), value.getG(), value.getB());
	}

	
	
}
