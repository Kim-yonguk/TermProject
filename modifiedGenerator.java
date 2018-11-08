package TermProject;

import java.io.File;
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
	public static int randomNumber;
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		File file=new File("test.txt");
		FileWriter fw = new FileWriter(file,true);
		System.out.print("테스트케이스 입력 : ");
		
		int testCases=sc.nextInt();
		fw.write(testCases+"\n");
		while(testCases>0) {
			System.out.println("가로 길이 입력 : ");
			xSize=sc.nextInt();		///가로길이
			
			System.out.println("세로 길이 입력 : ");
			ySize=sc.nextInt();		///세로길이
			
			
			cnt=5;
			Random r= new Random();
			int ran=r.nextInt(5);
			randomNumber=ran;
			
			
			arr=new int[ySize][xSize];
			completeArr=new int[ySize][xSize];
			
			init();
			makeWall(randomNumber);
			printSolution();
			
			
			startX=2;
			startY=2;
			arr[startY][startX]=1;
			
			printSolution();
//			if(solve(startX,startY,2)) {
//				System.out.println("Yes");
//			}else {
//				while(solve(startX,startY,2));
//			}
			boolean check=true;
			while(true) {
				if(solve(startX,startY,2) && arr[endY][endX]==xSize*ySize-randomNumber){
					System.out.println("yes");
					testCases--;
					break;
				}else {
					check=false;
				}
			}
			
			if(check) {
				printSolution();
				
				copy();
				
				String str="";
				str=process();
				
				
				try {
//					fw.write("<complete Array>\n");
//					fw.write(xSize+" "+ySize+"\n");
//					fw.write(str);
					
					arr=deleteArr();
					String arrStr="";
					arrStr=process2();
					
//					fw.write("<deleted Array>\n");
					fw.write(xSize+" "+ySize+"\n");
					fw.write(arrStr);
					
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				continue;
			}
		}
		
		fw.close();
		
	}
	public static void copy(){
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				completeArr[i][j]=arr[i][j];
			}
		}
	}
	public static String process() {
		String tmp="";
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				tmp+=String.format("%8d",completeArr[i][j]);
			}
			tmp+='\n';
		}
		
		return tmp;
	}
	public static String process2() {
		String tmp="";
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				tmp+=String.format("%8d",arr[i][j]);
			}
			tmp+='\n';
		}
		
		return tmp;
	}
	public static boolean solve(int x,int y,int count) {
		System.out.println(randomNumber);
		if(count==xSize*ySize+1-randomNumber) {
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
		
		if(minIndex==-1) {
			return false;
		}
		int newX=x+xMove[minIndex];
		int newY=y+yMove[minIndex];
		
		
		if(isSafe(newX,newY) && minIndex!=-1) {
			arr[y][x]=count-1;
			
			if(solve(newX,newY,count+1))
				return true;
			else
				arr[y][x]=0;
		}
		
		
		return false;
	}
	
	
	
	public static boolean isSafe(int x,int y) {
		if((x>=0 && x<xSize )&& (y>=0 && y<ySize) && arr[y][x]==0)
			return true;
		else
			return false;
	}
	
	
	public static void init() {
		for(int i=0; i<ySize; i++) {
			for(int j=0; j<xSize; j++) {
				arr[i][j]=0;
			}
		}
	}
	
	public static int[][] deleteArr(){
		Random r=new Random();
		int count=0;
		int i,j;
		while(count<cnt) {
			i=r.nextInt(ySize-1);
			j=r.nextInt(xSize-1);
			
			if((i==startY && j==startX) || (i==endY && j==endX) ||arr[i][j]==-1)
				continue;
			else {
				arr[i][j]=0;
				count++;
			}
				
		}
		return arr;
	}
	
	public static void makeWall(int randomNumber){
		Random r=new Random();
		int count=0;
		int i,j;
		while(count<randomNumber) {
			i=r.nextInt(ySize-1);
			j=r.nextInt(xSize-1);
			
			if(arr[i][j]==-1 || (i==endY&&j==endX) || (i==startY&&j==startX))
				continue;
			else {
				arr[i][j]=-1;
				count++;
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