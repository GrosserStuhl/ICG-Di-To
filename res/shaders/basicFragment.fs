#version 330

//cbC
in vec4 colorQ;

out vec4 fragColor;

void main()
{
	//cbC
	fragColor = colorQ;
	//cbU
	//fragColor = vec4(0.0, 1.0, 1.0, 1.0);
}