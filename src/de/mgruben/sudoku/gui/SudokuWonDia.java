package de.mgruben.sudoku.gui;

import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.mgruben.sudoku.documents.SudokuXMLDocument;

public class SudokuWonDia {
	private SudokuGUI sudokuGUI;
	// ...wenn gewonnen
	private JDialog wonDia = new JDialog(sudokuGUI.getFrame(), "!!Gewonnen!!");
	private JTextArea wonText = new JTextArea();
	private JPanel buttonPan = new JPanel();
	private JButton newGame = new JButton("Neu");
	private JButton exitGame = new JButton("Schließen");
	private JButton highBut = new JButton("Bestenliste");
	private JTextField player = new JTextField(System.getProperty("user.name"));
	private int toWin = sudokuGUI.getSize() * sudokuGUI.getSize();
	private SudokuActionListener sudokuActionListener;
	private SudokuStatusBar sudokuStatusBar;
	private SudokuXMLDocument sudokuXMLDocument;
	private SudokuHighscoreGUI sudokuHighscoreGUI;

	public void buildWonDia() {
		getNewGame().addActionListener(sudokuActionListener);
		getExitGame().addActionListener(sudokuActionListener);
		getHighBut().addActionListener(sudokuActionListener);
		getWonDia().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		getWonDia().setLocation(sudokuGUI.getFrame().getLocation());
		getWonDia().setLayout(new FlowLayout(FlowLayout.CENTER));
		JTextArea statsFor = new JTextArea("Statistik f�r: ");
		statsFor.setFocusable(false);
		getWonDia().add(statsFor);
		getWonDia().add(getPlayer());
		getWonDia().add(wonText);
		buttonPan.add(getNewGame());
		buttonPan.add(getExitGame());
		buttonPan.add(getHighBut());
		getWonDia().add(buttonPan);
		getWonDia().setSize(300, 150);
	}

	public void playerWonAndFinish() {
		sudokuStatusBar.getTimer().cancel();
		sudokuXMLDocument.updateFile(sudokuGUI.getDifficultyVal(),
				sudokuStatusBar.getScore(), sudokuStatusBar.getTime().getText()
						.toString(), getPlayer().getText());
		sudokuHighscoreGUI.getEasyTab().setModel(sudokuXMLDocument.getHighWithVal("easy"));
		sudokuHighscoreGUI.getMedTab().setModel(sudokuXMLDocument.getHighWithVal("medium"));
		sudokuHighscoreGUI.getHardTab().setModel(sudokuXMLDocument.getHighWithVal("hard"));
		for (int i = 0; i < sudokuGUI.getSize(); i++) {
			for (int j = 0; j < sudokuGUI.getSize(); j++)
				sudokuGUI.getFieldGUI()[i][j].setEnabled(false);
		}
		wonText.setText("Schwierigkeitsgrad:\t" + sudokuGUI.getDifficultyVal()
				+ "\nBenötigte Zeit (in hh:mm:ss):\t" + sudokuStatusBar.getTime().getText()
				+ "\nErreichte Punktzahl:\t" + sudokuStatusBar.getScore());
		sudokuGUI.getResetBut().setEnabled(false);
		getWonDia().setVisible(true);
		sudokuStatusBar.getTimer().purge();
	}

	public int getToWin() {
		return toWin;
	}

	public void setToWin(int toWin) {
		this.toWin = toWin;
	}

	public void setPlayer(JTextField player) {
		this.player = player;
	}

	public JTextField getPlayer() {
		return player;
	}

	public void setWonDia(JDialog wonDia) {
		this.wonDia = wonDia;
	}

	public JDialog getWonDia() {
		return wonDia;
	}

	public JButton getHighBut() {
		return highBut;
	}

	public void setHighBut(JButton highBut) {
		this.highBut = highBut;
	}

	public JButton getNewGame() {
		return newGame;
	}

	public void setNewGame(JButton newGame) {
		this.newGame = newGame;
	}

	public JButton getExitGame() {
		return exitGame;
	}

	public void setExitGame(JButton exitGame) {
		this.exitGame = exitGame;
	}
}
