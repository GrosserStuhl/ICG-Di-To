package shader;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import general.ShapeNode;
import general.Vertex;
import mathe.Color;
import mathe.Vector3f;
import ogl.app.App;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class PointLight extends ShapeNode implements App {

	public PointLight(Vertex[] vertices, Shader shader, Vector translation,
			float scale) {
		super(vertices, shader, translation, scale);
	}

	@Override
	public void display(int width, int height, Matrix parentMatrix) {

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

		
		

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
	}
	
}
