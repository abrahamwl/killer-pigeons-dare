package game;

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
}
