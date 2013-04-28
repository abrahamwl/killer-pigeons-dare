package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import game.action.*;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	public Controller controller;
	
	private int level;
	public int getLevel() {
		return level;
	}

	private int hitpoints;
	private boolean dead = false;
	
	public Actor (String name, int level) {
		super(name);
		hitpoints = 10 * level;
		this.level = level;
	}
	
	public void applyDamage(int damage) {
		hitpoints -= damage;
		if (hitpoints <= 0) {
			kill();
		}
	}
	
	public void kill() {
		dead = true;
		noDraw = true;
		x = Integer.MIN_VALUE;
		y = Integer.MIN_VALUE;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!noDraw) {
			image.draw(x * CELL_SIZE, y * CELL_SIZE);
			g.setColor(Color.red);
			g.fillRect(x * CELL_SIZE, y * CELL_SIZE + 60, 64, 3);
			g.setColor(Color.green);
			g.fillRect(x * CELL_SIZE, y * CELL_SIZE + 60, 64 * hitpoints / (level * 10), 3);
		}
	}
	
	@Override
	public boolean execute(Room r) {
		if (dead) {
			return true;
		}
		Action a = controller.chooseNextAction(r, r.gc);
		
		if (a instanceof ActionNoneYet) {
			return false;
		} else {
			a.execute(r, this);
			return true;
		}
	}
}
