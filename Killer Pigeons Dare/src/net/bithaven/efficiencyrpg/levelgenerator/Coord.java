package net.bithaven.efficiencyrpg.levelgenerator;

public class Coord implements Comparable<Coord>{
	public Coord parent = null;
	public float goodness = Float.MAX_VALUE; // Used for A* search
	public int x;
	public int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord(int x, int y, Coord parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	public Coord(int x, int y, Coord parent, float goodness) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.goodness = goodness;
	}
	
	public Coord(int x, int y, Coord parent, Coord destination) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.goodness = dist(destination);
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

