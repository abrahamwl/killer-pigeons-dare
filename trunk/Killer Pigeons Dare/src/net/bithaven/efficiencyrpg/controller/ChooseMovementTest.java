package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.levelgenerator.Util;

import org.junit.Before;
import org.junit.Test;

public class ChooseMovementTest {

	private Game test;
	private Room room;
	private Actor enemy;
	private Actor hero;
	private int w = 5;
	private int h = 5;

	@Before
	public void setUp() throws Exception {
		Entity.createImage = false;

		test = new Game("test");
		test.hero = new Character();
		hero = test.hero;
		enemy = new Actor("hi", 1, "yo");
		String[][] grid = Util.createRandomGrid(w,h,new String[]{"d", "W"}, System.currentTimeMillis());
		room = new Room(test, new String[] {Util.convertGridToRoomString(grid)}, 0);
	}

	public Dir ericFunc(int sx, int sy, int ex, int ey) {
		enemy.x = sx;
		enemy.y = sy;
		hero.x = ex;
		hero.y = ey;
		
		Action action = ShortestPathController.chooseSimpleMovement(room, enemy, hero);
		if (action instanceof ActionMove) return ((ActionMove) action).dir;
		return null;
	}
	
	public Dir abeFunc(int sx, int sy, int ex, int ey) {
		enemy.x = sx;
		enemy.y = sy;
		hero.x = ex;
		hero.y = ey;
		
		//System.out.println("abeFunc");
		Action action = AttackController.chooseMovement(room, enemy, hero);
		if (action instanceof ActionMove) return ((ActionMove) action).dir;
		return null;
	}
	
	@Test
	public void test() {
		int yes = 0;
		int no = 0;
		for(int x0 = 0; x0 < w; x0++)
			for(int y0 = 0; y0 < h; y0++)
				for(int x1 = 0; x1 < w; x1++)
					for(int y1 = 0; y1 < h; y1++) {
						Dir e = ericFunc(x0,y0,x1,y1);
						String es = (e == null ? null : e.toString());
						Dir a = abeFunc(x0,y0,x1,y1);
						String as = (a == null ? null : a.toString());
						if(e != null && a != null && e.equals(a)) yes++; else {
							no++;
							System.out.println("e: " + es + " a: " + as + "(" + x0 + ", " + y0 + ", " + x1 + ", " + y1 + ")");
						}
					}
		System.out.println("yes: " + yes + " no: " + no);
	}

}
