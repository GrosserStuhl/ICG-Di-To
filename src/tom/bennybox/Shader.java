package tom.bennybox;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

public class Shader {

	private int program;
	
	public Shader(){
		program = glCreateProgram();
		
		if(program == 0){
			System.err.println("Shader creation failed: could not find valid memory location");
		}
		
	}
	
	public void addVertexShader(String text){
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void bind(){
		glUseProgram(program);
	}
	
	public void addGeometryShader(String text){
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String text){
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	@SuppressWarnings("deprecation")
	public void compileShader(){
		glLinkProgram(program);
		
		if(glGetProgram(program, GL_LINK_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgram(program, GL_VALIDATE_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addProgram(String text, int type){
	     int shader = glCreateShader(type);
	     
	     if(shader == 0){
	    	 System.err.println("Shader creation failed: Could not find valid memory location.");
	    	 System.exit(1);
	     }
	     
	     glShaderSource(shader, text);
	     glCompileShader(shader);
	     
	     if(glGetShader(shader, GL_COMPILE_STATUS) == 0){
	    	 System.err.println(glGetShaderInfoLog(shader, 1024));
	    	 System.exit(1);
	     }
	     
	     glAttachShader(program, shader);
	}
	
}
