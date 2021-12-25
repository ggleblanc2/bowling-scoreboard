package com.ggl.bowling.scoreboard.model;

public class BowlingScoreboardModel {
	
	private int bowler, frame, numberOfBowlers, thrw;
	private final int maximumNumberOfBowlers;
	
	private Bowler[] bowlers;
	
	private BowlingScore[] scores;
	
	private String[] marks;
	
	public BowlingScoreboardModel() {
		this.maximumNumberOfBowlers = 6;
		this.bowlers = new Bowler[maximumNumberOfBowlers];
		this.scores = new BowlingScore[maximumNumberOfBowlers];
		this.marks = new String[maximumNumberOfBowlers];
		initialize();
	}
	
	public void initialize() {
		this.numberOfBowlers = maximumNumberOfBowlers;
		this.bowlers = new Bowler[maximumNumberOfBowlers];
		this.scores = new BowlingScore[maximumNumberOfBowlers];
		
		for (int index = 0; index < maximumNumberOfBowlers; index++) {
			this.marks[index] = "";
			this.scores[index] = new BowlingScore();
		}
		
		this.bowler = 0;
		this.frame = 0;
		this.thrw = 0;
	}

	public void setNumberOfBowlers(int numberOfBowlers) {
		this.numberOfBowlers = numberOfBowlers;
	}
	
	public void addBowler(Bowler bowler, int count) {
		bowlers[count] = bowler;
	}
	
	public void addMark(String mark) {
		marks[bowler] += mark;
	}
	
	public void incrementThrow(int increment) {
		thrw += increment;
		
		// increment bowler
		if (frame >= 9) {
			int limit = isThirdMark() ? 2 : 1;
			if (thrw > limit) {
				bowler++;
				thrw = 0;
			}
		} else {
			if (thrw > 1) {
				bowler++;
				thrw = 0;
			}
		}
		
		// Increment frame
		if (bowler >= numberOfBowlers) {
			frame++;
			bowler = 0;
		}
	}
	
	private boolean isThirdMark() {
		String tenth = marks[bowler].substring(18);
		return tenth.contains("X") || tenth.contains("/");
	}
	
	public void setScores(BowlingScore[] scores) {
		this.scores = scores;
	}

	public void setBowlers(Bowler[] bowlers) {
		this.bowlers = bowlers;
	}

	public Bowler[] getBowlers() {
		return bowlers;
	}

	public BowlingScore[] getScores() {
		return scores;
	}

	public String[] getMarks() {
		return marks;
	}
	
	public boolean isGameOver() {
		return frame > 9;
	}

	public int getBowler() {
		return bowler;
	}

	public int getFrame() {
		return frame;
	}

	public int getThrow() {
		return thrw;
	}

	public int getMaximumNumberOfBowlers() {
		return maximumNumberOfBowlers;
	}
	
	public int getNumberOfBowlers() {
		return numberOfBowlers;
	}

}
