package de.mgruben.sudoku.backend;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {
	private int sSize;
	public Integer[][] field;
	public ArrayList<Integer> list=new ArrayList<Integer>();
	private int countdown=500;
	private int y=0;
		
	//Konstruktor
	public GameBoard(int size){
		sSize=size;
		field=new Integer[sSize][sSize];
		//load available Nums
		for (int i=1;i<sSize+1;i++)
			list.add(i);
		
	//build Sudoku
		//build first Line
		Collections.shuffle(list);
		for(int i=0;i<list.size();i++)
			field[0][i]=list.get(i);
		
		//build Array of Positions
		Position[][] pos=new Position[sSize][sSize];
		
		//iterate through lines
		for (int j=1;j<sSize;j++){
			//iterate through columns
			for (int k=0;k<sSize;k++){
				try{
					field[j][k].byteValue();
				}catch (NullPointerException e){
				pos[j][k]=new Position(j, k, field,sSize);}
				if(k==0){
					pos[j][k].resetPoolLine();}
				else {
					pos[j][k].copyTemp(pos[j][k-1].poolLine);}
				pos[j][k].filterPool();
				try{
					field[j][k]=pos[j][k].getVal();
				}
				catch(IllegalArgumentException e){
					j--;
					break;
				}
				if(y==j)
					countdown--;
				if(countdown==0){
					j=0;
					pos=new Position[sSize][sSize];
					field=new Integer[sSize][sSize];
					Collections.shuffle(list);
					for(int i=0;i<list.size();i++)
						field[0][i]=list.get(i);
					countdown=500;
					break;
				}
				y=j;
				}			
			}			
		}
	
	public static void main(String[] args) {
		GameBoard spiel=new GameBoard(9);
		for(int i=0;i<spiel.list.size();i++){
			System.out.println("");
			if(i%3==0)
				System.out.println("_________________");
			for (int j=0;j<spiel.list.size();j++){
				if(j%3==0)
					System.out.print(" | ");
				System.out.print(spiel.field[i][j]);
			}
		}
	}
}
