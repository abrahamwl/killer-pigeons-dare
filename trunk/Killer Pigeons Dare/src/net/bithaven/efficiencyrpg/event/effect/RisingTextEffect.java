package net.bithaven.efficiencyrpg.event.effect;


import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.util.PlainBag;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RisingTextEffect extends Effect {
	private static final PlainBag<RisingTextEffect> risingTexts = new PlainBag<RisingTextEffect>();
	private boolean activated = false;
	int timePassed = 0;
	int x, width, height;
	float y;
	private Entity follow = null;
	private int followX, followY;
	private static final float MOVE_SPEED = (float)Entity.CELL_SIZE / (float)Game.FPS;
	private String text;
	Image image = null;
	public Color color;
	
	public RisingTextEffect (String text, int x, int y) {
		this(text, x, y, Color.red);
	}

	public RisingTextEffect (String text, Entity follow) {
		this(text, follow, Color.red);
	}

	public RisingTextEffect (String text, int x, int y, Color color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public RisingTextEffect (String text, Entity follow, Color color) {
		this.text = text;
		this.follow = follow;
		this.x = follow.x * Entity.CELL_SIZE + Entity.CELL_SIZE / 2;
		this.y = follow.y * Entity.CELL_SIZE + Entity.CELL_SIZE / 2;
		followX = follow.x;
		followY = follow.y;
		this.color = color;
	}
	
	@Override
	public void render(Game game, Graphics g) throws SlickException {
		//System.out.println("RisingTextEffect render. " + x + " " + y);//DEBUG
		Font f = g.getFont();
		if (image == null) {
			width = f.getWidth(text);
			height = f.getHeight(text);
			image = new Image(width, height);
			Graphics iGraphics = image.getGraphics();
			iGraphics.setColor(color);
			iGraphics.drawString(text, 0, 0);
			x -= width / 2;
			y -= height / 2;
		}

		if (activated == false) {
			System.out.println("Checking risingTexts...");
			for (RisingTextEffect e : risingTexts) {
				System.out.println("Checking risingText...");
				if (follow != null && e.follow == follow) {
					y = Math.max(y, e.y + (height + e.height) / 2 + 2);
				} else if (followX == e.followX && followY == e.followY) {
					y = Math.max(y, e.y + (height + e.height) / 2 + 2);
				}
			}
			risingTexts.add(this);
			activated = true;
		}
		
		image.draw(x, y);
		y -= MOVE_SPEED;
	}
	
	public void setText (String text) {
		x += width / 2;
		y += height / 2;
		this.text = text;
		image = null;
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		this.timePassed += timePassed;
		if (follow != null) {
			if (!follow.noDraw) {
				x += Entity.CELL_SIZE * (follow.x - followX);
				y += Entity.CELL_SIZE * (follow.y - followY);
				followX = follow.x;
				followY = follow.y;
			}
		}
	}

	@Override
	public EventState getMyEventState() {
		if (timePassed < 100) return EventState.PREVENT_TURN;
		if (timePassed < 1000) return EventState.ALLOW_TURN;
		risingTexts.remove(this);
		return EventState.DONE;
	}
}
