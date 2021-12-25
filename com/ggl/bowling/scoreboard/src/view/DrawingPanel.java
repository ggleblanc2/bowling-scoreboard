package com.ggl.bowling.scoreboard.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.ggl.bowling.scoreboard.model.Bowler;
import com.ggl.bowling.scoreboard.model.BowlingScore;
import com.ggl.bowling.scoreboard.model.BowlingScoreboardModel;

public class DrawingPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final int margin;
	
	private final BowlingScoreboardModel model;
	
	private final Dimension frameScore, nameArea, tenthFrameScore, throwScore;
	
	private final LogicalRectangles logicalRectangles;
	
	public DrawingPanel(BowlingScoreboardModel model) {
		this.model = model;
		this.logicalRectangles = new LogicalRectangles(model);
		this.margin = logicalRectangles.getMargin();
		this.frameScore = logicalRectangles.getFrameScore();
		this.nameArea = logicalRectangles.getNameArea();
		this.tenthFrameScore = logicalRectangles.getTenthFrameScore();
		this.throwScore = logicalRectangles.getThrowScore();
		
		int width = margin + nameArea.width + 10 * frameScore.width + 
				2 * tenthFrameScore.width + margin;
		int height = margin + nameArea.height * 
				(model.getMaximumNumberOfBowlers() + 2) + margin;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		Font font = g2d.getFont().deriveFont(Font.BOLD, 18f);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		drawBowlers(g2d);
		drawScores(g2d);
		font = g2d.getFont().deriveFont(Font.BOLD, 18f);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		drawHeadings(g2d);
		drawHorizontalLines(g2d);
		drawVerticalLines(g2d);
	}
	
	private void drawHeadings(Graphics2D g2d) {
		int x = margin + 10;
		int y = margin + nameArea.height - 14;
		g2d.drawString("Name", x, y);
		
		x += nameArea.width - 10;
		y = margin + throwScore.height;
		int height = nameArea.height - throwScore.height;
		
		for (int index = 1; index <= 9; index++) {
			int width = frameScore.width;
			Rectangle r = new Rectangle(x, y, width, height);
			drawCenteredString(g2d, Integer.toString(index), r);
			x += frameScore.width;
		}
		
		int width = tenthFrameScore.width;
		Rectangle r = new Rectangle(x, y, width, height);
		drawCenteredString(g2d, "10", r);
		
		x += tenthFrameScore.width;
		width = frameScore.width;
		r = new Rectangle(x, y, width, height);
		drawCenteredString(g2d, "Total", r);
		
		x += frameScore.width;
		r = new Rectangle(x, y, width, height);
		drawCenteredString(g2d, "H Total", r);
	}
	
	private void drawHorizontalLines(Graphics2D g2d) {
		int x1 = margin;
		int x2 = x1 + nameArea.width + frameScore.width * 9 + tenthFrameScore.width;
		int x3 = x2 + 2 * frameScore.width;
		int y = margin + nameArea.height;
		
		for (int index = 0; index < (model.getNumberOfBowlers() + 2); index++) {
			drawHorizontalThrowScoreLines(g2d, index, y);
			int x = x1;
			if (index >= (model.getNumberOfBowlers() + 1)) {
				x = x2;
			}
			g2d.setStroke(new BasicStroke(3f));
			g2d.drawLine(x, y, x3, y);
			y += nameArea.height;
		}
	}

	private void drawHorizontalThrowScoreLines(Graphics2D g2d, int index, int y) {
		if (index < model.getNumberOfBowlers()) {
			int x1 = margin + nameArea.width + frameScore.width;
			int x2 = x1 - throwScore.width * 2;
			int y1 = y + throwScore.height;
			for (int jndex = 0; jndex < 9; jndex++) {
				g2d.setStroke(new BasicStroke(1f));
				g2d.drawLine(x1, y1, x2, y1);
				x1 += frameScore.width;
				x2 += frameScore.width;
			}
			
			x1 += throwScore.width;
			g2d.drawLine(x1, y1, x2, y1);
		}
	}
	
	private void drawVerticalLines(Graphics2D g2d) {
		int x = margin;
		int y1 = margin + nameArea.height;
		int y2 = y1 + nameArea.height * model.getNumberOfBowlers();
		int y3 = y2 + nameArea.height;
		
		drawVerticalThrowScoreLines(g2d);
		
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(x, y1, x, y2);
		x += nameArea.width;
		
		for (int index = 0; index < 10; index++) {
			g2d.setStroke(new BasicStroke(3f));
			g2d.drawLine(x, y1, x, y2);
			x += frameScore.width;
		}
		
		x += tenthFrameScore.width - frameScore.width;
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(x, y1, x, y3);
		
		x += frameScore.width;
		g2d.drawLine(x, y1, x, y3);
		
		x += frameScore.width;
		g2d.drawLine(x, y1, x, y3);
	} 
	
	private void drawVerticalThrowScoreLines(Graphics2D g2d) {
		int x =  margin + nameArea.width + frameScore.width;
		int y1 = margin + nameArea.height;
		int y2 = y1 + throwScore.height;
		g2d.setStroke(new BasicStroke(1f));
		
		for (int index = 0; index < model.getNumberOfBowlers(); index++) {
			int x1 = x - throwScore.width * 2;
			for (int jndex = 0; jndex < 9; jndex++) {
				for (int kndex = 0; kndex < 2; kndex++) {
					g2d.drawLine(x1, y1, x1, y2);
					x1 += throwScore.width;
				}
				x1 += frameScore.width - throwScore.width * 2;
			}
			
			for (int jndex = 0; jndex < 3; jndex++) {
				g2d.drawLine(x1, y1, x1, y2);
				x1 += throwScore.width;
			}
			
			y1 += nameArea.height;
			y2 += nameArea.height;
		}
	}
	
	private void drawBowlers(Graphics2D g2d) {
		int x = margin + 10;
		int y = margin + 2 * nameArea.height - 14;
		
		Bowler[] bowlers = model.getBowlers();
		for (int index = 0; index < model.getNumberOfBowlers(); index++) {
			if (bowlers[index] != null) {
				String name = bowlers[index].getName();
				Rectangle r = logicalRectangles.getNameRectangle(index);
				g2d.drawString(name, r.x, r.y + r.height - 10);
				
				int handicap = bowlers[index].getHandicap();
				if (handicap > 0) {
					String text = Integer.toString(handicap);
					FontMetrics fm = g2d.getFontMetrics();
					int x1 = x + nameArea.width - fm.stringWidth(text) - margin - 5;
					int y1 = y - nameArea.height + 2 * throwScore.height - 9;
					g2d.drawString(text, x1, y1);
				}
				y += nameArea.height;
			}
		}
		
	}
	
	private void drawScores(Graphics2D g2d) {
		Bowler[] bowlers = model.getBowlers();
		if (bowlers[model.getBowler()] != null && !model.isGameOver()) {
			Rectangle r = logicalRectangles.getScoreRectangle(model.getBowler(), model.getFrame());
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		
		if (bowlers[model.getBowler()] != null) {
			g2d.setColor(Color.BLUE);
			String[] allMarks = model.getMarks();
			for (int index = 0; index < model.getNumberOfBowlers(); index++) {
				String marks = allMarks[index];
				for (int jndex = 0; jndex < marks.length(); jndex++) {
					char c = marks.charAt(jndex);
					int frame = jndex / 2;
					int mark = jndex % 2;
					
					if (frame > 9) {
						frame = 9;
						mark = marks.length() - 19;
					}
					
					Rectangle r = logicalRectangles.getThrowRectangle(index, frame, mark);
					drawCharacter(g2d, r, c);
				}
			}
			
			logicalRectangles.createGrandTotalRectangles(model.getNumberOfBowlers());
			Font font = g2d.getFont().deriveFont(Font.BOLD, 24f);
			g2d.setFont(font);
			int grandTotal = 0;
			int handicapSum = 0;
			int handicapGrandTotal = 0;
			Bowler[] bowlerInfo = model.getBowlers();
			BowlingScore[] scores = model.getScores();
			for (int bowler = 0; bowler < model.getNumberOfBowlers(); bowler++) {
				BowlingScore score = scores[bowler];
				String marks = allMarks[bowler];
				int[] values = score.calculateScore(marks);
				for (int frame = 0; frame < values.length; frame++) {
					int value = values[frame];
					Rectangle r = (Rectangle) logicalRectangles.getScoreRectangle(
							bowler, frame).clone();
					r.y += throwScore.height - 10;
					if (value >= 0) {
						drawCenteredString(g2d, Integer.toString(value), r);
					}
				}
				
				int total = score.getTotalScore();
				int handicap = bowlerInfo[bowler].getHandicap();
				int handicapTotal = handicap + total;
				Rectangle r = (Rectangle) logicalRectangles.getTotalRectangle(bowler).clone();
				r.y += throwScore.height - 10;
				if (total > 0) {
					drawCenteredString(g2d, Integer.toString(total), r);
				}
				r = (Rectangle) logicalRectangles.gethTotalRectangle(bowler).clone();
				r.y += throwScore.height - 10;
				if (handicap > 0) {
					drawCenteredString(g2d, Integer.toString(handicapTotal), r);
				}
				grandTotal += total;
				handicapSum += handicap;
				handicapGrandTotal += handicapTotal;
			}
			
			Rectangle r = (Rectangle) logicalRectangles.getGrandTotalRectangle().clone();
			r.y += throwScore.height - 20;
			if (grandTotal > 0) {
				drawCenteredString(g2d, Integer.toString(grandTotal), r);
			}
			r = (Rectangle) logicalRectangles.gethGrandTotalRectangle().clone();
			r.y += throwScore.height - 20;
			if (handicapSum > 0) {
				drawCenteredString(g2d, Integer.toString(handicapGrandTotal), r);
			}
		}
	}
	
	private void drawCharacter(Graphics2D g2d, Rectangle r, char c) {
		g2d.setStroke(new BasicStroke(3f));
		if (c == 'X') {
			g2d.drawLine(r.x, r.y, r.x + r.width, r.y + r.height);
			g2d.drawLine(r.x, r.y + r.height, r.x + r.width, r.y);
		} else if (c == '/') {
			g2d.drawLine(r.x, r.y + r.height, r.x + r.width, r.y);
		} else {
			String s = Character.toString(c);
			drawCenteredString(g2d, s, r);
		}
	}
	
	private void drawCenteredString(Graphics2D g2d, String s, Rectangle r) {
	    FontMetrics fm = g2d.getFontMetrics();
	    int x = r.x + (r.width - fm.stringWidth(s)) / 2;
	    int y = r.y + (fm.getAscent() + (r.height - (fm.getAscent() + fm.getDescent())) / 2);
	    g2d.drawString(s, x, y);
	}

}
