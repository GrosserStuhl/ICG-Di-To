package general;
import org.lwjgl.util.vector.Vector2f;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;


public class Mesh {

	public Vector[] positionData;
	public Color[] color;
	public Vector2f[] texCoord;
	
	public Vertex[] vertices;
	
	
	public Mesh(Vector[] vec, Color[] col) {
		this.positionData = vec;
		this.color = col;
		
	}
	
	public Mesh(Vector[] vData, Color[]color, Vertex[] vertices) {
		this.positionData = vData;
		this.color = color;
		this.vertices = vertices;
		
	}
	
	public Mesh(Vector[] vData, Vector2f[] texCoord, Vertex[] vertices) {
		this.positionData = vData;
		this.texCoord = texCoord;
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

	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	public void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}
	
	
	
}
