package util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import mathe.Matrix4f;

public class Util {

	public static FloatBuffer createFloatBuffer(int size){
		
		return BufferUtils.createFloatBuffer(size);
	}
	
	
	public static FloatBuffer createFloatBuffer(Matrix4f value){
		
		FloatBuffer buffer = createFloatBuffer(16);
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) 
				buffer.put(value.get(i,j));
			
		buffer.flip();
		
		return buffer;
	}
	
	//ACHTUNG glUniformMatrix4true -> param, rowMajor order(true) or columnMajor order (false)
}
