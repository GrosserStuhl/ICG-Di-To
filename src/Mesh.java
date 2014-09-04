import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.util.ArrayList;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;


public class Mesh {

	public Vector[] positionData;
	public Color[] colorData;
	public int[] faces;
	
	
	public Mesh(Vector[] vec, Color[] col) {
		this.positionData = vec;
		this.colorData = col;
		
	}
	
	public Mesh(Vector[] vData, int[] faces) {
		this.positionData = vData;
		this.colorData = createWhiteColor(vData.length);
		this.faces = faces;
		
	}
	
	public Color[] createWhiteColor(int length){
		Color[] c = new Color[length];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = vecmath.color(220, 220, 220);
		}
		
		return c;
	}
	
	
	
	
	public Vector[] getPositionData() {
		return positionData;
	}


	public void setPositionData(Vector[] positionData) {
		this.positionData = positionData;
	}


	public Color[] getColorData() {
		return colorData;
	}


	public void setColorData(Color[] colorData) {
		this.colorData = colorData;
	}
	
}
