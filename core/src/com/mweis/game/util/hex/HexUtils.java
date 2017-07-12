package com.mweis.game.util.hex;

import com.badlogic.gdx.math.Vector2;

public class HexUtils {
	
	public static Vector2 HEX_CORNER(Vector2 center, float size, int i, boolean isPointyTopped) {
		float angle_deg = 60 * i;
		if (isPointyTopped) {
			angle_deg += 30;
		}
		float angle_rad = (float) Math.toRadians(angle_deg);
		return new Vector2((float) (center.x + size * Math.cos(angle_rad)), (float)
				(center.y + size * Math.sin(angle_rad)));
	}
	
	
	
	private HexUtils() { };
}
