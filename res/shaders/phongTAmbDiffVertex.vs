uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec2 textureCoord;
varying vec2 ftextureCoord;

attribute vec3 ambientLight;
varying vec3 fambientLight;

attribute vec3 normal;
varying vec3 fnormal;

uniform mat4 transformMatrix;

void main() {

	fambientLight = ambientLight;
	ftextureCoord = textureCoord;
	
	fnormal = (projectionMatrix * transformMatrix *  vec4(normal,0.0)).xyz;
	
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}