package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import game.action.*;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	public Controller controller;
	
	public ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	private int level;
	public int getLevel() {
		return level;
	}

	private int hitpoints;
	private int maxHitpoints;
	private boolean dead = false;
	
	public Actor (String name, int level) {
		super(name);
		hitpoints = 10 * level;
		maxHitpoints = hitpoints;
		this.level = level;
	}
	
	public void applyDamage(int damage) {
		hitpoints -= damage;
		if (hitpoints <= 0) {
			kill();
		}
	}
	
	@Override
	public void init () {
		for (Ability ability : abilities) {
			if (ability.type == Ability.Type.TOUGH && ability.active) {
				hitpoints *= 1.5;
				maxHitpoints = hitpoints;
			}
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
			g.fillRect(x * CELL_SIZE, y * CELL_SIZE + 60, 64 * hitpoints / maxHitpoints, 3);
			
			g.setColor(Color.white);
			g.drawString(String.valueOf(level), x * CELL_SIZE, y * CELL_SIZE);
		}
	}
	
	@Override
	public boolean execute(Room r) {
		if (dead) {
			return true;
		}
		for (Ability ability : abilities) {
			ability.reset();
		}
		Action a = controller.chooseNextAction(r, r.gc);
		
		if (a instanceof ActionNoneYet) {
			return false;
		} else {
			a.execute(r, this);
			return true;
		}
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}
}
