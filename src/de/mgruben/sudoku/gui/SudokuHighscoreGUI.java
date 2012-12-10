package de.mgruben.sudoku.gui;
import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import de.mgruben.sudoku.documents.SudokuXMLDocument;


public class SudokuHighscoreGUI {
	private SudokuGUI sudokuGUI;
		// fürs Anzeigen der Highscore
		private JDialog highDia = new JDialog(sudokuGUI.getFrame(), "Highscore");
		private JPanel highDiaPane = new JPanel();
		private JButton locationHighBut = new JButton("Durchsuchen");
		private JButton closeHigh = new JButton("Schließen");
		private JTabbedPane highTabbedPane = new JTabbedPane();
		private JPanel easyPan = new JPanel();
		private JPanel medPan = new JPanel();
		private JPanel hardPan = new JPanel();
		private JTable easyTab;
		private JScrollPane easyPanScr = new JScrollPane();
		private JTable medTab;
		private JScrollPane medPanScr = new JScrollPane();
		private JTable hardTab;
		private JScrollPane hardPanScr = new JScrollPane();
		private SudokuActionListener sudokuActionListener;
		private SudokuXMLDocument sudokuXMLDocument;
	
	public void buildHighscoreDia() {
		getHighDia().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		getHighDia().setTitle("Bestenliste");
		setEasyTab(new JTable(sudokuXMLDocument.getHighWithVal("easy")));
		getEasyTab().setAutoCreateRowSorter(true);
		easyPan.setLayout(new BoxLayout(easyPan, BoxLayout.Y_AXIS));
		easyPan.add(getEasyTab().getTableHeader());
		easyPanScr.setViewportView(getEasyTab());
		easyPan.add(easyPanScr);
		setMedTab(new JTable(sudokuXMLDocument.getHighWithVal("medium")));
		getMedTab().setAutoCreateRowSorter(true);
		medPan.setLayout(new BoxLayout(medPan, BoxLayout.Y_AXIS));
		medPan.add(getMedTab().getTableHeader());
		medPanScr.setViewportView(getMedTab());
		medPan.add(medPanScr);
		setHardTab(new JTable(sudokuXMLDocument.getHighWithVal("hard")));
		getHardTab().setAutoCreateRowSorter(true);
		hardPan.setLayout(new BoxLayout(hardPan, BoxLayout.Y_AXIS));
		hardPan.add(getHardTab().getTableHeader());
		hardPanScr.setViewportView(getHardTab());
		hardPan.add(hardPanScr);
		highTabbedPane.addTab("Einfach", easyPan);
		highTabbedPane.addTab("Mittel", medPan);
		highTabbedPane.addTab("Schwer", hardPan);
		highDiaPane.setLayout(new BoxLayout(highDiaPane, BoxLayout.Y_AXIS));
		highDiaPane.add(highTabbedPane);
		getCloseHigh().addActionListener(sudokuActionListener);
		highDiaPane.add(getCloseHigh(), BorderLayout.SOUTH);
		getHighDia().add(highDiaPane);
		getHighDia().setLocation(sudokuGUI.getFrame().getLocation());
		getHighDia().pack();
		getHighDia().setVisible(false);
	}

	public JButton getLocationHighBut() {
		return locationHighBut;
	}

	public void setLocationHighBut(JButton locationHighBut) {
		this.locationHighBut = locationHighBut;
	}

	public JTable getEasyTab() {
		return easyTab;
	}

	public void setEasyTab(JTable easyTab) {
		this.easyTab = easyTab;
	}

	public JTable getMedTab() {
		return medTab;
	}

	public void setMedTab(JTable medTab) {
		this.medTab = medTab;
	}

	public JTable getHardTab() {
		return hardTab;
	}

	public void setHardTab(JTable hardTab) {
		this.hardTab = hardTab;
	}

	public JDialog getHighDia() {
		return highDia;
	}

	public void setHighDia(JDialog highDia) {
		this.highDia = highDia;
	}

	public JButton getCloseHigh() {
		return closeHigh;
	}

	public void setCloseHigh(JButton closeHigh) {
		this.closeHigh = closeHigh;
	}

}
