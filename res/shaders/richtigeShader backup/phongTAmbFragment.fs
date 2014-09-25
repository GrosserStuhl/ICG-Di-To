
uniform sampler2D img;
uniform vec3 ambient;
varying vec2 ftextureCoord;
varying vec3 fnormal;
varying vec3 lightVector;
varying vec4 worldPosition;

uniform float specIntensity;
uniform float specExponent;
uniform vec3 eyePosition;

//Ambiente Komponente des Lichts
// I(amb) = I(a) * k(amb);
//
// I(a) = Intensität des Umgebungslichts;
// k(amb) = Materialkonstante (hier ignoriert - vorerst);


// Diffuse Komponente des Lichts
// I(d) = I(in) * k(diff) * cosδ;
//
// δ =(Winkel zw. NormVektor derOberfläche und EinheitsVektor in Richtung des einfallenden Lichtstrahls);
// k(diff) = MaterialKonstante (hier ignoriert - vorerst);
// I(in) = Intensität (=Lichtstärke des einfallendenLichtstrahls);


// Spiegelnde Komponente des Lichts
// I(spec) = I(in) * k(spec) * cos^n δ;
//
// δ =(Winkel zw. idealer Reflexionsrichtung des ausfallenden Lichts u. der Blickrichtung des Betrachters);
// k(spec) = MaterialKonstante (hier ignoriert - vorerst);
// I(in) = Intensität (=Lichtstärke des einfallendenLichtstrahls);




void main() {

float diffuseFactor = max(dot(lightVector,fnormal), 0.0);

vec4 ambientLight = vec4(ambient,1);
vec4 textureColor = texture2D(img, ftextureCoord.st );


//gl_FragColor = colorLight * totalLight;

vec3 dirToEye = normalize(worldPosition.xyz);
vec3 reflectDirection = normalize(reflect(lightVector,fnormal));

// gibt den cos zwischen beiden aus
float specularFactor = max(dot(dirToEye,reflectDirection),0.0);
specularFactor = pow(specularFactor, specExponent);

float specularTerm = specIntensity * specularFactor;

gl_FragColor = textureColor * (diffuseFactor + specularFactor) + textureColor * ambientLight;


}


