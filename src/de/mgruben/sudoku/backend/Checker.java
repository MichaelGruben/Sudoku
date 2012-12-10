package de.mgruben.sudoku.backend;

public class Checker {
	Integer[][] field;
	int hori;
	int vert; 
	int pSize;
	
	public Checker(Integer[][] field, int hori, int vert, int pSize) {
		// TODO Auto-generated constructor stub
		this.field = field;
		this.hori = hori;
		this.vert = vert;
		this.pSize = pSize;
	}

	protected int[] checkVert(){
		int[] arr=new int[pSize];
//		System.out.println("\n\ndelete vert:");
		for(int j=0;j<pSize;j++){
			if(field[j][hori]!=null & j!=vert){
				arr[j]=field[j][hori];
//				System.out.print(arr[j]);
				}
			else
				arr[j]=0;
		}
		return arr;		
	}
	protected int[] checkBox(){
		int[] arr=new int[pSize];
		int k=hori%3;
		int l=vert%3;
		int nums=0;
//		System.out.println("\n\ndelete box");
		for(int j=vert-l;j<vert+3-l;j++){
			for(int i=hori-k;i<hori+3-k;i++){
				try{
					if(j!=vert && i!=hori)
					arr[nums]=field[j][i];
//					System.out.print(arr[nums]);
				}
				catch (NullPointerException e){
					arr[nums]=0;
				}
				nums++;
		}}
		return arr;
	}
}
