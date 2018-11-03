package TermProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class modifiedGenerator {

	public static int arr[][];
	public static int completeArr[][];
	public static int xMove[]= {0,1,1,1,0,-1,-1,-1};
	public static int yMove[]= {1,1,0,-1,-1,-1,0,1};
	public static int xSize;
	public static int ySize;
	public static int startX,startY;
	public static int endX,endY;
	public static int cnt;
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("가로 길이 입력 : ");
		xSize=sc.nextInt();		///가로길이
		
		System.out.println("세로 길이 입력 : ");
		ySize=sc.nextInt();		///세로길이
		
		
		cnt=xSize*ySize/5;
		
		
		arr=new int[ySize][xSize];
		completeArr=new int[ySize][xSize];
		init();
		
		startX=2;
		startY=2;
		arr[startX][startY]=1;
		if(solve(startX,startY,2)) {
			System.out.println("yes");
			//printSolution();
		}else {
			System.out.println("No");
		}
		
		copy();
		
		String str="";
		str=process();

		FileWriter fw = new FileWriter("test.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("<complete Array>\n");
		bw.write(str);
		
		arr=delete();
		String arrStr="";
		arrStr=process2();
		
		
		bw.write("<deleted Array>\n");
		bw.write(arrStr);
		bw.close();
		
		
		
	}
	public static void copy(){
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				completeArr[i][j]=arr[i][j];
			}
		}
	}
	public static int[][] delete(){
		Random r=new Random();
		int count=0;
		int i,j;
		while(count<cnt) {
			i=r.nextInt(ySize-1);
			j=r.nextInt(xSize-1);
			
			if((arr[i][j]==-1 || i==startY || i==endY || j==startX || j==endX))
				continue;
			else {
				arr[i][j]=-1;
				count++;
			}
				
		}
		return arr;
		
	}
	public static String process() {
		String tmp="";
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				tmp+=String.format("%3d",completeArr[i][j]);
			}
			tmp+='\n';
		}
		
		return tmp;
	}
	public static String process2() {
		String tmp="";
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				tmp+=String.format("%3d",arr[i][j]);
			}
			tmp+='\n';
		}
		
		return tmp;
	}
	public static boolean solve(int x,int y,int count) {
		if(count==xSize*ySize+1) {
			arr[y][x]=count-1;
			endX=x;
			endY=y;
			return true;
		}
		
		int minVal=8;
		int minIndex=-1;
		
		for(int i=0; i<8; i++) {
			int newX=x+xMove[i];
			int newY=y+yMove[i];
			int cnt=0;
			if(isSafe(newX,newY)) {
				for(int j=0; j<8; j++) {
					int tmpX=newX+xMove[j];
					int tmpY=newY+yMove[j];
					
					if(isSafe(tmpX,tmpY)) {
						cnt++;
					}
				}
				
				if(minVal>=cnt) {
					minVal=cnt;
					minIndex=i;
				}
			}
		}
		
		int newX=x+xMove[minIndex];
		int newY=y+yMove[minIndex];
		
		if(isSafe(newX,newY)) {
			arr[y][x]=count-1;
			
			if(solve(newX,newY,count+1))
				return true;
			else
				arr[y][x]=-1;
		}
		
		
		return false;
	}
	
	
	
	public static boolean isSafe(int x,int y) {
		if((x>=0 && x<xSize )&& (y>=0 && y<ySize) && arr[y][x]==-1)
			return true;
		else
			return false;
	}
	
	
	public static void init() {
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				arr[i][j]=-1;
			}
		}
	}
	
	public static void printSolution() {
		System.out.println("  출 력 결 과");
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				System.out.printf("%3d",arr[i][j]);
			}
			System.out.println();
		}
	}
	
	
}