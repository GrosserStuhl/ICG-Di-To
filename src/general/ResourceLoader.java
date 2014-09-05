package general;
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
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector> vData = new ArrayList<Vector>();
		ArrayList<Integer> fData = new ArrayList<Integer>();
		
		try{
			meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
			String line;
			
			while((line = meshReader.readLine()) != null){
				if (line.startsWith("v ")){

					vData.add(vec(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} /*else if(line.startsWith("f ") && !line.split("\\s+")[1].contains("/")){
					
					
					fData.add(Integer.parseInt(line.split("\\s+")[1]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[2]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[3]) -1);
					
				}*/ else if(line.startsWith("f ") && !line.split("\\s+")[1].contains("/") ){
					// normal faces als triangle der Form:
					// f 2 3 4
					// f 2 3 4
					
					
					System.out.println(Integer.parseInt(line.split("\\s+")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[1]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[2]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[3]) -1);
					
				} else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d\\//\\d") ){
					
//					System.out.println("inhalt: "+line.split("\\s+")[3].split("\\//")[1]);
					
					// holt die FaceDaten aus dem .obj-File mit dem Format z.B.  
					// f  1//2  7//2  5//2
					// f  1//2  7//2  5//2 ...
					
					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\//")[0]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\//")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\//")[0]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\//")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\//")[0]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\//")[1]) -1);
					
					
				}
		
			}
			meshReader.close();
			//TO-DO: alle faces-Möglichkeiten angeben, jetzt 1/4 implementiert.
			
			
			
			return m = new Mesh(createMeshVertexData(vData),createMeshFaceData(fData));
			
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
		Vector[] result = new Vector[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		
		
		return result;
	}
	
	public static int[] createMeshFaceData(ArrayList<Integer> data) {
		int[] result = new int[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i).intValue();
		}
		
		return result;
	}
	
	
	
	
	
}
