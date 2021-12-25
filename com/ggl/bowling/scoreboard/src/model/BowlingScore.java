package com.ggl.bowling.scoreboard.model;

public class BowlingScore {
	
	private int[] score;
	
	public BowlingScore() {
		this.score = new int[10];
	}
	
	public int[] calculateScore(String marks) {
		String[] frames = createFrames(marks);
		this.score = calculateFrames(frames);
		return score;
	}
	
	private String[] createFrames(String marks) {
		String[] frames = { "", "", "", "", "", "", "", "", "", "" };
		
		int mark = 0;
		int frame = 0;
		while (mark < marks.length()) {
			StringBuilder builder = new StringBuilder();
			builder.append(marks.charAt(mark++));
			if (mark < marks.length()) {
				builder.append(marks.charAt(mark++));
			}
			
			if (marks.length() > 20 && mark == 20) {
				builder.append(marks.charAt(mark++));
			} 
			
			frames[frame++] = builder.toString();
		}
		
		return frames;
	}
	
	private int[] calculateFrames(String[] frames) {
		int[] scores = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		
		for (int frame = 0; frame < frames.length; frame++) {
			String currentFrame = frames[frame];
			if (!currentFrame.isEmpty()) {
				int frameScore = 0;
				int value1 = valueOf(currentFrame, 0);
				int value2 = valueOf(currentFrame, 1);
				int value3 = valueOf(currentFrame, 2);
				
				if (frame < 9) {
					if (value1 == 30) {
						frameScore = 10 + getNextTwoMarks(frames, frame);
					} else if (value2 == 20) {
						frameScore = 10 + getNextMark(frames, frame);
					} else {
						frameScore = value1 + value2;
					}
				} else {
					frameScore = tenthFrameScore(value1, value2, value3);
				}
				
				scores[frame] = frameScore;
				if (frame > 0) {
					scores[frame] += scores[frame - 1];
				}
			}
		}
		
		return scores;
	}

	private int tenthFrameScore(int value1, int value2, int value3) {
		int frameScore;
		
		if (value1 == 30) {
			if (value2 == 30) {
				if (value3 == 30) {
					frameScore = 30;
				} else  {
					frameScore = 20 + value3;
				}
			} else if (value2 == 20) {
				frameScore = 10 + value3;
			} else {
				if (value3 == 20) {
					frameScore = 20;
				} else {
					frameScore = 10 + value2 + value3;
				}
			}
		} else {
			if (value2 == 20) {
				frameScore = 10 + value3;
			} else {
				frameScore = value1 + value2;
			}
		}
		
		return frameScore;
	}
	
	private int getNextTwoMarks(String[] frames, int frame) {
		int nextFrame = frame + 1;
		String currentFrame = frames[nextFrame];
		
		if (currentFrame.isEmpty()) {
			return 0;
		}

		int value1 = valueOf(currentFrame, 0);
		int value2 = valueOf(currentFrame, 1);
		
		if (value1 == 30) {
			if (nextFrame < 9) {
				return 10 + getNextMark(frames, nextFrame);
			} else {
				if (value2 == 30) {
					return 20;
				} else {
					return 10 + value2;
				}
			}
		} else {
			if (value2 == 20) {
				return 10;
			} else {
				return value1 + value2;
			}
		}
	}
	
	private int getNextMark(String[] frames, int frame) {
		int nextFrame = frame + 1;
		
		if (frames[nextFrame].isEmpty()) {
			return 0;
		}
		
		int value = valueOf(frames[nextFrame], 0);
		if (value == 30) {
			return 10;
		} else {
			return value;
		}
	}
	
	private int valueOf(String frame, int position) {
		char c = (frame.length() > position) ? frame.charAt(position) : ' ';

		if (c == 'X') {
			return 30;
		} else if (c == '/') {
			return 20;
		} else if (c == '-' || c == ' ') {
			return 0;
		} else {
			return Integer.valueOf(Character.toString(c));
		}
	}
	
	public int getTotalScore() {
		int total = 0;
		
		for (int index = score.length - 1; index >= 0; index--) {
			if (score[index] >= 0) {
				return score[index];
			}
		}
		
		return total;
	}

	public int[] getScore() {
		return score;
	}

}
