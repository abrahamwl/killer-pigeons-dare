package net.bithaven.efficiencyrpg;

import java.util.LinkedList;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.AbilityInterface;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.ui.UILayer;

import org.apache.commons.lang3.RandomUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * UILayer that both handles and displays a character's level up, allowing the player to make the relevant choices.
 * @author Abe
 *
 */
public class LevelUp extends UILayer {
	private Character c;
	private int newLevel;
	private int levelUpStep = 0;
	
	static final int IMAGE_WIDTH = 350;
	static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
	static final int IMAGE_HEIGHT = 82;
	static final int OUTER_HEIGHT = 512 - 128;
	static final int OUTER_LEFT = (800 - OUTER_WIDTH) / 2;
	static final int LEFT = 5;
	static final int OUTER_TOP = 64;
	static final int TOP = 5;
	static final int LIST_TOP = TOP + IMAGE_HEIGHT + 4 * 14;
	
	static final Image IMAGE_LEVEL_UP;
	static {
		Image temp = null;
		try {
			temp = new Image("res/text_level_up.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		IMAGE_LEVEL_UP = temp;
	}

	int selectsLeft;
	LinkedList<Ability> options;
	
	public LevelUp(Game game) {
		super(game, OUTER_LEFT, OUTER_TOP);
		c = game.hero;
		
		newLevel = c.level;
		boolean addingLevels = true;
		while (addingLevels) {
			int oldXP = (((newLevel * newLevel) + newLevel) / 2 - 1) * 5;
			if (c.totalXP - oldXP >= (newLevel + 1) * 5) {
				newLevel += 1;
			} else {
				addingLevels = false;
			}
		}
					
		if (newLevel > c.level) {
			selectsLeft = newLevel / 3 - c.level / 3;
			levelUpStep = 1;
		}
	}

	public void draw(GameContainer gc, Graphics g) {
		if (levelUpStep > 0) {
			g.setColor(InfoPanel.BROWN);
			g.fillRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
			g.setColor(Color.lightGray);
			g.drawRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
			
			IMAGE_LEVEL_UP.draw(LEFT, TOP);
			
			int line = TOP + IMAGE_HEIGHT + 14;
			g.setColor(Color.black);
			g.drawString("Welcome to level " + String.valueOf(newLevel) + "!", LEFT, line);
			line += 14;
			
			if (levelUpStep == 2) {
				g.drawString("Please select " + String.valueOf(selectsLeft) + " new ability below.", LEFT, line);
			}
		}
	}

	public void process(GameContainer gc) {
		if (selectsLeft > 0) {
			if (options == null) {
				options = new LinkedList<Ability>();
				for (Ability a : Ability.abilityTypes) {
					if (a.allowed(c)) options.add(a);
				}
				options.removeAll(c.abilities);
				
				if (options.size() > 0) {
					while (options.size() > 3) {
						options.remove(RandomUtils.nextInt(0, options.size()));
					}
					
					//Removes unused indexes.
					/*LinkedList<Ability> newOptions = new LinkedList<Ability>();
					for (Ability option : options) {
						if (option != null) {
							newOptions.add(option);
						}
					}
					options = newOptions;*/
					
					levelUpStep = 2;
				}
			}
		}
		
		Input input = gc.getInput();
		if (levelUpStep == 2) {
			int mX = input.getMouseX();
			int mY = input.getMouseY();
			
			
			if (children.size() == 0) {
				int line = LIST_TOP;
				for (AbilityInterface ability : options) {
					Ability.DisplayElement e = ability.getDisplayElement(game, LEFT, line);
					children.add(e);
					line += e.getHeight();
				}
			}
			
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				//System.out.println("Mouse click at " + mX + ", " + mY); //DEBUG
				if (mX >= LEFT && mX <= LEFT + IMAGE_WIDTH && mY >= LIST_TOP && mY <= LIST_TOP + options.size() * Ability.ICON_SIZE) {
					int selection = (mY - LIST_TOP) / Ability.ICON_SIZE;
					selectsLeft--;
					c.abilities.add(options.get(selection));
					levelUpStep = 1;
					children.clear();
					options = null;
				}
			}
		} else if (levelUpStep == 1) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				levelUpStep = 0;
				c.level = newLevel;
			}
		} else {
			game.popUILayer();
		}
	}

	@Override
	public int getWidth(Graphics g) {
		return OUTER_WIDTH;
	}
}
