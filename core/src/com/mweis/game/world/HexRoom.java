package com.mweis.game.world;

import com.mweis.game.util.hex.Hex;
import com.mweis.game.util.hex.HexLayout;

public class HexRoom {
	Hex hex;
	HexLayout layout;
	
	HexRoom(Hex hex, HexLayout layout) {
		this.hex = hex;
		this.layout = layout;
	}
	
	
}
