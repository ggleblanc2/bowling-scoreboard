package com.ggl.bowling.scoreboard.view;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;

public class LogicalRectangles {
	
	private final int margin;
	
	private final BowlingScoreboardModel model;
	
	private final Dimension frameScore, nameArea, tenthFrameScore, throwScore;
	
	private final Rectangle[][][] throwRectangle;
	
	private final Rectangle[][] scoreRectangle;
	
	private final Rectangle[] nameRectangle, hTotalRectangle, totalRectangle;
	
	private Rectangle hGrandTotalRectangle, grandTotalRectangle;
	
	public LogicalRectangles(BowlingScoreboardModel model) {
		this.model = model;
		this.margin = 20;
		this.throwScore = new Dimension(24, 24);
		this.nameArea = new Dimension(throwScore.width * 6, throwScore.height * 3);
		this.frameScore = new Dimension(throwScore.width * 3, throwScore.height * 3);
		this.tenthFrameScore = new Dimension(throwScore.width * 4, throwScore.height * 3);
		this.throwRectangle = new Rectangle[model.getMaximumNumberOfBowlers()][10][3];
		this.scoreRectangle = new Rectangle[model.getMaximumNumberOfBowlers()][10];
		this.hTotalRectangle = new Rectangle[model.getMaximumNumberOfBowlers()];
		this.totalRectangle = new Rectangle[model.getMaximumNumberOfBowlers()];
		this.nameRectangle = new Rectangle[model.getMaximumNumberOfBowlers()];
		createRectangles();
	}
	
	private void createRectangles() {
		int x = margin;
		int y = margin + nameArea.height;
		
		for (int bowler = 0; bowler < model.getMaximumNumberOfBowlers(); bowler++) {
			nameRectangle[bowler] = new Rectangle(x + 10, y + throwScore.height, 
					nameArea.width - 10, nameArea.height - throwScore.height);
			
			createScoreRectangles(bowler, x, y);
			
			y += nameArea.height;
		}
	}
	
	public void createGrandTotalRectangles(int numberOfBowlers) {
		int x = margin + nameArea.width + 9 * frameScore.width + tenthFrameScore.width;
		int y = margin + nameArea.height * (numberOfBowlers + 1);
		grandTotalRectangle = new Rectangle(x, y + throwScore.height, frameScore.width,
				frameScore.height - throwScore.height);
	
		x += frameScore.width;
		hGrandTotalRectangle = new Rectangle(x, y + throwScore.height, frameScore.width,
				frameScore.height - throwScore.height);
	}

	private void createScoreRectangles(int bowler, int x, int y) {
		int x1 = x + nameArea.width;
		for (int frame = 0; frame < 9; frame++) {
			createThrowRectangles(bowler, frame, x1, y);
			scoreRectangle[bowler][frame] = new Rectangle(x1, y, frameScore.width, 
					frameScore.height);
			x1 += frameScore.width;
		}

		createThrowRectangles(bowler, 9, x1, y);
		scoreRectangle[bowler][9] = new Rectangle(x1, y, tenthFrameScore.width, frameScore.height);

		x1 += tenthFrameScore.width;
		totalRectangle[bowler] = new Rectangle(x1, y, frameScore.width, frameScore.height);

		x1 += frameScore.width;
		hTotalRectangle[bowler] = new Rectangle(x1, y, frameScore.width, frameScore.height);
	}
	
	private void createThrowRectangles(int bowler, int frame, int x, int y) {
		int x1 = x + throwScore.width;
		for (int thrw = 0; thrw < 2; thrw++) {
			throwRectangle[bowler][frame][thrw] = new Rectangle(x1, y, throwScore.width,
					throwScore.height);
			x1 += throwScore.width;
		}
		
		if (frame == 9) {
			throwRectangle[bowler][frame][2] = new Rectangle(x1, y, throwScore.width,
					throwScore.height);
		}
	}

	public int getMargin() {
		return margin;
	}

	public BowlingScoreboardModel getModel() {
		return model;
	}

	public Dimension getFrameScore() {
		return frameScore;
	}

	public Dimension getNameArea() {
		return nameArea;
	}

	public Dimension getTenthFrameScore() {
		return tenthFrameScore;
	}

	public Dimension getThrowScore() {
		return throwScore;
	}

	public Rectangle getThrowRectangle(int bowler, int frame, int thrw) {
		return throwRectangle[bowler][frame][thrw];
	}

	public Rectangle getScoreRectangle(int bowler, int frame) {
		return scoreRectangle[bowler][frame];
	}

	public Rectangle getNameRectangle(int bowler) {
		return nameRectangle[bowler];
	}

	public Rectangle gethTotalRectangle(int bowler) {
		return hTotalRectangle[bowler];
	}
	
	public Rectangle getTotalRectangle(int bowler) {
		return totalRectangle[bowler];
	}

	public Rectangle gethGrandTotalRectangle() {
		return hGrandTotalRectangle;
	}

	public Rectangle getGrandTotalRectangle() {
		return grandTotalRectangle;
	}

}
