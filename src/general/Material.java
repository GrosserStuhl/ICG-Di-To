package general;

public class Material {

	private float Ns;
		//	Specifies the specular exponent for the current material.  This defines 
		//	the focus of the specular highlight.
		//	 
		//	 "exponent" is the value for the specular exponent.  A high exponent 
		//	results in a tight, concentrated highlight.  Ns values normally range 
		//	from 0 to 1000.  
	    // According to http://paulbourke.net/dataformats/mtl/
	
	private float Ka;
	// material ambient
	
	private float Kd;
	// material diffuse
	
	private float Ks;
	// material specular
	
	public Material(float Ns, float Ka, float Kd, float Ks) {
		this.Ns = Ns;
		this.Ka = Ka;
		this.Kd = Kd;
		this.Ks = Ks;
		
	}

	public float getNs() {
		return Ns;
	}

	public void setNs(float ns) {
		Ns = ns;
	}

	public float getKa() {
		return Ka;
	}

	public void setKa(float ka) {
		Ka = ka;
	}

	public float getKd() {
		return Kd;
	}

	public void setKd(float kd) {
		Kd = kd;
	}

	public float getKs() {
		return Ks;
	}

	public void setKs(float ks) {
		Ks = ks;
	}
	
	
	
	
	
}
