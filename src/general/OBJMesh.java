package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class OBJMesh extends ShapeNode implements App{

	private Texture t;
	private Shader s;
	
	
	public OBJMesh(Vertex[] vertices, Shader shader) {
		super(vertices, shader);
		
		this.s= shader;
	}
	

	

	@Override
	public void init() {

//		t = ResourceLoader.loadTexture("superman.png");
		positionData = positionBuffer(vertices.length);
		colorData = colorBuffer(vertices.length);
		finalizeBuffer(positionData, colorData, vertices);
		
	}

	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
				if (input.isKeyToggled(Keyboard.KEY_B))
					// Increase the angle with a speed of 90 degrees per second.
					angle += 150 * elapsed;
		
	}

	@Override
	public void display(int width, int height) {
		// The modeling transformation. Object space to world space.
//				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
			
		
		
//				GL13.glActiveTexture(GL13.GL_TEXTURE0);
//				int location = GL20.glGetUniformLocation(s.getProgram(), "img");
//				GL20.glUniform1i(location, 0);
//				// 0 because it is to use texture unit 0
//				
//				t.bind();
		
				Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1),
						angle);
				

				 getShader().getModelMatrixUniform().set(modelMatrix.mult(vecmath.translationMatrix(2, 0, 0)));
				 
				 glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				 positionData);
				 glEnableVertexAttribArray(Shader.getVertexAttribIdx());
				 
				 
				 //last number offset
				 glVertexAttribPointer(Shader.getColorAttribIdx(), 3, false, 0, colorData);
				 glEnableVertexAttribArray(Shader.getColorAttribIdx());
				
				 glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		
	}
	
	// Buffers mit TextureInfo (wurden schon in Texture gemacht)
	public void finalizeBuffer(FloatBuffer positionData,FloatBuffer colorData, Vertex[] vertices) {
			for (Vertex v : vertices) {
				positionData.put(v.position.asArray());
				colorData.put(v.color.asArray());
			}
			positionData.rewind();
			colorData.rewind();
		}


}
