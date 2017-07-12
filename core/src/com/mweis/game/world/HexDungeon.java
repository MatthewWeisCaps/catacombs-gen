package com.mweis.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.IntMap;
import com.mweis.game.util.hex.Hex;
import com.mweis.game.util.hex.HexLayout;
import com.mweis.game.util.hex.HexOrientation;

public class HexDungeon {
	
	IntMap<Hex> map;
	
	public HexDungeon(World world) {
		int width = 10;
		int height = 5;
		Vector2 size = new Vector2(100.0f, 100.0f);
		
		map = new IntMap<Hex>(width*height);
		IntMap<HexLayout> lmap = new IntMap<HexLayout>(width*height);
		
		for (int q = 0; q < width; q++) {
			int q_offset = q >> 1;
			for (int r = -q_offset; r < height - q_offset; r++) {
				Hex hex = new Hex(q, r, -q-r);
				map.put(hex.getUID(), hex);
				HexLayout layout = new HexLayout(HexOrientation.FLAT_TOP, size, new Vector2(q, r).scl(size));
				lmap.put(hex.getUID(), layout);
				
				BodyDef bodyDef = new BodyDef();
				
				bodyDef.type = BodyType.StaticBody;
				
				bodyDef.position.set(layout.origin);


				Body body = world.createBody(bodyDef);
				
				PolygonShape polygon = new PolygonShape();
				polygon.set(hex.polygonCorners(layout));

				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = polygon;
				
				
				Fixture fixture = body.createFixture(fixtureDef);

				polygon.dispose();
				
			}
		}
		
		
	}
}
