package com.mweis.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mweis.game.box2d.Box2dBodyFactory;

// a different way of representing rectangles, consistent with the TileMerging algorithm
class PointRectangle implements Comparable<PointRectangle> {
	float start_x, start_y, end_x, end_y;
	PointRectangle(float sx, float sy, float ex, float ey) {
		start_x = sx;
		start_y = sy;
		end_x = ex;
		end_y = ey;
	}
	PointRectangle(PointRectangle copy) {
		this.start_x = copy.start_x;
		this.start_y = copy.start_y;
		this.end_x = copy.end_x;
		this.end_y = copy.end_y;
	}
	@Override
	public int compareTo(PointRectangle other) {
		return this.start_y < other.start_y ? 1 : -1;
	}
	public boolean touching(PointRectangle b, float padding) {
		return  !(b.start_x-padding >= this.end_x ||
				b.end_x <= this.start_x-padding ||
				b.end_y <= this.start_y-padding ||
				b.start_y-padding >= this.end_y);
	}
	public void transform(float deltaLeft, float deltaRight, float deltaTop, float deltaBottom) {
		this.start_x += deltaLeft;
		this.end_x += deltaRight;
		this.start_y += deltaBottom;
		this.end_y += deltaTop;
	}
	public void reorient() {
		float sx = Math.min(this.start_x, this.end_x);
		float sy = Math.min(this.start_y, this.end_y);
		float ex = Math.max(this.start_x, this.end_x);
		float ey = Math.max(this.start_y, this.end_y);
		this.start_x = sx;
		this.start_y = sy;
		this.end_x = ex;
		this.end_y = ey;
	}
	@Override
	public String toString() {
		return String.format("start: (%f, %f), end: (%f, %f)%n", start_x, start_y, end_x, end_y);
	} 
}

public class WorldFactory {
	
	
	/*
	 * TileMerging algorithm from https://love2d.org/wiki/TileMerging, this merges into VERTICAL WALLS ONLY
	 * rectangles - an array of PointRectangles representing each room
	 */
	public static void verticalTileMerge(Array<PointRectangle> rectangles, Dungeon dungeon, float precision) {
		
		for (float x = 0.0f; x < dungeon.WIDTH; x += precision) {
			// Floats mean we need to watch the == sign!
			Float start_y = null;
			Float end_y = null;
			
			for (float y = 0.0f; y < dungeon.HEIGHT; y += precision) {
				// WARNING: if getRoomsContainingArea is changed, so must be the other call to it below!
				if (dungeon.getRoomsContainingArea(new Rectangle(x, y, precision, precision)).size == 0) { // check if chunk goes here
					if (start_y == null) {
						start_y = y;
					}
					end_y = y;
				} else if (start_y != null) {
					Array<PointRectangle> overlaps = new Array<PointRectangle>();
					for (PointRectangle r : rectangles) {
						if (r.end_x == x - precision
							&& start_y <= r.start_y
							&& end_y >= r.end_y) {
							overlaps.add(r);
						}
					}
					overlaps.sort();
					for (PointRectangle r : overlaps) {
						if (start_y < r.start_y) {
							PointRectangle new_rect = new PointRectangle(x, start_y, x, r.start_y - precision);
							rectangles.add(new_rect);
							start_y = r.start_y;
						}
						
						if (start_y == r.start_y) {
							r.end_x = r.end_x + precision;
							if (end_y == r.end_y) {
								start_y = null;
								end_y = null;
								break; // safeguard. if NullPointerException shows up eventually they put safeguard at start of this for-loop
							} else if (end_y > r.end_y) {
								start_y = r.end_y + precision;
							}
						}
					}
					if (start_y != null) {
						PointRectangle new_rect = new PointRectangle(x, start_y, x, end_y);
						rectangles.add(new_rect);
						start_y = null;
						end_y = null;
					}
				}
			}
			if (start_y != null) {
				PointRectangle new_rect = new PointRectangle(x, start_y, x, end_y);
				rectangles.add(new_rect);
				start_y = null;
				end_y = null;
			}
		}
	}
	
