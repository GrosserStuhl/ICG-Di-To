varying vec3 fcolor;
varying vec3 fambientLight;

void main() {

vec4 totalLight = vec4(fambientLight,1);
vec4 colorLight = vec4(fcolor,1 );

gl_FragColor = colorLight * totalLight;



}

