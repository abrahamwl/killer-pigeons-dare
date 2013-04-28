package game;

public class Ability {
	
	public enum Type {
		TOUGH(true), //Extra 50% HP.
		QUICK(true), //Acts <power> extra times in a turn.
		BLOCK(true), //Negates the first melee attack it would receive in a turn.
		COUNTER_WAIT(false); //If it waits, the first <power> times it would be melee attacked in a turn,
								//it attacks back instead.
		
		protected boolean activeByDefault;
		
		private Type(boolean a) {
			activeByDefault = a;
		}
	}

	public Type type;
	public int power = 1;
	public boolean active = true;
	
	public Ability (Type type) {
		this.type = type;
		active = type.activeByDefault;
	}
	
	public void reset() {
		active = type.activeByDefault;
	}
}