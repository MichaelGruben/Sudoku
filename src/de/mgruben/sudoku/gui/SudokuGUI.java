package de.mgruben.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import de.mgruben.sudoku.backend.Position;
import de.mgruben.sudoku.controller.GameController;
import de.mgruben.sudoku.controller.XMLController;
import de.mgruben.sudoku.documents.SudokuXMLDocument;
import de.mgruben.sudoku.threads.UhrzeitThread;

/**
 * TODO: Hilfedatei(noch ausführen (Latex)) - Netzwerk Coop-Modus
 * 
 * @author mgruben
 * 
 */
public class SudokuGUI implements MouseMotionListener, KeyListener {
	// Frame
	private JFrame frame = new JFrame("Sudoku");

	// Hauptpanel
	private JPanel root = new JPanel();

	// Das Spielfeld
	private GameBoardGUI gameBoardGUI = new GameBoardGUI();

	// Das Menü
	private SudokuMenueBar sudokuMenueBar = new SudokuMenueBar();

	// Das Einstellungen-Menü
	private PropertiesMenuGUI propertiesMenuGUI = new PropertiesMenuGUI(
			getFrame());

	// Default-Einstellungen
	private String difficultyVal = new String("medium");
	private int showAnz = propertiesMenuGUI.getiMed();
	private String highscoreLocation = System.getProperty("user.home")
			+ System.getProperty("file.separator") + "sudokuHighScore.xml";

	// Hilfemenü
	private SudokuHelpMenu sudokuHelpMenu = new SudokuHelpMenu();

	// Panel, das das Spielfeld enthält
	private JPanel GameAreaPan = new JPanel();

	// Das Spielfeld
	private JPanel gameArea = new JPanel();
	// ...kann ausgetauscht werden durch ...
	private JTextField pausePan = new JTextField("Pause");
	
	// Statusleiste
	private SudokuStatusBar sudokuStatusBar = new SudokuStatusBar();

	// fürs Speichern
	private JFileChooser fs = new JFileChooser();

	// Spiel speichern
	private SudokuXMLDocument gameData;
	private File safeGameFile = null;

	// Highscore speichern
//	private SudokuXMLDocument highScoreForXML;
//	private JTextField locationOfHighscoreFile = new JTextField(getHighscoreFileLocation());

	// Zwischenspeicher für Tastaturnavigation
	private Integer cacheX = 0;
	private Integer cacheY = 0;

	// Netzwerken
//	private SudokuServer serv = new SudokuServer(this);

	// Der GameController
	private GameController contrGame = new GameController(this);

	// Der XMLController
	private XMLController contr = new XMLController(this);

//	private JTextField[][] fieldGUIArray;
//
//	private int sizeOfGameArea;

	private JMenuItem saveButton;

//	private Integer[][] fieldGUIInt;

//	private Color startValCol;

	private SudokuHighscoreGUI sudokuHighscoreGUI = new SudokuHighscoreGUI();

	private SudokuWonDia sudokuWonDia = new SudokuWonDia();

	private SudokuActionListener sudokuActionListener;

	private UhrzeitThread zeit = new UhrzeitThread();

