package game;

import java.util.ArrayList;

import game.entity.Actor;
import game.entity.Entity;
import game.ui.UIElement;

import org.newdawn.slick.*;

public class InfoPanel extends UIElement {
	int x, y, width, height;
	Actor target = null;
	public static final Color BROWN = new Color(.7f, .4f, .2f);
	private RoomLayer layer;
	
	InfoPanel (RoomLayer layer, int x, int y, int width, int height) {
		this.layer = layer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(GameContainer gc, Graphics g) throws SlickException {
		g.translate(x, y);
		g.setColor(BROWN);
		g.fillRoundRect(0, 0, width, height, 5);
		
		if (target != null) {
			g.drawImage(target.image, 5, 5);
			g.setColor(Color.black);
			g.drawString(target.name, 10 + Entity.CELL_SIZE, 5);
			g.setColor(Color.red);
			g.fillRect(5, 65, 64, 3);
			g.setColor(Color.green);
			g.fillRect(5, 65, 64 * target.getHitpoints() / target.getMaxHitpoints(), 3);
			
			g.setColor(Color.white);
			g.drawString(String.valueOf(target.getLevel()), 5, 5);
			
			int y = 10 + Entity.CELL_SIZE;
			
			y += target.abilities.size() * 14;
			
			if (target.poisoned > 0) {
				y += 14;
				g.setColor(Color.green);
				g.drawString(target.poisoned + " POISON", 5, y);
			}
		}
		g.translate(-x, -y);
	}
	
	@Override
	public void process(GameContainer gc) throws SlickException {
		// Update the InfoPanel
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		ArrayList<Entity> actors = layer.room.entitiesAt(x / Entity.CELL_SIZE, y / Entity.CELL_SIZE, Actor.class);
		if (!actors.isEmpty()) {
			if (target != (Actor)actors.get(0)) {
				target = (Actor)actors.get(0);
				
				children.clear();

				int lY = 10 + Entity.CELL_SIZE;
				
				for (Ability a : target.abilities) {
					children.add(a.type.getDisplayElement(layer.game, this.x + 5, this.y + lY));
					lY += 14;
				}
				
			}
		}

	}
}
