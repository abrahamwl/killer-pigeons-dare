package game.action;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import game.*;
import game.entity.*;
import game.entity.Character;

public class ActionMeleeAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Dir dir;

	static Sound soundEffectAttackSnake = null;
	static Sound soundEffectAttackGoblin = null;
	static Sound soundEffectAttackKillerPidgeon = null;
	static Sound soundEffectAttackCharacter= null;
	static Sound soundEffectAttackFlameo = null;
	
	{
		String extension = "ogg";
		File[] f = (new File("./res/")).listFiles(new RegexpFilter(".*aif"));
		if(f.length != 0) extension = "aif";
		
		try {
			soundEffectAttackSnake = new Sound("res/sound_effect_attack_snake." + extension);
			soundEffectAttackGoblin = new Sound("res/sound_effect_attack_goblin." + extension);
			soundEffectAttackKillerPidgeon = new Sound("res/sound_effect_attack_killer_pidgeon." + extension);
			soundEffectAttackCharacter= new Sound("res/sound_effect_attack_character." + extension);
			soundEffectAttackFlameo = new Sound("res/sound_effect_attack_flameo." + extension);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public ActionMeleeAttack(Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Room r, Actor a) {
		ArrayList<Entity> target = r.entitiesAt(a.x + dir.x, a.y + dir.y, Actor.class);

		if (target.size() > 0) {
			Actor victim = (Actor)(target.get(0));
			for (Ability ability : victim.abilities) {
				if (ability.active) {
					if (ability.type == Ability.Type.COUNTER_WAIT) {
						ability.active = false;
						new ActionMeleeAttack(dir.flip()).execute(r, victim);
						return;
					}
					if (ability.type == Ability.Type.BLOCK) {
						ability.active = false;
						return;
					}
				}
			}
			victim.applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
			
			if(a instanceof Snake) soundEffectAttackSnake.play();
			if(a instanceof Goblin) soundEffectAttackGoblin.play();
			if(a instanceof KillerPidgeon) soundEffectAttackKillerPidgeon.play();
			if(a instanceof Character) soundEffectAttackCharacter.play();
			if(a instanceof Flameo) soundEffectAttackFlameo.play();
		}
	}
}
