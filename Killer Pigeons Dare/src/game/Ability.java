package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.ui.UIElement;

public class Ability {
	
	public enum Type {
		TOUGH(true, "This character has 50% more hitpoints."),
		BLOCK(true, "This character blocks the first melee attack against it in a turn."),
		COUNTER_WAIT(false, "When this character waits, it will stop the first attack\nagainst it in a turn and counter-attack."),
		FLYING(true, "This character can move over water."),
		POISONOUS(true, "This character's attacks cause its level x 2 damage per turn.");
		
		protected boolean activeByDefault;
		public final String tooltip;
		
		private Type(boolean activeByDefault, String toolTip) {
			this.tooltip = toolTip;
			this.activeByDefault = activeByDefault;
		}
		
		public class DisplayElement extends UIElement {
			private int width = 0, height = 0;
			
			private DisplayElement (Game game, int x, int y) {
				super(game, x, y);
			}
			
			@Override
			public void draw(GameContainer gc, Graphics g)
					throws SlickException {
				g.setColor(Color.blue);
				String name = Type.this.toString();
				g.drawString(name, 0, 0);
				width = g.getFont().getWidth(name);
				height = g.getFont().getLineHeight();
			}

			@Override
			public void process(GameContainer gc) throws SlickException {
				int mX = gc.getInput().getMouseX();
				int mY = gc.getInput().getMouseY();
				
				if (mX >= 0 && mX <= width && mY >= 0 && mY <= height) {
					game.tooltip = Type.this.tooltip;				}
			}				
		}
		
		public UIElement getDisplayElement (Game game, int x, int y) {
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