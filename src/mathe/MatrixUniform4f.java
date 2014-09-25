package mathe;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import ogl.app.Uniform;

public class MatrixUniform4f extends Uniform {

	private FloatBuffer buffer;
	private Matrix4f m;
	
  /**
   * Create a new named uniform binding for the specified shader program.
   * 
   * @param program
   *          The shader program that this uniform is bound to.
   * @param name
   *          The name of the uniform varibale as seen in the shader program.
   */
  public MatrixUniform4f(int program, String name) {
    super(program, name);
    buffer = BufferUtils.createFloatBuffer(16);
    buffer.put(Matrix4f.identityMatrix().asArray());
    buffer.rewind();
  }

  /**
   * Transfer a new value to the shader uniform variable.
   * 
   * @param m
   *          The new matix value for the uniform variable.
   */
  public void set(Matrix4f m) {
	  
	this.m = m;  
    buffer.rewind();
    buffer.put(m.asArray());
    buffer.rewind();

    glUniformMatrix4(location, false, buffer);
  }
  
  public FloatBuffer getBuffer() {
	return buffer;
  }
  
  public Matrix4f getMatrix() {
	return m;
  }
}
