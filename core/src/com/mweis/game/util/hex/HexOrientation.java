package com.mweis.game.util.hex;

/*
 * Implementation of:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
public enum HexOrientation {
	POINTY_TOP((float)Math.sqrt(3.0), (float)Math.sqrt(3.0) / 2.0f, 0.0f, 3.0f / 2.0f,
			(float)Math.sqrt(3.0) / 3.0f, -1.0f / 3.0f, 0.0f, 2.0f / 3.0f,
            0.5f),
	
	FLAT_TOP(3.0f / 2.0f, 0.0f, (float)Math.sqrt(3.0) / 2.0f, (float)Math.sqrt(3.0),
            2.0f / 3.0f, 0.0f, -1.0f / 3.0f, (float)Math.sqrt(3.0) / 3.0f,
            0.0f);
	
	public final float f0, f1, f2, f3,
					   b0, b1, b2, b3,
					   start_angle;
	
	HexOrientation(float f0, float f1, float f2, float f3, 
					float b0, float b1, float b2, float b3,
					float start_angle) {
		this.f0 = f0;
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		
		this.start_angle = start_angle;
	}
}
