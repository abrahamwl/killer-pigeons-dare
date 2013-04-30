package game;

import java.util.ArrayList;

import game.entity.Actor;
import game.entity.Entity;
import game.ui.UIElement;

import org.newdawn.slick.*;

public class InfoPanel extends UIElement {
	public class RoomListButton extends UIElement implements MouseListener {
		private static final String CAPTION = "Room List";
		private int width;
		private int height;
		private int x;
		private int y;
		
		public RoomListButton (GameContainer gc) {
			gc.getInput().addMouseListener(this);
		}

		@Override
		public void draw(GameContainer gc, Graphics g) throws SlickException {
			width = g.getFont().getWidth(CAPTION);
			height = g.getFont().getLineHeight();
			x = 800 - (Game.MARGIN + width) / 2;
			y = 512 - 5 - 2 * 14;
			
			g.setColor(Color.lightGray);
			g.fillRoundRect(x - 5, y - 5, width + 10, height + 10, 5);
			g.setColor(Color.blue);
			g.drawRoundRect(x - 5, y - 5, width + 10, height + 10, 5);
			g.setColor(Color.black);
			g.drawString(CAPTION, x, y);
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

		@Override
		public void inputEnded() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inputStarted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseWheelMoved(int change) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(int button, int x, int y, int clickCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(int button, int mX, int mY) {
			if (button == Input.MOUSE_LEFT_BUTTON) {
				if (mX >= x - 5 && mX <= x + width + 10 && mY >= y - 5 && mY <= y + height + 10) {
					layer.game.pushUILayer(new RoomList(layer));
				}
			}
		}

		@Override
		public void mouseReleased(int button, int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(int oldx, int oldy, int newx, int newy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(int oldx, int oldy, int newx, int newy) {
			// TODO Auto-generated method stub
			
		}

	}

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
		children.add(new RoomListButton(layer.game.gc));
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
			if (!target.isDead()) {
				g.setColor(Color.green);
				g.fillRect(5, 65, 64 * target.getHitpoints() / target.getMaxHitpoints(), 3);
			}
			
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
				
				ArrayList<UIElement> remove = new ArrayList<UIElement>();
				for (UIElement child : children) {
					if (child instanceof Ability.Type.DisplayElement) {
						remove.add(child);
					}
				}
				children.removeAll(remove);

				int lY = 10 + Entity.CELL_SIZE;
				
				for (Ability a : target.abilities) {
					children.add(a.type.getDisplayElement(layer.game, this.x + 5, this.y + lY));
					lY += 14;
				}
				
			}
		}

	}
}
