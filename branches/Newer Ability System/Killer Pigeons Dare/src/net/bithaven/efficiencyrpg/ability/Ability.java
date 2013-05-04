package net.bithaven.efficiencyrpg.ability;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.ui.UIElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.reflections.Reflections;

public abstract class Ability implements AbilityInterface {
	public static LinkedHashSet<Ability> abilityTypes;
	static {
		abilityTypes = new LinkedHashSet<Ability>();
		Reflections reflections = new Reflections("net.bithaven.efficiencyrpg.ability.abilities");

		 Set<Class<? extends Ability>> allClasses = 
		     reflections.getSubTypesOf(Ability.class);
		 
		try {
			for (Class<? extends Ability> c : allClasses) {
				c.newInstance();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class Instance {
		protected Actor a;
		boolean enabled;

		public Instance(Actor a) {
			this.a = a;
		}

		public void doNewTurn() {
			setEnabled(true);
		}
		
		public void doWait() {
		}
		
		public final void setEnabled(boolean enabled) {
			this.enabled = enabled;
			if (enabled) {
				a.activeAbilities.add(Ability.this);
			} else {
				a.activeAbilities.remove(Ability.this);
			}
		}

		public String getDescription() {
			return a.name + generalDescription;
		}
	}
	public HashMap<Actor,Instance> instances = new HashMap<Actor,Instance>();
	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#on(net.bithaven.efficiencyrpg.entity.Actor)
	 */
	public Instance on(Actor a) {
		Instance out = instances.get(a);
		if (out == null) {
			out = getNewInstance(a);
			instances.put(a, out);
		}
		return out;
	}
	
	protected abstract Instance getNewInstance(Actor a);

	public final String name;
	public final Image icon;
	protected final String generalDescription;

	public MovementPassabilityModifier movementPassabilityModifier = null;
	
	protected Ability (String name, String description, Image icon) {
		this.name = name;
		generalDescription = description;
		this.icon = icon;
		abilityTypes.add(this);
	}
	
	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#getGeneralDescription()
	 */
	public String getGeneralDescription() {
		return "A character with " + name + generalDescription;
	}
	
	public class DisplayElement extends UIElement {
		private int width = 0, height = 0;
		private int targetX, targetY;
		private Actor a = null;

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		private DisplayElement (Game game, int x, int y) {
			super(game, x, y);
			height = 32;
		}
		
		public DisplayElement(Game game, int x, int y, Actor a) {
			this(game, x, y, a, Integer.MIN_VALUE, Integer.MIN_VALUE);
		}
		
		public DisplayElement(Game game, int x, int y, Actor a, int targetX, int targetY) {
			this(game, x, y);
			this.a = a;
			this.targetX = targetX;
			this.targetY = targetY;
		}

		@Override
		public void draw(GameContainer gc, Graphics g)
				throws SlickException {
			g.drawImage(icon, 0, 0);
			if (a == null) {
				g.setColor(Color.blue);
			} else {
				if (targetX == Integer.MIN_VALUE || !(this instanceof ActivatedAbility)) {
					if (on(a).enabled) {
						g.setColor(Color.blue);
					} else {
						g.setColor(Color.darkGray);
					}
				} else {
					switch (((ActivatedAbility)this).getStatusOf(a, targetX, targetY)) {
					case INVALID:
						g.setColor(Color.lightGray);
						break;
					case NOT_RECOMMENDED:
						g.setColor(Color.red);
						break;
					case OKAY:
						g.setColor(Color.blue);
					}
				}
			}
			g.drawString(name, icon.getWidth() + 2, (icon.getHeight() - g.getFont().getLineHeight()) / 2);
			width = icon.getWidth() + 2 + g.getFont().getWidth(name);
		}

		@Override
		public void process(GameContainer gc) throws SlickException {
			int mX = gc.getInput().getMouseX();
			int mY = gc.getInput().getMouseY();
			
			if (mX >= 0 && mX <= width && mY >= 0 && mY <= height) {
				if (a == null) {
					game.tooltip = getGeneralDescription();
				} else {
					game.tooltip = on(a).getDescription();
				}
			}
		}				
	}
	
	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#getDisplayElement(net.bithaven.efficiencyrpg.Game, int, int)
	 */
	public DisplayElement getDisplayElement (Game game, int x, int y) {
		return new DisplayElement(game, x, y);
	}

	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#getDisplayElement(net.bithaven.efficiencyrpg.entity.Actor, net.bithaven.efficiencyrpg.Game, int, int)
	 */
	public DisplayElement getDisplayElement (Actor a, Game game, int x, int y, int targetX, int targetY) {
		return new DisplayElement(game, x, y, a, targetX, targetY);
	}

	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#remove(net.bithaven.efficiencyrpg.entity.Actor)
	 */
	public void remove(Actor actor) {
		instances.remove(actor);
	}

	public static Ability getAbility(Class<? extends Ability> type) {
		for (Ability ability : abilityTypes) {
			if (type.isInstance(ability)) return ability;
		}
		
		throw (new Error());
	}

	public String getName() {
		return name;
	}

	public Image getIcon() {
		return icon;
	}
}