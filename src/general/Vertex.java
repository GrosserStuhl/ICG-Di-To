package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;

public class Vertex {

	Vector position;
	Color color;
	Vector2f textureCoord;
	Vector normal;

	Vertex(Vector p, Color c) {
		position = p;
		color = c;
		
		// muss gemacht werden, damit es nicht null ist -> sau hässlich 
		normal = vecmath.vector(0f, 0f, 0f);
	}
	
//	Vertex(Vector p, Color c, Vector n) {
//		position = p;
//		color = c;
//		normal = n;
//	}
	
	public Vertex(Vector p, Vector2f t) {
		position = p;
		textureCoord = t;
	}

	// Make construction of vertices easy on the eyes.
	public static Vertex v(Vector p, Color c) {
		return new Vertex(p, c);
	}
	
	public static Vertex vt(Vector p, Vector2f t) {
		return new Vertex(p, t);
	}

	public static Vertex[] cubeVertices(Vector[] p, Color[] c) {
		Vertex[] vertices = {
				// back (urspr. Front)
				v(p[0], c[0]), v(p[1], c[1]), v(p[2], c[2]), v(p[3], c[3]),
				// front (urspr. back)
				v(p[4], c[4]), v(p[5], c[5]), v(p[6], c[6]), v(p[7], c[7]),
				// right
				v(p[1], c[1]), v(p[4], c[4]), v(p[7], c[7]), v(p[2], c[2]),
				// top
				v(p[3], c[3]), v(p[2], c[2]), v(p[7], c[7]), v(p[6], c[6]),
				// left
				v(p[5], c[5]), v(p[0], c[0]), v(p[3], c[3]), v(p[6], c[6]),
				// bottom
				v(p[5], c[5]), v(p[4], c[4]), v(p[1], c[1]), v(p[0], c[0]) };
		
		

		return vertices;
	}

	public static Vertex[] triangleVertices(Vector[] p, Color[] c) {
		Vertex[] vertices = {
				// back (urspr. Front)
				v(p[0], c[0]), v(p[1], c[1]), v(p[4], c[4]),
				// front (urspr. back)
				v(p[2], c[2]), v(p[4], c[4]), v(p[3], c[3]),
				// left
				v(p[4], c[4]), v(p[0], c[0]), v(p[3], c[3]),
				// right
				v(p[1], c[1]), v(p[2], c[2]), v(p[4], c[4]),
				// bottom
				v(p[3], c[3]), v(p[2], c[2]), v(p[1], c[1]),

		/*
		 * geht iwie auch ohne v(p[0], c[0])
		 */};

		return vertices;
	}

	public Vector getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}



	public static String toString(Vector position, Color color) {

		return "Vertex p:" + position + " Color:" + color;
	}

	
	
	public static Vertex[] meshVertices(Vector[] p, Color[] c, int[] faces) {

		Vertex[] vertices = new Vertex[faces.length];

//		System.out.println(p.length);

		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = v(p[faces[i]], c[faces[i]]);
		}

		return vertices;
	}
	
	
	public static Vertex[] meshVertices(Vector[] p, Vector2f[] t, ArrayList<OBJIndex> indices) {

		Vertex[] vertices = new Vertex[indices.size()];
		
		
		for (int i = 0; i < vertices.length; i++) {
			
			OBJIndex temp = indices.get(i);
			
			vertices[i] = vt(p[temp.vertexIndex],t[temp.texCoordIndex]);
		}

		return vertices;
		
	}
	
	public static Vertex[] fakeColor(Vector[] p, Color[] c,  ArrayList<OBJIndex> indices){
		
		Vertex[] vertices = new Vertex[indices.size()];

//		System.out.println(p.length);

		for (int i = 0; i < vertices.length; i++) {
			
			OBJIndex temp = indices.get(i);
			
			vertices[i] = v(p[temp.vertexIndex], c[temp.vertexIndex]);
		}
		
		return vertices;
		
	}
	
	
	public static void calcNormals(Vertex[] vertices, int[] indices){
		
		// += 3 because of triangles
		for (int i = 0; i < indices.length; i+= 3) {
			
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			// one line / direction of the triangle ( top to right-bottom)
			Vector v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			
			// from top to left-bottom
			Vector v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
			
			Vector normal = v1.cross(v2).normalize();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
			
			
			
		}
		
		// this will set the length and direction are gonna be correct for calculating
		for (int i = 0; i < vertices.length; i++) {
			vertices[i].setNormal(vertices[i].getNormal().normalize());
			
		}
		
		
	}
	
	




	
	public Vector2f getTextureCoord() {
		return textureCoord;
	}
	
	public void setNormal(Vector normal) {
		this.normal = normal;
	}
	
	public Vector getNormal() {
		return normal;
	}

	public static void calcNormals(Vertex[] vertices,
			ArrayList<OBJIndex> indices) {
		// += 3 because of triangles
				for (int i = 0; i < indices.size(); i+= 3) {
					
					
					int i0 = indices.get(i).normalIndex;
					int i1 = indices.get(i + 1).normalIndex;
					int i2 = indices.get(i + 2).normalIndex;
					
					// one line / direction of the triangle ( top to right-bottom)
					Vector v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
					
					// from top to left-bottom
					Vector v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
					
					Vector normal = v1.cross(v2).normalize();
					
					vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
					vertices[i1].setNormal(vertices[i0].getNormal().add(normal));
					vertices[i2].setNormal(vertices[i0].getNormal().add(normal));
					
					
					
				}
				
				// this will set the length and direction are gonna be correct for calculating
				for (int i = 0; i < vertices.length; i++) {
					vertices[i].setNormal(vertices[i].getNormal().normalize());
					
				}
		
	}
	
	

	

	

}
