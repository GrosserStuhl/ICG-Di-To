uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 color;
varying vec3 fcolor;

void main() {

	fcolor = color;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}
			
			