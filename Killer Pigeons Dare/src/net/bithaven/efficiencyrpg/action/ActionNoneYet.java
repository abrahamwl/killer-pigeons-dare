package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Actor;

// This is a very special action that should almost never be used. It tells the system to do other things,
// like update the graphics, and then ask the Controller for an action again later. At this point it is only 
// by human interface controllers.
final public class ActionNoneYet extends Action {
	public static final ActionNoneYet INSTANCE = new ActionNoneYet();
	
	private ActionNoneYet () {
		
	}

	@Override
	public void execute(Actor a) {
		return;
	}

}
