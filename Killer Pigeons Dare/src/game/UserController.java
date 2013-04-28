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
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			return moveAttack(room, Dir.EAST);
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			return moveAttack(room, Dir.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			return moveAttack(room, Dir.WEST);
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			return moveAttack(room, Dir.NORTH);
		}
		
		return ActionNoneYet.INSTANCE;
	}
}
