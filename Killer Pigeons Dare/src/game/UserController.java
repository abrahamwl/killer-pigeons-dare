package game;

import game.action.*;
import game.entity.Character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class UserController extends BasicController {
	
	public UserController (Character avatar) {
		a = avatar;
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_NUMPAD6)) {
			return moveAttack(room, Dir.EAST);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD3)) {
			return moveAttack(room, Dir.SOUTH_EAST);
		}
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_NUMPAD2)) {
			return moveAttack(room, Dir.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD1)) {
			return moveAttack(room, Dir.SOUTH_WEST);
		}
		if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_NUMPAD4)) {
			return moveAttack(room, Dir.WEST);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD7)) {
			return moveAttack(room, Dir.NORTH_WEST);
		}
		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_NUMPAD8)) {
			return moveAttack(room, Dir.NORTH);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD9)) {
			return moveAttack(room, Dir.NORTH_EAST);
		}
		
		return ActionNoneYet.INSTANCE;
	}
}
