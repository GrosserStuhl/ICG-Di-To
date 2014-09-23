package shapes;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import general.ShapeNode;
import general.Vertex;

import java.nio.FloatBuffer;

import mathe.Color;
import mathe.Vector3f;
import ogl.app.App;
import ogl.app.Texture;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import shader.PhongShader;
import shader.PointLight;
import shader.Shader;

public class OBJModel extends ShapeNode implements App {

	private PointLight[] pls;
	private Texture t;

	public OBJModel(Vertex[] vertices, Shader shader, Texture t,  Vector translation, float scale, PointLight[] pls) {
		super(vertices, shader, translation, scale);
		
		this.t = t;
		this.pls = pls;
		
		positionData = createFloatBuffer(vertices.length * 3);
		textureData = createFloatBuffer(vertices.length * 2);
		normalData = createFloatBuffer(vertices.length * 3);
		ambientData = createFloatBuffer(vertices.length * 3);

		finalizeBuffers(positionData, textureData, normalData, vertices);
	}

	FloatBuffer positionData;
	FloatBuffer textureData;
	FloatBuffer normalData;
	
	FloatBuffer ambientData;

	
	@Override
	public void display(int width, int height, Matrix parentMatrix) {
		t.bind();

		Matrix modelMatrix = parentMatrix.mult(vecmath
				.translationMatrix(translation));
		modelMatrix = modelMatrix.mult(vecmath.rotationMatrix(1, 0, 1, angle));
		
		setTransformation(modelMatrix);

		getShader().getModelMatrixUniform().set(modelMatrix);
		
		
		Matrix viewMatrix = getShader().getViewMatrixUniform().getMatrix();
		Matrix normalMatrix = (viewMatrix.mult(modelMatrix).invertRigid().transpose());
		getShader().getNormalMatrixUniform().set(normalMatrix);
		
		getShader().getpLViewMatrixUniform().set(viewMatrix);
		getShader().getVpositionUniform().set(new Vector3f( -2, 0, 3f));
		getShader().getVcolorUniform().set(new Color(1,0,0));
		getShader().getFambientUniform().set((float) 0.8);
		
		getShader().getConstantAttenuationUniform().set((float) 0);
		getShader().getLinearAttenuationUniform().set((float) 1);
		getShader().getExponentAttenuationUniform().set((float) 1);
		
	
		
		
		
		
		//////////LIGHTNING SECTION ////////////////////
		// ambient light
		getShader().getAmbientLightVectorUniform().set(PhongShader.getAmbientLight());
				
		// diffuse Lightning
		getShader().getLightPositionVectorUniform().set(new Vector3f(0, 0, 16));
		
		// specular Lightning
		getShader().getSpecularIntensityFloatUniform().set(2);
		getShader().getSpecularExponentFloatUniform().set(32);
		////////////////////////////////////////////////
		
		glVertexAttribPointer(Shader.getVertexAttribIdx(), 3, false, 0,
				positionData);
		glEnableVertexAttribArray(Shader.getVertexAttribIdx());
		
		glVertexAttribPointer(Shader.getNormalAttribIdx(), 3, false, 0,
				normalData);
		glEnableVertexAttribArray(Shader.getNormalAttribIdx());

		// last number offset
		glVertexAttribPointer(Shader.getTextureAttribIdx(), 2, false, 0,
				textureData);
		glEnableVertexAttribArray(Shader.getTextureAttribIdx());
		
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
	}
	

	private void finalizeBuffers(FloatBuffer p, FloatBuffer t,FloatBuffer n, Vertex[] vertices) {
		for (Vertex v : vertices) {
				p.put(v.getPosition().asArray());
				t.put(v.getTextureCoord().asArray());
				n.put(v.getNormal().asArray());
		}
		p.rewind();
		t.rewind();
		n.rewind();
	}


}
