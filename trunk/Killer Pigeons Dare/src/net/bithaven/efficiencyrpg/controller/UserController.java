package net.bithaven.efficiencyrpg.controller;


import java.util.ArrayList;
import java.util.LinkedHashSet;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.InfoPanel;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ActivatedAbility;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.ui.UILayer;
import net.bithaven.efficiencyrpg.ui.UIElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class UserController extends BasicController {
	private ActionActivateAbility useAbility = null;
	
	public UserController (Character avatar) {
		a = avatar;
	}

	public Action chooseNextAction() {
		if (useAbility != null) {
			Action out = useAbility;
			useAbility = null;
			return out;
		}
		
		Input input = a.room.game.gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			ArrayList<ActivatedAbility> abilities = a.activeAbilities.getAll(ActivatedAbility.class);
			if (abilities.size() > 0) {
				int x = input.getMouseX();
				int y = input.getMouseY();

				int targetX = Integer.MIN_VALUE;
				int targetY = Integer.MIN_VALUE;				
				if (x >= 0 && x < 512 && y >= 0 && y < 512) {
					targetX = x / Entity.CELL_SIZE;
					targetY = y / Entity.CELL_SIZE;
				}
				
				ArrayList<ActivatedAbility> validAbilities = new ArrayList<ActivatedAbility>();
				for (ActivatedAbility ability : abilities) {
					if (ability.getStatusOf(a, targetX, targetY) != ActivatedAbility.Status.INVALID) {
						validAbilities.add(ability);
					}
				}
				if (validAbilities.size() > 0)
					a.room.game.pushUILayer(new AbilityMenu(a.room.game, validAbilities, x, y, targetX, targetY));
			}
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = input.getMouseX();
			int y = input.getMouseY();
			int hX = a.x  * Entity.CELL_SIZE;
			int hY = a.y  * Entity.CELL_SIZE;
			if (x > hX + Entity.CELL_SIZE) {
				if (y < hY) {
					return moveAttack(a.room, Dir.NORTH_EAST);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(a.room, Dir.SOUTH_EAST);
				} else {
					return moveAttack(a.room, Dir.EAST);
				}
			} else if (x < hX) {
				if (y < hY) {
					return moveAttack(a.room, Dir.NORTH_WEST);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(a.room, Dir.SOUTH_WEST);
				} else {
					return moveAttack(a.room, Dir.WEST);
				}
			} else {
				if (y < hY) {
					return moveAttack(a.room, Dir.NORTH);
				} else if (y > hY + Entity.CELL_SIZE) {
					return moveAttack(a.room, Dir.SOUTH);
				} else {
					return new ActionWait();
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_NUMPAD6)) {
			return moveAttack(a.room, Dir.EAST);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD3)) {
			return moveAttack(a.room, Dir.SOUTH_EAST);
		}
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_NUMPAD2)) {
			return moveAttack(a.room, Dir.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD1)) {
			return moveAttack(a.room, Dir.SOUTH_WEST);
		}
		if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_NUMPAD4)) {
			return moveAttack(a.room, Dir.WEST);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD7)) {
			return moveAttack(a.room, Dir.NORTH_WEST);
		}
		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_NUMPAD8)) {
			return moveAttack(a.room, Dir.NORTH);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD9)) {
			return moveAttack(a.room, Dir.NORTH_EAST);
		}
		if (input.isKeyPressed(Input.KEY_NUMPAD5)) {
			return new ActionWait();
		}
		
		//DEBUG
		if (System.getProperty("debug") != null) {
			if (input.isKeyPressed(Input.KEY_X)) {
				System.out.println("Adding 50 XP...");
				((Character)a).addXP(50);
				return ActionNoneYet.INSTANCE;
			}
		}
		
		return ActionNoneYet.INSTANCE;
	}
	
	private class AbilityMenu extends UILayer {
		int width = 0, height = 0;
		int targetX, targetY;
		ArrayList<ActivatedAbility> abilities;
		
		public AbilityMenu(Game game, ArrayList<ActivatedAbility> abilities, int x, int y, int targetX, int targetY) {
			super(game, x, y);
			this.targetX = targetX;
			this.targetY = targetY;
			this.abilities = abilities;
			int line = 2;
			for (ActivatedAbility ability : abilities) {
				children.add(ability.getDisplayElement(a, game, 2, line, targetX, targetY));
				line += Ability.ICON_SIZE;
			}
		}

		@Override
		public void draw(GameContainer gc, Graphics g) throws SlickException {
			g.setColor(InfoPanel.BROWN);
			g.fillRect(0, 0, getWidth(g), height);
			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth(g), height);
		}

		@Override
		public void process(GameContainer gc) throws SlickException {
			Input input = gc.getInput();
			
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				int x = input.getMouseX();
				int y = input.getMouseY();
				game.popUILayer();
				if (x >= 2 && x <= width - 2 && y >= 2 && y <= height - 2) {
					useAbility = new ActionActivateAbility(abilities.get((y - 2) / 32), targetX, targetY);
				}
			}
		}

		@Override
		public int getWidth(Graphics g) {
			if (width == 0) {
				Font f = g.getFont();
				height = children.size() * 32 + 4;
				for (UIElement ability : children) {
					width = Math.max(width, ability.getWidth(g) + 4);
				}
			}
			
			return width;
		}
		
	}
}
