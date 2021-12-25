package com.ggl.bowling.scoreboard;

import javax.swing.SwingUtilities;

import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;
import com.ggl.bowling.scoreboard.view.BowlingScoreboardFrame;

public class BowlingScoreboard implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new BowlingScoreboard());
	}

	@Override
	public void run() {
		new BowlingScoreboardFrame(new BowlingScoreboardModel());
	}

}
