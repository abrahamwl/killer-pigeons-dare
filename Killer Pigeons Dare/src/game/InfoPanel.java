package game;

import org.newdawn.slick.*;

public class InfoPanel {
	int x, y, width, height;
	Image panel = null;
	Graphics g;
	Actor target = null;
	private static final Color BROWN = new Color(.7f, .4f, .2f);
	
	InfoPanel (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (panel == null) {
			try {
				panel = new Image (width, height);
				g = panel.getGraphics();
				g.setColor(BROWN);
				g.fillRoundRect(0, 0, width, height, 5);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		g.flush();
		g.drawImage(panel, x, y);
	}
}
