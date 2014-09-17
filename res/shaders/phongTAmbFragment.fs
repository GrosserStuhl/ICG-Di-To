
uniform sampler2D img;


varying vec2 ftextureCoord;
varying vec3 fambientLight;

void main() {

vec4 totalLight = vec4(fambientLight,1);

// diese scheiße hier brauche ich nicht
//vec4 colorLight = vec4(ftextureCoord.st, 1);

vec4 textureColor = texture2D(img, ftextureCoord.st );

// diese scheiße hier brauche ich nicht
//colorLight *= textureColor;


gl_FragColor = textureColor * totalLight;



}

