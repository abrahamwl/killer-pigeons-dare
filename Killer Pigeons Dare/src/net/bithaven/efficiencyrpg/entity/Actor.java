package net.bithaven.efficiencyrpg.entity;

import java.util.EnumMap;
import java.util.Set;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.AbilityInterface;
import net.bithaven.efficiencyrpg.ability.AbilityList;
import net.bithaven.efficiencyrpg.ability.HandlesDamaged;
import net.bithaven.efficiencyrpg.ability.TriggersAfterInit;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionNoneYet;
import net.bithaven.efficiencyrpg.controller.Controller;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;


/**
 * The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
 * and other NPCs.
 * 
 * @author Abe
 *
 */
public class Actor extends Entity {
	public Controller controller;
	
	public Sound attackSound = null;
	
	public Damage.Type defaultMeleeDamageType = Damage.Type.IMPACT;
	
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
	
	public EnumMap<StatusEffect.Effect,StatusEffect> statusEffects = new EnumMap<StatusEffect.Effect,StatusEffect>(StatusEffect.Effect.class);
	
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
		HandlesDamaged handler = activeAbilities.getFirst(HandlesDamaged.class);
		if (handler != null) {
			damage = handler.modifyDamage(damage);
		}
		
		if (damage.amount < 0) {
			damage = new Damage(-applyHealth(-damage.amount), damage.type);
		} else {
			hitpoints -= damage.amount;
	
			if (hitpoints <= 0) {
				kill();
			}
		}
		
		return damage;
	}
	
	public int applyHealth(int health) {
		health = Math.min(health, maxHitpoints - hitpoints);
		hitpoints += health;
		return health;
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
		dead = false;
		statusEffects.clear();
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
		disable();
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
			for (StatusEffect status : statusEffects.values()) {
				status.execute();
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
