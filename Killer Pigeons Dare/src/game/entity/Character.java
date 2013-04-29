package game.entity;

import java.util.ArrayList;
import java.util.EnumSet;

import game.*;

import org.newdawn.slick.*;

public class Character extends Actor {
	int totalXP = 0;
	
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
	
	public void addXP (int turns, int monsterCount, int totalMonsterLevels) {
		double n = 2.0 * (double)turns / (double)monsterCount;
		totalXP += (int)(totalMonsterLevels * 5 / n);
	}
	
	public boolean doLevelUp(GameContainer gc) {
		if (levelUpStep == 0) {
			newLevel = (totalXP / 5);
			newLevel = (((newLevel * newLevel) + newLevel) / 2) + 1;
						
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
		static final int LEFT = 128 + 5;
		static final int TOP = 64 + 5;
		static final int LIST_TOP = TOP + 3 * 14;
		
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
				int line = TOP;
				
				g.setColor(InfoPanel.BROWN);
				g.fillRoundRect(128, 64, 800 - 256, 512 - 128, 5);
				g.setColor(Color.lightGray);
				g.drawRoundRect(128, 64, 800 - 256, 512 - 128, 5);
				
				g.setColor(Color.black);
				g.drawString("You have leveled up!", LEFT, TOP);
				line += 14;
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
		noDraw = false;
	}
}
