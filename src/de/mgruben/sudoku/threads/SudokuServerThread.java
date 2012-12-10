package de.mgruben.sudoku.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import de.mgruben.sudoku.gui.SudokuGUI;

public class SudokuServerThread extends Thread {
	Socket sverb;
	int nverb;
	SudokuGUI parent;

	public SudokuServerThread(Socket s, int n, SudokuGUI parent2) {
		this.sverb = s;
		this.nverb = n;
		this.parent = parent2;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sverb.getInputStream()));
			PrintStream out = new PrintStream(sverb.getOutputStream());
			out.println("Hallo - Willkommen beim Sudoku!\r(type 'exit' for quit)\n");
			boolean weiter = true;
			while (weiter) {
				String str = in.readLine();
				if (str == null)
					weiter = false;
				else {
					try {
						out.println(parent.fillVal(
								Integer.parseInt(str.charAt(0) + ""),
								Integer.parseInt(str.charAt(1) + ""),
								str.charAt(2)));
					} catch (Exception e) {
						System.out.println(e);
					}
					weiter = !(str.trim().equals("exit"));
				}
			}
			sverb.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
