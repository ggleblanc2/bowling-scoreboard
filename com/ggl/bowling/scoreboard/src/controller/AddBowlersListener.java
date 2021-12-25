package com.ggl.bowling.scoreboard.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;

import com.ggl.bowling.scoreboard.model.Bowler;
import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;
import com.ggl.bowling.scoreboard.view.BowlingScoreboardFrame;

public class AddBowlersListener implements ActionListener {
	
	private final BowlingScoreboardFrame view;
	
	private final BowlingScoreboardModel model;
	
	private final JDialog dialog;
	
	private final JTextField[] nameField, handicapField;

	public AddBowlersListener(BowlingScoreboardFrame view, BowlingScoreboardModel model,
			JDialog dialog, JTextField[] nameField, JTextField[] handicapField) {
		this.view = view;
		this.model = model;
		this.dialog = dialog;
		this.nameField = nameField;
		this.handicapField = handicapField;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
//		System.out.println("actionPerformed");
		String name = nameField[0].getText().trim();
		if (name.isEmpty()) {
			return;
		}
		
		boolean errorFound = false;
		for (int index = 0; index < model.getMaximumNumberOfBowlers(); index++) {
			String text = handicapField[index].getText().trim();
			if (!text.isEmpty()) {
				if (valueOf(text, index) < 0) {
					errorFound = true;
				}
			}
		}
		
		if (errorFound) {
			return;
		}
		
		int count = 0;
		for (int index = 0; index < model.getMaximumNumberOfBowlers(); index++) {
			name = nameField[index].getText().trim();
			
			if (!name.isEmpty()) {
				int handicap = 0;
				String text = handicapField[index].getText().trim();
				if (!text.isEmpty()) {
					handicap = valueOf(text, index);
				}
				model.addBowler(new Bowler(name, handicap), count++);
			}
		}
		
//		System.out.println("Number of bowlers " + count);
		model.setNumberOfBowlers(count);
		view.enableAllNumberButtons();
		view.repaint();
		dialog.dispose();
	}
	
	private int valueOf(String text, int index) {
		try {
			return Integer.valueOf(text);
		} catch (NumberFormatException e) {
			handicapField[index].setSelectionStart(0);
			handicapField[index].setSelectionEnd(text.length());
			return -1;
		}
	}

}
