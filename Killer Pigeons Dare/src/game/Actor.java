package game;

import org.newdawn.slick.Image;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor {
	int x, y; //Location in room coordinates.

	String name;

	Image image = null;
}
