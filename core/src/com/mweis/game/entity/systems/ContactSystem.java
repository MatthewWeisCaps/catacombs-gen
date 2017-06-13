package com.mweis.game.entity.systems;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/*
 * Callback methods for box2d Contacts, also a system which will eventually need to poll data
 * from contact data structures. Combining a system and callbacks puts all potential references
 * to these structures in one scope.
 */
public class ContactSystem implements ContactListener {
	
	/*
	 * 
	 */
	@Override
	public void beginContact(Contact contact) {
		
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
}
