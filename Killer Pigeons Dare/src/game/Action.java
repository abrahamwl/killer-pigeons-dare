package game;

public class Action {
	enum Type {
		NONE_YET,
		END_TURN,
		MOVE;
	}
	
	Type type;
	
	Dir dir = Dir.NO_DIRECTION;
}
