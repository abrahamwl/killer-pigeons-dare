package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.util.ImageLibrary;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Door extends Entity {
	private static final Image CLOSED_IMAGE = ImageLibrary.load("res/open1/dc-dngn/dngn_closed_door.png");
	private static final Image OPEN_IMAGE = ImageLibrary.load("res/open1/dc-dngn/dngn_open_door.png");
	public int roomNumber = 1;
	//private Image rnImage = null;
	private int distanceFromCharacterStart;
	
	public int getDistanceFromCharacterStart() {
		return distanceFromCharacterStart;
	}

	public Door (int roomNumber) {
		super("Door", CLOSED_IMAGE, EnumSet.range(Layer.GROUND, Layer.THING));
		this.roomNumber = roomNumber;
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
		distanceFromCharacterStart = (int)Math.max(Math.abs(x - r.game.hero.x), Math.abs(y - r.game.hero.y));
	}

	@Override
	public boolean isDestructible () {
		return false;
	}

	@Override
	public void render (GameContainer gc, Graphics g) {
		super.render(gc, g);
		
		if (image == CLOSED_IMAGE && ((Character)room.allEntitiesOfType(Character.class).get(0)).record.get(roomNumber) != null) {
			image = OPEN_IMAGE;
		}

		/*if (rnImage == null) {
			Font f = g.getFont();
			String text = Integer.toString(roomNumber);
			try {
				rnImage = new Image(f.getWidth(text), f.getHeight(text));
				Graphics iGraphics = rnImage.getGraphics();
				Color color;
				if (((Character)room.allEntitiesOfType(Character.class).get(0)).record.get(roomNumber) == null) {
					color = Color.red;
				} else {
					color = Color.blue;
				}
				iGraphics.setColor(color);
				iGraphics.drawString(text, 0, 0);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rnImage.draw(x * CELL_SIZE, y * CELL_SIZE);*/
	}
}
