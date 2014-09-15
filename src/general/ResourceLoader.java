package general;
import static ogl.vecmathimp.FactoryDefault.vecmath;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

import ogl.app.Texture;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;

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
		
		Mesh m = null;
		
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector> vData = new ArrayList<Vector>();
		ArrayList<Vector> nData = new ArrayList<Vector>();
		ArrayList<Vector2f> texCoord = new ArrayList<Vector2f>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		
		
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
			
			
			Vector[] positionData = createMeshVertexData(vData);
//			Color[] col = null;
			
			
			
			//create arrays
//			int[] faces = toIntArray(fData);
//			
//			Vertex[] vertices = null;
//			
//			// if theres texture data available read it and hand it over, if not create white color
//			if(!texCoord.isEmpty()){
//				int[] texFaces = toIntArray(texFaceData);
//				Vector2f[] texCoordArray = createTextureVertexData(texCoord);
//				
//				vertices = Vertex.meshVertices(positionData,texCoordArray, faces, texFaces);
//				return new Mesh(positionData,texCoordArray,vertices);
//			}else{
//				col = createWhiteColor(vData.size());
//				vertices = Vertex.meshVertices(positionData,col, faces);
//				return m = new Mesh(positionData,col,vertices);
//			}
			
			
//			col = createWhiteColor(vData.size());
			
			
			Vertex[] vertices = Vertex.meshVertices(positionData, toVector2fArray(texCoord), indices);
			return new Mesh(positionData,toVector2fArray(texCoord),vertices);
			
//			Vertex[] vertices  = Vertex.fakeColor(positionData, col, indices);
//			return new Mesh(positionData,col,vertices); 
			
			
		} catch(Exception e){
			
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
		
	}
	
	
	public static Vector2f[] toVector2fArray(ArrayList<Vector2f> data) {
		Vector2f[] result = new Vector2f[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		
		
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
	
	


	private static Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}
	
	
	

	
	private static Vector2f v2f(float x, float y) {
		return new Vector2f(x,y);
	}
	
	
	

	
	public static Vector[] createMeshVertexData(ArrayList<Vector> data) {
		Vector[] result = new Vector[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		
		
		return result;
	}
	
	public static int[] toIntArray(ArrayList<Integer> data) {
		int[] result = new int[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i).intValue();
		}
		
		
		return result;
	}
	
	public static Color[] setTextureCoords(int length){
		return null;
	}
	
	
	
	public static Color[] createWhiteColor(int length){
		Color[] c = new Color[length];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = vecmath.color(1, 0, 0);
		}
		
		return c;
	}
	
	
	
	
	
	
}
