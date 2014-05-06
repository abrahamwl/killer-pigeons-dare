package net.bithaven.efficiencyrpg;

import org.newdawn.slick.Graphics;

public final class Util {
	private Util () {
	}

	public static final void drawStringCentered(Graphics g, String string, int x, int y) {
		int width = g.getFont().getWidth(string);
		g.drawString(string, x - width / 2, y);
	}
}
