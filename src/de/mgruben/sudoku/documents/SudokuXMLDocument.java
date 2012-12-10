package de.mgruben.sudoku.documents;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class SudokuXMLDocument {
	private SAXBuilder builder=new SAXBuilder();
	private Element root;
	private String path=new String();
	private File file;
	private Document doc=new Document();
	
	public JTextField[][] pField;
	public int pSize;
	public int pShowAnz;
	public String pPlayer;
	public String pTime;
	public int pScore;
	public String pDifficultyVal;
	public String pHighLoc;
	public int pToWin;

	public SudokuXMLDocument(String selectedFile) {
		path = selectedFile;
		file = new File(path);
		try {
			doc=builder.build(file);
			root=doc.getRootElement();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			doc.addContent(genHigh());
			writeFile();
		} 		
	}
	
	public SudokuXMLDocument(String selectedFile, JTextField[][] field, int size, int showAnz, String player, String time, int score, String difficultyVal, String highLoc, int toWin) {
		path = selectedFile;
		file = new File(path);
		try {
			doc=builder.build(file);
			doc.removeContent();
			doc.addContent(genSave(field,size,showAnz,player,time,score,difficultyVal,highLoc,toWin));
			writeFile();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			doc.addContent(genSave(field,size,showAnz,player,time,score,difficultyVal,highLoc,toWin));
			writeFile();
		} 		
	}
	
	public SudokuXMLDocument(File file,int size){
		pField=new JTextField[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++)
				pField[i][j]=new JTextField("0");
		}
		try {
			doc=builder.build(file);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		Element root=doc.getRootElement();
		Element prefs=root.getChild("preferences");
		pDifficultyVal=prefs.getAttributeValue("difficultyVal");
		pHighLoc=prefs.getAttributeValue("highLoc");
		pPlayer=prefs.getAttributeValue("player");
		pScore=Integer.parseInt(prefs.getAttributeValue("score"));
		pShowAnz=Integer.parseInt(prefs.getAttributeValue("showAnz"));
		pSize=Integer.parseInt(prefs.getAttributeValue("size"));
		pTime=prefs.getAttributeValue("time");
		pToWin=Integer.parseInt(prefs.getAttributeValue("toWin"));
		Element fieldElem=root.getChild("field");
		@SuppressWarnings("unchecked")
		List<Element> children = fieldElem.getChildren("value");		
		for (Element elem : children) {
			String val=elem.getAttributeValue("value");
			int x=Integer.parseInt(elem.getAttributeValue("x"));
			int y=Integer.parseInt(elem.getAttributeValue("y"));
			String col = elem.getAttributeValue("color");
			pField[x][y]=new JTextField(val);
			pField[x][y].setForeground(new Color(Integer.parseInt(col)));			
		}
	}

	private Element genSave(JTextField[][] field,int size, int showAnz, String player, String time, int score, String difficultyVal, String highLoc, int toWin) {
		root = new Element("Savegame");
		Element prefs=new Element("preferences");
		prefs.setAttribute("size", String.valueOf(size));
		prefs.setAttribute("showAnz", String.valueOf(showAnz));
		prefs.setAttribute("player", player);
		prefs.setAttribute("time", time);
		prefs.setAttribute("score", String.valueOf(score));
		prefs.setAttribute("difficultyVal", difficultyVal);
		prefs.setAttribute("highLoc",highLoc);
		prefs.setAttribute("toWin", String.valueOf(toWin));
		root.addContent(prefs);
		Element fieldElem = new Element("field");
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++) {
				if(!field[i][j].getText().equals("")){
				Element value=new Element("value");
				value.setAttribute("x",String.valueOf(i));
				value.setAttribute("y",String.valueOf(j));
				value.setAttribute("color",String.valueOf(field[i][j].getForeground().getRGB()));
				value.setAttribute("value",field[i][j].getText());
				fieldElem.addContent(value);}
			}
		}
		root.addContent(fieldElem);
		return root;
	}

	public void updateFile(String difficulty, int score, String time,
			String player) {
		Element anzGrp = root.getChild(difficulty);
		Element newEntry = new Element("Entry");
		newEntry.setAttribute("time", time);
		newEntry.setAttribute("score", String.valueOf(score));
		newEntry.setAttribute("name", player);
		anzGrp.addContent(newEntry);
		writeFile();
	}

	private Element genHigh() {
		root = new Element("SudokuHighscore");
		Element[] vorgAnz = new Element[3];
		vorgAnz[0] = new Element("easy");
		vorgAnz[1] = new Element("medium");
		vorgAnz[2] = new Element("hard");
		for (int i = 0; i < 3; i++)
			root.addContent(vorgAnz[i]);
		return root;
	}

	private void writeFile() {
		try {
			FileOutputStream out = new FileOutputStream(path);
			XMLOutputter serializer = new XMLOutputter(Format.getPrettyFormat());
			serializer.output(doc, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SudokuXMLDocument high = new SudokuXMLDocument(
				"C:\\Users\\mgruben\\sudokuHighScore.xml");
		high.updateFile("hard", 5020, "00:00:10","baerbel");
	}

	public DefaultTableModel getHighWithVal(String difficulty) {
		String[] columnNames = { "Name", "Score", "Time" };
		try {
			root = doc.getRootElement();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}
		Element valElem = root.getChild(difficulty);
		DefaultTableModel model = new DefaultTableModel(columnNames, valElem.getChildren().size());
		try {
			for (int i = 0; i < valElem.getChildren().size(); i++) {
				Element entryElem = (Element) valElem.getChildren().toArray()[i];
				model.setValueAt(entryElem.getAttributeValue("name"), i, 0);
				model.setValueAt(entryElem.getAttributeValue("score"), i, 1);
				model.setValueAt(entryElem.getAttributeValue("time"), i, 2);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("no entries");
		}
		return model;
	}
}