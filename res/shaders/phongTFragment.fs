
uniform sampler2D img;

uniform vec3 baseColor;
varying vec2 ftextureCoord;
varying vec3 fambientLight;

void main() {

vec4 totalLight = vec4(fambientLight,1);

vec4 colorLight = vec4(baseColor, 1);

vec4 textureColor = texture2D(img, ftextureCoord.st );

colorLight = textureColor * colorLight ;


gl_FragColor = colorLight * totalLight;



}

