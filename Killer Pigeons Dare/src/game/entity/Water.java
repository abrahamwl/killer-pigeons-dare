package game.entity;

import game.Ability;
import game.Actor;
import game.Entity;
import game.Room;

public class Water extends Entity {

	@Override
	public boolean execute(Room r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean passableFor(Actor actor) {
		for (Ability a : actor.abilities) {
			if (a.type == Ability.Type.FLYING && a.active) {
				return true;
			}
			if (a.type == Ability.Type.SWIMMING && a.active) {
				return true;
			}
		}
		
		return false;
	}

}
