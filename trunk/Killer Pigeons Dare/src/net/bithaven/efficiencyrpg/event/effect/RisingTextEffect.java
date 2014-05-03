package net.bithaven.efficiencyrpg.event.effect;


import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.entity.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RisingTextEffect extends Effect {
	int timePassed = 0;
	int x, width, height;
	float y;
	private static final float MOVE_SPEED = (float)Entity.CELL_SIZE / (float)Game.FPS;
	final String text;
	Image image = null;
	Color color;
	
	public RisingTextEffect (String text, int x, int y) {
		this(text, x, y, Color.red);
	}

	public RisingTextEffect (String text, int x, int y, Color color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public void render(Game game, Graphics g) throws SlickException {
		//System.out.println("RisingTextEffect render. " + x + " " + y);//DEBUG
		Font f = g.getFont();
		if (image == null) {
			width = f.getWidth(text);
			height = f.getHeight(text);
			image = new Image(width, f.getHeight(text));
			Graphics iGraphics = image.getGraphics();
			iGraphics.setColor(color);
			iGraphics.drawString(text, 0, 0);
			x -= width / 2;
			y -= height / 2;
		}
		
		image.draw(x, y);
		y -= MOVE_SPEED; 
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		this.timePassed += timePassed;
	}

	@Override
	public EventState getMyEventState() {
		if (timePassed < 100) return EventState.PREVENT_TURN;
		if (timePassed < 1000) return EventState.ALLOW_TURN;
		return EventState.DONE;
	}
}
