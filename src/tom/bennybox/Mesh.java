package tom.bennybox;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

	// this is going to be a pointer
	private int vbo;
	// how much data we have from this pointer on
	private int size;

	public Mesh() {

		vbo = glGenBuffers();
		size = 0;
	}
	
	public void addVertices(Vertex[] vertices){
		size = vertices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		
		// hints opengl this data isnt gonna be changing = static 
		// also possible: GL_DYNAMIC_DRAW(sometimes changing) or GL_STREAM_DRAW(changing every frame) just for optimizing
		glBufferData(GL_ARRAY_BUFFER,Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
	}
	
	
	public void draw(){
		
		//dividing up the data, how to interpret the data
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		// 3 float elements, last element for optimization, multiplied by four each floating point number is four bytes , where it starts 0 (right at the beginning)
		glVertexAttribPointer(0, 3, GL_FLOAT,false, Vertex.SIZE * 4, 0);
		glDrawArrays(GL_TRIANGLES, 0, size);
		
		glDisableVertexAttribArray(0);
	}
	

}
