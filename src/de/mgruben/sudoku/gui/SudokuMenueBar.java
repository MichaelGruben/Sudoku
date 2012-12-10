package de.mgruben.sudoku.gui;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class SudokuMenueBar {

	
	// Men�
		private JMenuBar menueBar = new JMenuBar();
		// Startmen�
		private JMenu startMenu = new JMenu("Spiel");
		// Startmen�inhalte
		private JMenuItem startBut = new JMenuItem("Neu | (F2)");
		private JMenuItem resetBut = new JMenuItem("Nochmal | (F3)");
		private JMenuItem pauseBut = new JMenuItem("Pause | (p)");
		private JMenuItem showHigh = new JMenuItem("Bestenliste | (b)");
		private JMenuItem saveBut = new JMenuItem("Speichern | (s)");
		private JMenuItem saveUnderBut = new JMenuItem("Speichern unter | (Strg+s)");
		private JMenuItem loadBut = new JMenuItem("Laden | (Strg+l)");
		private JMenuItem exitBut = new JMenuItem("Beenden | (Alt+F4)");
		public JMenuBar getMenueBar() {
			return menueBar;
		}
		public void setMenueBar(JMenuBar menueBar) {
			this.menueBar = menueBar;
		}
		public JMenu getStartMenu() {
			return startMenu;
		}
		public void setStartMenu(JMenu startMenu) {
			this.startMenu = startMenu;
		}
		public JMenuItem getPauseBut() {
			return pauseBut;
		}
		public void setPauseBut(JMenuItem pauseBut) {
			this.pauseBut = pauseBut;
		}
		public JMenuItem getStartBut() {
			return startBut;
		}
		public void setStartBut(JMenuItem startBut) {
			this.startBut = startBut;
		}
		public JMenuItem getResetBut() {
			return resetBut;
		}
		public void setResetBut(JMenuItem resetBut) {
			this.resetBut = resetBut;
		}
		public JMenuItem getExitBut() {
			return exitBut;
		}
		public void setExitBut(JMenuItem exitBut) {
			this.exitBut = exitBut;
		}
		public JMenuItem getSaveUnderBut() {
			return saveUnderBut;
		}
		public void setSaveUnderBut(JMenuItem saveUnderBut) {
			this.saveUnderBut = saveUnderBut;
		}
		public JMenuItem getShowHigh() {
			return showHigh;
		}
		public void setShowHigh(JMenuItem showHigh) {
			this.showHigh = showHigh;
		}
		public JMenuItem getLoadBut() {
			return loadBut;
		}
		public void setLoadBut(JMenuItem loadBut) {
			this.loadBut = loadBut;
		}
		public JMenuItem getSaveBut() {
			return saveBut;
		}
		public void setSaveBut(JMenuItem saveBut) {
			this.saveBut = saveBut;
		}
}
