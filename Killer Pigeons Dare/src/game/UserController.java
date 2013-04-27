package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class UserController implements Controller {
	
	UserController (Character avatar) {
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			Action out = new Action();
			out.type = Action.Type.MOVE;
			out.dir = Dir.EAST;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			Action out = new Action();
			out.type = Action.Type.MOVE;
			out.dir = Dir.SOUTH;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			Action out = new Action();
			out.type = Action.Type.MOVE;
			out.dir = Dir.WEST;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			Action out = new Action();
			out.type = Action.Type.MOVE;
			out.dir = Dir.NORTH;
			return out;
		}
		
		Action out = new Action();
		out.type = Action.Type.NONE_YET;
		return out;
	}
}
