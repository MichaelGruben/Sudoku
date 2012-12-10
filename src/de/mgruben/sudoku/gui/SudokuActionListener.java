package de.mgruben.sudoku.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.mgruben.sudoku.controller.GameController;
import de.mgruben.sudoku.controller.XMLController;
import de.mgruben.sudoku.documents.SudokuXMLDocument;

@SuppressWarnings("serial")
public class SudokuActionListener extends ActionEvent implements ActionListener {

	private PropertiesMenuGUI sudokuPropertiesMenuGUI;
	private SudokuGUI sudokuGUI;
	private SudokuXMLDocument sudokuXMLDocument;
	private SudokuHighscoreGUI sudokuHighscoreGUI;
	private SudokuMenueBar sudokuMenueBar;
	private SudokuWonDia sudokuWonDia;
	private GameController sudokuGameController;
	private XMLController sudokuXMLController;
	private SudokuHelpMenu sudokuHelpMenu;

	public SudokuActionListener(Object source, int id, String command) {
		super(source, id, command);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object scr = e.getSource();

		// individuelle Funktionen
		if (scr == sudokuPropertiesMenuGUI.getEasyCheck()) {
			sudokuGUI.setShowAnz(sudokuPropertiesMenuGUI.getiEasy());
			sudokuGUI.setDifficultyVal("easy");
		} else if (scr == sudokuPropertiesMenuGUI.getMediumCheck()) {
			sudokuGUI.setShowAnz(sudokuPropertiesMenuGUI.getiMed());
			sudokuGUI.setDifficultyVal("medium");
		} else if (scr == sudokuPropertiesMenuGUI.getHardCheck()) {
			sudokuGUI.setShowAnz(sudokuPropertiesMenuGUI.getiHard());
			sudokuGUI.setDifficultyVal("hard");
		} else if (scr == sudokuPropertiesMenuGUI.getPrefsMen()) {
			sudokuPropertiesMenuGUI.getPrefsDia().setVisible(true);
		} else if (scr == sudokuPropertiesMenuGUI.getPrefsOK()
				|| scr == sudokuPropertiesMenuGUI.getPrefsAssume()) {
			sudokuGUI.setHighscoreLocation(sudokuPropertiesMenuGUI
					.getLocationHigh().getText());
			sudokuPropertiesMenuGUI.setiEasy(Integer
					.parseInt(sudokuPropertiesMenuGUI.getEasyText().getText()));
			sudokuPropertiesMenuGUI.setiMed(Integer
					.parseInt(sudokuPropertiesMenuGUI.getMedText().getText()));
			sudokuPropertiesMenuGUI.setiHard(Integer
					.parseInt(sudokuPropertiesMenuGUI.getHardText().getText()));
			sudokuHighscoreGUI.getEasyTab().setModel(
					sudokuXMLDocument.getHighWithVal("easy"));
			sudokuHighscoreGUI.getMedTab().setModel(
					sudokuXMLDocument.getHighWithVal("medium"));
			sudokuHighscoreGUI.getHardTab().setModel(
					sudokuXMLDocument.getHighWithVal("hard"));
		} else if (scr == sudokuHighscoreGUI.getCloseHigh()) {
			sudokuHighscoreGUI.getHighDia().setVisible(false);
		} else if (scr == sudokuMenueBar.getShowHigh()
				|| scr == sudokuWonDia.getHighBut()) {
			sudokuHighscoreGUI.getHighDia().setVisible(true);
		} else if (scr == sudokuMenueBar.getResetBut())
			sudokuGameController.resetGame();
		else if (scr == sudokuMenueBar.getSaveUnderBut())
			sudokuXMLController.saveAs(true);
		else if (scr == sudokuHighscoreGUI.getLocationHighBut()) {
			sudokuGUI.getFs().showSaveDialog(sudokuGUI.getFrame());
			sudokuPropertiesMenuGUI.getLocationHigh().setText(
					sudokuGUI.getFs().getSelectedFile().toString());
		} else if (scr == sudokuMenueBar.getLoadBut()) {
			sudokuXMLController.load();
		} else if (scr == sudokuMenueBar.getSaveBut()) {
			sudokuXMLController.saveAs(false);
		} else if (scr == sudokuHelpMenu.getShMan()) {
			try {
				Desktop.getDesktop().open(new File("SudokuHandbuch.pdf"));
			} catch (IOException e2) {
			} catch (java.lang.IllegalArgumentException e2) {
				JOptionPane
						.showMessageDialog(
								sudokuGUI.getFrame(),
								"Das Handbuch befindet sich nicht im Programmverzeichnis! (Datei: SudokuHandbuch.pdf)",
								"Handbuch nicht gefunden",
								JOptionPane.WARNING_MESSAGE);
			}
		} else if (scr == sudokuPropertiesMenuGUI.getFreigeben()) {
			sudokuPropertiesMenuGUI.freigabe();
		}

		// gemeinschaftliche Funktionen
		if (scr == sudokuPropertiesMenuGUI.getEasyCheck()
				|| scr == sudokuPropertiesMenuGUI.getMediumCheck()
				|| scr == sudokuPropertiesMenuGUI.getHardCheck()
				|| scr == sudokuMenueBar.getStartBut()
				|| scr == sudokuWonDia.getNewGame())
			sudokuGameController.startNewGame();
		if (scr == sudokuMenueBar.getPauseBut()
				|| scr == sudokuPropertiesMenuGUI.getPrefsOK()
				|| scr == sudokuPropertiesMenuGUI.getPrefsAbort()
				|| scr == sudokuHighscoreGUI.getCloseHigh()
				|| scr == sudokuMenueBar.getShowHigh() || scr == sudokuWonDia.getHighBut()
				|| scr == sudokuPropertiesMenuGUI.getPrefsMen())
			sudokuGameController.setPause();
		if (scr == sudokuPropertiesMenuGUI.getPrefsOK()
				|| scr == sudokuPropertiesMenuGUI.getPrefsAbort())
			sudokuPropertiesMenuGUI.getPrefsDia().setVisible(false);
		if (scr == sudokuMenueBar.getExitBut() || scr == sudokuWonDia.getExitGame())
			System.exit(0);
	}

}
