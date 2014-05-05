package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.SoundLibrary;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.abilities.AbilityFireFriend;
import net.bithaven.efficiencyrpg.ability.abilities.AbilityFlying;
import net.bithaven.efficiencyrpg.ability.abilities.AbilityPoisonous;
import net.bithaven.efficiencyrpg.ability.abilities.AbilitySummonHellstone;
import net.bithaven.efficiencyrpg.ability.abilities.AbilityTough;
import net.bithaven.efficiencyrpg.controller.AttackController;
import net.bithaven.efficiencyrpg.controller.FlameoController2;

import org.newdawn.slick.Sound;

public class GenericEnemy extends Actor {
	public GenericEnemy (int level, String name, int waitRange, Class<? extends Ability> ability, String image, Sound attackSound) {
		super(name, level, image);
		this.controller = new AttackController(this, waitRange);
		if (ability != null) abilities.add(Ability.getAbility(ability));
		this.attackSound = attackSound;
	}
	
	public static GenericEnemy newEnemyFromCharacter (char c, int level) {
		GenericEnemy e;
		switch (c) {
		case 'G': return new GenericEnemy(level, "Golem", 0, AbilityTough.class, "res/open1/dc-mon/nonliving/clay_golem.png", SoundLibrary.getSound("res/sound_effect_attack_goblin"));
		case 'S': return new GenericEnemy(level, "Snake", 0, AbilityPoisonous.class, "res/open1/dc-mon/animals/snake.png", SoundLibrary.getSound("res/sound_effect_attack_snake"));
		case 'K': return new GenericEnemy(level, "Giant Fly", 0, AbilityFlying.class, "res/open1/dc-mon/animals/giant_blowfly.png", SoundLibrary.getSound("res/sound_effect_attack_killer_pidgeon"));
		case 'O': e =  new GenericEnemy(level, "Kobold", 2, null, "res/open1/dc-mon/kobold.png", null);
			e.gender = Gender.MALE;
			return e;
		case 'F': e = new GenericEnemy(level, "Flameo", 0, AbilityFireFriend.class, "res/open1/dc-mon/nonliving/fire_vortex.png", SoundLibrary.getSound("res/sound_effect_attack_flameo"));
			e.abilities.add(Ability.getAbility(AbilitySummonHellstone.class));
			e.controller = new FlameoController2(e);
			return e;
		default:
			return null;
		}
	}
}
