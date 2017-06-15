package com.mweis.game.util;

/*
 * Definitions of categories used by the Filter in order to fine-tune fixture collisions.
 */
public enum FilterCategory {
	
	BOUNDARY((short) 2),
	SENSOR((short) 4), // exists so raycasts can avoid
	LIGHT((short) 8),
	FRIENDLY_MOB((short) 16),
	ENEMY_MOB((short) 32);
	
	private final short bits;
	FilterCategory(short bits) {
		this.bits = bits;
	}
	
	public short getBits() {
		return this.bits;
	}
}
