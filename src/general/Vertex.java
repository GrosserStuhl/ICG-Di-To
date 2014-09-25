package general;

import java.util.ArrayList;

import mathe.Color;
import mathe.Vector2f;
import mathe.Vector3f;


import shapes.OBJIndex;

public class Vertex {

	Vector3f position;
	Color color;
	Vector2f textureCoord;
	Vector3f normal;

	public Vertex(Vector3f p, Vector2f t) {
		position = p;
		textureCoord = t;
	}
	
	
	
	
	public Vertex(Vector3f p, mathe.Color c, Vector3f n) {
		position = p;
		color = c;
		normal = n;
	}
	
	public Vertex(Vector3f p, Vector2f t, Vector3f n) {
		position = p;
		textureCoord = t;
		normal = n;
	}
	
	
	
	
	
	

	// Make construction of vertices easy on the eyes.
	public static Vertex v(Vector3f p, Color c) {
		return new Vertex(p, c);
	}

	public static Vertex vt(Vector3f p, Vector2f t) {
		return new Vertex(p, t);
	}

	Vertex(Vector3f p, Color c) {
		position = p;
		color = c;

		// muss gemacht werden, damit es nicht null ist -> sau hässlich
		normal = new Vector3f(0f, 0f, 0f);
	}

	public static Vertex v3fC(Vector3f p, mathe.Color c) {
		return new Vertex(p, c);
	}



	// überladen mit meiner Vektorenklasse
	public static Vertex[] cubeVertices(Vector3f[] p, Color[] c) {
		Vertex[] vertices = {
				// back (urspr. Front)
				v3fC(p[0], c[0]),v3fC(p[1], c[1]),v3fC(p[2], c[2]),v3fC(p[3], c[3]),
				// front (urspr. back)
				v3fC(p[4], c[4]),v3fC(p[5], c[5]),v3fC(p[6], c[6]),v3fC(p[7], c[7]),
				// right
				v3fC(p[1], c[1]),v3fC(p[4], c[4]),v3fC(p[7], c[7]),v3fC(p[2], c[2]),
				// top
				v3fC(p[3], c[3]), v3fC(p[2], c[2]),v3fC(p[7], c[7]),v3fC(p[6], c[6]),
				// left
				v3fC(p[5], c[5]), v3fC(p[0], c[0]), v3fC(p[3], c[3]),v3fC(p[6], c[6]),
				// bottom
				v3fC(p[5], c[5]), v3fC(p[4], c[4]), v3fC(p[1], c[1]),v3fC(p[0], c[0]) 
		};
		return vertices;
	}

	
	public static Vertex[] triangleVertices(Vector3f[] p, Color[] c) {
		Vertex[] vertices = {
				// back (urspr. Front)
				v3fC(p[0], c[0]), v3fC(p[1], c[1]), v3fC(p[4], c[4]),
				// front (urspr. back)
				v3fC(p[2], c[2]), v3fC(p[4], c[4]), v3fC(p[3], c[3]),
				// left
				v3fC(p[4], c[4]), v3fC(p[0], c[0]), v3fC(p[3], c[3]),
				// right
				v3fC(p[1], c[1]), v3fC(p[2], c[2]), v3fC(p[4], c[4]),
				// bottom
				v3fC(p[3], c[3]), v3fC(p[2], c[2]), v3fC(p[1], c[1]),

		/*
		 * geht iwie auch ohne v(p[0], c[0])
		 */};

		return vertices;
	}
	
	
	
	public Vector3f getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public static String toString(Vector3f position, Color color) {

		return "Vertex p:" + position + " Color:" + color;
	}

	public static Vertex[] meshVertices(Vector3f[] p, mathe.Color[] c, Vector3f[] n, ArrayList<OBJIndex> indices) {
		Vertex[] vertices = new Vertex[indices.size()];
		for (int i = 0; i < vertices.length; i++) {
			OBJIndex temp = indices.get(i);
			vertices[i] = v_pcn(p[temp.vertexIndex], c[temp.vertexIndex], n[temp.normalIndex]);
		}
		return vertices;
	}
	
	
	/**
	 *  returns a new Vertex from OBJModel with vertexpositions, colorData, and normals
	 * @param p = Vector3f position;
	 * @param c = mathe.Color colorData;
	 * @param n = Vector3f normals;
	 * @return new Vertex(p,c,n);
	 */
	public static Vertex v_pcn(Vector3f p, mathe.Color c,Vector3f n) {
		return new Vertex(p, c, n);
	}
	
	
	
	
	/**
	 * Creates OBJ-Model vertices. These usually have VertexCoordinates, TextureCoordinates and NormalData.
	 * @param p = Vector3f[] position;
	 * @param t = Vector2f[] textureCoordinates;
	 * @param n = Vector3f[] normalData;
	 * @param indices = ArrayList<OBJIndex> indices;
	 * @return new Vertex[] vertices
	 */
	public static Vertex[] modelVertices(Vector3f[] p, Vector2f[] t,Vector3f[] n, ArrayList<OBJIndex> indices) {
		Vertex[] vertices = new Vertex[indices.size()];
		for (int i = 0; i < vertices.length; i++) {
			OBJIndex temp = indices.get(i);
			vertices[i] = v_ptn(p[temp.vertexIndex], t[temp.texCoordIndex],n[temp.normalIndex]);
		}
		return vertices;
	}
	
	/**
	 *  returns a new Vertex from OBJModel with vertexpositions, normals, and texturecoords
	 * @param p = Vector3f position;
	 * @param t = Vector2f textureCoordinates;
	 * @param n = Vector3f normals;
	 * @return
	 */
	public static Vertex v_ptn(Vector3f p, Vector2f t,Vector3f n) {
		return new Vertex(p, t, n);
	}
	
	
	

	public static Vertex[] fakeColor(Vector3f[] p, Color[] c,
			ArrayList<OBJIndex> indices) {
		Vertex[] vertices = new Vertex[indices.size()];
		for (int i = 0; i < vertices.length; i++) {
			OBJIndex temp = indices.get(i);
			vertices[i] = v(p[temp.vertexIndex], c[temp.vertexIndex]);
		}
		return vertices;
	}
	
	
	public static void calcNormals(Vertex[] vertices, int[] indices) {
		int pointer = 0;
		
		for (int i = 0; i < indices.length; i += 3) {
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
			
			Vector3f normal = v1.cross(v2).normalize();
			
			
			vertices[pointer].setNormal(normal);
			pointer++;
			vertices[pointer].setNormal(normal);
			pointer++;
			vertices[pointer].setNormal(normal);
			pointer++;
		}
		for (int i = 0; i < vertices.length; i++)
			vertices[i].setNormal(vertices[i].getNormal().normalize());
		
	}

}
