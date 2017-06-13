package com.mweis.game.entity;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mweis.game.box2d.Box2dLocation;
import com.mweis.game.box2d.Box2dSteeringUtils;

public abstract class Agent<A extends Agent<A, S>, S extends State<A>> implements Telegraph, Location<Vector2> {
	private Body body; // collisions occur between fixtures, not bodies. Therefore a "hitbox" and "collisionbox" can both exist still.
	private StateMachine<A, S> stateMachine;
	
	public Body getBody() {
		return this.body;
	}
	
	protected void setBody(Body body) {
		this.body = body;
	}
	
	public StateMachine<A, S> getStateMachine() {
		return this.stateMachine;
	}
	
	protected void setStateMachine(StateMachine<A, S> stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	@Override
	public Vector2 getPosition () {
		return body.getPosition();
	}

	@Override
	public float getOrientation () {
		return body.getAngle();
	}

	@Override
	public void setOrientation (float orientation) {
		body.setTransform(getPosition(), orientation);
	}

	@Override
	public Location<Vector2> newLocation () {
		return new Box2dLocation();
	}

	@Override
	public float vectorToAngle (Vector2 vector) {
		return Box2dSteeringUtils.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector (Vector2 outVector, float angle) {
		return Box2dSteeringUtils.angleToVector(outVector, angle);
	}
	
	@Override
	public boolean handleMessage(Telegram msg) {
		return stateMachine.handleMessage(msg);
	}
}
