package com.mweis.game.util;

/*
 * Definitions of categories used by the Filter in order to fine-tune fixture collisions.
 */
public enum FilterCategory {
	
	BOUNDARY((short) 0x01),
	SENSOR((short) 0x02), // exists so raycasts can avoid
	LIGHT((short) 0x04),
	FRIENDLY_MOB((short) 0x08),
	ENEMY_MOB((short) 0x10);
	
	private final short bits;
	FilterCategory(short bits) {
		this.bits = bits;
	}
	
	public short getBits() {
		return this.bits;
	}
}
