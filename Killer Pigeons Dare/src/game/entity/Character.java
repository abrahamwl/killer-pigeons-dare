package game.entity;

import game.Actor;
import game.UserController;

import org.newdawn.slick.*;

public class Character extends Actor {
	int totalXP = 0;
	
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
	
	void addXP (int turns, int monsterCount, int totalMonsterLevels) {
		double n = 2.0 * (double)turns / (double)monsterCount;
		totalXP += (int)(totalMonsterLevels * 5 / n);
	}
}
