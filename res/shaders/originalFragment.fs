
uniform sampler2D text;

varying vec3 fcolor;
varying vec2 ftex;

void main() {

//gl_FragColor = vec4(fcolor, 1.0);
gl_FragColor = texture2D(text, ftex.st );

}

