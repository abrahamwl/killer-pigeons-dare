package game.ui;

import game.Game;

import java.util.ArrayList;

import org.newdawn.slick.ControlledInputReciever;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class UIElement implements ControlledInputReciever {
	protected boolean enabled = false;
	
	protected int parentX = 0, parentY = 0;
	protected int x = 0, y = 0;
	
	protected Game game;
	
	public UIElement(Game game, int x, int y) {
		super();
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
		e.parentX = parentX + x;
		e.parentY = parentY + y;
		e.setEnabled(enabled);
	}
	
	public class Children extends ArrayList<UIElement> {
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
		Input input = gc.getInput();
		input.setOffset(-(parentX + x), -(parentY + y));
		for (UIElement child : children) {
			child.update(gc);
		}

		process(gc);
		input.setOffset(-parentX, -parentY);
	}

	public abstract void draw(GameContainer gc, Graphics g) throws SlickException;

	public abstract void process(GameContainer gc) throws SlickException;

	@Override
	public void inputEnded() {
		game.gc.getInput().setOffset(0, 0);
	}

	@Override
	public void inputStarted() {
		game.gc.getInput().setOffset(-(parentX + x), -(parentY + y));
	}
	
	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}
}
