package net.bithaven.efficiencyrpg;

import java.util.ArrayList;
import java.util.List;

public enum Dir {
	NO_DIRECTION(null, null),
	NORTH(0, -1),
	NORTH_EAST(1, -1),
	EAST(1, 0),
	SOUTH_EAST(1, 1),
	SOUTH(0, 1),
	SOUTH_WEST(-1, 1),
	WEST(-1, 0),
	NORTH_WEST(-1, -1);
	
	public final Integer x, y;
	
	@SuppressWarnings("serial")
	public final static List<Dir> compass = new ArrayList<Dir>() {{
		add(Dir.NORTH);
		add(Dir.NORTH_EAST);
		add(Dir.EAST);
		add(Dir.SOUTH_EAST);
		add(Dir.SOUTH);
		add(Dir.SOUTH_WEST);
		add(Dir.WEST);
		add(Dir.NORTH_WEST);
	}};
	
	private Dir (Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Dir flip() {
		switch(this) {
		case NORTH:
			return SOUTH;
		case NORTH_EAST:
			return SOUTH_WEST;
		case EAST:
			return WEST;
		case SOUTH_EAST:
			return NORTH_WEST;
		case SOUTH:
			return NORTH;
		case SOUTH_WEST:
			return NORTH_EAST;
		case WEST:
			return EAST;
		case NORTH_WEST:
			return SOUTH_EAST;
		default:
			return NO_DIRECTION;
		}
	}
	
	public static Dir fromXY(int x, int y) {
		if (x > 0) {
			if (y > 0) {
				return SOUTH_EAST;
			} else if (y < 0) {
				return NORTH_EAST;
			} else {
				return EAST;
			}
		} else if (x < 0) {
			if (y > 0) {
				return SOUTH_WEST;
			} else if (y < 0) {
				return NORTH_WEST;
			} else {
				return WEST;
			}
		} else {
			if (y > 0) {
				return SOUTH;
			} else if (y < 0) {
				return NORTH;
			} else {
				return NO_DIRECTION;
			}
		}
	}
	
	// 0 is North, pi is South, 1/2 * pi is East, 3/2 * pi is West
	public static Dir fromRadian(double radian) {
		return fromXY(Math.round((int) Math.sin(radian)), (int) Math.round(Math.cos(radian)));
	}
	
	public static void main(String[] arg) {
		System.out.println("fromRadian(0.0 * PI): " + fromRadian(0.0 * Math.PI));
		System.out.println("fromRadian(0.5 * PI): " + fromRadian(0.5 * Math.PI));
		System.out.println("fromRadian(1.0 * PI): " + fromRadian(1.0 * Math.PI));
		System.out.println("fromRadian(1.5 * PI): " + fromRadian(1.5 * Math.PI));
	}
}
