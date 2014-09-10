package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import general.Shader;
import general.ShapeNode;
import general.Vertex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.Texture;
import ogl.vecmath.Matrix;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class OBJModel extends ShapeNode implements App{

	public OBJModel(Vertex[] vertices, Shader shader, Texture t) {
		super(vertices, shader);
		
		this.t = t;
	}
	
	private Texture t;
//	private FloatBuffer positionData;

	@Override
	public void init() {
		
		positionData = positionBuffer(vertices.length);
		colorData = colorBuffer(vertices.length);
		finalizeBuffers(positionData, vertices);
		
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
		
				Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1),
						angle);
				

				 getShader().getModelMatrixUniform().set(modelMatrix.mult(vecmath.translationMatrix(2, 0, 0)));
				 glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				 positionData);
				 glEnableVertexAttribArray(Shader.getVertexAttribIdx());
				 glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0, colorData);
				 glEnableVertexAttribArray(Shader.getTextureAttribIdx());
				
				 glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		
	}


}
