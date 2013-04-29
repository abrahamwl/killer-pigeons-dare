package game.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class UIElement {
	public final ArrayList<UIElement> children = new ArrayList<UIElement>();

	public final void render(GameContainer gc, Graphics g)  throws SlickException {
		draw(gc, g);
		
		for (UIElement child : children) {
			child.render(gc, g);
		}
	}

	public final void update(GameContainer gc)  throws SlickException {
		for (UIElement child : children) {
			child.update(gc);
		}

		process(gc);		
	}

	public abstract void draw(GameContainer gc, Graphics g) throws SlickException;

	public abstract void process(GameContainer gc) throws SlickException;
}
