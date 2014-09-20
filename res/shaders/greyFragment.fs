varying vec3 fcolor;

uniform sampler2D img;
varying vec2 ftextureCoord;

varying float fboolFloat;

void main() {


vec4 textureColor = texture2D(img, ftextureCoord.st);
vec4 color = vec4(fcolor, 1.0);
		
		if(textureColor != vec4(0,0,0,0))
			color = textureColor;
		

	gl_FragColor = color;

}

