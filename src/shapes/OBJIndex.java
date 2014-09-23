package shapes;

public class OBJIndex {

	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	
	private int colorIndex;
	
	public OBJIndex() {
	}

	public OBJIndex(int vertexIndex, int colorIndex) {
		this.vertexIndex = vertexIndex;
		this.colorIndex = colorIndex;
	}
	
	public int getColorIndex() {
		return colorIndex;
	}
}
