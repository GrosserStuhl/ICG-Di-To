package general;
import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.util.ArrayList;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;


public class Mesh {

	public Vector[] positionData;
	public Color[] color;
	public int[] faceData;
	
	public Vertex[] vertices;
	
	
	public Mesh(Vector[] vec, Color[] col) {
		this.positionData = vec;
		this.color = col;
		
	}
	
	public Mesh(Vector[] vData, Color[]color, int[] faces, Vertex[] vertices) {
		this.positionData = vData;
		this.color = color;
		this.faceData = faces;
		this.vertices = vertices;
		
	}
	
	
	
	
	
	
	public Vector[] getPositionData() {
		return positionData;
	}


	public void setPositionData(Vector[] positionData) {
		this.positionData = positionData;
	}


	public Color[] getColorData() {
		return color;
	}


	public void setColorData(Color[] color) {
		this.color = color;
	}

	public int[] getFaceData() {
		return faceData;
	}

	public void setFaceData(int[] faces) {
		this.faceData = faces;
	}
	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	public void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}
	
	
	
}
