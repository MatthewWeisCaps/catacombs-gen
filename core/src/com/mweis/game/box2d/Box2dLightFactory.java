package com.mweis.game.box2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.mweis.game.util.FilterCategory;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/*
 * For dynamic lights
 */
public class Box2dLightFactory {
	
	/*
	 * @param Body body - can be null!
	 */
	public static ConeLight createConeLight(RayHandler rayHandler, int rays, float distance, float angle, Body body) {
		ConeLight light = new ConeLight(rayHandler, rays, Color.WHITE, distance, 0.0f, 0.0f, 0.0f, angle);
		
		Box2dFilterBuilder builder = new Box2dFilterBuilder(FilterCategory.LIGHT);
		builder.enableAllMaskCategories(); // collide with everything...
		builder.disableMaskBit(FilterCategory.SENSOR); // ... except sensors
//		builder.disableMaskBit(FilterCategory.BOUNDARY);
		
		
		light.setContactFilter(builder.build());
		
		if (body != null) {
			light.attachToBody(body);
		}
		return light;
	}
	
	/*
	 * @param Body body - can be null!
	 */
	public static PointLight createPointLight(RayHandler rayHandler, int rays, float distance, Body body) {
		PointLight light = new PointLight(rayHandler, rays); // attached to player
		light.setSoftnessLength(5.0f);
		light.setDistance(distance);
		
		Box2dFilterBuilder builder = new Box2dFilterBuilder(FilterCategory.LIGHT);
		builder.enableAllMaskCategories(); // collide with everything...
		builder.disableMaskBit(FilterCategory.SENSOR); // ... except sensors
//		builder.disableMaskBit(FilterCategory.BOUNDARY);
		
		light.setContactFilter(builder.build());
		
		if (body != null) {
			light.attachToBody(body);
		}
		return light;
	}
	
	private Box2dLightFactory() { }
}
