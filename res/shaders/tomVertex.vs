

void main() {

	fbaseColor = baseColor;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1);
			
			
}




#version 400
	
	layout (location = 0) in vec3 VertexPosition;
	layout (location = 1) in vec3 VertexNormal;
	
	out vec3 LightIntensity;
	
	uniform vec4 LightPosition; // Light position in eye coords.
	uniform vec3 Kd; // Diffuse reflectivity
	uniform vec3 Ld; // Light source intensity
	
	uniform mat4 ModelViewMatrix;
	uniform mat3 NormalMatrix;
	uniform mat4 ProjectionMatrix;
	
	uniform mat4 MVP; // Projection * ModelView
	
	// eigenes zeug ab hier
	
	uniform mat4 modelMatrix;
	uniform mat4 viewMatrix;
	uniform mat4 projectionMatrix;

	attribute vec3 vertex;
	attribute vec3 baseColor;
	varying vec3 fbaseColor;
	
	attribute vec3 normal;
	varying vec3 fnormal;
	
	
	void main()
	{
	  // Convert normal and position to eye coords
	  vec3 tnorm = normalize( NormalMatrix * normal);
	  vec4 eyeCoords = ModelViewMatrix * vec4(vertex,1.0);
	  vec3 s = normalize(vec3(LightPosition - eyeCoords));
	
	  // The diffuse shading equation
	  LightIntensity = Ld * Kd * max( dot( s, tnorm ), 0.0 );
	
	  // Convert position to clip coordinates and pass along
	  gl_Position = MVP * vec4(vertex,1.0);
	}	