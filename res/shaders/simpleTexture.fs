varying vec3 fcolor;

uniform sampler2D img  ;// to be able to get the image for 2D images
//varying vec2 texcoord; // in

void main() {

//vec4 texcolor = texture2D(img, texcoord);
//gl_FragColor = vec4(fcolor, 1.0);

gl_FragColor = texture2D(img, gl_TexCoord[0].xy);

}