	/*
	 * Merge walls into larger rectangles iff the walls have the same start and end y
	 * rectangles - an array of vertically merged rectangles
	 */
	private static void horizontalTileMerge(Array<PointRectangle> rectangles, Dungeon dungeon, float precision) {
		boolean mergeFlag = false;
		do {
			mergeFlag = false;
			PointRectangle new_rect = null, old_rect1 = null, old_rect2 = null;
			int size = rectangles.size; // must calc here is it's changing often
			A: for (int i=0; i < size; i++) {
				for (int j=0; j < size; j++) { // can j start at i+1?
					if (i != j) {
						PointRectangle r1 = rectangles.get(i);
						PointRectangle r2 = rectangles.get(j);
						if (withinNUnits(r1.start_x, r2.end_x, precision) || withinNUnits(r1.end_x, r2.start_x, precision)) {
							if (r1.start_y == r2.start_y && r1.end_y == r2.end_y) {
								// combine the walls
								float sx = Math.min(r1.start_x, Math.min(r1.end_x, Math.min(r2.start_x, r2.end_x)));
								float sy = Math.min(r1.start_y, Math.min(r1.end_y, Math.min(r2.start_y, r2.end_y)));
								float ex = Math.max(r1.start_x, Math.max(r1.end_x, Math.max(r2.start_x, r2.end_x)));
								float ey = Math.max(r1.start_y, Math.max(r1.end_y, Math.max(r2.start_y, r2.end_y)));
								new_rect = new PointRectangle(sx, sy, ex, ey);
								old_rect1 = r1;
								old_rect2 = r2;
								mergeFlag = true;
								break A;
							}
						}
					}
				}
			}
			if (new_rect != null) {
				rectangles.removeValue(old_rect1, true);
				rectangles.removeValue(old_rect2, true);
				rectangles.add(new_rect);
			}
		} while (mergeFlag);
	}
	
	/*
	 * Shrinks rectangles so that they won't cover areas the player can't reach
	 * rectangles - an array of horizontally and vertically merged PointRectangles
	 */
	private static void shrinkRectangles(Array<PointRectangle> rectangles, Dungeon dungeon, float precision) {
		for (PointRectangle r : rectangles) {
			float max_y = Math.max(r.start_y, r.end_y); // will change and must be recomputed per iteration
			float min_y = Math.min(r.start_y, r.end_y); // will change and must be recomputed per iteration
			float max_x = Math.max(r.start_x, r.end_x); // constant
			float min_x = Math.min(r.start_x, r.end_x); // constant
			
			
			// shrink top down
			boolean canShrink = true;
			while (canShrink) {
				
				
				if (dungeon.getPotentialRoomsInArea(new Rectangle(min_x, max_y - precision, max_x - min_x, precision)).size == 0
						&& min_y >= 0){
					if (r.end_y > r.start_y) {
						r.end_y -= precision;
					} else {
						r.start_y -= precision;
					}
					max_y = Math.max(r.start_y, r.end_y);
					min_y = Math.min(r.start_y, r.end_y);
				} else {
					canShrink = false;
				}
			}
			
			// shrink bottom up
			canShrink = true;
			while (canShrink) {
				if (dungeon.getPotentialRoomsInArea(new Rectangle(min_x, min_y, max_x - min_x, precision)).size == 0
						&& max_y <= dungeon.HEIGHT){
					if (r.start_y < r.end_y) {
						r.start_y += precision;
					} else {
						r.end_y += precision;
					}
					max_y = Math.max(r.start_y, r.end_y);
					min_y = Math.min(r.start_y, r.end_y);
				} else {
					canShrink = false;
				}
			}
		}
	}
	
	/*
	 * Slice rectangles bigger than allowed by box2d into smaller pieces
	 * rectangles - are merged and shrunk
	 */
	private static void sliceLargeRectangles(Array<PointRectangle> rectangles, Dungeon dungeon) {
		for (int i=0; i < rectangles.size; i++) {
			PointRectangle r = rectangles.get(i);
			float sx = Math.min(r.start_x, r.end_x);
			float sy = Math.min(r.start_y, r.end_y);
			float ex = Math.max(r.start_x, r.end_x);
			float ey = Math.max(r.start_y, r.end_y);
			float height = Math.max(r.start_y, r.end_y) - sy;
			float width = Math.max(r.start_x, r.end_x) - sx;
			
			if (height > dungeon.MAX_BOX2D_STATIC_BODY_SIZE) {
				float halfway = (ey - sy + 1)/2.0f;
				PointRectangle top_half = new PointRectangle(sx, sy + halfway, ex, ey);
				PointRectangle bottom_half = new PointRectangle(sx, sy, ex, sy + halfway);
				rectangles.removeIndex(i);
				i--;
				rectangles.add(top_half);
				rectangles.add(bottom_half);
			} else if (width > dungeon.MAX_BOX2D_STATIC_BODY_SIZE) {
				float halfway = (ex - sx)/2.0f;
				PointRectangle left_half = new PointRectangle(sx, sy, sx + halfway, ey);
				PointRectangle right_half = new PointRectangle(sx + halfway, sy, ex, ey);
				rectangles.removeIndex(i);
				i--;
				rectangles.add(left_half);
				rectangles.add(right_half);
			}
		}
	}
	
	/*
	 * makes the box2d representation of the rectangles. Also attempts to remove unused partitions.
	 */
	private static void box2difyRectangles(Array<PointRectangle> rectangles, World world, Dungeon dungeon, float minimumWallWidth) {
		for (PointRectangle r : rectangles) {
			float sx = Math.min(r.start_x, r.end_x);
			float sy = Math.min(r.start_y, r.end_y);
			float height = Math.max(r.start_y, r.end_y) - sy;
			float width = Math.max(r.start_x, r.end_x) - sx;
			
			if (dungeon.getPotentialRoomsInArea(new Rectangle(sx, sy, width, height)).size == 0) {
				continue; // skip any rooms with no use
			}
			height = Math.max(height, minimumWallWidth);
			width = Math.max(width, minimumWallWidth);
			
			// prints size of big rects
//			if (max(width, height) > MAX_BOX2D_STATIC_BODY_SIZE) {
//				System.out.format("sx: %d, sy: %d, width: %d, height: %d%n", sx, sy, width, height);
//			}
			
			Box2dBodyFactory.createStaticRectangle(sx, sy, width, height, world);
		}
	}
	
