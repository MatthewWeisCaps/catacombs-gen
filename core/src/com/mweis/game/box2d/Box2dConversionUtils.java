package com.mweis.game.box2d;

import com.mweis.game.util.Constants;

public final class Box2dConversionUtils {
	
	/*
	 * Not dealing with art until after the core logic is set up.
	 */
	public static float pixelsToMeters (int pixels) {
//		return (float)pixels * Constants.MPP;
		return pixels;
	}

	public static int metersToPixels (float meters) {
//		return (int)(meters * Constants.PPM);
		return (int)meters;
	}
	
	private Box2dConversionUtils() { };
}
