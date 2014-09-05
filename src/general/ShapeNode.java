package general;
import static ogl.vecmathimp.FactoryDefault.vecmath;

import java.nio.FloatBuffer;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;

import org.lwjgl.BufferUtils;


public class ShapeNode extends Node{

	private FloatBuffer positionData;
	private FloatBuffer colorData;
	
	public ShapeNode(Vertex[] vertices) {
		
		this.positionData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.vectorSize());
		this.colorData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.colorSize());
		
	}
	
	
	
	
}
