package net.bithaven.efficiencyrpg.levelgenerator;


public class Level {
	public int id;
	public Coord start = null;
	public int prior = 0;
	public int enemyLevel = 1;
	public Integer[] exits;
	public String[][] environment = null;
	public String[][] entities = null;
	
	public Level() {}
	public static Level newBuilder() {return new Level();}
	public Level id(int id) {this.id = id;return this;}		
	public Level prior(int prior) {this.prior = prior;return this;}
	public Level enemyLevel(int enemyLevel) {this.enemyLevel = enemyLevel;return this;}
	public Level start(Coord start) {this.start = start;return this;}
	public Level exits(Integer[] exits) {this.exits = exits;return this;}
	public Level environment(String[][] environment) {this.environment = environment;return this;}
	public Level entities(String[][] entities) {this.entities = entities;return this;}
	public Level create() {return this;}
}
