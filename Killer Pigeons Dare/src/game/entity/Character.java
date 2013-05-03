package game.entity;

import game.*;
import game.controller.UserController;

import org.newdawn.slick.*;

import java.util.TreeMap;

public class Character extends Actor {
	public TreeMap<Integer,Record> record = new TreeMap<Integer,Record>();
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
		attackSound = SoundLibrary.getSound("res/sound_effect_attack_character");
	}
	
	public void addXP (int xp) {
		totalXP += xp;
	}
	
	public int calcXP (int turns, int monsterCount, int totalMonsterLevels) {
		double n = .5 * (double)turns / (double)monsterCount;
		int xp = (int)(totalMonsterLevels * 5 / n);
		return xp;
	}
	
	public void refresh() {
		dead = false;
		hitpoints = maxHitpoints;
		poisoned = 0;
		noDraw = false;
	}
	
	public class Record {
		public int roomNumber;
		private Integer escapeTurn = null;
		private Integer killTurn = null;
		
		public Record (int roomNumber, Integer escapeTurn, Integer killTurn) {
			this.roomNumber = roomNumber;
			this.escapeTurn = escapeTurn;
			this.killTurn = killTurn;
		}

		public Integer getEscapeTurn() {
			return escapeTurn;
		}

		public Integer getKillTurn() {
			return killTurn;
		}

		public void set(Integer escapeTurn, Integer killTurn) {
			if (escapeTurn != null) { 
				if (this.escapeTurn == null) {
					this.escapeTurn = escapeTurn;
				} else if (escapeTurn < this.escapeTurn) {
					this.escapeTurn = escapeTurn;
				}
			}
			if (killTurn != null) { 
				if (this.killTurn == null) {
					this.killTurn = killTurn;
				} else if (killTurn < this.killTurn) {
					this.killTurn = killTurn;
				}
			}
		}
	}
}
