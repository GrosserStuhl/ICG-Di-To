uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 color;
varying vec3 fcolor;

attribute vec3 ambientLight;
varying vec3 fambientLight;

uniform vec3 lightPosition;

void main() {

	fambientLight = ambientLight;
	fcolor = color;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}