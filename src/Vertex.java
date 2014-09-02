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
			// front
			v(p[0], c[0]), v(p[1], c[1]), v(p[2], c[2]), v(p[3], c[3]),
			// back
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
	
}
