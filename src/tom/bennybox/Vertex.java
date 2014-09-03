package tom.bennybox;

public class Vertex {

	// how many different numbers are total in this class
	// Vector3f has 3
	public static final int SIZE = 3;
	
	private Vector3f pos;
	
	public Vertex(Vector3f pos){
		this.pos = pos;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
}
