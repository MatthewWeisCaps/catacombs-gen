package com.mweis.game.entity.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mweis.game.entity.components.ContactComponent;

/*
 * THE WORLD MUST SET ITS CONTACT LISTENER TO THIS!
 * 
 * Callback methods for box2d Contacts, this will send a notification 
 */
public class BodyContactListener implements ContactListener {
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
				
		if (fixtureA.getUserData() != null) {
			((ContactComponent) contact.getFixtureA().getUserData()).beginContact(fixtureA, fixtureB);
		}
		
		if (fixtureB.getUserData() != null) {
			((ContactComponent) contact.getFixtureB().getUserData()).beginContact(fixtureB, fixtureA);
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
				
		if (fixtureA.getUserData() != null) {
			((ContactComponent) contact.getFixtureA().getUserData()).endContact(fixtureA, fixtureB);
		}
		
		if (fixtureB.getUserData() != null) {
			((ContactComponent) contact.getFixtureB().getUserData()).endContact(fixtureB, fixtureA);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
}
