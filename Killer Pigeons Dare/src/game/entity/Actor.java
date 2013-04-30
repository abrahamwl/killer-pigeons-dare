package game.entity;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

import game.Ability;
import game.Room;
import game.Ability.Type;
import game.action.*;
import game.controller.Controller;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	public Controller controller;
	
	public Sound attackSound = null;
	
	public ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	public int level;
	public int getLevel() {
		return level;
	}

	public int hitpoints;
	public int getHitpoints() {
		return hitpoints;
	}

	public int maxHitpoints;
	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	protected boolean dead = false;
	
	public int poisoned = 0;
	
	public boolean isDead() {
		return dead;
	}

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
	public void init (Room r) {
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
		Action a = controller.chooseNextAction(r, r.game.gc);
		
		if (a instanceof ActionNoneYet) {
			return false;
		} else {
			a.execute(r, this);
			applyDamage(poisoned);
			return true;
		}
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}
	
	public Ability getAbility(Ability.Type type) {
		for (Ability a : abilities) {
			if (a.type == type) {
				return a;
			}
		}
		return null;
	}
}
