package general;
import java.util.ArrayList;

import mathe.Vector3f;

import org.lwjgl.util.vector.Vector2f;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;
import shapes.OBJIndex;


public class Mesh {

	public Vector[] positionData;
	public Color[] color;
	public Vector2f[] texCoord;
	
	public Vertex[] vertices;
	
	private Vector3f[] position3f;
	private Vector3f[] normal3f;
	private mathe.Color[] colorM; 
	private ArrayList<OBJIndex> indices;
	
	
	public Mesh(Vector[] vec, Color[] col) {
		this.positionData = vec;
		this.color = col;
		
	}
	
	public Mesh(Vector[] vData, Color[]color, Vertex[] vertices) {
		this.positionData = vData;
		this.color = color;
		this.vertices = vertices;
		
	}
	
	
	// TEST OB DAS AUCH REICHT
	public Mesh(Vertex[] vertices, ArrayList<OBJIndex> indices) {
		this.vertices = vertices;
		this.indices = indices;
		
	}
	
	
	
	public Mesh(Vector[] vData, Vector2f[] texCoord, Vertex[] vertices) {
		this.positionData = vData;
		this.texCoord = texCoord;
		this.vertices = vertices;
		
	}
	
	
	

	public Mesh(Vector3f[] p, Vector2f[] t,Vector3f[] n, Vertex[] vertices) {
		this.position3f = p;
		this.texCoord = t;
		this.normal3f = n;
		this.vertices = vertices;
	}

	public Mesh(Vector3f[] p, mathe.Color[] c,Vector3f[] n, Vertex[] vertices) {
		this.position3f = p;
		this.colorM = c;
		this.normal3f = n;
		this.vertices = vertices;
		
	}
	
	
	

	public Vector[] getPositionData() {
		return positionData;
	}
	
	
	
	public Vector3f[] getNormal3f() {
		return normal3f;
	}
	
	public Vector3f[] getPosition3f() {
		return position3f;
	}
	
	public Vector2f[] getTexCoord() {
		return texCoord;
	}

	
	public mathe.Color[] getColorM() {
		return colorM;
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
	
	public ArrayList<OBJIndex> getIndices() {
		return indices;
	}
	
	
	
}
