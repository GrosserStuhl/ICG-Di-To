import java.nio.FloatBuffer;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;


public class Vertex {

	Vector position;
	Color color;

	Vertex(Vector p, Color c) {
		position = p;
		color = c;
	}
	
	// Make construction of vertices easy on the eyes.
	public static Vertex v(Vector p, Color c) {
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
			
			/* geht iwie auch ohne
			v(p[0], c[0])*/ };
		
		
		
		return vertices;
		}
	
	
	
	public static Vertex[] modelVertices(Vector[] p, Color[] c) {
		// * 3 weil es drei float koordinaten x, y, z, gibt
		Vertex[] vertices = new Vertex[p.length * 3];
		
		System.out.println("LÄNGE : "+vertices.length);
		System.out.println("Colorlength: "+c.length);

// pData richtig
//		for (int i = 0; i < p.length; i++) {
//			System.out.println(p[i]);
//		}
		
		for (int i = 0; i < vertices.length; i++) {
			for (int j = 0; j < p.length; j++) {
				vertices[i] = v(p[j],c[j]);
			}
		}
//		for (int i = 0; i < vertices.length; i++) {
//			System.out.println(toString(vertices[i].position,vertices[i].color));
//		}
		
		
		return vertices;
		}
	
	public static String toString(Vector position, Color color){
		
		return "Vertex p:"+position+" Color:"+color;
	}
	
//	public static String toString(){
//		
//		return "Vertex p:"+position+" Color:"+color;
//	}
	
	
}
