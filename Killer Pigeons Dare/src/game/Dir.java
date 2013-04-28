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
}
