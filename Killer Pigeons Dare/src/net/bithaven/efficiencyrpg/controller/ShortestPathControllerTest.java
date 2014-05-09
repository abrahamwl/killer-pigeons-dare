package net.bithaven.efficiencyrpg.controller;

import static org.junit.Assert.*;

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

public class ShortestPathControllerTest {
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
		String[][] grid = Util.createRandomGrid(w,h,new String[]{"d"}, System.currentTimeMillis());
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
	
	@Test
	public void test() {
		int x = 1;
		int y = 1;
		Dir d1 = null;
		for(Dir d : Dir.compass) {
			d1 = ericFunc(x, y, x + d.x * 2, y + d.y * 2);
			assert(d.equals(d1));
		}
	}
}