	SudokuGUI() {
		// configuration for usage by key-type
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (e.isControlDown()) {
						if (e.getKeyCode() == KeyEvent.VK_S)
							contr.saveAs(true);
						else if (e.getKeyCode() == KeyEvent.VK_L) {
							contr.load();
						}
					} else if (e.getKeyCode() == KeyEvent.VK_F2)// Taste F2
						getContrGame().startNewGame();
					else if (e.getKeyCode() == KeyEvent.VK_F3)// Taste F3
						getContrGame().resetGame();
					else if (e.getKeyCode() == KeyEvent.VK_P)
						getContrGame().setPause();
					else if (e.getKeyCode() == KeyEvent.VK_S)
						contr.saveAs(false);
				}
				return false;
			}
		});
		getFrame().setFocusTraversalKeysEnabled(true);
		getFrame().setLocation(
				new Double(Toolkit.getDefaultToolkit().getScreenSize()
						.getWidth() / 5).intValue(),
				new Double(Toolkit.getDefaultToolkit().getScreenSize()
						.getHeight() / 5).intValue());
		gameArea.setLayout(new GridLayout(getSize(), getSize()));
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				getFieldGUI()[i][j] = new JTextField();
				getFieldGUI()[i][j].setBackground(SudokuColor.backCol
						.getColor());
				getFieldGUI()[i][j].addMouseMotionListener(this);
				getFieldGUI()[i][j].addKeyListener(this);
				getFieldGUI()[i][j].setName(String.valueOf(i)
						+ String.valueOf(j));
				getFieldGUI()[i][j].setHorizontalAlignment(JTextField.CENTER);
				getFieldGUI()[i][j]
						.setFont(new Font("SansSerif", Font.BOLD, 20));
				getFieldGUI()[i][j].setBorder(new MatteBorder(1, 1, 1, 1,
						SudokuColor.borderCol.getColor()));
				if (i % 3 == 0 & j != 0 & i != 0 & j % 3 == 0)
					getFieldGUI()[i][j].setBorder(new MatteBorder(5, 5, 1, 1,
							SudokuColor.borderCol.getColor()));
				else if (i % 3 == 0 & i != 0)
					getFieldGUI()[i][j].setBorder(new MatteBorder(5, 1, 1, 1,
							SudokuColor.borderCol.getColor()));
				else if (j % 3 == 0 & j != 0)
					getFieldGUI()[i][j].setBorder(new MatteBorder(1, 5, 1, 1,
							SudokuColor.borderCol.getColor()));
				gameArea.add(getFieldGUI()[i][j]);
			}
		}

		// Build game Menu
		buildMenue();

		// set pause action
		setPauseAction();

		// Build help menu
		sudokuHelpMenu.buildHelpMenu();

		// Build preferences menu
		propertiesMenuGUI.buildPreferencesMenue();

		sudokuMenueBar.getMenueBar().add(sudokuMenueBar.getStartMenu());
		sudokuMenueBar.getMenueBar().add(propertiesMenuGUI.getPropsMenu());

		sudokuMenueBar.getMenueBar().add(sudokuHelpMenu.getHelpMenu());

		// ???
		root.setLayout(new BorderLayout(0, 0));
		root.add(sudokuMenueBar.getMenueBar(), BorderLayout.NORTH);
		getCenterPan().setLayout(new CardLayout());
		getCenterPan().add(gameArea, "GameArea");
		getCenterPan().add(pausePan, "PausePanel");
		root.add(getCenterPan(), BorderLayout.CENTER);
		getSudokuStatusBar().getStatusBar().setLayout(new BorderLayout());
		getSudokuStatusBar().getStatusBar().add(getSudokuStatusBar().getTime(),
				BorderLayout.EAST);
		getSudokuStatusBar().getStatusBar().add(getScoreAnz(), BorderLayout.WEST);
		root.add(getSudokuStatusBar().getStatusBar(), BorderLayout.SOUTH);
		getFrame().add(root);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setVisible(true);
		getFrame().setSize(800, 800);
		getFrame().setResizable(false);

		// Build wonDia
		sudokuWonDia.buildWonDia();

		// Build Highscore Dia
		sudokuHighscoreGUI.buildHighscoreDia();

		// set Timer Option
		setTimerOption();
	}

	private void setTimerOption() {
		sudokuStatusBar.getTimer().scheduleAtFixedRate(getZeit(), 0, 1000);
	}

	private void setPauseAction() {
		sudokuMenueBar.getPauseBut().addActionListener(sudokuActionListener);
		pausePan.setHorizontalAlignment(JTextField.CENTER);
		pausePan.setName("pausePan");
		pausePan.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		pausePan.setFocusable(false);
	}

	private void buildMenue() {
		sudokuMenueBar.getStartBut().addActionListener(sudokuActionListener);
		sudokuMenueBar.getResetBut().addActionListener(sudokuActionListener);
		sudokuMenueBar.getExitBut().addActionListener(sudokuActionListener);
		getSaveBut().addActionListener(sudokuActionListener);
		getSaveBut().setEnabled(false);
		sudokuMenueBar.getSaveUnderBut()
				.addActionListener(sudokuActionListener);
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getStartBut());
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getResetBut());
		sudokuMenueBar.getStartMenu().addSeparator();
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getPauseBut());
		sudokuMenueBar.getShowHigh().addActionListener(sudokuActionListener);
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getShowHigh());
		sudokuMenueBar.getStartMenu().addSeparator();
		sudokuMenueBar.getStartMenu().add(getSaveBut());
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getSaveUnderBut());
		sudokuMenueBar.getLoadBut().addActionListener(sudokuActionListener);
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getLoadBut());
		sudokuMenueBar.getStartMenu().addSeparator();
		sudokuMenueBar.getStartMenu().add(sudokuMenueBar.getExitBut());
	}

	public void mouseMoved(MouseEvent arg0) {
		String name = arg0.getComponent().getName();
		setCacheX(Integer.parseInt(name.substring(0, 1)));
		setCacheY(Integer.parseInt(name.substring(1, 2)));
		colorCross(getCacheX(), getCacheY());
		getFieldGUI()[getCacheX()][getCacheY()].requestFocus();
	}

	public void colorCross(int vert, int hori) {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (i == vert || j == hori)
					getFieldGUI()[i][j].setBackground(SudokuColor.crossSelCol
							.getColor());
				else
					getFieldGUI()[i][j].setBackground(SudokuColor.backCol
							.getColor());
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN
				|| arg0.getKeyCode() == KeyEvent.VK_KP_DOWN) {
			if (getCacheX() < 8) {
				setCacheX(getCacheX() + 1);
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP
				|| arg0.getKeyCode() == KeyEvent.VK_KP_UP) {
			if (getCacheX() > 0) {
				setCacheX(getCacheX() - 1);
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT
				|| arg0.getKeyCode() == KeyEvent.VK_KP_LEFT) {
			if (getCacheY() > 0) {
				setCacheY(getCacheY() - 1);
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT
				|| arg0.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
			if (getCacheY() < 8) {
				setCacheY(getCacheY() + 1);
			}
		}
		try {
			colorCross(getCacheX(), getCacheY());
		} catch (java.lang.NullPointerException e) {

		}
		getFieldGUI()[getCacheX()][getCacheY()].requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (getToWin() == 0) {
			sudokuWonDia.playerWonAndFinish();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		String compName = arg0.getComponent().getName();
		Integer aktx = Integer.parseInt(compName.substring(0, 1));
		Integer akty = Integer.parseInt(compName.substring(1, 2));
		char value = arg0.getKeyChar();

		if (Character.isDigit(value) & value != '0') { // Mülleingaben
														// aussortieren
			fillVal(aktx, akty, value);
		} else if (value == KeyEvent.VK_DELETE
				|| value == KeyEvent.VK_BACK_SPACE) {
			getFieldGUIInt()[aktx][akty] = 0;
		} else {
			arg0.consume();
		}
		setToWin(getSize() * getSize() - getShowAnz());
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (getFieldGUI()[i][j].getForeground().equals(
						SudokuColor.rightCol.getColor()))
					setToWin(getToWin() - 1);
			}
		}
	}

	public String fillVal(Integer aktx, Integer akty, char value) {
		JTextField aktFeld = getFieldGUI()[aktx][akty];
		aktFeld.setText(""); // aktuelles Feld l�schen, sodass danach nicht
		// zwei Werte darin stehen
		aktFeld.setText(value + "");
		// Eintragen der GUIWerte in das tats�chliche Feld, um Eingaben auf
		// Korrektheit pr�fen zu k�nnen
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (!getFieldGUI()[i][j].getText().equals(""))
					getFieldGUIInt()[i][j] = Integer
							.parseInt(getFieldGUI()[i][j].getText());
			}
		}

		Position pos = new Position(aktx, akty, getFieldGUIInt(), getSize());
		for (int i = 0; i < getSize(); i++) {
			try {
				int val = getFieldGUIInt()[aktx][i];
				if (val == Integer.parseInt(value + "") && i != akty) {
					pos.poolTemp.remove(val);
					break;
				}
			} catch (NullPointerException e2) {

			}
		}
		pos.filterPool();
		if (!pos.poolTemp.contains(Integer.parseInt(value + ""))) {
			if (getScore() >= 150
					& aktFeld.getForeground() != SudokuColor.wrongCol
							.getColor()) {
				setScore(getScore() - 150);
			}
			aktFeld.setForeground(SudokuColor.wrongCol.getColor());

		} else {
			if (aktFeld.getForeground() != SudokuColor.rightCol.getColor()) {
				setScore(getScore() + 100);
			}
			aktFeld.setForeground(SudokuColor.rightCol.getColor());
		}
		getScoreAnz().setText("Punkte: " + getScore());
		return "Farbe der gefuellten Zahl " + aktFeld.getForeground();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFileChooser getFs() {
		return fs;
	}

	public void setFs(JFileChooser fs) {
		this.fs = fs;
	}

	public File getsFile() {
		return safeGameFile;
	}

	public void setsFile(File sFile) {
		this.safeGameFile = sFile;
	}

	public SudokuXMLDocument getGameData() {
		return gameData;
	}

	public void setGameData(SudokuXMLDocument gameData) {
		this.gameData = gameData;
	}

	public JTextField[][] getFieldGUI() {
		return gameBoardGUI.getFieldGUI();
	}

