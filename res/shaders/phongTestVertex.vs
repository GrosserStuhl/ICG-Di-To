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

uniform mat4 inv_4x4;

struct lightSource
{
  vec4 position;
  vec4 diffuse;
};
lightSource light0 = lightSource(
    vec4(-1.0, 1.0, -1.0, 0.0),
    vec4(1.0, 1.0, 1.0, 1.0)
);
 
struct material
{
  vec4 diffuse;
};
material mymaterial = material(vec4(1.0, 0.8, 0.8, 1.0));



void main() {

	mat4 mvp = projectionMatrix * viewMatrix * modelMatrix;
	vec4 normalDirection = normalize(gl_NormalMatrix * vec4(normal,0.0));
    vec4 lightDirection = normalize(vec4(light0.position,1.0));
	
	vec3 diffuseReflection
    = vec3(light0.diffuse) * vec3(mymaterial.diffuse)
    * max(0.0, dot(normalDirection, lightDirection));
 
    fbaseColor = vec4(diffuseReflection, 1.0).xyz;
	
	
    gl_Position = mvp * vec4(vertex,1.0);
  
  
//	fambientLight = ambientLight;
//	fbaseColor = baseColor;
//	fnormal = (modelMatrix * projectionMatrix*  vec4(normal,0.0)).xyz;
//	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}