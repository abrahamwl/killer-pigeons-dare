package net.bithaven.efficiencyrpg.controller;


import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.Action;

import org.newdawn.slick.GameContainer;

public interface Controller {
	public Action chooseNextAction (Room room, GameContainer gc);
}
