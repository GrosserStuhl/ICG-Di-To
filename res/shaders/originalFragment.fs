
uniform sampler2D texture0;

varying vec3 fcolor;
varying vec2 ftex;

void main() {

//gl_FragColor = vec4(fcolor, 1.0);
gl_FragColor = texture2D(texture0, ftex.st );

}

