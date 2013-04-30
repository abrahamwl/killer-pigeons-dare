package game.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class UIElement {
	protected boolean enabled = false;
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for (UIElement child : children) {
			child.setEnabled(enabled);
		}
	}
	
	public class Children extends ArrayList<UIElement> {
		@Override
		public void add(int index, UIElement element) {
			element.setEnabled(enabled);
			super.add(index, element);
		}
		
		@Override
		public boolean add(UIElement e) {
			e.setEnabled(enabled);
			return super.add(e);
		}
		
		@Override
		public UIElement set(int index, UIElement element) {
			element.setEnabled(enabled);
			return super.set(index, element);
		}
	}

	public final Children children = new Children();

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
