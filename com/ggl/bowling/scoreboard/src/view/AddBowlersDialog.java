package com.ggl.bowling.scoreboard.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ggl.bowling.scoreboard.controller.AddBowlersListener;
import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;

public class AddBowlersDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isCancelled;
	
	private final BowlingScoreboardFrame view;
	
	private final BowlingScoreboardModel model;
	
	private JButton okButton;
	
	private final JTextField[] nameField, handicapField;
	
	public AddBowlersDialog(BowlingScoreboardFrame view, BowlingScoreboardModel model) {
		super(view.getFrame(), "Add Bowlers", true);
		this.view = view;
		this.model = model;
		this.nameField = new JTextField[model.getMaximumNumberOfBowlers()];
		this.handicapField = new JTextField[model.getMaximumNumberOfBowlers()];
		this.isCancelled = false;
		
		this.add(createMainPanel(), BorderLayout.CENTER);
		this.add(createButtonPanel(), BorderLayout.SOUTH);
		
		this.pack();
		this.setLocationRelativeTo(view.getFrame());
		this.getRootPane().setDefaultButton(okButton);
		this.setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font font = panel.getFont().deriveFont(Font.BOLD, 16f);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		
		for (int index = 0; index < model.getMaximumNumberOfBowlers(); index++) {
			JLabel label = new JLabel("Bowler " + (index + 1) + ":");
			label.setFont(font);
			panel.add(label, gbc);
			
			gbc.gridx++;
			nameField[index] = new JTextField(15);
			nameField[index].setFont(font);
			panel.add(nameField[index], gbc);
			
			gbc.gridx++;
			label = new JLabel("Handicap:");
			label.setFont(font);
			panel.add(label, gbc);
			
			gbc.gridx++;
			handicapField[index] = new JTextField(5);
			handicapField[index].setFont(font);
			panel.add(handicapField[index], gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
		}
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font font = panel.getFont().deriveFont(Font.BOLD, 16f);
		
		okButton = new JButton("OK");
		okButton.addActionListener(
			new AddBowlersListener(view, model, this, nameField, handicapField));
		okButton.setFont(font);
		panel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> {
			isCancelled = true;
			dispose();
		});
		cancelButton.setFont(font);
		panel.add(cancelButton);
		
		Dimension d = cancelButton.getPreferredSize();
		okButton.setPreferredSize(d);
		
		return panel;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

}
