
uniform sampler2D img;
uniform vec3 ambient;
varying vec2 ftextureCoord;
varying vec3 fnormal;
varying vec3 lightVector;
varying vec4 worldPosition;

uniform float specIntensity;
uniform float specExponent;

uniform mat4 pLPosLight;

// describes how quickly the light fades out
//  formular: 
// att = aC + aL * d + aQ * d2


struct PointLight
{
	vec3 vcolor;
	vec3 vposition;
	
	float fambient;
	
	float fconstantAtt;
    float flinearAtt;
    float fexpAtt; 

};

uniform PointLight pointLight;



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


//vec4 getPointLightColor(PointLight ptLight, vec3 vWorldPos, vec3 vNormal)
//{
//   vec3 pLVector =  (pLPosLight*vec4(ptLight.vposition,1.0)).xyz;
	
 //  vec3 vPosToLight = pLVector - vWorldPos.xyz;
  // float fDist = length(vPosToLight);
   //vPosToLight = normalize(vPosToLight);
   
   
   
   //float fDiffuse = max(dot(pLVector, normalize(reflect(lightVector,fnormal))),0.0);

   //float fAttTotal = ptLight.fconstantAtt + ptLight.flinearAtt*fDist + ptLight.fexpAtt*fDist*fDist;

   //return vec4(ptLight.vcolor, 1.0)*(ptLight.fambient+fDiffuse)/fAttTotal;
//}

vec4 getPointLightColor(PointLight ptLight, vec4 vWorldPos, vec3 vNormal)
{
   vec3 pLPositionToView =  (pLPosLight*vec4(ptLight.vposition,1.0)).xyz;
	
   vec3 vPosToLight = pLPositionToView - vWorldPos.xyz;
   float fDist = length(vPosToLight);
   vPosToLight = normalize(vPosToLight);
   
   
   float fDiffuse = max(dot(vPosToLight,vNormal), 0.0);
   
   vec3 dirToEye = normalize(vWorldPos.xyz);
   vec3 reflectDirection = normalize(reflect(vPosToLight,vNormal));
   float specularFactor = max(dot(dirToEye,reflectDirection),0.0);
   specularFactor = pow(specularFactor, specExponent);
   float specularTerm = specIntensity * specularFactor;
   
   
   float fAttTotal = ptLight.fconstantAtt + ptLight.flinearAtt*fDist + ptLight.fexpAtt*fDist*fDist;

   return vec4(ptLight.vcolor, 1.0)*(ptLight.fambient+fDiffuse)/fAttTotal;
}



void main() {

float diffuseFactor = max(dot(lightVector,fnormal), 0.0);

vec4 ambientLight = vec4(ambient,1);
vec4 textureColor = texture2D(img, ftextureCoord.st );

vec4 pointLightColor =  getPointLightColor(pointLight, worldPosition, fnormal);

vec3 dirToEye = normalize(worldPosition.xyz);
vec3 reflectDirection = normalize(reflect(lightVector,fnormal));

// gibt den cos zwischen beiden aus
float specularFactor = max(dot(dirToEye,reflectDirection),0.0);
specularFactor = pow(specularFactor, specExponent);

float specularTerm = specIntensity * specularFactor;

//gl_FragColor = textureColor * (diffuseFactor + specularFactor) + textureColor * ambientLight;


gl_FragColor = textureColor * (diffuseFactor + specularTerm) + textureColor * ambientLight;
	+ pointLightColor;


}


