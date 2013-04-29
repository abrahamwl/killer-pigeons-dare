package game;

import org.newdawn.slick.*;

public class InfoPanel {
	int x, y, width, height;
	Image panel = null;
	Graphics g;
	Actor target = null;
	public static final Color BROWN = new Color(.7f, .4f, .2f);
	private boolean redraw = true;
	
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
		
		if (redraw) {
			g = panel.getGraphics();
			g.setColor(BROWN);
			g.fillRoundRect(0, 0, width, height, 5);
			
			if (target != null) {
				g.drawImage(target.image, 5, 5);
				g.setColor(Color.black);
				g.drawString(target.name, 10 + Entity.CELL_SIZE, 5);
				g.setColor(Color.red);
				g.fillRect(5, 65, 64, 3);
				g.setColor(Color.green);
				g.fillRect(5, 65, 64 * target.getHitpoints() / target.getMaxHitpoints(), 3);
				
				g.setColor(Color.white);
				g.drawString(String.valueOf(target.getLevel()), 5, 5);
				
				int y = 10 + Entity.CELL_SIZE;
				
				g.setColor(Color.blue);
				for (Ability a : target.abilities) {
					g.drawString(a.type.toString(), 5, y);
					y += 14;
				}
				
				if (target.poisoned > 0) {
					y += 14;
					g.setColor(Color.green);
					g.drawString(target.poisoned + " POISON", 5, y);
				}
			}
			
			redraw = false;
		}
		
		g.flush();
		g.drawImage(panel, x, y);
	}
	
	public void triggerRedraw() {
		redraw = true;
	}
}
