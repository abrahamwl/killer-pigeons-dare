package net.bithaven.efficiencyrpg;

import java.util.ArrayList;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.AbilityInterface;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;
import net.bithaven.efficiencyrpg.ui.UIElement;

import org.newdawn.slick.*;

public class InfoPanel extends UIElement {
	public class RoomListButton extends UIElement implements MouseListener {
		private static final String CAPTION = "Room List";
		private int width;
		private int height;
		
		public RoomListButton (Game game, int x) {
			super(game, x, Game.LEVEL_AREA_WIDTH - 10 - 2 * 14);
			game.gc.getInput().addMouseListener(this);
		}

		@Override
		public void draw(GameContainer gc, Graphics g) throws SlickException {
			width = g.getFont().getWidth(CAPTION) + 10;
			height = g.getFont().getLineHeight() + 10;
			x = (InfoPanel.this.width - width + 10) / 2;
			
			g.setColor(Color.lightGray);
			g.fillRoundRect(0, 0, width, height, 5);
			g.setColor(Color.blue);
			g.drawRoundRect(0, 0, width, height, 5);
			g.setColor(Color.black);
			g.drawString(CAPTION, 5, 5);
		}

		@Override
		public void process(GameContainer gc) throws SlickException {
		}

		@Override
		public void setInput(Input input) {
		}

		@Override
		public boolean isAcceptingInput() {
			return enabled;
		}

		public void mouseWheelMoved(int change) {
			// TODO Auto-generated method stub
			
		}

		public void mouseClicked(int button, int x, int y, int clickCount) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(int button, int mX, int mY) {
			if (button == Input.MOUSE_LEFT_BUTTON) {
				//System.out.println("Click: " + mX + ", " + mY + " Absolute: " + getAbsoluteX() + ", " + getAbsoluteY() + " Button: " + x + ", " + y + " InfoPanel: " + InfoPanel.this.x + ", " + InfoPanel.this.y);//DEBUG
				if (mX >= 0 && mX <= width && mY >= 0 && mY <= height) {
					layer.game.pushUILayer(new RoomList(layer));
				}
			}
		}

		public void mouseReleased(int button, int x, int y) {
			// TODO Auto-generated method stub
			
		}

		public void mouseMoved(int oldx, int oldy, int newx, int newy) {
			// TODO Auto-generated method stub
			
		}

		public void mouseDragged(int oldx, int oldy, int newx, int newy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getWidth(Graphics g) {
			return width;
		}

	}

	int width, height;
	Actor target = null;
	public static final Color BROWN = new Color(.7f, .4f, .2f);
	private RoomLayer layer;
	
	InfoPanel (RoomLayer layer, int x, int y, int width, int height) {
		super(layer.game, x, y);
		this.layer = layer;
		this.width = width;
		this.height = height;
		children.add(new RoomListButton(game, width / 2 - 50));
	}
	
	public void draw(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(BROWN);
		g.fillRoundRect(0, 0, width, height, 5);
		
		if (target != null) {
			target.renderAt(g, 5, 5);
			g.setColor(Color.black);
			g.drawString(target.name, 5 + Entity.CELL_SIZE, 5);
			
			int y = 10 + Entity.CELL_SIZE;
			
			y += target.abilities.size() * (Ability.ICON_SIZE);
			
			for (StatusEffect status : target.statusEffects.values()) {
				y += 14;
				g.setColor(status.effect.color);
				g.drawString(status.toString(), 5, y);
			}
		}
	}
	
	@Override
	public void process(GameContainer gc) throws SlickException {
		// Update the InfoPanel
		int x = gc.getInput().getMouseX() + this.x; // Adjust to the parent layer coordinates.
		int y = gc.getInput().getMouseY() + this.y; // Adjust to the parent layer coordinates.
		//System.out.println("Mouse: " + x + ", " + y + ", InfoPanel: " + this.x + ", " + this.y);//DEBUG
		ArrayList<Actor> actors = layer.room.entitiesAt(x / Entity.CELL_SIZE, y / Entity.CELL_SIZE, Actor.class);
		if (!actors.isEmpty()) {
			if (target != (Actor)actors.get(0)) {
				target = (Actor)actors.get(0);
				
				ArrayList<UIElement> remove = new ArrayList<UIElement>();
				for (UIElement child : children) {
					if (child instanceof Ability.DisplayElement) {
						remove.add(child);
					}
				}
				children.removeAll(remove);

				int lY = 10 + Entity.CELL_SIZE;
				
				for (AbilityInterface a : target.abilities) {
					//children.add(a.getDisplayElement(target, layer.game, 5, lY, target.x, target.y));
					children.add(a.getDisplayElement(target, layer.game, 5, lY));
					lY += Ability.ICON_SIZE;
				}
				
			}
		}

	}

	@Override
	public int getWidth(Graphics g) {
		return width;
	}

	public void clear() {
		target = null;

		ArrayList<UIElement> remove = new ArrayList<UIElement>();
		for (UIElement child : children) {
			if (child instanceof Ability.DisplayElement) {
				remove.add(child);
			}
		}
		children.removeAll(remove);
	}
}
