package game;

public class Action {
	enum Type {
		END_TURN,
		MOVE;
	}
	
	Type type;
	
	Dir dir = Dir.NO_DIRECTION;
}
