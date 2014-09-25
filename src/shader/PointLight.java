package shader;

import mathe.Color;
import mathe.Vector3f;

public class PointLight  {
	
	public static final PointLight red = new PointLight(new Vector3f(-2, 0, 3f),new Color(1,0,0), (float) 0.8, (float) 0, (float) 1, (float) 1 );
	public static final PointLight green = new PointLight(new Vector3f(2, 0, -3f),new Color(0,1,0), (float) 0.4, (float) 0, (float) 1, (float) 1 );
	public static final PointLight blue = new PointLight(new Vector3f(0, 10, -3f),new Color(0,0,1), (float) 0.4, (float) 0, (float) 1, (float) 1 );
	
	
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


	public static PointLight getRed() {
		return red;
	}


	public static PointLight getGreen() {
		return green;
	}


	public static PointLight getBlue() {
		return blue;
	}


	
}
