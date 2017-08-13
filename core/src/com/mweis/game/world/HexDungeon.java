package com.mweis.game.world;


import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.api.contract.SatelliteData;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import rx.functions.Action1;

public class HexDungeon {
	
	/*
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
	*/
	class SomeData implements SatelliteData {

		@Override
		public double getMovementCost() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isOpaque() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isPassable() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setMovementCost(double arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setOpaque(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPassable(boolean arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public HexDungeon(World world) {
			
		HexagonalGridBuilder<SomeData> hgb = new HexagonalGridBuilder<SomeData>();
		hgb.setGridHeight(10);
		hgb.setGridWidth(10);
		hgb.setGridLayout(HexagonalGridLayout.RECTANGULAR);
		hgb.setRadius(50.0d);
		HexagonalGrid<SomeData> grid = hgb.build();
		
		Array<Hexagon<SomeData>> arr = new Array<Hexagon<SomeData>>(hgb.getGridHeight() * hgb.getGridWidth());
		grid.getHexagons().forEach(new Action1<Hexagon<SomeData>>() {
			@Override
			public void call(Hexagon<SomeData> arg0) {
				/*
				 * 
				 * https://github.com/Hexworks/hexameter/blob/eaa705c79b8e6b10fbd3d00f5edd805f0133534a/hexameter-examples/hexameter-rest-example/src/main/java/org/codetome/hexameter/restexample/dto/GridDto.java
				 */
			}
		});
	}
}









