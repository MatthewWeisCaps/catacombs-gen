package com.mweis.game.box2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/*
 * For dynamic lights
 */
public class Box2dLightFactory {
	
	public static ConeLight createConeLight(RayHandler rayHandler, int rays, float distance, float angle, Body body) {
		ConeLight light = new ConeLight(rayHandler, rays, Color.WHITE, distance, 0.0f, 0.0f, 0.0f, angle);
		light.attachToBody(body);
		return light;
	}
	
	private Box2dLightFactory() { }
}
