package de.mgruben.sudoku.gui;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class SudokuHelpMenu {
	
	private JMenu helpMenu = new JMenu("Hilfe");
	private JMenuItem shMan = new JMenuItem("Handbuch �ffnen...");
	private SudokuActionListener sudokuActionListener;
	
	public void buildHelpMenu() {

		getShMan().addActionListener(sudokuActionListener);
		getHelpMenu().add(getShMan());
	}

	public JMenu getHelpMenu() {
		return helpMenu;
	}

	public void setHelpMenu(JMenu helpMenu) {
		this.helpMenu = helpMenu;
	}

	public JMenuItem getShMan() {
		return shMan;
	}

	public void setShMan(JMenuItem shMan) {
		this.shMan = shMan;
	}

}
