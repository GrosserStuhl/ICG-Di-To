package tom.bennybox;

import org.lwjgl.input.Keyboard;

public class Game {

	public Game() {
		// TODO Auto-generated constructor stub
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
		
	}
	
}
