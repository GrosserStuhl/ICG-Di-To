package tom.bennybox;

import org.lwjgl.input.Keyboard;

public class Game {

	private Mesh mesh;
	private Shader shader;
	
	public Game() {
		mesh = new Mesh();
		shader = new Shader();
		
		Vertex[] data = new Vertex[] {
				// bottomLeftCorner
				new Vertex(new Vector3f(-1,-1,-0)),
		        new Vertex(new Vector3f(0,1,0)),
		        new Vertex(new Vector3f(1,-1,0)),
		        };
		mesh.addVertices(data);
		
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();
	}
	
	public void input(){
		if(Input.getKeyDown(Keyboard.KEY_UP))
			System.out.println("pressed");
		if(Input.getKeyUp(Keyboard.KEY_UP))
			System.out.println("up no longer pressed");
		
		if(Input.getMouseDown(1))
			System.out.println("You just clicked at "+Input.getMousePosition());
		if(Input.getMouseUp(1))
			System.out.println("You just released your MouseButton");
	}
	
	public void update(){
		
	}
	
	public void render(){
		shader.bind();
		mesh.draw();
	}
	
}
