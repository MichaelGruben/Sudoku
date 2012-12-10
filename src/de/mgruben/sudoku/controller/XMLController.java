package de.mgruben.sudoku.controller;

import java.awt.Color;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.mgruben.sudoku.documents.SudokuXMLDocument;
import de.mgruben.sudoku.gui.SudokuGUI;

public class XMLController {
	JFrame frame;
	JFileChooser fs;
	File sFile;
	SudokuXMLDocument gameData;
	JTextField[][] fieldGUI;
	int size;
	int showAnz;
	JLabel time;
	int score;
	String difficultyVal;
	String highLoc;
	int toWin;
	JMenuItem saveBut;
	private GameController contrGame;
	JLabel scoreAnz;
	JTextField player;
	Integer[][] fieldGUIInt;
	private SudokuGUI sudokuGUI;
	private Color startValCol;

	public XMLController(SudokuGUI sudokuGUI) {
		this.sudokuGUI=sudokuGUI;
		this.frame = sudokuGUI.getFrame();
		this.fs = sudokuGUI.getFs();
		this.sFile = sudokuGUI.getsFile();
		this.gameData = sudokuGUI.getGameData();
		this.fieldGUI = sudokuGUI.getFieldGUI();
		this.size = sudokuGUI.getSize();
		this.showAnz = sudokuGUI.getShowAnz();
		this.time = sudokuGUI.getSudokuStatusBar().getTime();
		this.score = sudokuGUI.getScore();
		this.difficultyVal = sudokuGUI.getDifficultyVal();
		this.highLoc = sudokuGUI.getHighscoreFileLocation();
		this.toWin = sudokuGUI.getToWin();
		this.saveBut = sudokuGUI.getSaveBut();
		this.contrGame = sudokuGUI.getContrGame();
		this.scoreAnz = sudokuGUI.getScoreAnz();
		this.player = sudokuGUI.getPlayer();
		this.fieldGUIInt = sudokuGUI.getFieldGUIInt();
		this.startValCol = sudokuGUI.getStartValCol();
	}

	public void saveAs(boolean newFile) {
		if (newFile) {
			contrGame.setPause();
			fs.showSaveDialog(frame);
			try {
				sFile = fs.getSelectedFile();
			} catch (java.lang.NullPointerException e) {
				System.out.println(e);
				return;
			}
		}
		gameData = new SudokuXMLDocument(sFile.toString(), fieldGUI, size, showAnz,
				System.getProperty("user.name"), time.getText(), score,
				difficultyVal, highLoc, toWin);
		if (newFile) {
			if (sFile.exists()) {
				int result = JOptionPane.showConfirmDialog(frame,
						sFile.getAbsolutePath() + " �berschreiben?",
						"�berschreiben", JOptionPane.YES_NO_OPTION);
				if (result != JOptionPane.YES_OPTION) {
					contrGame.setPause();
					return;
				}
			}
			contrGame.setPause();
			saveBut.setEnabled(true);
		}
	}

	public void load() {
		contrGame.setPause();
		fs.showOpenDialog(frame);
		sFile = fs.getSelectedFile();
		gameData = new SudokuXMLDocument(fs.getSelectedFile(), size);
		score = gameData.pScore;
		scoreAnz.setText("Punkte: " + String.valueOf(score));
		player.setText(gameData.pPlayer);
		size = gameData.pSize;
		showAnz = gameData.pShowAnz;
		String[] timeStr = Pattern.compile(":").split(gameData.pTime);
		sudokuGUI.getZeit().sek = Integer.valueOf(timeStr[2].trim());
		sudokuGUI.getZeit().min = Integer.valueOf(timeStr[1].trim());
		sudokuGUI.getZeit().h = Integer.valueOf(timeStr[0].trim());
		time.setText(gameData.pTime);
		difficultyVal = gameData.pDifficultyVal;
		highLoc = gameData.pHighLoc;
		toWin = gameData.pToWin;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fieldGUI[i][j].setText("");
				fieldGUI[i][j].setEditable(true);
				fieldGUI[i][j].setEnabled(true);
				fieldGUI[i][j].setFocusable(true);
				fieldGUIInt[i][j] = 0;
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (gameData.pField[i][j].getText().equals("0"))
					continue;
				fieldGUI[i][j].setText(gameData.pField[i][j].getText());
				fieldGUI[i][j].setForeground(gameData.pField[i][j]
						.getForeground());
				if (gameData.pField[i][j].getForeground().equals(startValCol)) {
					fieldGUI[i][j].setEditable(false);
					fieldGUI[i][j].setFocusable(false);
				}
			}
		}
		fieldGUI[0][0].requestFocus();
		sudokuGUI.colorCross(0, 0);
		saveBut.setEnabled(true);
		// runTimer();
	}
}
