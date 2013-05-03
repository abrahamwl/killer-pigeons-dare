package net.bithaven.efficiencyrpg.entity;

import java.util.ArrayList;
import java.util.Set;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.AbilityList;
import net.bithaven.efficiencyrpg.ability.TriggersAfterInit;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.controller.Controller;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;


// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	public Controller controller;
	
	public Sound attackSound = null;
	
	public AbilityList abilities = new AbilityList();
	public AbilityList activeAbilities = new AbilityList();
	
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
		dead = false;
		poisoned = 0;
		noDraw = false;
		maxHitpoints = level * 10;
		hitpoints = maxHitpoints;
		for (Ability ability : abilities) {
			ability.doNewTurn();
			Set<TriggersAfterInit> set = activeAbilities.getPrioritizedSet(TriggersAfterInit.class);
			for (TriggersAfterInit trigger : set) {
				trigger.afterInit();
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
			ability.doNewTurn();
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
	
	public Ability getAbility(Class<? extends Ability> type) {
		for (Ability a : abilities) {
			if (type.isInstance(a)) {
				return a;
			}
		}
		return null;
	}
	
	public int getCenterX() {
		return x * CELL_SIZE + CELL_SIZE / 2;
	}

	public int getCenterY() {
		return y * CELL_SIZE + CELL_SIZE / 2;
	}
}
