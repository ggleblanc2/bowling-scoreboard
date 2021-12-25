package com.ggl.bowling.scoreboard.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;
import com.ggl.bowling.scoreboard.view.BowlingScoreboardFrame;

public class AddMarksListener implements ActionListener {
	
	private final BowlingScoreboardFrame view;
	
	private final BowlingScoreboardModel model;

	public AddMarksListener(BowlingScoreboardFrame view, BowlingScoreboardModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		view.enableAllNumberButtons();
		
		JButton button = (JButton) event.getSource();
		String text = button.getText();
		if (text.equals("X")) {
			if (model.getFrame() >= 9) {
				model.addMark(text);
				model.incrementThrow(1);
			} else {
				model.addMark("X ");
				model.incrementThrow(2);
			}
		} else {
			model.addMark(text);
			int thrw = model.getThrow();
			if (thrw == 0) {
				view.disableNumberButtons();
				int value = valueOf(text);
				view.enableNumberButtons(11 - value);
			}
			model.incrementThrow(1);
		}
		
		if (model.isGameOver()) {
			view.disableNumberButtons();
		}
		
		view.repaint();
	}
	
	private int valueOf(String text) {
		 if (text.equals("-")) {
			return 0;
		} else {
			return Integer.valueOf(text);
		}
	}

}
