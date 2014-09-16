uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec2 textureCoord;
varying vec2 ftextureCoord;

attribute vec3 ambientLight;
varying vec3 fambientLight;

void main() {

	fambientLight = ambientLight;
	ftextureCoord = textureCoord;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}