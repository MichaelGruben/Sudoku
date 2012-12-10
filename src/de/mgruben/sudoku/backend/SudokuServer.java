package de.mgruben.sudoku.backend;

import java.net.ServerSocket;
import java.net.Socket;

import de.mgruben.sudoku.gui.SudokuGUI;
import de.mgruben.sudoku.threads.SudokuServerThread;

public class SudokuServer extends Thread {
	public ServerSocket servSock;
	public Socket sverb;
	private SudokuGUI parent;

	public SudokuServer(SudokuGUI parent2) {
		this.parent = parent2;
	}

	public void run() {
		int anz = 1;
		System.out.println("beforetry");
		try {
			System.out.println("before declare servsock");
			servSock = new ServerSocket(7788);
			System.out.println("declared servsock");
			while (true) {
				sverb = servSock.accept();
				System.out.println("Verbindung: " + anz);
				new SudokuServerThread(sverb, anz, parent).start();
				anz++;
			}
		} catch (Exception e) {
			System.out.println("exeption in server");
			System.out.println(e);
		}
	}
}
