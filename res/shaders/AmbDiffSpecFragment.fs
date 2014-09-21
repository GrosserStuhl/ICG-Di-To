
varying vec3 fcolor;
varying vec3 fnormal;
varying vec3 fambientLight;
varying vec3 lightVector;
varying vec3 worldPosition;

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
// I(spec) = I(in) * k(spec) * cos^n  δ;
//
// δ =(Winkel zw. idealer Reflexionsrichtung des ausfallenden Lichts u. der Blickrichtung des Betrachters);
// k(spec) = MaterialKonstante (hier ignoriert - vorerst);
// I(in) = Intensität (=Lichtstärke des einfallendenLichtstrahls);




void main() {

float diffuseFactor = max(dot(normalize(lightVector), normalize(fnormal)), 0.0);

vec4 ambientLight = vec4(fambientLight,1);
vec4 color = vec4(fcolor,1 );
vec4 specularColor = vec4(0,0,0,0);


//gl_FragColor = colorLight * totalLight;

vec3 dirToEye = normalize(eyePosition - worldPosition);
vec3 reflectDirection = normalize(reflect(vec3(1.0,1.0,1.0),fnormal));

// gibt den cos zwischen beiden aus
float specularFactor = dot(dirToEye,reflectDirection);
specularFactor = pow(specularFactor, specExponent);

float specularTerm = specIntensity * specularFactor;

gl_FragColor = color * (ambientLight + diffuseFactor + specularTerm) * vec4(1.0,1.0,1.0,1.0);


}

