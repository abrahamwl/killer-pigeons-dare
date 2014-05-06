package net.bithaven.efficiencyrpg.ability;


import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.entity.features.Damage;
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
		Reflections reflections = new Reflections("net.bithaven.efficiencyrpg.ability");

		LinkedList<Class<? extends Ability>> allClasses = getSubClasses(Ability.class, reflections);
		 
		try {
			for (Class<? extends Ability> c : allClasses) {
				if (!Modifier.isAbstract(c.getModifiers())) {
					c.newInstance();
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static <T extends Ability> LinkedList<Class<? extends T>> getSubClasses(Class<T> type, Reflections reflections) {
		LinkedList<Class<? extends T>> out = new LinkedList<Class<? extends T>>();
		Set<Class<? extends T>> in = reflections.getSubTypesOf(type);
		for (Class<? extends T> c : in) {
			System.out.println("Loading Ability: " + c.getName());
			out.add(c);
			out.addAll(getSubClasses(c, reflections));
		}
		return out;
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
			return generalDescription.replace("<Name>", Character.toUpperCase(a.name.charAt(0)) + a.name.substring(1))
					.replace("<name>", a.name)
					.replace("<it>", a.gender.object)
					.replace("<itself>", a.gender.reflexive)
					.replace("<its>", a.gender.possesive)
					+ (element != null ? "\n(" + element.toString() + " element)" : "");
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
	
	public static enum Category {
		NORMAL,
		NATURE,
		NEGATIVE;
	}
	
	public final Category category;
	
	public final Damage.Type element;
	
	protected abstract Instance getNewInstance(Actor a);

	public final String name;
	public final Image icon;
	public static final int ICON_SIZE = 32;
	protected final String generalDescription;

	public MovementPassabilityModifier movementPassabilityModifier = null;
	
	protected Ability (String name, String description, int x, int y) {
		this(name, description, x, y, Category.NORMAL);
	}
	
	protected Ability (String name, String description, int x, int y, Category category) {
		this(name, description, x, y, category, null);
	}
	
	protected Ability (String name, String description, int x, int y, Category category, Damage.Type element) {
		this.name = name;
		generalDescription = description;
		if(Entity.createImage) this.icon = Game.iconSheet.getSprite(x, y);
		else this.icon = null;
		abilityTypes.add(this);
		this.category = category;
		this.element = element;
	}
	
	/* (non-Javadoc)
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#getGeneralDescription()
	 */
	public String getGeneralDescription() {
		return  generalDescription.replace("<Name>", "A character with " + name)
				.replace("<name>", "a character with " + name)
				.replace("<it>", "him")
				.replace("<itself>", "himself")
				.replace("<its>", "his")
				+ (element != null ? "\n(" + element.toString() + " element)" : "");
	}
	
	public class DisplayElement extends UIElement {
		private int width = 0, height = 0;
		private int targetX, targetY;
		private Actor a = null;

		public int getWidth(Graphics g) {
			if (width == 0) width = icon.getWidth() + 2 + g.getFont().getWidth(name);
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
				//System.out.println("Ability.DisplayElement.draw(): a == null");//DEBUG
				g.setColor(Color.blue);
			} else {
				//System.out.println("Ability.DisplayElement.draw(): a == " + a.toString());//DEBUG
				if (targetX == Integer.MIN_VALUE || !(Ability.this instanceof ActivatedAbility)) {
					if (on(a).enabled) {
						g.setColor(Color.blue);
					} else {
						g.setColor(Color.darkGray);
					}
				} else {
					switch (((ActivatedAbility)Ability.this).checkValidityOf(a, targetX, targetY)) {
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
		}

		@Override
		public void process(GameContainer gc) throws SlickException {
			int mX = gc.getInput().getMouseX();
			int mY = gc.getInput().getMouseY();
			
			if (mX >= 0 && mX <= getWidth(gc.getGraphics()) && mY >= 0 && mY <= getHeight()) {
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
	 * @see net.bithaven.efficiencyrpg.ability.AbilityInterface#getDisplayElement(net.bithaven.efficiencyrpg.entity.Actor, net.bithaven.efficiencyrpg.Game)
	 */
	public DisplayElement getDisplayElement (Actor a, Game game, int x, int y) {
		return new DisplayElement(game, x, y, a);
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

	public static LinkedList<Ability> getAbilities(Category category) {
		LinkedList<Ability> out = new LinkedList<Ability>();
		
		for (Ability ability : abilityTypes) {
			if (ability.category == category) {
				out.add(ability);
			}
		}
		
		return out;
	}

	public String getName() {
		return name;
	}

	public Image getIcon() {
		return icon;
	}
	
	public boolean allowed (Actor a) {
		if (element == null && category != Category.NATURE) {
			return true;
		}
		boolean isNature = (category == Category.NATURE);
		for (Ability ability : a.abilities) {
			if ((isNature || ability.category == Category.NATURE) && ability.element != element) {
				return false;
			}
		}
		
		return true;
	}
}