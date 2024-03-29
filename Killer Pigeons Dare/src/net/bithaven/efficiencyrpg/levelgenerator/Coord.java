package net.bithaven.efficiencyrpg.levelgenerator;

import net.bithaven.efficiencyrpg.Dir;

public class Coord implements Comparable<Coord>{
	public Coord parent = null;
	public double goodness = Float.MAX_VALUE; // Used for A* search
	public int x;
	public int y;

	// Form a coord based on direction from starting point
	public Coord(Coord c, Dir d) {
		this.x = c.x + d.x;
		this.y = c.y + d.y;
	}
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord(int x, int y, Coord parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	public Coord(int x, int y, Coord parent, double d) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.goodness = d;
	}
	
	public boolean equals(Object c) {
		return  c instanceof Coord && 
				this.x == ((Coord) c).x && 
				this.y == ((Coord) c).y;
	}

	public int compareTo(Coord c) {
		return (int) Math.signum(goodness - c.goodness);
	}
	
	public float dist(Coord c) {
		return (float) Math.hypot(x - c.x, y - c.y);
	}
}

