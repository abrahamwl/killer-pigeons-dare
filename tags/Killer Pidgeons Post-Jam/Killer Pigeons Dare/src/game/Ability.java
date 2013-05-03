package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.ui.UIElement;

public class Ability {
	
	public enum Type {
		TOUGH(true, "This character has 50% more hitpoints.", Game.iconSheet.getSubImage(9, 18)),
		BLOCK(true, "This character blocks the first melee attack against it in a turn.", Game.iconSheet.getSubImage(2, 12)),
		COUNTER_WAIT(false, "When this character waits, it will stop the first attack\nagainst it in a turn and counter-attack.", Game.iconSheet.getSubImage(5, 25)),
		FLYING(true, "This character can move over water.", Game.iconSheet.getSubImage(2, 16)),
		POISONOUS(true, "This character's attacks cause its level x 2 damage per turn.", Game.iconSheet.getSubImage(2, 24));
		
		protected boolean activeByDefault;
		public final String tooltip;
		public final Image icon;
		
		private Type(boolean activeByDefault, String toolTip, Image icon) {
			this.tooltip = toolTip;
			this.activeByDefault = activeByDefault;
			this.icon = icon;
		}
		
		public class DisplayElement extends UIElement {
			private int width = 0, height = 0;
			
			public int getWidth() {
				return width;
			}

			public int getHeight() {
				return height;
			}

			private DisplayElement (Game game, int x, int y) {
				super(game, x, y);
				height = icon.getHeight();
			}
			
			@Override
			public void draw(GameContainer gc, Graphics g)
					throws SlickException {
				g.drawImage(icon, 0, 0);
				g.setColor(Color.blue);
				String name = Type.this.toString();
				g.drawString(name, icon.getWidth() + 2, (icon.getHeight() - g.getFont().getLineHeight()) / 2);
				width = icon.getWidth() + 2 + g.getFont().getWidth(name);
			}

			@Override
			public void process(GameContainer gc) throws SlickException {
				int mX = gc.getInput().getMouseX();
				int mY = gc.getInput().getMouseY();
				
				if (mX >= 0 && mX <= width && mY >= 0 && mY <= height) {
					game.tooltip = Type.this.tooltip;				}
			}				
		}
		
		public DisplayElement getDisplayElement (Game game, int x, int y) {
			return new DisplayElement(game, x, y);
		}
	}

	public Type type;
	public int power = 1;
	public boolean active = true;
	
	public Ability (Type type) {
		this.type = type;
		active = type.activeByDefault;
		
	}
	
	public void reset() {
		active = type.activeByDefault;
	}
}