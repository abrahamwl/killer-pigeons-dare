package game;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	Controller controller;

	@Override
	public boolean execute(Room r) {
		Action a = controller.chooseNextAction(r, r.gc);
		
		if (a.type == Action.Type.NONE_YET) {
			return false;
		}
		
		if (a.type == Action.Type.MOVE) {
			switch (a.dir) {
			case NORTH:
				if (r.checkForTypeAt(x, y - 1, Wall.class)) {
					return true;
				} else {
					y--;
					return true;
				}
			case EAST:
				if (r.checkForTypeAt(x + 1, y, Wall.class)) {
					return true;
				} else {
					x++;
					return true;
				}
			case SOUTH:
				if (r.checkForTypeAt(x, y + 1, Wall.class)) {
					return true;
				} else {
					y++;
					return true;
				}
			case WEST:
				if (r.checkForTypeAt(x - 1, y, Wall.class)) {
					return true;
				} else {
					x--;
					return true;
				}
			}
		}
		return true;
	}
}
