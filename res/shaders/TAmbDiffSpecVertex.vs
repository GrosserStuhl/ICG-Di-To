uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec2 textureCoord;
varying vec2 ftextureCoord;

attribute vec3 ambientLight;
varying vec3 fambientLight;

uniform vec3 lightPosition;

attribute vec3 normal;
varying vec3 fnormal;

uniform mat4 transformMatrix;


varying vec3 lightVector;
varying vec3 worldPosition;

void main() {

	fambientLight = ambientLight;
	ftextureCoord = textureCoord;
	fnormal = (vec4(normal,0) * transformMatrix).xyz;
	
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
	lightVector = (transformMatrix * vec4(lightPosition, 1.0)).xyz - (transformMatrix * vec4(vertex.xyz, 1.0)).xyz;		
	
	worldPosition = (vec4(vertex,1.0) * transformMatrix).xyz;
}