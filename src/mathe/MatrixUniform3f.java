package mathe;

import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import ogl.app.Uniform;
import ogl.vecmath.Matrix;

public class MatrixUniform3f extends Uniform {

	private FloatBuffer buffer;

	/**
	 * Create a new named uniform binding for the specified shader program.
	 * 
	 * @param program
	 *            The shader program that this uniform is bound to.
	 * @param name
	 *            The name of the uniform varibale as seen in the shader
	 *            program.
	 */
	public MatrixUniform3f(int program, String name) {
		super(program, name);

		buffer = BufferUtils.createFloatBuffer(9);
		// buffer.put(vecmath.identityMatrix().asArray());
		// buffer.rewind();
	}

	/**
	 * Transfer a new value to the shader uniform variable.
	 * 
	 * @param m
	 *            The new matix value for the uniform variable.
	 */
	public void set(FloatBuffer b) {

		buffer = BufferUtils.createFloatBuffer(9);

		buffer.rewind();
		buffer = b.duplicate();
		buffer.rewind();

		glUniformMatrix3(location, false, buffer);
	}

	public void set(Matrix m) {

		buffer = BufferUtils.createFloatBuffer(16);

		buffer.rewind();
		buffer.put(m.asArray());
		buffer.rewind();

		glUniformMatrix3(location, false, buffer);
	}

	public FloatBuffer getBuffer() {
		return buffer;
	}
}
