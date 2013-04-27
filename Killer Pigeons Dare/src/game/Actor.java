package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public abstract class Actor extends Entity {
	Controller controller;

	public abstract void decide();
}
