package com.mweis.game.box2d;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mweis.game.util.FilterCategory;

/*
 * Filter building class, so that Filter bits don't need to be memorized.
 * 
 * categoryBits says "I am ..."
 * maskBits says "I collide with ..."
 * 
 * does not support group bits at this time.
 */
public class Box2dFilterBuilder {
	
	/*
	 * These are the default values and comments from Filter.class. DO NOT CHANGE THESE!
	 * -.. but might want to change category
	 */
	
	/** The collision category bits. Normally you would just set one bit. */
	private final short categoryBitsDEFAULT = 0x0001;

	/** The collision mask bits. This states the categories that this shape would accept for collision. */
	private final short maskBitsDEFAULT = -1;

	/** Collision groups allow a certain group of objects to never collide (negative) or always collide (positive). Zero means no
	 * collision group. Non-zero group filtering always wins against the mask bits. */
	private final short groupIndexDEFAULT = 0;
	
	
	private short categoryBits, maskBits, groupIndex;
	
	public Box2dFilterBuilder(FilterCategory category) {
		reset(); // there for safety.
		this.categoryBits = category.getBits();
	}
	
	public Box2dFilterBuilder enableMaskBit(FilterCategory category) {
		this.maskBits = (short) (this.maskBits | category.getBits());
		return this;
	}
	
	public Box2dFilterBuilder disableMaskBit(FilterCategory category) {
		this.maskBits = (short) (this.maskBits & ~category.getBits());
		return this;
	}
	
	public Box2dFilterBuilder enableAllMaskCategories() {
		this.maskBits = (short) -1; // all 1's
		return this;
	}
	
	public Box2dFilterBuilder disableAllMaskCategories() {
		this.maskBits = (short) 0; // all 0's
		return this;
	}
	
	
	/*
	 * These are the default values and comments from Filter.class. DO NOT CHANGE THESE!
	 */
	public Box2dFilterBuilder reset() {
		categoryBits = categoryBitsDEFAULT;
		maskBits = maskBitsDEFAULT;
		groupIndex = groupIndexDEFAULT;
		return this;
	}
	
	public Filter build() {
		Filter filter = new Filter();
		filter.categoryBits = this.categoryBits;
		filter.maskBits = this.maskBits;
		filter.groupIndex = this.groupIndex;
		return filter;
	}
	
	public void copyToFixture(FixtureDef fixtureDef) {
		fixtureDef.filter.categoryBits = this.categoryBits;
		fixtureDef.filter.maskBits = this.maskBits;
		fixtureDef.filter.groupIndex = this.groupIndex;
	}
	
}
