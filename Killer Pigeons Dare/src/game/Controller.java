package game;

import game.action.Action;

import org.newdawn.slick.GameContainer;

public interface Controller {
	public Action chooseNextAction (Room room, GameContainer gc);
}
