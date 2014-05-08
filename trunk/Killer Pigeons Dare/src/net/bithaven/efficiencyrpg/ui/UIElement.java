package net.bithaven.efficiencyrpg.ui;


import java.util.ArrayList;

import net.bithaven.efficiencyrpg.Game;

import org.newdawn.slick.ControlledInputReciever;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class UIElement implements ControlledInputReciever {
	protected boolean enabled = false;
	
	private UIElement parent = null;
	protected int x = 0, y = 0;
	
	protected int getAbsoluteX() {
		if (parent != null) {
			return parent.getAbsoluteX() + x;
		} else {
			return x;
		}
	}
	
	protected int getAbsoluteY() {
		if (parent != null) {
			return parent.getAbsoluteY() + y;
		} else {
			return y;
		}
	}

	public abstract int getWidth(Graphics g);
	
	protected Game game;
	
	public UIElement(Game game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for (UIElement child : children) {
			child.setEnabled(enabled);
		}
	}
	
	private void addEffects(UIElement e) {
		e.parent = this;
		e.setEnabled(enabled);
	}
	
	public class Children extends ArrayList<UIElement> {
		private static final long serialVersionUID = -2197783254691759930L;

		@Override
		public void add(int index, UIElement element) {
			addEffects(element);
			super.add(index, element);
		}
		
		@Override
		public boolean add(UIElement e) {
			addEffects(e);
			return super.add(e);
		}
		
		@Override
		public UIElement set(int index, UIElement element) {
			addEffects(element);
			return super.set(index, element);
		}
	}

	public final Children children = new Children();

	public final void render(GameContainer gc, Graphics g)  throws SlickException {
		g.translate(x, y);
		draw(gc, g);
		
		for (UIElement child : children) {
			child.render(gc, g);
		}
		g.translate(-x, -y);
	}

	public final void update(GameContainer gc)  throws SlickException {
		for (UIElement child : children) {
			child.update(gc);
		}

		Input input = gc.getInput();
		input.setOffset(-getAbsoluteX(), -getAbsoluteY());
		process(gc);
		if (parent == null) {
			input.setOffset(0, 0);
		} else {
			input.setOffset(-parent.getAbsoluteX(), -parent.getAbsoluteY());
		}
	}

	/**
	 * Should be overridden to provide UIElement's equivalent to Slick2D's render() method. UIElement.render()
	 * calls UIElement.draw() after making some adjustments to allow draw to function relative to the UIElement's
	 * position.
	 * @param gc Should be passed from render().
	 * @param g Should be passed from render().
	 * @throws SlickException
	 */
	public abstract void draw(GameContainer gc, Graphics g) throws SlickException;

	public abstract void process(GameContainer gc) throws SlickException;

	public void inputEnded() {
		game.gc.getInput().setOffset(0, 0);
	}

	public void inputStarted() {
		game.gc.getInput().setOffset(-getAbsoluteX(), -getAbsoluteY());
	}
	
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}
}
