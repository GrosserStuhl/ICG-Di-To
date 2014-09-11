uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
//attribute vec3 color; //in
//varying vec3 fcolor; //out


//varying vec2  texcoord;// because of two dimensional textureimage

void main() {

	//fcolor = color;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
	gl_TexCoord[1] = gl_MultiTexCoord1; // sollte auch funktionieren: vec2(glMultiTexCoord0);		
		
	
}