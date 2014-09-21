uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec3 color;
varying vec3 fcolor;

attribute vec3 ambientLight;
varying vec3 fambientLight;

uniform vec3 lightPosition;

attribute vec3 normal;
varying vec3 fnormal;

uniform mat4 transformMatrix;


varying vec3 lightVector;

void main() {

	fambientLight = ambientLight;
	fcolor = color;
	fnormal = (vec4(normal,0) * transformMatrix * projectionMatrix).xyz;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
	lightVector = (transformMatrix * vec4(lightPosition, 1.0)).xyz - (transformMatrix * vec4(vertex.xyz, 1.0)).xyz;		

}