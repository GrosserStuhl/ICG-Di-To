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
	
	
	public static Mesh loadMesh(String fileName){
		
		Mesh m = null;
		
		Material mat = null;
		
		BufferedReader meshReader = null;
		
		ArrayList<Vector> vData = new ArrayList<Vector>();
		ArrayList<Integer> fData = new ArrayList<Integer>();
		ArrayList<Vector> nData = new ArrayList<Vector>();
		ArrayList<Vector2f> texCoord = new ArrayList<Vector2f>();
		
		ArrayList<Integer> texFaceData = new ArrayList<Integer>();
		
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

					
					
					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[0]) -1);
//					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[1]) -1);
					
					
				} else if(line.startsWith("f ") && line.split("\\s+")[1].matches("\\d+\\/\\d+\\/\\d+") ){
					// holt die FaceDaten aus dem .obj-File mit zusätzlichen Textur und Vektornormaleninfos. Das Format z.B.  
					// f  1/3/2  1/7/2  9/5/2
					// f  1/2/5  7/2/6  5/2/3 ...
					
					//    Bsp. 7/2/6 : hier wäre die erste Zahl(7) die vertex koordinate, die zweite Zahl (2) die Texturkoordinate, die dritte Zahl (6) die Vektornormale

					fData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[0]) -1);
					texFaceData.add(Integer.parseInt(line.split("\\s+")[1].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[0]) -1);
					texFaceData.add(Integer.parseInt(line.split("\\s+")[2].split("\\/")[1]) -1);
					
					fData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[0]) -1);
					texFaceData.add(Integer.parseInt(line.split("\\s+")[3].split("\\/")[1]) -1);
				}
		
			}
			meshReader.close();
			
			Vector[] positionData = createMeshVertexData(vData);
			Color[] col = null;
			
			//create arrays
			int[] faces = createMeshFaceData(fData);
			
			Vertex[] vertices = null;
			
			// if theres texture data available read it and hand it over, if not create white color
			if(!texCoord.isEmpty()){
				int[] texFaces = createTextureFaces(texFaceData);
				Vector2f[] texCoordArray = createTextureVertexData(texCoord);
				
				vertices = Vertex.meshVertices(positionData,texCoordArray, faces, texFaces);
				return new Mesh(positionData,texCoordArray,vertices);
			}else{
				col = createWhiteColor(vData.size());
				vertices = Vertex.meshVertices(positionData,col, faces);
				return m = new Mesh(positionData,col,vertices);
			}
			
			
			
			
			
			
		} catch(Exception e){
			
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
		
	}
	
	
	public static Vector2f[] createTextureVertexData(ArrayList<Vector2f> data) {
		Vector2f[] result = new Vector2f[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i);
		}
		
		
		return result;
	}
	
	private static int[] createTextureFaces(ArrayList<Integer> texFaceData) {
		int[] result = new int[texFaceData.size()];
		
		for (int i = 0; i < texFaceData.size(); i++) {
			result[i] = texFaceData.get(i).intValue();
		}
		
		System.out.println("CreateTextureFaces "+result.length + "input: "+texFaceData.size());
		
		return result;
	}


	private static Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}
	
	
	
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
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
	
	public static int[] createMeshFaceData(ArrayList<Integer> data) {
		int[] result = new int[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			result[i] = data.get(i).intValue();
		}
		
//		System.out.println("facelength: "+result.length);
		
		return result;
	}
	
	public static Color[] setTextureCoords(int length){
		return null;
	}
	
	
	
	public static Color[] createWhiteColor(int length){
		Color[] c = new Color[length];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = vecmath.color(220, 220, 220);
		}
		
		return c;
	}
	
	
	
	
	
}
