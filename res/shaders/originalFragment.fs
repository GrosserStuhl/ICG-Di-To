
uniform sampler2D img;

varying vec3 fcolor;
varying vec2 ftextureCoord;

void main() {

//gl_FragColor = vec4(fcolor, 1.0);
gl_FragColor = texture2D(img, ftextureCoord.st );

}

