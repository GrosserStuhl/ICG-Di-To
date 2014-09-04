package tom.bennybox;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Util {

	public static FloatBuffer createFloatBuffer(int size){
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices){
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		
		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
		}
		
		buffer.flip();
		
		return buffer;
		
	}

	public static String[] removeEmptyStrings(String[] data){
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++) 
			if(!data[i].equals(""))
				result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data){
		int[] result = new int[data.length];
		
		for (int i = 0; i < data.length; i++) {
			result[i] = data[i].intValue();
		}
		
		return result;
	}
	
}
