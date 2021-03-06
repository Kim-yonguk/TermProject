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
		File file=new File("input.txt");
		FileWriter fw = new FileWriter(file,true);
		System.out.print("테스트케이스 입력 : ");
		int testCases=sc.nextInt();
		
		fw.write(testCases+"\n");
		while(testCases>0) {
			
			xSize=randomRange(5, 15);
			ySize=randomRange(5, 15);
			
			cnt=(int) (Math.random()*xSize*ySize/6+xSize);
			
			
//			int ran=r.nextInt(10);
//			ran=(int)(Math.random()*10)+1;
			
			int ran=randomRange(1,10);
			
			randomNumber=ran;
			
			
			arr=new int[ySize][xSize];
			completeArr=new int[ySize][xSize];
			
			init();
			makeWall(randomNumber);
			
			
			startX=2;
			startY=2;
			arr[startY][startX]=1;
			
			boolean check=true;
			while(true) {
				if(solve(startX,startY,2) && arr[endY][endX]==xSize*ySize-randomNumber){
					System.out.println("yes");
					testCases--;
					break;
				}else {
					System.out.println("No");
					check=false;
					break;
				}
			}
			
			if(check) {
				
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
					fw.write(arrStr+"\n");
					
					
				
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
	
	
	
	public static boolean isSafe(int x,int y) {		/// x,y값이 가로,세로길이보다 작고 그 배열이 마지않은곳이면 true 리턴 
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
	
	public static int[][] deleteArr(){			//완성된 퍼즐배열에서 시작점과 종료점,벽이 아닌 부분을 랜덤으로 0으로 초기화
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
	
	public static void makeWall(int randomNumber){		///완성된 부분에서 반복문을 돌면서 랜덤수 만큼 벽을 생성 이미 벽이거나 시작점이면 다른지점을 생성
		Random r=new Random();
		int count=0;
		int i,j;
		while(count<randomNumber) {
			i=r.nextInt(ySize-1);
			j=r.nextInt(xSize-1);
			
			if(arr[i][j]==-1 || (i==startY&&j==startX))
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
	
	public static int randomRange(int n1, int n2) {
		   return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}
}