package com.mweis.game.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dBodyFactory {
	
	public static Body createDynamicCircle(Vector2 position, Box2dFilterBuilder filter, World world) {
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		
		Body body = world.createBody(bodyDef);
		body.setLinearDamping(0.10f);
		body.setAngularDamping(0.50f);
		body.setFixedRotation(true);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(1.0f);
		
//		PolygonShape polygon = new PolygonShape();
//		polygon.setAsBox(1.0f, 1.0f); // this is half-width and half-height, thus a 2x2 meter box

		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.15f;
		fixtureDef.restitution = 0.0f;
		
		filter.copyToFixture(fixtureDef);
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		circle.dispose();
//		polygon.dispose();
		
		return body;
	}
	
	
	
	public static Body createStaticSquare(Vector2 position, float size, Box2dFilterBuilder filter, World world) {
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.StaticBody;
		
		bodyDef.position.set(position);


		Body body = world.createBody(bodyDef);
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(size / 2, size / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		
		filter.copyToFixture(fixtureDef);
		
		Fixture fixture = body.createFixture(fixtureDef);

		polygon.dispose();
		
		return body;
	}
	
	public static Body createStaticRectangle(float x, float y, float width, float height, Box2dFilterBuilder filter, World world) {
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.StaticBody;
		
		bodyDef.position.set(x + width/2, y + height/2);


		Body body = world.createBody(bodyDef);
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(width / 2.0f, height / 2.0f);

		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		
		filter.copyToFixture(fixtureDef);
		
		Fixture fixture = body.createFixture(fixtureDef);

		polygon.dispose();
		
		return body;
	}
	
	public static Body createEdge(Vector2 v1, Vector2 v2, Box2dFilterBuilder filter, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		Body body = world.createBody(bodyDef);
		
		EdgeShape edge = new EdgeShape();
		edge.set(v1, v2);


		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		
		filter.copyToFixture(fixtureDef);
		
		Fixture fixture = body.createFixture(fixtureDef);

		edge.dispose();
		
		return body;
	}
	
	private Box2dBodyFactory() { };
}