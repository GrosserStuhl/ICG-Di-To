
varying vec3 fcolor;
varying vec3 fnormal;
varying vec3 fambientLight;
varying vec3 lightVector;



void main() {

float diffuseFactor = max(dot(normalize(lightVector), normalize(fnormal)), 0.0);

vec4 totalLight = vec4(fambientLight,1);
vec4 colorLight = vec4(fcolor,1 );

//gl_FragColor = colorLight * totalLight;
gl_FragColor = totalLight * colorLight * diffuseFactor * vec4(1.0,1.0,1.0,1.0);


}

