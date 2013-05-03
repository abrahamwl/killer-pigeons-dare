package net.bithaven.efficiencyrpg;

import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.ui.UILayer;

import org.newdawn.slick.*;



class RoomList extends UILayer {
	static final int IMAGE_WIDTH = 350;
	static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
	static final int IMAGE_HEIGHT = 82;
	static final int OUTER_HEIGHT = IMAGE_HEIGHT + 4 * 14 + 10;
	static final int OUTER_LEFT = (800 - IMAGE_WIDTH) / 2;
	static final int LEFT = 5;
	static final int OUTER_TOP = 64;
	static final int TOP = 5;
	
	private RoomLayer rLayer;
	private int listTop;
	
	RoomList (RoomLayer rLayer) {
		super(rLayer.game, OUTER_LEFT, OUTER_TOP);
		this.rLayer = rLayer;
	}

	public void draw(GameContainer gc, Graphics g) {
		g.setColor(InfoPanel.BROWN);
		g.fillRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
		g.setColor(Color.lightGray);
		g.drawRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
		
		int line = TOP;
		g.setColor(Color.black);
		Util.drawStringCentered(g, "ROOM LIST", LEFT + IMAGE_WIDTH / 2, line);
		line += 14;
		Util.drawStringCentered(g, "Click a room below redo to it.", LEFT + IMAGE_WIDTH / 2, line);
		line += 14 * 2;
		g.drawString("Room Number", LEFT, line);
		g.drawString("Escape Turns", LEFT + 120, line);
		g.drawString("Clear Turns", LEFT + 240, line);
		line += 14;
		listTop = line;
		g.setColor(Color.blue);
		for (Character.Record r : rLayer.game.hero.record.values()) {
			Integer e = r.getEscapeTurn();
			Integer k = r.getKillTurn();
			String eS = (e == null ? "-" : e.toString());
			String kS = (k == null ? "-" : k.toString());
			g.drawString(String.valueOf(r.roomNumber), LEFT, line);
			g.drawString(eS, LEFT + 120, line);
			g.drawString(kS, LEFT + 240, line);
			line += 14;
		}
	}

	public void process(GameContainer gc) {
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int x = gc.getInput().getMouseX();
			int y = gc.getInput().getMouseY();
			System.out.println("Click: " + x + ", " + y + " Absolute: " + getAbsoluteX() + ", " + getAbsoluteY() + " RoomList: " + this.x + ", " + this.y);//DEBUG
			int count = rLayer.game.hero.record.size();
			if (x >= LEFT && x <= LEFT + IMAGE_WIDTH && y >= listTop && y <= listTop + count * 14) {
				rLayer.loadRoom(rLayer.game.hero.record.get((y - listTop) / 14).roomNumber);
			}
			rLayer.game.popUILayer();
		}
	}
}