//	public void setFieldGUI(JTextField[][] fieldGUI) {
//		this.fieldGUIArray = fieldGUI;
//	}

	public int getSize() {
		return gameBoardGUI.getSize();
	}

//	public void setSize(int size) {
//		this.sizeOfGameArea = gameBoardGUI.getSize();
//	}

	public int getShowAnz() {
		return showAnz;
	}

	public void setShowAnz(int showAnz) {
		this.showAnz = showAnz;
	}

	public int getScore() {
		return getSudokuStatusBar().getScore();
	}

	public void setScore(int score) {
		getSudokuStatusBar().setScore(score);
	}

	public String getDifficultyVal() {
		return difficultyVal;
	}

	public void setDifficultyVal(String difficultyVal) {
		this.difficultyVal = difficultyVal;
	}

	public String getHighscoreFileLocation() {
		return highscoreLocation;
	}

	public void setHighscoreLocation(String highLoc) {
		this.highscoreLocation = highLoc;
	}

	public int getToWin() {
		return sudokuWonDia.getToWin();
	}

	public void setToWin(int toWin) {
		sudokuWonDia.setToWin(toWin);
	}

	public JMenuItem getSaveBut() {
		return saveButton;
	}

	public void setSaveBut(JMenuItem saveBut) {
		this.saveButton = saveBut;
	}

	public GameController getContrGame() {
		return contrGame;
	}

	public void setContrGame(GameController contrGame) {
		this.contrGame = contrGame;
	}

	public JLabel getScoreAnz() {
		return getSudokuStatusBar().getScoreAnz();
	}

	public void setScoreAnz(JLabel scoreAnz) {
		getSudokuStatusBar().setScoreAnz(scoreAnz);
	}

	public JTextField getPlayer() {
		return sudokuWonDia.getPlayer();
	}

	public void setPlayer(JTextField player) {
		sudokuWonDia.setPlayer(player);
	}

	public Integer[][] getFieldGUIInt() {
		return gameBoardGUI.getFieldGUIInt();
	}

