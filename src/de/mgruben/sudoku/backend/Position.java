package de.mgruben.sudoku.backend;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Position {
	private int pSize;
	protected int[] vertNums;
	protected int[] boxNums;
	public Set<Integer> poolTemp= new HashSet<Integer>();
	public Set<Integer> poolLine= new HashSet<Integer>();
	public boolean lastPlus;
	
	public Position(int vert,int hori, Integer[][] field, int size){
		pSize=size;
		vertNums=new int[size];
		boxNums=new int[size];
		Checker check = new Checker(field,hori,vert,pSize);
		vertNums=check.checkVert();
		boxNums=check.checkBox();
//		for(int i=0;i<size;i++){
//			System.out.println("vert "+vertNums[i]+"\tbox "+boxNums[i]);
//		}
		resetPoolLine();
	}
	
	public void copyTemp(Set<Integer> poolLine2){
		poolTemp.clear();
		for(Integer val:poolLine2){
			poolTemp.add(val);}
	}
	
	public void copyLine(Set<Integer> pool2){
		poolLine.clear();
		for (Integer val:pool2){
			poolLine.add(val);
		}
	}
	
	public void filterPool() {
		copyLine(poolTemp);
		for(int i=0;i<vertNums.length;i++){
			if(vertNums[i]!=0)
				poolTemp.remove(vertNums[i]);
		}
		for(int i=0;i<boxNums.length;i++){
			if(boxNums[i]!=0)
				poolTemp.remove(boxNums[i]);
		}
	}

	public void resetPoolLine(){
		poolLine.clear();
		for (int i=0;i<pSize;i++){
			poolLine.add(i+1);
			}
		copyTemp(poolLine);
	}
	
	public int getVal(){
		int rand=new Random().nextInt(poolTemp.size());
		int val=Integer.parseInt(poolTemp.toArray()[rand].toString());
		if(val<1){			
			poolTemp.remove(val);
			getVal();}
		poolLine.remove(val);
		return val;
	}
}
