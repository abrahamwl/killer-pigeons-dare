package game.effect;

import game.ui.UIElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Effect {
	public abstract void render(GameContainer gc, Graphics g)  throws SlickException;

	public abstract void update(GameContainer gc, int timePassed)  throws SlickException;

	public boolean allowGameLogicToContinue() {
		return true;
	}
}
