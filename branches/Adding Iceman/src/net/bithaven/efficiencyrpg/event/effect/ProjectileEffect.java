package net.bithaven.efficiencyrpg.event.effect;


import java.util.LinkedList;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class ProjectileEffect extends Effect {
	private static final float TRAVEL_SECONDS = .5f;
	int timePassed = 0;
	float x, y;
	float targetX, targetY;
	private float moveSpeedX;
	private float moveSpeedY;
	Image image = null;
	public LinkedList<Effect> fireEffects = new LinkedList<Effect>();
	public LinkedList<Effect> hitEffects = new LinkedList<Effect>();
	
	public ProjectileEffect(Image image, int x, int y, int targetX, int targetY) {
		this.image = image;
		this.x = x - Entity.CELL_SIZE / 2;
		this.y = y - Entity.CELL_SIZE / 2;
		this.targetX = targetX - Entity.CELL_SIZE / 2;
		this.targetY = targetY - Entity.CELL_SIZE / 2;
		moveSpeedX = (targetX - x) / (float)(Game.FPS * TRAVEL_SECONDS);
		moveSpeedY = (targetY - y) / (float)(Game.FPS * TRAVEL_SECONDS);
	}

	@Override
	public void render(Game game, Graphics g) throws SlickException {
		System.out.println("ProjectileEffect render. " + x + " " + y);//DEBUG
		x += moveSpeedX; 
		y += moveSpeedY; 
		image.draw(x, y);
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		if (this.timePassed == 0 && fireEffects.size() > 0) {
			game.events.addAll(fireEffects);
		}
		this.timePassed += timePassed;
		System.out.println(String.valueOf(timePassed));//DEBUG
		if (this.timePassed >= 1000 * TRAVEL_SECONDS && hitEffects.size() > 0) {
			game.events.addAll(hitEffects);
		}
	}

	@Override
	public EventState getMyEventState() {
		if (timePassed < 1000 * TRAVEL_SECONDS) return EventState.PREVENT_TURN;
		return EventState.DONE;
	}
}
