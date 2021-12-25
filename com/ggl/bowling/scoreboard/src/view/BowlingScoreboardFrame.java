package com.ggl.bowling.scoreboard.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ggl.bowling.scoreboard.controller.AddMarksListener;
import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;

public class BowlingScoreboardFrame {
	
	private final BowlingScoreboardModel model;
	
	private final DrawingPanel drawingPanel;
	
	private JButton addBowlersButton;
	private JButton[] numberButton;
	
	private final JFrame frame;

	public BowlingScoreboardFrame(BowlingScoreboardModel model) {
		this.model = model;
		this.drawingPanel = new DrawingPanel(model);
		this.frame = createAndShowGUI();
	}
	
	private JFrame createAndShowGUI() {
		JFrame frame = new JFrame("Bowling Scoreboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(drawingPanel, BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println("Frame size " + frame.getSize());
		
		return frame;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(createBowlerPanel());
		panel.add(createNumberButtonPanel());
		panel.add(createResetPanel());
		return panel;
	}
	
	private JPanel createBowlerPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		Font font = panel.getFont().deriveFont(Font.BOLD, 16f);
		
		addBowlersButton = new JButton("Add Bowlers");
		addBowlersButton.addActionListener(event -> {
			AddBowlersDialog dialog = new AddBowlersDialog(this, model);
			if (!dialog.isCancelled()) {
				addBowlersButton.setEnabled(false);
			}
		});
		addBowlersButton.setFont(font);
		panel.add(addBowlersButton);
		
		return panel;
	}
	
	private JPanel createNumberButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		Font font = panel.getFont().deriveFont(Font.BOLD, 20f);
		
		String[] text = { "-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "/", "X" };
		this.numberButton = new JButton[text.length];
		AddMarksListener listener = new AddMarksListener(this, model);
		
		for (int index = 0; index < text.length; index++) {
			numberButton[index] = new JButton(text[index]);
			numberButton[index].addActionListener(listener);
			numberButton[index].setFont(font);
			panel.add(numberButton[index]);
		}
		
		disableNumberButtons();
		
		return panel;
	}
	
	private JPanel createResetPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		Font font = panel.getFont().deriveFont(Font.BOLD, 16f);
		
		JButton button = new JButton("Reset Scoreboard");
		button.addActionListener(event -> {
			model.initialize();
			enableAddBowlersButton();
			disableNumberButtons();
			repaint();
		});
		button.setFont(font);
		panel.add(button);
		
		return panel;
	}
	
	public void enableAddBowlersButton() {
		addBowlersButton.setEnabled(true);
	}
	
	public void disableNumberButtons() {
		for (int index = 0; index < numberButton.length; index++) {
			numberButton[index].setEnabled(false);
		}
	}
	
	public void enableAllNumberButtons() {
		for (int index = 0; index < numberButton.length; index++) {
			numberButton[index].setEnabled(true);
		}
		numberButton[10].setEnabled(false);
	}
	
	public void enableNumberButtons(int value) {
		for (int index = 0; index < value; index++) {
			numberButton[index].setEnabled(true);
		}
		numberButton[10].setEnabled(true);
	}
	
	public void repaint() {
		drawingPanel.repaint();
	}

	public JFrame getFrame() {
		return frame;
	}

}
