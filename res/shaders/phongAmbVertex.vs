uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 baseColor;
varying vec3 fbaseColor;

attribute vec3 ambientLight;
varying vec3 fambientLight;

void main() {

	fambientLight = ambientLight;
	fbaseColor = baseColor;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}