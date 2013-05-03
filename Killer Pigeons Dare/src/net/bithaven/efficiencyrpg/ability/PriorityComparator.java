package net.bithaven.efficiencyrpg.ability;

import java.util.Comparator;
import java.util.HashMap;

public class PriorityComparator implements Comparator<Hooked> {
	private static HashMap<Class<? extends Hooked>,PriorityComparator> map = new HashMap<Class<? extends Hooked>,PriorityComparator>();
	Class<? extends Hooked> c;
	
	public static PriorityComparator getComparator (Class<? extends Hooked> c) {
		PriorityComparator out = map.get(c);
		if (out == null) {
			out = new PriorityComparator(c);
			map.put(c, out);
		}
		return out;
	}
	
	private PriorityComparator (Class<? extends Hooked> c) {
		this.c = c;
	}

	public int compare(Hooked o1, Hooked o2) {
		return Integer.compare(-o1.getPriority(c), -o2.getPriority(c));
	}
}
