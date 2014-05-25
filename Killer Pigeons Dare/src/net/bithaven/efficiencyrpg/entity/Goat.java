package net.bithaven.efficiencyrpg.entity;

public class Goat extends Actor {
	private Goat(String name, int level, String imageName) {
		super(name, level, imageName);
	}

	public static Goat giveMeAGoatPlease(int level) {
		Goat goat = new Goat("Goat", level, "res/open1/dc-mon/animals/yak.png");
		// goat.controller = 
		return goat;
	}
}
