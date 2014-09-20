uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

attribute vec3 vertex;
//attribute vec3 baseColor;
//varying vec3 fbaseColor;

attribute vec2 textureCoord;
varying vec2 ftextureCoord;

void main() {

	//fbaseColor = baseColor;
	ftextureCoord = textureCoord;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}
			
			