package de.mgruben.sudoku.threads;

import java.util.TimerTask;

import de.mgruben.sudoku.controller.GameController;
import de.mgruben.sudoku.gui.SudokuStatusBar;

	public class UhrzeitThread extends TimerTask {
		public int sek = 0;
		public int min = 0;
		public int h = 0;
		private String sn = "";
		private String mn = "";
		private String hn = "";
		private String answer = "";
		private SudokuStatusBar sudokuStatusBar;
		private GameController sudokuGameController;

		@Override
		public void run() {
			if (!sudokuGameController.checkIfGameIsPaused()) {
				if (sek <= 59) {
					sek++;
				} else {
					sek = 0;
					if (min <= 59) {
						min++;
					} else {
						min = 0;
						h++;
					}
				}
				if (h < 10)
					hn = "0";
				else
					hn = "";
				if (min < 10)
					mn = "0";
				else
					mn = "";
				if (sek < 10)
					sn = "0";
				else
					sn = "";
				answer = hn + h + " : " + mn + min + " : " + sn + sek;
				sudokuStatusBar.getTime().setText(answer);
			}
		}
	}