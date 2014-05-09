package net.bithaven.efficiencyrpg;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	/**
	 * @param radian 0 is North, pi is South, 1/2 * pi is East, 3/2 * pi is West.
	 * @return The Dir that returns to the radian.
	 */
	public static Dir fromRadian(double radian) {
		// Rounding is used due to inexactness of double: 0.0 == 6.1234E-17
		// The weird use of signum and abs is b/c ceil rounds in the positive 
		// direction, not in the direction of greater magnitude
		double x_raw = round(Math.sin(radian), 2);
		int x = (int) (Math.signum(x_raw) * Math.ceil(Math.abs(x_raw)));
		double y_raw = round(Math.cos(radian), 2);
		int y = (int) (Math.signum(y_raw) * Math.ceil(Math.abs(y_raw)));
		System.out.println("x_raw: " + x_raw + " y_raw: " + y_raw); // DEBUG
		System.out.println("x: " + x + " y: " + y); // DEBUG
		return fromXY(x, y);
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}	
	public static void main(String[] arg) {
		System.out.println("atan2(1,0): " + Math.atan2(0,1) / Math.PI);
		System.out.println("fromRadian(0.0 * PI): " + fromRadian(0.0 * Math.PI));
		System.out.println("atan2(0,1): " + Math.atan2(1,0) / Math.PI);
		System.out.println("fromRadian(0.5 * PI): " + fromRadian(0.5 * Math.PI));
		System.out.println("atan2(-1,0): " + Math.atan2(0,-1) / Math.PI);
		System.out.println("fromRadian(1.0 * PI): " + fromRadian(1.0 * Math.PI));
		System.out.println("atan2(0,-1): " + Math.atan2(-1,0) / Math.PI);
		System.out.println("fromRadian(-0.5 * PI): " + fromRadian(-0.5 * Math.PI));
	}
}
