package com.mweis.game.util.hex;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/*
 * Implementation of Hex from:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
public class Hex {
	
	/*
	 * TODO:
	 * only implemented section 2. Need to continue from 2.1
	 */
	public static final Hex[] HEX_DIRECTIONS = {
			new Hex(1, 0, -1), new Hex(1, -1, 0), new Hex(0, -1, 1),
			new Hex(-1, 0, 1), new Hex(-1, 1, 0), new Hex(0, 1, -1)
	};
	
	private int[] v;
	
	public Hex(int q, int r, int s) {
		assert(q + r + s == 0);
		v = new int[3];
		v[0] = q;
		v[1] = r;
		v[2] = s;
	}
	
	public Hex(int q, int r) {
		v = new int[3];
		v[0] = q;
		v[1] = r;
		v[2] = -q - r;
	}
	
	public Hex(Hex copy) {
		v = new int[3];
		for (int i=0; i < 2; i++) {
			v[i] = copy.v[i];
		}
	}
	
	public int q() {
		return v[0];
	}
	
	public int r() {
		return v[1];
	}
	
	public int s() {
		return v[2];
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hex) {
			Hex o = (Hex)obj;
			return v[0] == o.v[0] && v[1] == o.v[1] && v[2] == o.v[2];
		} else {
			return super.equals(obj);
		}
	}
	
	/*
	 * Returns this for chaining.
	 */
	public Hex add(Hex other) {
		for (int i=0; i < 2; i++) {
			v[i] += other.v[i];
		}
		return this;
	}
	
	/*
	 * Static implementation of Addition.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static Hex HEX_ADD(Hex a, Hex b) {
		return new Hex(a.v[0] + b.v[0], a.v[1] + b.v[1], a.v[2] + b.v[2]);
	}
	
	/*
	 * Returns this for chaining.
	 */
	public Hex sub(Hex other) {
		for (int i=0; i < 2; i++) {
			v[i] -= other.v[i];
		}
		return this;
	}
	
	/*
	 * Static implementation of Subtract.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static Hex HEX_SUB(Hex a, Hex b) {
		return new Hex(a.v[0] - b.v[0], a.v[1] - b.v[1], a.v[2] - b.v[2]);
	}
	
	/*
	 * Returns this for chaining.
	 */
	public Hex mul(int factor) {
		for (int i=0; i < 2; i++) {
			v[i] *= factor;
		}
		return this;
	}
	
	/*
	 * Static implementation of Multiply.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static Hex HEX_MUL(Hex a, int k) {
		return new Hex(a.v[0] * k, a.v[1] * k, a.v[2] * k);
	}
	
	public int len() {
		return (Math.abs(v[0]) + Math.abs(v[1]) + Math.abs(v[2])) / 2;
	}
	
	public static int HEX_LEN(Hex hex) {
		return hex.len();
	}
	
	public int dist(Hex other) {
		return HEX_LEN(HEX_SUB(this, other));
	}
	
	public static int HEX_DIST(Hex a, Hex b) {
		return HEX_LEN(HEX_SUB(a, b));
	}
	
	/*
	 * Returns the corresponding constant HEX_DIRECTION to the integer.
	 * @param dir should be 0 to 5
	 */
	public static Hex HEX_DIRECTION(int dir) {
		return HEX_DIRECTIONS[(6 + (dir % 6)) & 6];
	}
	
	public Hex neighbor(int dir) {
		return HEX_ADD(this, HEX_DIRECTION(dir));
	}
	
	public static Hex HEX_NEIGHBOR(Hex hex, int dir) {
		return HEX_ADD(hex, HEX_DIRECTION(dir));
	}
	
	public int getUID() {
		return q() << 16 + r() << 8 + s();
	}
	
	/*
	 * Algorithms
	 */
	
	public Vector2 hexToPixel(HexLayout layout) {
		HexOrientation M = layout.orientation;
		float x = (M.f0 * q() + M.f1 * r()) * layout.size.x;
		float y = (M.f2 * q() + M.f3 * r()) * layout.size.y;
		return new Vector2(x + layout.origin.x, y + layout.origin.y);
	}
	
	public static Vector2 HEX_TO_PIXEL(HexLayout layout, Hex hex) {
		HexOrientation M = layout.orientation;
		float x = (M.f0 * hex.q() + M.f1 * hex.r()) * layout.size.x;
		float y = (M.f2 * hex.q() + M.f3 * hex.r()) * layout.size.y;
		return new Vector2(x + layout.origin.x, y + layout.origin.y);
	}
	
	public static FractionalHex PIXEL_TO_HEX(HexLayout layout, Vector3 p) {
		HexOrientation M = layout.orientation;
	    Vector2 pt = new Vector2((p.x - layout.origin.x) / layout.size.x,
	                     (p.y - layout.origin.y) / layout.size.y);
	    float q = M.b0 * pt.x + M.b1 * pt.y;
	    float r = M.b2 * pt.x + M.b3 * pt.y;
	    return new FractionalHex(q, r, -q - r);
	}
	
	public static FractionalHex PIXEL_TO_HEX(HexLayout layout, Vector2 p) {
		HexOrientation M = layout.orientation;
	    Vector2 pt = new Vector2((p.x - layout.origin.x) / layout.size.x,
	                     (p.y - layout.origin.y) / layout.size.y);
	    float q = M.b0 * pt.x + M.b1 * pt.y;
	    float r = M.b2 * pt.x + M.b3 * pt.y;
	    return new FractionalHex(q, r, -q - r);
	}
	
	private static Vector2 HEX_CORNER_OFFSET(HexLayout layout, int corner) {
		Vector2 size = layout.size;
		float angle = (float) (2.0 * Math.PI * (layout.orientation.start_angle + corner) / 6.0f);
		return new Vector2((float) (size.x * Math.cos(angle)), (float)(size.y * Math.sin(angle)));
	}
	
	public Vector2[] polygonCorners(HexLayout layout) {
		return POLYGON_CORNERS(layout);
	}
	
	public static Vector2[] POLYGON_CORNERS(HexLayout layout) {
		Vector2[] corners = new Vector2[6];
		for (int i=0; i < corners.length; i++) {
			Vector2 offset = HEX_CORNER_OFFSET(layout, i);
			corners[i] = new Vector2(layout.origin.x + offset.x,
									layout.origin.y + offset.y);
		}
		return corners;
	}
}
