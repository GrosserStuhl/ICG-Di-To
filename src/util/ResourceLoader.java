package util;
import general.Mesh;
import general.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import mathe.Vector2f;
import mathe.Vector3f;
import ogl.app.Texture;
import shapes.OBJIndex;

public class ResourceLoader {

	
	
	public static String loadShader(String fileName){
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/"+ fileName));
			String line;
			
			while((line = shaderReader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
	
	
	
	public static Texture loadTexture(String fileName){
		
		
		try{
			Texture t = new Texture(new File("./res/textures/"+fileName));
			
			return t;
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
		
		
	}
	
	
	public static Mesh loadOBJModel(String fileName){
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector3f> vData = new ArrayList<Vector3f>();
		ArrayList<Vector3f> nData = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoord = new ArrayList<Vector2f>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		
		
		String line;
		
		try{
			meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
			
			
			while((line = meshReader.readLine()) != null){
				if (line.startsWith("v ")){
					// extracts vertex info
					vData.add(v3f(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("vn ")){
					// extracts Vectornormal info
					
					nData.add(v3f(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("vt ")){
					// extracts Vectornormal info
					
					texCoord.add(v2f(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2])));
					
				}
				
//				else if(line.startsWith("f ") && !line.split("\\s+")[1].contains("/") ){
//					// face triangulated, without any other than pure vertex info 
//					//Bsp. Format:
//					// f 2 3 4
//					// f 2 3 4
//					
//					fData.add(Integer.parseInt(line.split("\\s+")[1]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[2]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[3]) -1);
					
//				} 
//			
//			else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/+\\d+") ){
//					
//					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Normaleninfos. Das Format z.B.  
//					// f  1//2  7//2  5//2
//					// f  1//2  7//2  5//2 ...
//					
//					//    Bsp. 7//2 : hier wäre die erste Zahl(7) die vertex koordinate und die zweite Zahl (2) die VertexNormalenkoordinate
//					//    			  wegen Einfachheit wurde hier erstmal auf VertexNormalen verzichtet und jedesmal nur die Vertexkoordinate extrahiert
//
//					
//					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/+")[0]) -1);
////					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/+")[1]) -1);
//					
//					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/+")[0]) -1);
////					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/+")[1]) -1);
//					
//					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/+")[0]) -1);
////					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/+")[1]) -1);
//					
//					
//				} 
//			
			else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/{1}\\d+") ){
					
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Texturinfos. Hier fehlen die Vektornormalen. Das Format z.B.  
					// f  1/2  7/2  5/2
					// f  1/2  7/2  5/2 ...
					
					//    Bsp. 7/2 : hier wäre die erste Zahl(7) die vertex koordinate und die zweite Zahl (2) die Texturkoordinate
					//    			  wegen Einfachheit wurde hier erstmal auf Texturkoordinaten verzichtet und jedesmal nur die Vertexkoordinate extrahiert

					
					
				for (int j = 0; j < line.split("\\s+").length - 2; j++) {
					indices.add(parseOBJIndex(line.split("\\s+")[1 ],false));
					indices.add(parseOBJIndex(line.split("\\s+")[2 + j],false));
				}
					
		
			}else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/\\d+\\/\\d+") ){
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Textur und Vektornormaleninfos. Das Format z.B.  
					// f  1/3/2  1/7/2  9/5/2
					// f  1/2/5  7/2/6  5/2/3 ...
					
					//    Bsp. 7/2/6 : hier wäre die erste Zahl(7) die vertex koordinate, die zweite Zahl (2) die Texturkoordinate, die dritte Zahl (6) die Vektornormale

//					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[0]) -1);
//					texFaceData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[1]) -1);
					
				for (int j = 0; j < line.split("\\s+").length - 3; j++) {
					indices.add(parseOBJIndex(line.split("\\s+")[1], true));
					indices.add(parseOBJIndex(line.split("\\s+")[2 + j], true));
					indices.add(parseOBJIndex(line.split("\\s+")[3 + j], true));
				}
				
					
				
				
				}
		
			}
			meshReader.close();
			
			
			Vector3f[] positionData = toVector3fArray(vData);
			Vector3f[] normalData = toVector3fArray(nData);
			Vector2f[] textureCoord = toVector2fArray(texCoord);
			
			
//			Vertex[] vertices = Vertex.modelVertices(positionData, textureCoord,normalData, indices);
			Vertex[] vertices = Vertex.modelVertices(positionData, textureCoord, normalData, indices);

			
			return new Mesh(vertices,indices);

		} catch(Exception e){
			
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
		
	}
	
	
	public static Mesh loadOBJMesh(String fileName){
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector3f> vData = new ArrayList<Vector3f>();
		ArrayList<Vector3f> nData = new ArrayList<Vector3f>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		
		String line;
		
		try{
			meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
			
			
			while((line = meshReader.readLine()) != null){
				if (line.startsWith("v ")){
					// extracts vertex info
					vData.add(v3f(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("vn ")){
					// extracts Vectornormal info
					
					nData.add(v3f(Float.parseFloat(line.split("\\s+")[1]),Float.parseFloat(line.split("\\s+")[2]),Float.parseFloat(line.split("\\s+")[3])));
					
				} else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/{1}\\d+") ){
					
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Texturinfos. Hier fehlen die Vektornormalen. Das Format z.B.  
					// f  1/2  7/2  5/2
					// f  1/2  7/2  5/2 ...
					
					//    Bsp. 7/2 : hier wäre die erste Zahl(7) die vertex koordinate und die zweite Zahl (2) die Texturkoordinate
					//    			  wegen Einfachheit wurde hier erstmal auf Texturkoordinaten verzichtet und jedesmal nur die Vertexkoordinate extrahiert

					
					for (int j = 0; j < line.split("\\s+").length - 2; j++) {
						indices.add(parseOBJIndex(line.split("\\s+")[1 ],false));
						indices.add(parseOBJIndex(line.split("\\s+")[2 + j],false));
					}
						
		
			}else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/\\d+\\/\\d+") ){
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Textur und Vektornormaleninfos. Das Format z.B.  
					// f  1/3/2  1/7/2  9/5/2
					// f  1/2/5  7/2/6  5/2/3 ...
					
					//    Bsp. 7/2/6 : hier wäre die erste Zahl(7) die vertex koordinate, die zweite Zahl (2) die Texturkoordinate, die dritte Zahl (6) die Vektornormale

					for (int j = 0; j < line.split("\\s+").length - 3; j++) {
						indices.add(parseOBJIndex(line.split("\\s+")[1], true));
						indices.add(parseOBJIndex(line.split("\\s+")[2 + j], true));
						indices.add(parseOBJIndex(line.split("\\s+")[3 + j], true));
					}
			}
				
				
		
	}
			meshReader.close();
			
			
			Vector3f[] positionData = toVector3fArray(vData);
			Vector3f[] normalData = toVector3fArray(nData);
			mathe.Color[] col = createGreenColor(vData.size());
			
			Vertex[] vertices = null;
			
				vertices = Vertex.meshVertices(positionData,col,normalData, indices);
				return new Mesh(positionData,col,normalData,vertices);
				
			
		} catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	
	public static Vector2f[] toVector2fArray(ArrayList<Vector2f> data) {
		Vector2f[] result = new Vector2f[data.size()];
		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);
		
		return result;
	}
	
	
	private static OBJIndex parseOBJIndex(String token, boolean hasVectorNormals){
		
		String[] values = token.split("\\/");
		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) -1;
		result.texCoordIndex = Integer.parseInt(values[1]) -1;
		
		if(hasVectorNormals)
		result.normalIndex = Integer.parseInt(values[2]) -1;
		
		return result;
		
	}
	
	
	
	private static Vector3f v3f(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}

	private static Vector2f v2f(float x, float y) {
		return new Vector2f(x,y);
	}
	
	
	

	
	public static Vector3f[] toVector3fArray(ArrayList<Vector3f> data) {
		Vector3f[] result = new Vector3f[data.size()];
		for (int i = 0; i < data.size(); i++) 
			result[i] = data.get(i);
			
		return result;
	}
	
	public static int[] toIntArray(ArrayList<Integer> data) {
		int[] result = new int[data.size()];
		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i).intValue();
		
		return result;
	}
	
	
	
	
	public static mathe.Color[] createGreenColor(int length){
		mathe.Color[] c = new mathe.Color[length];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = new mathe.Color(0,1,0);
		}
		
		return c;
	}
	
	
	
	
	
	
}
