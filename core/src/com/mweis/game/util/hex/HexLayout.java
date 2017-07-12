package com.mweis.game.util.hex;

import com.badlogic.gdx.math.Vector2;

/*
 * Implementation of:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
public class HexLayout {
	public HexOrientation orientation;
	public Vector2 size;
	public Vector2 origin;
	
	public HexLayout(HexOrientation orientation, Vector2 size, Vector2 origin) {
		this.orientation = orientation;
		this.size = size;
		this.origin = origin;
	}
}
