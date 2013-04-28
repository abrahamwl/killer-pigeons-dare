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
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = input.getMouseX();
			int y = input.getMouseY();
			int hX = a.x  * Entity.CELL_SIZE;
			int hY = a.y  * Entity.CELL_SIZE;
			if (x > hX + Entity.CELL_SIZE) {
				if (y < hY) {
					return moveAttack(room, Dir.NORTH_EAST);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(room, Dir.SOUTH_EAST);
				} else {
					return moveAttack(room, Dir.EAST);
				}
			} else if (x < hX) {
				if (y < hY) {
					return moveAttack(room, Dir.NORTH_WEST);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(room, Dir.SOUTH_WEST);
				} else {
					return moveAttack(room, Dir.WEST);
				}
			} else {
				if (y < hY) {
					return moveAttack(room, Dir.NORTH);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(room, Dir.SOUTH);
				} else {
					return new ActionWait();
				}
			}
		}
		
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
		if (input.isKeyPressed(Input.KEY_NUMPAD5)) {
			return new ActionWait();
		}
		
		return ActionNoneYet.INSTANCE;
	}
}
