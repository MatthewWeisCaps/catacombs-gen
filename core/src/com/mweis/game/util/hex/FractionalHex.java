package com.mweis.game.util.hex;

/*
 * Implementation of:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
public class FractionalHex {
	public static final Hex[] HEX_DIRECTIONS = {
			new Hex(1, 0, -1), new Hex(1, -1, 0), new Hex(0, -1, 1),
			new Hex(-1, 0, 1), new Hex(-1, 1, 0), new Hex(0, 1, -1)
	};
	
	private float[] v;
	
	public FractionalHex(float q, float r, float s) {
		assert(q + r + s == 0);
		v = new float[3];
		v[0] = q;
		v[1] = r;
		v[2] = s;
	}
	
	public FractionalHex(float q, float r) {
		v = new float[3];
		v[0] = q;
		v[1] = r;
		v[2] = -q - r;
	}
	
	public FractionalHex(FractionalHex copy) {
		v = new float[3];
		for (int i=0; i < 2; i++) {
			v[i] = copy.v[i];
		}
	}
	
	public float q() {
		return v[0];
	}
	
	public float r() {
		return v[1];
	}
	
	public float s() {
		return v[2];
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hex) {
			FractionalHex o = (FractionalHex)obj;
			return v[0] == o.v[0] && v[1] == o.v[1] && v[2] == o.v[2];
		} else {
			return super.equals(obj);
		}
	}
	
	/*
	 * Returns this for chaining.
	 */
	public FractionalHex add(FractionalHex other) {
		for (int i=0; i < 2; i++) {
			v[i] += other.v[i];
		}
		return this;
	}
	
	/*
	 * Static implementation of Addition.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static FractionalHex HEX_ADD(FractionalHex a, FractionalHex b) {
		return new FractionalHex(a.v[0] + b.v[0], a.v[1] + b.v[1], a.v[2] + b.v[2]);
	}
	
	/*
	 * Returns this for chaining.
	 */
	public FractionalHex sub(FractionalHex other) {
		for (int i=0; i < 2; i++) {
			v[i] -= other.v[i];
		}
		return this;
	}
	
	/*
	 * Static implementation of Subtract.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static FractionalHex HEX_SUB(FractionalHex a, FractionalHex b) {
		return new FractionalHex(a.v[0] - b.v[0], a.v[1] - b.v[1], a.v[2] - b.v[2]);
	}
	
	/*
	 * Returns this for chaining.
	 */
	public FractionalHex mul(float factor) {
		for (int i=0; i < 2; i++) {
			v[i] *= factor;
		}
		return this;
	}
	
	/*
	 * Static implementation of Multiply.
	 * Returns a NEW hex and does not touch the params given.
	 */
	public static FractionalHex HEX_MUL(FractionalHex a, int k) {
		return new FractionalHex(a.v[0] * k, a.v[1] * k, a.v[2] * k);
	}
	
	public float len() {
		return (Math.abs(v[0]) + Math.abs(v[1]) + Math.abs(v[2])) / 2.0f;
	}
	
	public static float HEX_LEN(Hex hex) {
		return hex.len();
	}
	
	/*
	 * Algorithms
	 */
	public Hex hexRound() {
		int q = (int)q();
		int r = (int)r();
		int s = (int)s();
		float q_diff = Math.abs(q - q());
		float r_diff = Math.abs(r - r());
		float s_diff = Math.abs(s - s());
		if (q_diff > r_diff && q_diff > s_diff) {
			q = -r - s;
		} else if (r_diff > s_diff) {
			r = -q - s;
		} else {
			s = -q - r;
		}
		return new Hex(q, r, s);
	}
	
}
