package de.mgruben.sudoku.gui;
import java.awt.Color;

public enum SudokuColor {
	rightCol {public Color getColor(){return new Color(80, 200, 80);}}, 
	wrongCol {public Color getColor(){return new Color(255,0,0);}}, 
	borderCol {public Color getColor(){return new Color(0,0,0);}},
	startValCol {public Color getColor(){return new Color(0,0,255);}},
	crossSelCol {public Color getColor(){return new Color(200, 255, 200);}},
	backCol {public Color getColor(){return new Color(230, 255, 200);}};

	public Color getColor() {
		// TODO Auto-generated method stub
		return this.getColor();
	};
}
