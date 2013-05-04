package net.bithaven.efficiencyrpg.ability;


import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
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

public abstract class Ability {
	private static HashSet<Class<? extends Ability>> abilityTypes;
	static {
		Reflections reflections = new Reflections("net.bithaven.efficiencyrpg.ability.abilities");

		 Set<Class<? extends Ability>> allClasses = 
		     reflections.getSubTypesOf(Ability.class);
		 
		 HashSet<Class<? extends Ability>> abilityTypes = getAbilityTypes();
		 
		 for (Class<? extends Ability> c : allClasses) {
			 abilityTypes.add(c);
		 }
	}

	
	public static final HashSet<Class<? extends Ability>> getAbilityTypes() {
		if (abilityTypes == null){
			 abilityTypes = new HashSet<Class<? extends Ability>>();
		}
		return abilityTypes;
	}

	public Actor a;
	
	public static final String name = "Unnamed Ability";
	public static final String generalDescription = "";
	public static final Image icon = null;
	
	private boolean enabled;
	
	public MovementPassabilityModifier movementPassabilityModifier = null;
	
	public static final Ability getNew(Class<? extends Ability> c, Actor a) {
		System.out.println(c.getSimpleName());
		try {
			return c.getConstructor(Actor.class).newInstance(a);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			a.activeAbilities.add(this);
		} else {
			a.activeAbilities.remove(this);
		}
	}

	protected Ability (Actor a) {
		this.a = a;
	}
	
	public void doNewTurn() {
		setEnabled(true);
	}
	
	public void doWait() {
	}
	
	public static String getGeneralDescription(Class<? extends Ability> c) {
		try {
			return "A character with " + (String)c.getField("name").get(null) + (String)c.getField("generalDescription").get(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return generalDescription;
	}
	
	public String getDescription() {
		try {
			return a.name + (String)this.getClass().getField("generalDescription").get(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return getGeneralDescription(getClass());
	}

	public static Image getIcon(Class<? extends Ability> c) {
		try {
			return (Image)c.getField("icon").get(null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Image getIcon() {
		return getIcon(getClass());
	}
	
	public static String getName(Class<? extends Ability> c) {
		try {
			return (String)c.getField("name").get(null);
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getName() {
		return getName(getClass());
	}
	
	public static class DisplayElement extends UIElement {
		private Ability ability = null;
		private Class<? extends Ability> clazz = null;
		private int width = 0, height = 0;
		private Image icon;
		private String description;
		private String name;
		
		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		private DisplayElement (Game game, int x, int y, Ability ability) {
			super(game, x, y);
			height = 32;
			this.ability = ability;
			description = ability.getDescription();
			icon = ability.getIcon();
			name = ability.getName();
		}
		
		public DisplayElement(Game game, int x, int y, Class<? extends Ability> c) {
			super(game, x, y);
			height = 32;
			this.clazz = c;
			description = getGeneralDescription(c);
			icon = getIcon(c);
			name = getName(c);
		}

		@Override
		public void draw(GameContainer gc, Graphics g)
				throws SlickException {
			g.drawImage(icon, 0, 0);
			if (ability == null) {
				g.setColor(Color.blue);
			} else {
				if (ability.enabled) {
					g.setColor(Color.blue);
				} else {
					g.setColor(Color.darkGray);
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
				game.tooltip = description;
			}
		}				
	}
	
	public DisplayElement getDisplayElement (Game game, int x, int y) {
		return new DisplayElement(game, x, y, this);
	}

	public static DisplayElement getDisplayElement (Class <? extends Ability> c, Game game, int x, int y) {
		return new DisplayElement(game, x, y, c);
	}
}