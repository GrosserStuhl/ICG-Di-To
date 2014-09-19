uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 baseColor;
varying vec3 fbaseColor;

attribute vec3 ambientLight;
varying vec3 fambientLight;

attribute vec3 normal;
varying vec3 fnormal;

uniform mat4 transformMatrix;

void main() {

	
	fambientLight = ambientLight;
	fbaseColor = baseColor;
	//fnormal = (projectionMatrix * transformMatrix *  vec4(normal,1.0)).xyz;
	fnormal = (projectionMatrix * transformMatrix *  vec4(normal,1.0)).xyz;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
}