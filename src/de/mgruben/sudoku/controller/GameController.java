package de.mgruben.sudoku.controller;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.mgruben.sudoku.backend.GameBoard;
import de.mgruben.sudoku.gui.SudokuGUI;


public class GameController {
	JPanel centerPan;
	boolean isPaused=false;
	JTextField[][] fieldGUI;
	Integer cacheX;
	Integer cacheY;
	int size;
	Color startValCol;
	JDialog wonDia;
	int score;
	int showAnz;
	private Integer[][] fieldGUIInt;
	private JMenuItem resetBut;
	private SudokuGUI sudokuGUI;
	
	public GameController(SudokuGUI sudokuGUI) {
		this.sudokuGUI=sudokuGUI;
		this.centerPan = sudokuGUI.getCenterPan();
		this.isPaused = checkIfGameIsPaused();
		this.fieldGUI = sudokuGUI.getFieldGUI();
		this.cacheX = sudokuGUI.getCacheX();
		this.cacheY = sudokuGUI.getCacheY();
		this.size = sudokuGUI.getSize();
		this.startValCol = sudokuGUI.getStartValCol();
		this.wonDia = sudokuGUI.getWonDia();
		this.score = sudokuGUI.getScore();
		this.showAnz = sudokuGUI.getShowAnz();
		this.fieldGUIInt = sudokuGUI.getFieldGUIInt();
		this.resetBut = sudokuGUI.getResetBut();
	}

	public GameController() {
		// TODO Auto-generated constructor stub
	}

	public void setPause() {
		CardLayout cl = (CardLayout) (centerPan.getLayout());
		if (!isPaused) {
			cl.show(centerPan, "PausePanel");
			isPaused = true;
		} else {
			cl.show(centerPan, "GameArea");
			isPaused = false;
			fieldGUI[cacheX][cacheY].requestFocus();
		}
	}

	public void resetGame() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!fieldGUI[i][j].getForeground().equals(startValCol)) {
					fieldGUI[i][j].setText("");
					fieldGUIInt[i][j] = 0;
				}
			}
			// runTimer(); //Timer zurücksetzen oder nicht?
		}
	}

	public void startNewGame() {
		if(null != wonDia)
		wonDia.setVisible(false);
		score = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fieldGUI[i][j].setText("");
				fieldGUI[i][j].setEditable(true);
				fieldGUI[i][j].setEnabled(true);
				fieldGUI[i][j].setFocusable(true);
				fieldGUIInt[i][j] = 0;
			}
		}
		
		if (null != resetBut)
			resetBut.setEnabled(true);
		
		GameBoard spiel = new GameBoard(size);
		for (int k = 0; k < showAnz; k++) {
			int i = (int) (Math.random() * size);
			int j = (int) (Math.random() * size);
			try {
				if (!fieldGUI[i][j].getText().equals(""))
					k--;
				fieldGUI[i][j].setText(spiel.field[i][j] + "");
				fieldGUI[i][j].setForeground(startValCol);
				fieldGUI[i][j].setEditable(false);
				fieldGUI[i][j].setFocusable(false);
			} catch (ArrayIndexOutOfBoundsException e2) {
				k--;
			}
		}
		cacheX = 0;
		cacheY = 0;
		fieldGUI[0][0].requestFocus();
		sudokuGUI.colorCross(0, 0);
		runTimer();
	}
	private void runTimer() {
		sudokuGUI.getZeit().sek = 0;
		sudokuGUI.getZeit().min = 0;
		sudokuGUI.getZeit().h = 0;
	}

	public boolean checkIfGameIsPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = !isPaused;
	}
}