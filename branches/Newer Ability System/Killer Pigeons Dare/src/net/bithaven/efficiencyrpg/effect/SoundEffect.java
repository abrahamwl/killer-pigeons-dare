package net.bithaven.efficiencyrpg.effect;

import net.bithaven.efficiencyrpg.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundEffect extends Effect {
	public final Sound sound;

	public SoundEffect(Sound sound) {
		this.sound = sound;
	}

	@Override
	public void render(Game game, Graphics g) throws SlickException {
		return;
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		if (sound != null){
			if (!sound.playing()) sound.play();
		}
	}

	@Override
	public LogicStep getMyLogicStep() {
		return LogicStep.DONE;
	}
}