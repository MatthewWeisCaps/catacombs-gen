package com.mweis.game.entity.agents.zombie;

import java.util.function.BiConsumer;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mweis.game.box2d.Box2dFilterBuilder;
import com.mweis.game.box2d.Box2dLightFactory;
import com.mweis.game.box2d.DefaultSteering;
import com.mweis.game.entity.Agent;
import com.mweis.game.entity.components.ContactComponent;
import com.mweis.game.util.FilterCategory;
import box2dLight.ConeLight;
import box2dLight.RayHandler;

public class ZombieAgent extends Agent<ZombieAgent, ZombieState> {
	
	ConeLight vision;
	DefaultSteering steering;
	Seek<Vector2> seek;
	
	public ZombieAgent(Body body, RayHandler rayHandler) {
		super(body);
//		super.setBody(body);
		super.setStateMachine(new DefaultStateMachine<ZombieAgent, ZombieState>(this));
		super.getStateMachine().changeState(ZombieState.IDLE);
		
		// setup FoV fixture
		float distance = 20.0f;
		float coneAngle = 60.0f;
		PolygonShape polygon = new PolygonShape();
		Vector2[] verts = new Vector2[4];
		verts[0] = Vector2.Zero;
		
		// right is zero
//		verts[1] = new Vector2(distance, (float) Math.tan(coneAngle*2.0) * -distance);
//		verts[2] = new Vector2(distance, (float) Math.tan(coneAngle*2.0) * distance);
		
		// up is zero
		verts[1] = new Vector2((float) Math.tan(coneAngle*2.0) * -distance, distance);
		verts[2] = new Vector2((float) Math.tan(coneAngle*2.0) * distance, distance);
		
		verts[3] = Vector2.Zero;
		polygon.set(verts);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		fixtureDef.isSensor = true;
		
		Box2dFilterBuilder builder = new Box2dFilterBuilder(FilterCategory.SENSOR);
		builder.disableAllMaskCategories();
		builder.enableMaskBit(FilterCategory.FRIENDLY_MOB); // allow FoV to find Player and his allies.
		builder.copyToFixture(fixtureDef); // make fixture's filter a copy of the builder's
		
		Fixture fovFixture = this.getBody().createFixture(fixtureDef);
		
		BiConsumer<Fixture, Fixture> onBeginContact = new BiConsumer<Fixture, Fixture>() {
			@Override
			public void accept(Fixture self, Fixture other) {
				System.out.println("self: " + self.getFilterData().categoryBits);
				System.out.println("other: " + other.getFilterData().categoryBits);
				MessageManager.getInstance().dispatchMessage(
						null,
						(Agent)self.getBody().getUserData(), // send to this (zombie) Agent
						0, // 0 (temp) = Enemy Found
						(Agent)other.getBody().getUserData()); // extra info is the new Agent (i.e. Location2D) to follow
			}
		};
		
		fovFixture.setUserData(new ContactComponent(onBeginContact, null));
		
		this.vision = Box2dLightFactory.createConeLight(rayHandler, 500, distance, +90.0f, coneAngle, body);
		
		float boundingRadius = 0.01f; // side of box by default in factory. all these need to be moved to xml file
		this.steering = new DefaultSteering(super.getBody(), false, boundingRadius);
		float speedFactor = 5.0f;
		
		steering.setMaxLinearAcceleration(10*speedFactor);
		steering.setMaxLinearSpeed(3*speedFactor);
		steering.setMaxAngularAcceleration(2000*speedFactor);
		steering.setMaxAngularSpeed(60000*speedFactor);
		
		this.seek = new Seek<Vector2>(steering);
		for (Fixture f : body.getFixtureList()) {
			System.out.println(f.toString());
		}
	}	
}
