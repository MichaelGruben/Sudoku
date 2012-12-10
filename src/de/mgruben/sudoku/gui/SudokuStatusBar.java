package de.mgruben.sudoku.gui;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SudokuStatusBar {

	private JPanel statusBar = new JPanel();
	// Zeitanzeige in der Statusleiste
	private JLabel time = new JLabel("00:00:00");
	private Timer timer = new Timer();
//	private UhrzeitThread zeit = new UhrzeitThread();
	// Punkteanzeige in der Statusleiste
	private JLabel scoreAnz = new JLabel("Punkte: 0");
	private int score = 0;
	public JPanel getStatusBar() {
		return statusBar;
	}
	public void setStatusBar(JPanel statusBar) {
		this.statusBar = statusBar;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public JLabel getScoreAnz() {
		return scoreAnz;
	}
	public void setScoreAnz(JLabel scoreAnz) {
		this.scoreAnz = scoreAnz;
	}
	public JLabel getTime() {
		return time;
	}
	public void setTime(JLabel time) {
		this.time = time;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
