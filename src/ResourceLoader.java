import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;

public class ResourceLoader {

	
	public static Mesh loadMesh(String fileName){
		
		Mesh m = null;
		
//		StringBuilder vertexData = new StringBuilder();
		
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector> vData = new ArrayList<Vector>();
		
		try{
			meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
			String line;
			
			while((line = meshReader.readLine()) != null){
				if (line.startsWith("v ")){
//					String[] tokens = line.split(" ");
					vData.add(vec(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} /*else
					meshData.append(line+"\n");*/
		
			}
			meshReader.close();
			
			
			Vector[] meshVertexData = createMeshVertexData(vData);
//			Color[] colorRandom = createRandomColor(vData.length);
			System.out.println(meshVertexData[0].toString());
			
			return m = new Mesh(createMeshVertexData(vData));
			
		} catch(Exception e){
			
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
		
	}
	
	
	
	
	private static Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}
	
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
	}
	
	
	

	
	public static Vector[] createMeshVertexData(ArrayList<Vector> data) {
		Vector[] meshData = new Vector[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			meshData[i] = data.get(i);
		}
		
		return meshData;
	}
	
	
	
	
	
}
