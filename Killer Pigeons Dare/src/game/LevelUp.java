package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.entity.Character;
import game.ui.UILayer;

public class LevelUp extends UILayer {
	private Character c;
	private Game game;
	private int newLevel;
	private int levelUpStep = 0;
	
	static final int IMAGE_WIDTH = 350;
	static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
	static final int IMAGE_HEIGHT = 82;
	static final int OUTER_HEIGHT = 512 - 128;
	static final int OUTER_LEFT = (800 - IMAGE_WIDTH) / 2;
	static final int LEFT = OUTER_LEFT + 5;
	static final int OUTER_TOP = 64;
	static final int TOP = OUTER_TOP + 5;
	static final int LIST_TOP = TOP + IMAGE_HEIGHT + 4 * 14;
	
	static final Image IMAGE_LEVEL_UP;
	static {
		Image temp = null;
		try {
			temp = new Image("res/text_level_up.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMAGE_LEVEL_UP = temp;
	}

	int selectsLeft;
	ArrayList<Ability.Type> options;
	
	public void draw(GameContainer gc, Graphics g) {
		if (levelUpStep == 1) {
			options = new ArrayList<Ability.Type>();
			for (Ability.Type type : Ability.Type.values()) {
				if (c.getAbility(type) == null) {
					options.add(type);
				}
			}
			if (options.size() == 0) {
				levelUpStep = 0;
				c.level = newLevel;
				return;
			} else {
				levelUpStep = 2;
			}
		} else if (levelUpStep == 2) {
			
			g.setColor(InfoPanel.BROWN);
			g.fillRoundRect(OUTER_LEFT, OUTER_TOP, OUTER_WIDTH, OUTER_HEIGHT, 5);
			g.setColor(Color.lightGray);
			g.drawRoundRect(OUTER_LEFT, OUTER_TOP, OUTER_WIDTH, OUTER_HEIGHT, 5);
			
			IMAGE_LEVEL_UP.draw(LEFT, TOP);
			
			int line = TOP + IMAGE_HEIGHT + 14;
			g.setColor(Color.black);
			g.drawString("Welcome to level " + String.valueOf(newLevel) + "!", LEFT, line);
			line += 14;

			if (selectsLeft > 0) {
				g.drawString("Please select " + String.valueOf(selectsLeft) + " new ability below.", LEFT, line);
				line += 14;
			
				line = LIST_TOP;
				for (Ability.Type type : options) {
					if (c.getAbility(type) == null) {
						g.setColor(Color.blue);
						g.drawString(type.toString(), LEFT, line);
						line += 14;
					}
				}
			}
				
			return;
		}
	}

	public void process(GameContainer gc) {
		if (levelUpStep == 2) {
			Input input = gc.getInput();
			int mX = input.getMouseX();
			int mY = input.getMouseY(); 
			
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (selectsLeft > 0) {
					if (mX >= LEFT && mX <= LEFT + 800 - 256 - 5 && mY >= LIST_TOP && mY <= LIST_TOP + options.size() * 14) {
						int selection = (mY - LIST_TOP) / 14;
						selectsLeft--;
						c.abilities.add(new Ability(options.get(selection)));
						c.maxHitpoints = (int)(newLevel * 10.0 * (c.getAbility(Ability.Type.TOUGH) == null ? 1.0 : 1.5));
						c.hitpoints = c.maxHitpoints;
						levelUpStep = 1;
					}
				} else {
					levelUpStep = 0;
					c.level = newLevel;
				}
			}
		}
		if (levelUpStep == 0) {
			game.popUILayer();
		}
	}
	
	public LevelUp(Game game) {
		this.game = game;
		c = game.hero;
		
		newLevel = c.level;
		boolean addingLevels = true;
		while (addingLevels) {
			int oldXP = (((newLevel * newLevel) + newLevel) / 2) * 5;
			if (c.totalXP - oldXP >= (newLevel + 1) * 5) {
				newLevel = c.level + 1;
			} else {
				addingLevels = false;
			}
		}
					
		if (newLevel > c.level) {
			c.maxHitpoints = (int)(newLevel * 10.0 * (c.getAbility(Ability.Type.TOUGH) == null ? 1.0 : 1.5));
			c.hitpoints = c.maxHitpoints;
			
			selectsLeft = newLevel / 3 - c.level / 3;
			levelUpStep = 1;
		}
	}
}
