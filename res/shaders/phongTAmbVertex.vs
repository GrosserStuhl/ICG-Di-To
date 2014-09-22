
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
attribute vec2 textureCoord;
varying vec2 ftextureCoord;


uniform vec3 lightPosition;

attribute vec3 normal;
varying vec3 fnormal;

uniform mat4 normalMatrix;

varying vec3 lightVector;
varying vec4 worldPosition;



void main() {

	worldPosition = viewMatrix * modelMatrix * vec4(vertex,1.0);
	ftextureCoord = textureCoord;

	fnormal =  normalize ((normalMatrix * vec4(normal,0.0)).xyz);
	lightVector =  normalize(  (viewMatrix*vec4(lightPosition, 1.0)).xyz - worldPosition.xyz);
	
	gl_Position = projectionMatrix * worldPosition;
}