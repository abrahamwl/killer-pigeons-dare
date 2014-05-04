package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.HashMap;
import java.util.Map;

public class Level {
	private int id;
	private Coord start = null;
	private int prior = 0;
	private Integer[] exits;
	private Map<String, String> mtdt = new HashMap<String, String>();
	private String[][] environment = null;
	private String[][] entities = null;
	
	public Level() {}
	public static Level newBuilder() {return new Level();}
	public Level id(int id) {this.id = id;return this;}		
	public Level prior(int prior) {this.prior = prior;return this;}
	public Level start(Coord start) {this.start = start;return this;}
	public Level exits(Integer[] exits) {this.exits = exits;return this;}
	public Level mtdt(Map<String, String> mtdt) {this.mtdt = mtdt;return this;}
	public Level environment(String[][] environment) {this.environment = environment;return this;}
	public Level entities(String[][] entities) {this.entities = entities;return this;}
	public Level create() {return this;}
}
