package game;

public class Ability {
	
	public enum Type {
		TOUGH(true, "This character has 50% more hitpoints."),
		BLOCK(true, "This character blocks the first melee attack against it in a turn."),
		COUNTER_WAIT(false, "When this character waits, it will stop the first attack against it in a turn and counter-attack."),
		FLYING(true, "This character can move over water.");
		
		protected boolean activeByDefault;
		public final String toolTip;
		
		private Type(boolean activeByDefault, String toolTip) {
			this.toolTip = toolTip;
			this.activeByDefault = activeByDefault;
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