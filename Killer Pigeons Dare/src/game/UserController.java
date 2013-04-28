package game;

import game.action.*;
import game.entity.Character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class UserController implements Controller {
	
	public UserController (Character avatar) {
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			return new ActionMove(Dir.EAST);
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			return new ActionMove(Dir.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			return new ActionMove(Dir.WEST);
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			return new ActionMove(Dir.NORTH);
		}
		
		return ActionNoneYet.INSTANCE;
	}
}
