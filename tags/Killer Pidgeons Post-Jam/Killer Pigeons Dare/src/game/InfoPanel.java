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
		
		public RoomListButton (Game game, int x) {
			super(game, x, 512 - 10 - 2 * 14);
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
				//System.out.println("Click: " + mX + ", " + mY + " Absolute: " + getAbsoluteX() + ", " + getAbsoluteY() + " Button: " + x + ", " + y + " InfoPanel: " + InfoPanel.this.x + ", " + InfoPanel.this.y);//DEBUG
				if (mX >= 0 && mX <= width && mY >= 0 && mY <= height) {
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
	}
	
	@Override
	public void process(GameContainer gc) throws SlickException {
		// Update the InfoPanel
		int x = gc.getInput().getMouseX() + this.x; // Adjust to the parent layer coordinates.
		int y = gc.getInput().getMouseY() + this.y; // Adjust to the parent layer coordinates.
		//System.out.println("Mouse: " + x + ", " + y + ", InfoPanel: " + this.x + ", " + this.y);//DEBUG
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
					children.add(a.type.getDisplayElement(layer.game, 5, lY));
					lY += 14;
				}
				
			}
		}

	}
}