package com.mweis.game.entity.components;

import java.util.function.BiConsumer;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.sun.istack.internal.Nullable;

public class ContactComponent /* implements Component */ {
	
	private BiConsumer<Fixture, Fixture> beginContact, endContact;
	
	/*
	 * Either can be null if only one behavior is desired.
	 */
	public ContactComponent(@Nullable BiConsumer<Fixture, Fixture> beginContact, @Nullable BiConsumer<Fixture, Fixture> endContact) {
		this.beginContact = beginContact;
		this.endContact = endContact;
	}
	
	// maybe have setContact w/ protected visibility?
	
	public void beginContact(Fixture self, Fixture other) {
		if (this.beginContact != null) {
			this.beginContact.accept(self, other);
		}
	}
	
	public void endContact(Fixture self, Fixture other) {
		if (this.endContact != null) {
			this.endContact.accept(self, other);
		}
	}
}
