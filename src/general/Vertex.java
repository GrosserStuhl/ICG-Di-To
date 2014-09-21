package general;

import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import mathe.Vector3f;

import org.lwjgl.util.vector.Vector2f;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;
import shapes.OBJIndex;

public class Vertex {

	Vector position;
	Color color;
	Vector2f textureCoord;
	Vector normal;

	Vector3f position3f;
	Vector3f normal3f;

	Vertex(Vector p, Color c) {
		position = p;
		color = c;

		// muss gemacht werden, damit es nicht null ist -> sau hässlich
		normal = vecmath.vector(0f, 0f, 0f);
	}

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

	Vertex(Vector3f p, Color c) {
		position3f = p;
		color = c;

		// muss gemacht werden, damit es nicht null ist -> sau hässlich
		normal3f = new Vector3f(0f, 0f, 0f);
	}

	public static Vertex v3fC(Vector3f p, Color c) {
		return new Vertex(p, c);
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

	// überladen mit meiner Vektorenklasse
	public static Vertex[] cubeVertices(Vector3f[] p, Color[] c) {
		Vertex[] vertices = {
				// back (urspr. Front)
				v3fC(p[0], c[0]),
				v3fC(p[1], c[1]),
				v3fC(p[2], c[2]),
				v3fC(p[3], c[3]),
				// front (urspr. back)
				v3fC(p[4], c[4]),
				v3fC(p[5], c[5]),
				v3fC(p[6], c[6]),
				v3fC(p[7], c[7]),
				// right
				v3fC(p[1], c[1]),
				v3fC(p[4], c[4]),
				v3fC(p[7], c[7]),
				v3fC(p[2], c[2]),
				// top
				v3fC(p[3], c[3]), v3fC(p[2], c[2]),
				v3fC(p[7], c[7]),
				v3fC(p[6], c[6]),
				// left
				v3fC(p[5], c[5]), v3fC(p[0], c[0]), v3fC(p[3], c[3]),
				v3fC(p[6], c[6]),
				// bottom
				v3fC(p[5], c[5]), v3fC(p[4], c[4]), v3fC(p[1], c[1]),
				v3fC(p[0], c[0]) };

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
	
	
	
	

	public Vector3f getPosition3f() {
		return position3f;
	}

	public Vector getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
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

	public Vector3f getNormal3f() {
		return normal3f;
	}

	public void setNormal3f(Vector3f normal3f) {
		this.normal3f = normal3f;
	}

	public static String toString(Vector position, Color color) {

		return "Vertex p:" + position + " Color:" + color;
	}

	public static Vertex[] meshVertices(Vector[] p, Color[] c, int[] faces) {
		Vertex[] vertices = new Vertex[faces.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = v(p[faces[i]], c[faces[i]]);
		}
		return vertices;
	}

	public static Vertex[] meshVertices(Vector[] p, Vector2f[] t,
			ArrayList<OBJIndex> indices) {
		Vertex[] vertices = new Vertex[indices.size()];
		for (int i = 0; i < vertices.length; i++) {
			OBJIndex temp = indices.get(i);
			vertices[i] = vt(p[temp.vertexIndex], t[temp.texCoordIndex]);
		}
		return vertices;
	}

	public static Vertex[] fakeColor(Vector[] p, Color[] c,
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
			
			Vector3f v1 = vertices[i1].getPosition3f().sub(vertices[i0].getPosition3f());
			Vector3f v2 = vertices[i2].getPosition3f().sub(vertices[i0].getPosition3f());
			
			Vector3f normal = v1.cross(v2).normalize();
			
			
			vertices[pointer].setNormal3f(normal);
			pointer++;
			vertices[pointer].setNormal3f(normal);
			pointer++;
			vertices[pointer].setNormal3f(normal);
			pointer++;
		}
		for (int i = 0; i < vertices.length; i++)
			vertices[i].setNormal3f(vertices[i].getNormal3f().normalize());
		
	}

}
