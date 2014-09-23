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

public class PointLight  {
	
	
	private Vector3f plPosition;
	private Color plColor;
	private float fAmbientTerm;
	
	private float constantAttenuation;
	private float linearAttenuation;
	private float exponentAttenuation;
	
	public PointLight(Vector3f plPos, Color plCol, float fAmb, float constAtt, float linAtt, float expAtt ) {
		this.plPosition = plPos;
		this.plColor = plCol;
		this.fAmbientTerm = fAmb;
		this.constantAttenuation = constAtt;
		this.linearAttenuation = linAtt;
		this.exponentAttenuation = expAtt;

	}


	public Vector3f getPlPosition() {
		return plPosition;
	}

	public Color getPlColor() {
		return plColor;
	}

	public float getfAmbientTerm() {
		return fAmbientTerm;
	}

	public float getConstantAttenuation() {
		return constantAttenuation;
	}
	
	public float getExponentAttenuation() {
		return exponentAttenuation;
	}
	
	public float getLinearAttenuation() {
		return linearAttenuation;
	}


	
}
