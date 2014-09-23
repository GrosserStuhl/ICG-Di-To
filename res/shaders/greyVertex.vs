uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 color;
varying vec3 fcolor;

attribute vec2 textureCoord;
varying vec2 ftextureCoord;


void main() {

	fcolor = color;
	ftextureCoord = textureCoord;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}