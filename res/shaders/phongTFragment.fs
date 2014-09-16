
uniform sampler2D img;

varying vec2 ftextureCoord;
varying vec3 fambientLight;

void main() {

vec4 totalLight = vec4(fambientLight,1);
vec4 colorLight = vec4(ftextureCoord.st,1,0 );

vec4 textureColor = texture2D(img, ftextureCoord.st );

colorLight *= textureColor;


gl_FragColor = colorLight * totalLight;



}

