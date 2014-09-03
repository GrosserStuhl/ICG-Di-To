#version 330

layout (location = 0) in vec3 position;

// cbC
out vec4 colorQ;

void main()
{
	// clamp everything within that range (cbC) values between 0 and 1
	colorQ = vec4( clamp(position, 0.0, 1.0), 1.0);
	//cbC
	gl_Position = vec4(0.25 * position, 1.0);
	
	//cbU
	// gl_Position = vec4(0.25 * position, 1.0);
}