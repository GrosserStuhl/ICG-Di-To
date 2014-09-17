


// basic components of light (a DataType)
struct BaseLight
{
	vec3 color;
	// passing a constant intensity
	float intensity;
};

struct DirectionalLight
{
	BaseLight base;
	vec3 direction;
};


varying vec3 fbaseColor;
varying vec3 fambientLight;
varying vec3 fnormal;

uniform DirectionalLight directionalLight;



vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
	float diffuseFactor = dot(normal, -direction);
	
	// colorOutput 
	vec4 diffuseColor = vec4(0,0,0,0);
	
	// if its having any effect on the surface at all, then we gonna change the diffuse color
	if(diffuseFactor > 0)
	{
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
	}
	
	return diffuseColor;
	
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
	return calcLight(directionalLight.base, -directionalLight.direction,normal);
}

void main() {

vec4 totalLight = vec4(fambientLight,1);
vec4 colorLight = vec4(fbaseColor,1 );

vec3 normal = normalize(fnormal);
totalLight += calcDirectionalLight(directionalLight, normal);


gl_FragColor = colorLight * totalLight;



}

