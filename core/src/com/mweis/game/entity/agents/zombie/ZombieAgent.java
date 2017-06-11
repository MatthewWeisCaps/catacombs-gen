package com.mweis.game.entity.agents.zombie;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mweis.game.box2d.Box2dLightFactory;
import com.mweis.game.box2d.DefaultSteering;
import com.mweis.game.entity.Agent;
import com.mweis.game.entity.agents.player.PlayerAgent;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

public class ZombieAgent extends Agent<ZombieAgent, ZombieState> {
	
	PlayerAgent player;
	ConeLight vision;
	DefaultSteering steering;
	Seek<Vector2> seek;
	
	public ZombieAgent(PlayerAgent player, Body body, float boundingRadius, RayHandler rayHandler) {
		super.setBody(body);
		super.setStateMachine(new DefaultStateMachine<ZombieAgent, ZombieState>(this));
		super.getStateMachine().changeState(ZombieState.IDLE);
		this.player = player;
		float distance = 20.0f;
		float coneAngle = 60.0f;
		
		PolygonShape polygon = new PolygonShape();
		Vector2[] verts = new Vector2[4];
		verts[0] = Vector2.Zero;
		verts[1] = new Vector2(distance, (float)Math.tan(coneAngle*2.0) * -distance);
		verts[2] = new Vector2(distance, (float)Math.tan(coneAngle*2.0) * distance);
		verts[3] = Vector2.Zero;
		polygon.set(verts);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		fixtureDef.isSensor = true;
		
		this.getBody().createFixture(fixtureDef);
		
		/*
		 * TODO: figure out contact filtering so that Zombie fov doesn't intercept any lights..
		 * Need to make class holding constants for contact filtering assignments.
		 */
		
		this.vision = Box2dLightFactory.createConeLight(rayHandler, 500, distance, coneAngle, body);
		
		this.steering = new DefaultSteering(super.getBody(), false, boundingRadius);
		this.seek = new Seek<Vector2>(steering);
	}	
}
