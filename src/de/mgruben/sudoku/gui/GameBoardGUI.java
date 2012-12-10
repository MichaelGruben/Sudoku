package de.mgruben.sudoku.gui;
import javax.swing.JTextField;


public class GameBoardGUI {
	// Das Spielfeld
		private int size = 9;
		// alle Felder des Spiels als Textfeld
		private JTextField[][] fieldGUI = new JTextField[getSize()][getSize()];
		// ... und die dazugeh�rigen Inhalte (Ziffern von 0 bis size)
		private Integer[][] fieldGUIInt = new Integer[getSize()][getSize()];
		public JTextField[][] getFieldGUI() {
			return fieldGUI;
		}
		public void setFieldGUI(JTextField[][] fieldGUI) {
			this.fieldGUI = fieldGUI;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public Integer[][] getFieldGUIInt() {
			return fieldGUIInt;
		}
		public void setFieldGUIInt(Integer[][] fieldGUIInt) {
			this.fieldGUIInt = fieldGUIInt;
		}
}
