package mathe;

public class Color {

	private float r,g,b;
	
	public Color(Vector3f c) {
		
		r = c.getX();
		g = c.getY();
		b = c.getZ();
		
	}
	
	public Color(float r, float g, float b) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		
	}


	
	public float length(){
		return (float)Math.sqrt(r * r + g * g + b * b);
	}
	
	public float dot(Color c){
		return r * c.getR() + g * c.getG() + b * c.getB();
	}
	
	public Color cross(Color c){
		float r_ = g * c.getB() - b * c.getG();
		float g_ = b * c.getR() - r * c.getB();
		float b_ = r * c.getG() - g * c.getR();
		
		return new Color(r_, g_, b_);
	}
	
	public Color normalize(){
		float length = length();
		
		//this will set the overall vectorlength to 1
		
		r /= length;
		g /= length;
		b /= length;
		
		return this;
		
	}
	

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
	
	
}
