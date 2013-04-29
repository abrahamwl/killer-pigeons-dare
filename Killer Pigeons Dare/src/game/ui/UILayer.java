package game.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class UILayer extends UIElement {

	@Override
	public abstract void draw(GameContainer gc, Graphics g) throws SlickException;

	@Override
	public abstract void process(GameContainer gc) throws SlickException;
}
