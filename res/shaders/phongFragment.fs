varying vec3 fbaseColor;
uniform vec3 ambientLight;

void main() {

vec4 totalLight = vec4(ambientLight,1);
vec4 color = vec4(baseColor,1 );

gl_FragColor = color*totalLight;



}

