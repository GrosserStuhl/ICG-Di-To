varying vec3 fbaseColor;
varying vec3 fambientLight;

void main() {

vec4 totalLight = vec4(fambientLight,1);
vec4 colorLight = vec4(fbaseColor,1 );

gl_FragColor = colorLight * totalLight;



}

