package de.mgruben.sudoku.gui;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import de.mgruben.sudoku.backend.SudokuServer;

public class PropertiesMenuGUI {
		// Einstellungen
		private int iEasy = 55;
		private int iMed = 40;
		private int iHard = 25;		
		
		// Einstellungen - Menü
		private JMenu propsMenu = new JMenu("Einstellungen");
		
		// Einstellungen - Menü Inhalte		
		private JMenu difficulty = new JMenu("Schwierigkeit");
		private ButtonGroup btnGrp = new ButtonGroup();
		private JRadioButtonMenuItem easyCheck = new JRadioButtonMenuItem("Einfach");
		private JRadioButtonMenuItem mediumCheck = new JRadioButtonMenuItem("Mittel", true);
		private JRadioButtonMenuItem hardCheck = new JRadioButtonMenuItem("Schwer");
		private JCheckBoxMenuItem freigeben = new JCheckBoxMenuItem("Spiel freigeben", false);
		private JMenuItem prefsMen = new JMenuItem("Einstellungen...");
		private JDialog prefsDia;
		private JTextField easyText = new JTextField();
		private JTextField medText = new JTextField();
		private JTextField hardText = new JTextField();
		private JTextField locationHigh = new JTextField();
		private JPanel prefsDiaPan = new JPanel();
		private JPanel highPrefDiaPan = new JPanel();
		private JPanel diffPan = new JPanel();
		private JPanel prefsButtonPan = new JPanel();
		private JButton prefsOK = new JButton("OK");
		private JButton prefsAbort = new JButton("Abbrechen");
		private JButton prefsAssume = new JButton("Übernehmen");
		private SudokuHighscoreGUI sudokuHighscoreGUI;
		private SudokuActionListener sudokuActionListener;
//		private SudokuXMLDocument sudokuXMLDocument;
		private SudokuGUI sudokuGUI;
		private SudokuServer serv;
		
		public PropertiesMenuGUI(JFrame frame) {
			setPrefsDia(new JDialog(frame, "weitere Einstellungen"));
		}

		public JMenu getPropsMenu() {
			return propsMenu;
		}

		public void setPropsMenu(JMenu propsMenu) {
			this.propsMenu = propsMenu;
		}

		public JDialog getPrefsDia() {
			return prefsDia;
		}

		public void setPrefsDia(JDialog prefsDia) {
			this.prefsDia = prefsDia;
		}

		public JPanel getPrefsDiaPan() {
			return prefsDiaPan;
		}

		public void setPreferencesDialoguePanel(JPanel prefsDiaPan) {
			this.prefsDiaPan = prefsDiaPan;
		}

		public JPanel getHighscorePreferencesDialoguePanel() {
			return highPrefDiaPan;
		}

		public void setHighscorePreferencesDialoguePanel(JPanel highPrefDiaPan) {
			this.highPrefDiaPan = highPrefDiaPan;
		}

		public JPanel getDiffPan() {
			return diffPan;
		}

		public void setDiffPan(JPanel diffPan) {
			this.diffPan = diffPan;
		}

		public JTextField getEasyText() {
			return easyText;
		}

		public void setEasyText(JTextField easyText) {
			this.easyText = easyText;
		}

		public int getiEasy() {
			return iEasy;
		}

		public void setiEasy(int iEasy) {
			this.iEasy = iEasy;
		}

		public JTextField getMedText() {
			return medText;
		}

		public void setMedText(JTextField medText) {
			this.medText = medText;
		}

		public int getiMed() {
			return iMed;
		}

		public void setiMed(int iMed) {
			this.iMed = iMed;
		}

		public JTextField getHardText() {
			return hardText;
		}

		public void setHardText(JTextField hardText) {
			this.hardText = hardText;
		}

		public int getiHard() {
			return iHard;
		}

		public void setiHard(int iHard) {
			this.iHard = iHard;
		}
		
