package game.entity;

import game.Ability;
import game.SoundLibrary;
import game.controller.AttackController;
import game.controller.FlameoController;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Sound;

public class GenericEnemy extends Actor {
	public GenericEnemy (int level, String name, int waitRange, Ability.Type ability, int spriteX, int spriteY, Sound attackSound) {
		super(name, level);
		this.controller = new AttackController(this, waitRange);
		if (ability != null) abilities.add(new Ability(ability));
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(spriteX, spriteY);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.attackSound = attackSound;
	}
	
	public static GenericEnemy newEnemyFromCharacter (char c, int level) {
		switch (c) {
		case 'G': return new GenericEnemy(level, "Golem", 0, Ability.Type.TOUGH, 2, 0, SoundLibrary.getSound("res/sound_effect_attack_goblin"));
		case 'S': return new GenericEnemy(level, "Snake", 0, Ability.Type.POISONOUS, 7, 0, SoundLibrary.getSound("res/sound_effect_attack_snake"));
		case 'K': return new GenericEnemy(level, "Killer Pidgeon", 0, Ability.Type.FLYING, 4, 0, SoundLibrary.getSound("res/sound_effect_killer_pidgeon"));
		case 'O': return new GenericEnemy(level, "Goblin", 2, null, 8, 5, null);
		case 'F': GenericEnemy e = new GenericEnemy(level, "Flameo", 0, null, 6, 0, SoundLibrary.getSound("res/sound_effect_attack_flameo"));
			e.controller = new FlameoController(e);
			return e;
		default:
			return null;
		}
	}
}
