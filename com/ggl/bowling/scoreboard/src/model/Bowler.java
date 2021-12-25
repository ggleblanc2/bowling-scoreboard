package com.ggl.bowling.scoreboard.model;

public class Bowler {
	
	private final int handicap;
	
	private final String name;
	
	public Bowler(String name) {
		this(name, 0);
	}

	public Bowler(String name, int handicap) {
		this.name = name;
		this.handicap = handicap;
	}

	public int getHandicap() {
		return handicap;
	}

	public String getName() {
		return name;
	}

}
