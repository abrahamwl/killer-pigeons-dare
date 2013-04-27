package game;

import org.newdawn.slick.GameContainer;

public interface Controller {
	public Action chooseNextAction (Room room, GameContainer gc);
}
