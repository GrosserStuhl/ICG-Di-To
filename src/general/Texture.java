package general;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	private int id;

	public Texture(int id) {
		this.id = id;
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE,id);
	}
	
	public int getID(){
		return id;
	}

}
