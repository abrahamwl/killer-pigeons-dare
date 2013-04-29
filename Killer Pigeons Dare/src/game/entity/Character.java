package game.entity;

import game.*;
import game.controller.UserController;

import org.newdawn.slick.*;

public class Character extends Actor {
	
	public int totalXP = 5;
	
	public Character () {
		super("Hero", 1);
		this.controller = new UserController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int addXP (int turns, int monsterCount, int totalMonsterLevels) {
		double n = .5 * (double)turns / (double)monsterCount;
		int xp = (int)(totalMonsterLevels * 5 / n);
		totalXP += xp;
		return xp;
	}
	
	public void refresh() {
		dead = false;
		hitpoints = maxHitpoints;
		poisoned = 0;
		noDraw = false;
	}
}
