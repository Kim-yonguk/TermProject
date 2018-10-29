package TermProject;

import java.util.Scanner;

public class generator {

	public static int size;
	public static int arr[][];
	
	public static int xArr[]= {0,1,1,1,0,-1,-1,-1};	///x축 이동
	public static int yArr[]= {1,1,0,-1,-1,-1,0,1};	///y축 이동
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		size=sc.nextInt();
		arr=new int[size][size];
		
		process();			////배열 초기화 
		int startX=sc.nextInt();		///시작 x값
		int startY=sc.nextInt();		///시작 y값
		arr[startX][startY]=1;		///시작지점 체크
		
		if(solve(startX,startY,2)) {
			printSolution();
		}else {
			System.out.println("No Solution");
		}
	}
	
	
	public static boolean isSafe(int x,int y) {
		if(x>=0 && x<size && y>=0 && y<size && arr[x][y]==-1)
			return true;
		else
			return false;
	}
	
	public static void printSolution() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void process() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				arr[i][j]=-1;
			}
		}
	}
	
	public static boolean solve(int x,int y,int count) {
		
		if(count==Math.pow(size, 2)+1) {
			arr[x][y]=count-1;
			return true;
		}
			
		int minVal=size;
		int minIndex=-1;
		
		for(int i=0; i<size; i++) {
			int newX=x+xArr[i];
			int newY=y+yArr[i];
			
			int val=0;
			
			if(isSafe(newX,newY)) {
				
				for(int j=0; j<size; j++) {
					int tmpX=newX+xArr[j];
					int tmpY=newY+yArr[j];
					
					if(isSafe(tmpX,tmpY)) {
						val++;
					}
				}
				
				if(minVal>val) {
					minVal=val;
					minIndex=i;
				}
			}
		}
		
		int newX=x+xArr[minIndex];
		int newY=y+yArr[minIndex];
	
		
		if(isSafe(newX,newY)) {
			arr[x][y]=count-1;
			
			if(solve(newX,newY,count+1))
				return true;
			else
				arr[x][y]=-1;
		}
		
		
		return false;
	}
}