	/*
	 * Makes a border around the WIDTH and HEIGHT of the world. Might not be desired.
	 */
	private static void createBorderAroundWorld(Array<PointRectangle> rectangles, World world, Dungeon dungeon) {
		Vector2 bottom_left = Vector2.Zero;
		Vector2 bottom_right = new Vector2(dungeon.WIDTH, 0);
		Vector2 top_left = new Vector2(0, dungeon.HEIGHT);
		Vector2 top_right = new Vector2(dungeon.WIDTH, dungeon.HEIGHT);
		Box2dBodyFactory.createEdge(top_left, top_right, world); // top
		Box2dBodyFactory.createEdge(bottom_left, bottom_right, world); // bottom
		Box2dBodyFactory.createEdge(bottom_left, top_left, world); // left
		Box2dBodyFactory.createEdge(bottom_right, top_right, world); // right
	}
	
	private static void closeSmallGapsBetweenWalls(Array<PointRectangle> rectangles, World world, Dungeon dungeon, float precision) {
		
		float padding = precision * 2.0f;
		
		for (PointRectangle r : rectangles) {
			r.reorient();
		}
		
		for (int i=0; i < rectangles.size; i++) {
			for (int j=0; j < rectangles.size; j++) {
				
				if (i == j) {
					continue;
				}
				
				PointRectangle a = rectangles.get(i);
				PointRectangle b = rectangles.get(j);
				
				if (!a.touching(b, 0.0f) && a.touching(b, padding)) {
										
					// w.r.t. a
					float deltaLeft = 0.0f;
					float deltaRight = 0.0f;
					float deltaTop = 0.0f;
					float deltaBottom = 0.0f;
					
					PointRectangle test_a = new PointRectangle(a);
					test_a.start_x -= padding;
					if (test_a.touching(b, 0.0f)) {
						deltaLeft = -padding;
					}
					
					test_a = new PointRectangle(a);
					test_a.end_x += padding;
					if (test_a.touching(b, 0.0f)) {
						deltaRight = padding;
					}

					test_a = new PointRectangle(a);
					test_a.start_y -= padding;
					if (test_a.touching(b, 0.0f)) {
						deltaBottom = -padding;
					}
					
					test_a = new PointRectangle(a);
					test_a.end_y += padding;
					if (test_a.touching(b, padding)) {
						deltaTop = padding;
					}
					
					a.transform(deltaLeft, deltaRight, deltaTop, deltaBottom);
				}
			}
		}
	}
	
	public static World createWorldFromDungeon(Dungeon dungeon, float precision, float minimumWallWidth) {
		
		if (precision < 0.25f) {
			Gdx.app.error("WorldFactory", "Precision below 0.25f is unstable", new IllegalArgumentException());
		}
		
		World world = new World(Vector2.Zero, true);
		
//		Array<PointRectangle> rectangles = new Array<PointRectangle>(false, 100); // should use this one! much faster w/o ordering
		Array<PointRectangle> rectangles = new Array<PointRectangle>();
		
		verticalTileMerge(rectangles, dungeon, precision);
		horizontalTileMerge(rectangles, dungeon, precision);
		
		
		/*
		 * Needlessly tall bodies can cause coll errors in box2d, we need to shrink out areas not affecting the map
		 */
		shrinkRectangles(rectangles, dungeon, precision);
		
		
		/*
		 * Any box2d objects bigger than the permitted size need to be cut into half until they are small enough
		 */

		sliceLargeRectangles(rectangles, dungeon);
		
		closeSmallGapsBetweenWalls(rectangles, world, dungeon, precision);
		
		/*
		 * Tiles are now merged, time to box2dify them.
		 * Also, remove any unused partitions here.
		 */
		box2difyRectangles(rectangles, world, dungeon, minimumWallWidth);
		
		/*
		 * Optional, make a border around the entire dungeon preventing escape if.
		 * 
		 * We might not want this actually, it could be cool to just put water/lava/height around the edges instead,
		 * light could bleed in and it would be a tactical spot for players with knockback abilities to kite enemy mobs to.
		 * (also light might hurt mobs, meaning kiting mobs towards the light is smart if the mob's weakness is known.
		 */
		createBorderAroundWorld(rectangles, world, dungeon);
		
		return world;
	}
	
	private static boolean withinNUnits(float a, float b, float range) {
		return Math.abs(a - b) <= range;
	}
	
	private WorldFactory() { }
}
