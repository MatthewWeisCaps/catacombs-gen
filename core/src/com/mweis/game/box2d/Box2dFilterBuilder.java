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
	
	private Filter filter;
	
	public Box2dFilterBuilder(FilterCategory category) {
		filter = new Filter();
		filter.categoryBits = category.getBits();
	}
	
	public Box2dFilterBuilder enableMaskBit(FilterCategory category) {
		filter.maskBits = (short) (filter.maskBits | category.getBits());
		return this;
	}
	
	public Box2dFilterBuilder disableMaskBit(FilterCategory category) {
		filter.maskBits = (short) (filter.maskBits & ~category.getBits());
		return this;
	}
	
	public Box2dFilterBuilder enableAllMaskCategories() {
		filter.maskBits = (short) -1; // all 1's
		return this;
	}
	
	public Box2dFilterBuilder disableAllMaskCategories() {
		filter.maskBits = (short) 0; // all 0's
		return this;
	}
	
	public Filter build() {
		return filter;
	}
	
	public void copyToFixture(FixtureDef fixtureDef) {
		fixtureDef.filter.categoryBits = filter.categoryBits;
		fixtureDef.filter.groupIndex = filter.groupIndex;
		fixtureDef.filter.maskBits = filter.maskBits;
	}
	
}
