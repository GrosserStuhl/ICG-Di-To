package general;
import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;

public class ResourceLoader {

	
	public static Mesh loadMesh(String fileName){
		
		Mesh m = null;
		
		Material mat = null;
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector> vData = new ArrayList<Vector>();
		ArrayList<Integer> fData = new ArrayList<Integer>();
		ArrayList<Vector> nData = new ArrayList<Vector>();
		
		String line;
		
		try{
			meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
			
			
			while((line = meshReader.readLine()) != null){
				if (line.startsWith("v ")){
					// extracts vertex info
					vData.add(vec(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("vn ")){
					// extracts Vectornormal info
					
					nData.add(vec(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("f ") && !line.split("\\s+")[1].contains("/") ){
					// face triangulated, without any other than pure vertex info 
					//Bsp. Format:
					// f 2 3 4
					// f 2 3 4
					
					
					
					fData.add(Integer.parseInt(line.split("\\s+")[1]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[2]) -1);
					fData.add(Integer.parseInt(line.split("\\s+")[3]) -1);
					
				} else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/+\\d+") ){
					
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Geometryinfos. Das Format z.B.  
					// f  1//2  7//2  5//2
					// f  1//2  7//2  5//2 ...
					
					//    Bsp. 7//2 : hier wäre die erste Zahl(7) die vertex koordinate und die zweite Zahl (2) die VertexNormalenkoordinate
					//    			  wegen Einfachheit wurde hier erstmal auf VertexNormalen verzichtet und jedesmal nur die Vertexkoordinate extrahiert

					
					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/+")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/+")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/+")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/+")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/+")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/+")[1]) -1);
					
					
				} else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/{1}\\d+") ){
					
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Texturinfos. Das Format z.B.  
					// f  1/2  7/2  5/2
					// f  1/2  7/2  5/2 ...
					
					//    Bsp. 7/2 : hier wäre die erste Zahl(7) die vertex koordinate und die zweite Zahl (2) die Texturkoordinate
					//    			  wegen Einfachheit wurde hier erstmal auf Texturkoordinaten verzichtet und jedesmal nur die Vertexkoordinate extrahiert

					System.out.println("Hier drin Texturen");
					
					
					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[1]) -1);
					
					
				}
		
			}
			meshReader.close();
			//TO-DO: alle faces-Möglichkeiten angeben, jetzt 1/4 implementiert.
			
			Vector[] positionData = createMeshVertexData(vData);
			int[] faces = createMeshFaceData(fData);
			
			
			// prüft ob das Modell(.obj) file auch zusätzlich ein Material File (.mtl) mit dem gleichen Namen hat
			// und versucht dann dessen Infos herauszulesen.
			if(new File("./res/models/"+ fileName.replace(".obj", ".mtl")).exists())
			try{
				meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName.replace(".obj", ".mtl")));
				
				float Ns = 0; float Ka = 0; float Kd = 0; float Ks = 0;
				
				while((line = meshReader.readLine()) != null){
					if (line.startsWith("Ns ")){
						Ns = Float.parseFloat(line.split("\\s+")[1]);
					} else if (line.startsWith("Ka ")){
						Ka = Float.parseFloat(line.split("\\s+")[1]);
					} else if (line.startsWith("Kd ")){
						Kd = Float.parseFloat(line.split("\\s+")[1]);
					} else if (line.startsWith("Ks ")){
						Ks = Float.parseFloat(line.split("\\s+")[1]);
					}
				}
				meshReader.close();
				
				if(Ns != 0 && Ka != 0 && Kd != 0 && Ks != 0)
					mat = new Material(Ns,Ka,Kd,Ks);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			Color[] whiteColor = createWhiteColor(vData.size());
			
			
			Vertex[] vertices = Vertex.meshVertices(positionData,whiteColor, faces);
			
			
			
			return m = new Mesh(positionData,whiteColor, faces,vertices);
			
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
		
//		System.out.println("facelength: "+result.length);
		
		return result;
	}
	
	public static Color[] createWhiteColor(int length){
		Color[] c = new Color[length];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = vecmath.color(220, 220, 220);
		}
		
		return c;
	}
	
	
	
	
	
}
