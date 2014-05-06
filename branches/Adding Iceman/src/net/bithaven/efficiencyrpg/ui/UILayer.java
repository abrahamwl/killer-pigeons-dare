package net.bithaven.efficiencyrpg.ui;


import net.bithaven.efficiencyrpg.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class UILayer extends UIElement {

	public UILayer(Game game, int x, int y) {
		super(game, x, y);
	}

	@Override
	public abstract void draw(GameContainer gc, Graphics g) throws SlickException;

	@Override
	public abstract void process(GameContainer gc) throws SlickException;
}