//	public void setFieldGUIInt(Integer[][] fieldGUIInt) {
//		this.fieldGUIInt = fieldGUIInt;
//	}

	public UhrzeitThread getZeit() {
		return zeit;
	}

	public void setZeit(UhrzeitThread zeit) {
		this.zeit = zeit;
	}

	public JPanel getCenterPan() {
		return GameAreaPan;
	}

	public void setCenterPan(JPanel centerPan) {
		this.GameAreaPan = centerPan;
	}

	public Integer getCacheX() {
		return cacheX;
	}

	public void setCacheX(Integer cacheX) {
		this.cacheX = cacheX;
	}

	public Integer getCacheY() {
		return cacheY;
	}

	public void setCacheY(Integer cacheY) {
		this.cacheY = cacheY;
	}

	public Color getStartValCol() {
		return SudokuColor.startValCol.getColor();
	}

//	public void setStartValCol(Color startValCol) {
//		this.startValCol = SudokuColor.startValCol.getColor();
//	}

	public JDialog getWonDia() {
		return sudokuWonDia.getWonDia();
	}

	public void setWonDia(JDialog wonDia) {
		sudokuWonDia.setWonDia(wonDia);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	public JMenuItem getResetBut() {
		return sudokuMenueBar.getResetBut();
	}

	public SudokuStatusBar getSudokuStatusBar() {
		return sudokuStatusBar;
	}

	public void setSudokuStatusBar(SudokuStatusBar sudokuStatusBar) {
		this.sudokuStatusBar = sudokuStatusBar;
	}
}