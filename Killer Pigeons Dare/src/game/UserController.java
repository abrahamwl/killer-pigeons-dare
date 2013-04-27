package game;

import game.action.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class UserController implements Controller {
	
	UserController (Character avatar) {
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			ActionMove out = new ActionMove();
			out.dir = Dir.EAST;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			ActionMove out = new ActionMove();
			out.dir = Dir.SOUTH;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			ActionMove out = new ActionMove();
			out.dir = Dir.WEST;
			return out;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			ActionMove out = new ActionMove();
			out.dir = Dir.NORTH;
			return out;
		}
		
		return new ActionNoneYet();
	}
}