		public void buildPreferencesMenue() {
			sudokuHighscoreGUI.getLocationHighBut().addActionListener(sudokuActionListener);
//			sudokuXMLDocument = new SudokuXMLDocument(getHighLoc());
			getPrefsDia().setModalityType(
					Dialog.ModalityType.APPLICATION_MODAL);
			getPrefsDiaPan().setLayout(
					new BoxLayout(getPrefsDiaPan(),
							BoxLayout.Y_AXIS));
			getHighscorePreferencesDialoguePanel().setLayout(
					new BoxLayout(getHighscorePreferencesDialoguePanel(), BoxLayout.X_AXIS));
			getHighscorePreferencesDialoguePanel().setBorder(BorderFactory
					.createTitledBorder("Lokation der Bestenliste"));
			getHighscorePreferencesDialoguePanel().add(getLocationHigh());
			getHighscorePreferencesDialoguePanel().add(sudokuHighscoreGUI.getLocationHighBut());
			getPrefsDiaPan().add(getHighscorePreferencesDialoguePanel());
			getDiffPan().setLayout(new GridLayout(3, 2));
			getDiffPan().setBorder(BorderFactory
					.createTitledBorder("Anzahl vorgegebener Zahlen f�r..."));
			getDiffPan().add(new JLabel("Einfach: "));
			getEasyText().setText(String.valueOf(getiEasy()));
			getDiffPan().add(getEasyText());
			getDiffPan().add(new JLabel("Mittel: "));
			getMedText().setText(String.valueOf(getiMed()));
			getDiffPan().add(getMedText());
			getDiffPan().add(new JLabel("Schwer: "));
			getHardText().setText(String.valueOf(getiHard()));
			getDiffPan().add(getHardText());
			getPrefsDiaPan().add(diffPan);
			getPrefsButtonPan().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			getPrefsOK().addActionListener(sudokuActionListener);
			getPrefsButtonPan().add(getPrefsOK());
			getPrefsAbort().addActionListener(sudokuActionListener);
			getPrefsButtonPan().add(getPrefsAbort());
			getPrefsAssume().addActionListener(sudokuActionListener);
			getPrefsButtonPan().add(getPrefsAssume());
			getPrefsDiaPan().add(getPrefsButtonPan());
			getPrefsDia().add(getPrefsDiaPan());
			getPrefsDia().pack();
			getPrefsMen().addActionListener(sudokuActionListener);
			getBtnGrp().add(getEasyCheck());
			getBtnGrp().add(getMediumCheck());
			getBtnGrp().add(getHardCheck());
			getEasyCheck().addActionListener(sudokuActionListener);
			getEasyCheck().setName("easy");
			getMediumCheck().addActionListener(sudokuActionListener);
			getMediumCheck().setName("medium");
			getHardCheck().addActionListener(sudokuActionListener);
			getHardCheck().setName("hard");
			getDifficulty().add(getEasyCheck());
			getDifficulty().add(getMediumCheck());
			getDifficulty().add(getHardCheck());
			getPropsMenu().add(getDifficulty());
			getFreigeben().addActionListener(sudokuActionListener);
			getPropsMenu().add(getFreigeben());
			getPropsMenu().add(getPrefsMen());
		}

//		private String getHighLoc() {
//			return null;
//		}

		public JPanel getPrefsButtonPan() {
			return prefsButtonPan;
		}

		public void setPrefsButtonPan(JPanel prefsButtonPan) {
			this.prefsButtonPan = prefsButtonPan;
		}

		public JButton getPrefsAssume() {
			return prefsAssume;
		}

		public void setPrefsAssume(JButton prefsAssume) {
			this.prefsAssume = prefsAssume;
		}

		public JButton getPrefsAbort() {
			return prefsAbort;
		}

		public void setPrefsAbort(JButton prefsAbort) {
			this.prefsAbort = prefsAbort;
		}

		public JButton getPrefsOK() {
			return prefsOK;
		}

		public void setPrefsOK(JButton prefsOK) {
			this.prefsOK = prefsOK;
		}

		public JRadioButtonMenuItem getEasyCheck() {
			return easyCheck;
		}

		public void setEasyCheck(JRadioButtonMenuItem easyCheck) {
			this.easyCheck = easyCheck;
		}

		public JRadioButtonMenuItem getMediumCheck() {
			return mediumCheck;
		}

		public void setMediumCheck(JRadioButtonMenuItem mediumCheck) {
			this.mediumCheck = mediumCheck;
		}

		public JRadioButtonMenuItem getHardCheck() {
			return hardCheck;
		}

		public void setHardCheck(JRadioButtonMenuItem hardCheck) {
			this.hardCheck = hardCheck;
		}

		public ButtonGroup getBtnGrp() {
			return btnGrp;
		}

		public void setBtnGrp(ButtonGroup btnGrp) {
			this.btnGrp = btnGrp;
		}

		public JMenu getDifficulty() {
			return difficulty;
		}

		public void setDifficulty(JMenu difficulty) {
			this.difficulty = difficulty;
		}

		public JCheckBoxMenuItem getFreigeben() {
			return freigeben;
		}

		public void setFreigeben(JCheckBoxMenuItem freigeben) {
			this.freigeben = freigeben;
		}

		public JMenuItem getPrefsMen() {
			return prefsMen;
		}

		public void setPrefsMen(JMenuItem prefsMen) {
			this.prefsMen = prefsMen;
		}
		
		public void freigabe() {
			if (getFreigeben().isSelected()) {
				System.out.println("ich mache neuen Server auf");
				serv = new SudokuServer(sudokuGUI);
				serv.start();
			} else {
				System.out.println("ich unterbreche");
				serv.interrupt();
				try {
					serv.sverb.close();
					serv.servSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public JTextField getLocationHigh() {
			return locationHigh;
		}

		public void setLocationHigh(JTextField locationHigh) {
			this.locationHigh = locationHigh;
		}
}
