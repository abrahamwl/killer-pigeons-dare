package game.entity;

import java.util.ArrayList;
import game.*;
import game.controller.UserController;

import org.newdawn.slick.*;

public class Character extends Actor {
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
	
	int totalXP = 5;
	
	private int newLevel;
	private int levelUpStep = 0;
	
	private AbilitySelect select;
	
	public Character () {
		super("Hero", 1);
		this.controller = new UserController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int addXP (int turns, int monsterCount, int totalMonsterLevels) {
		double n = .5 * (double)turns / (double)monsterCount;
		int xp = (int)(totalMonsterLevels * 5 / n);
		totalXP += xp;
		return xp;
	}
	
	public boolean doLevelUp(GameContainer gc) {
		if (levelUpStep == 0) {
			newLevel = level;
			boolean addingLevels = true;
			while (addingLevels) {
				int oldXP = (((newLevel * newLevel) + newLevel) / 2) * 5;
				if (totalXP - oldXP >= (newLevel + 1) * 5) {
					newLevel = level + 1;
				} else {
					addingLevels = false;
				}
			}
						
			if (newLevel > level) {
				maxHitpoints = (int)(newLevel * 10.0 * (getAbility(Ability.Type.TOUGH) == null ? 1.0 : 1.5));
				hitpoints = maxHitpoints;
				
				select = new AbilitySelect(newLevel / 3 - level / 3);
				levelUpStep = 1;
				return false;
			} else {
				return true;
			}
		} else {
			select.update(gc);
			return false;
		}
	}
	
	public void deferredRender (GameContainer gc, Graphics g) {
		if (levelUpStep > 0) {
			System.out.println("Render"); // DEBUG
			select.render(gc, g);
		}
	}
	
	class AbilitySelect {
		static final int IMAGE_WIDTH = 350;
		static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
		static final int IMAGE_HEIGHT = 82;
		static final int OUTER_HEIGHT = 512 - 128;
		static final int OUTER_LEFT = (800 - IMAGE_WIDTH) / 2;
		static final int LEFT = OUTER_LEFT + 5;
		static final int OUTER_TOP = 64;
		static final int TOP = OUTER_TOP + 5;
		static final int LIST_TOP = TOP + IMAGE_HEIGHT + 4 * 14;
		
		int selectsLeft;
		ArrayList<Ability.Type> options;
		
		AbilitySelect (int selects) {
			selectsLeft = selects;
		}

		public void render(GameContainer gc, Graphics g) {
			if (levelUpStep == 1) {
				options = new ArrayList<Ability.Type>();
				for (Ability.Type type : Ability.Type.values()) {
					if (getAbility(type) == null) {
						options.add(type);
					}
				}
				if (options.size() == 0) {
					levelUpStep = 0;
					level = newLevel;
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
						if (getAbility(type) == null) {
							g.setColor(Color.blue);
							g.drawString(type.toString(), LEFT, line);
							line += 14;
						}
					}
				}
					
				return;
			}
		}

		public void update(GameContainer gc) {
			if (levelUpStep == 2) {
				Input input = gc.getInput();
				int mX = input.getMouseX();
				int mY = input.getMouseY(); 
				
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					if (selectsLeft > 0) {
						if (mX >= LEFT && mX <= LEFT + 800 - 256 - 5 && mY >= LIST_TOP && mY <= LIST_TOP + options.size() * 14) {
							int selection = (mY - LIST_TOP) / 14;
							selectsLeft--;
							abilities.add(new Ability(options.get(selection)));
							levelUpStep = 1;
						}
					} else {
						levelUpStep = 0;
						level = newLevel;
					}
				}
			}
		}
		
	}

	public void refresh() {
		dead = false;
		hitpoints = maxHitpoints;
		poisoned = 0;
		noDraw = false;
	}
}
