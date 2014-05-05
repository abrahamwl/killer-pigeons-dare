package net.bithaven.efficiencyrpg.entity;

import java.util.ArrayList;
import java.util.Set;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.AbilityInterface;
import net.bithaven.efficiencyrpg.ability.AbilityList;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.HandlesDamage;
import net.bithaven.efficiencyrpg.ability.TriggersAfterInit;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.controller.Controller;
import net.bithaven.efficiencyrpg.event.DamageEvent;
import net.bithaven.efficiencyrpg.other.Damage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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

	public Actor (String name, int level, String imageName) {
		super(name, imageName, Layer.ACTOR);
		hitpoints = 10 * level;
		maxHitpoints = hitpoints;
		this.level = level;
	}
	
	public Damage applyDamage(Damage damage) {
		HandlesDamage handler = activeAbilities.getFirst(HandlesDamage.class);
		if (handler != null) {
			damage = handler.modifyDamage(damage);
		}

		if (hitpoints - damage.amount < maxHitpoints) {
			hitpoints -= damage.amount;
		} else {
			damage = new Damage(hitpoints - maxHitpoints, damage.type);
			hitpoints = maxHitpoints;
		}
		if (hitpoints <= 0) {
			kill();
		}
		
		return damage;
	}
	
	public void applyHealth(int health) {
		hitpoints += health;
		if (hitpoints > maxHitpoints) {
			hitpoints = maxHitpoints;
		}
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
		dead = false;
		poisoned = 0;
		noDraw = false;
		maxHitpoints = level * 10;
		hitpoints = maxHitpoints;
		for (AbilityInterface ability : abilities) {
			System.out.println(ability.toString());//DEBUG
			ability.on(this).doNewTurn();
			Set<TriggersAfterInit> set = activeAbilities.getPrioritizedSet(TriggersAfterInit.class);
			for (TriggersAfterInit trigger : set) {
				trigger.afterInit(this);
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
			renderAt(g, x * CELL_SIZE, y * CELL_SIZE);
		}
	}
	
	public void renderAt (Graphics g, int x, int y) {
		image.draw(x, y);
		
		g.setColor(Color.red);
		g.fillRect(x + 1, y + (CELL_SIZE - 4), CELL_SIZE - 2, 3);
		if (!isDead()) {
			g.setColor(Color.green);
			g.fillRect(x + 1, y + (CELL_SIZE - 4), (CELL_SIZE - 2) * hitpoints / maxHitpoints, 3);
		}
		
		g.setColor(Color.white);
		g.drawString(String.valueOf(level), x, y);
	}
	
	@Override
	public boolean execute() {
		if (dead) {
			return true;
		}
		for (AbilityInterface ability : abilities) {
			ability.on(this).doNewTurn();
		}
		Action a = controller.chooseNextAction();
		
		if (a instanceof ActionNoneYet) {
			return false;
		} else {
			a.execute(this);
			if (poisoned != 0) {
				room.game.events.add(new DamageEvent(null, null, this, new Damage(poisoned, Damage.Type.POISON), null));
			}
			return true;
		}
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}
	
	public int getCenterX() {
		return x * CELL_SIZE + CELL_SIZE / 2;
	}

	public int getCenterY() {
		return y * CELL_SIZE + CELL_SIZE / 2;
	}
	
	@Override
	public void cleanup() {
		for (AbilityInterface ability : abilities) {
			ability.remove(this);
		}
		abilities.clear();
		super.cleanup();
	}
}
