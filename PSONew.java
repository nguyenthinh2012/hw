import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PSONew {
	class person{
		float[] x;
		int[] y;
		float[] v;
		int value;
		public person(int p){
			x = new float[p];
			y = new int[p];
			v = new float[p];
			for(int i = 0 ; i < p ; i ++){
				float r1 = r.nextFloat();
				float r2 = r.nextFloat();
				x[i] = -10 + 20*r1;
				v[i] = -4 + 8 *r2;
				float k = Math.abs(x[i] % 2);
				if(k >= 1){
					y[i] = 1;
				}else{
					y[i] = 0;
				}
			}
			value = getFiness(y);
		}
		
		public void update(){
			int size = x.length;
			for(int i = 0 ; i < size ; i++){
				float vTemp = v[i];
				float w = r.nextFloat();
				float r1 = r.nextFloat();
				float r2 = r.nextFloat();
				if(w < 0.5){
					w += 0.5;
				}
				v[i] = vTemp * w + 2 *r1 * (pBest.x[i] - x[i]) + 2 * r2 * (gBest.x[i] - x[i]);
				x[i] += v[i];
				while(x[i] > 1000000 || x[i] < -1000000){
					if(x[i] > 1000000){
						x[i] -= 1000000;
					}
					if(x[i] < - 1000000 ){
						x[i] += 1000000;
					}
				}
			}
			for(int i = 0 ; i < size; i++){
				float k = Math.abs(x[i] % 2);
				if(k >= 1){
					y[i] = 1;
				}else{
					y[i] = 0;
				}
			}
			value = getFiness(y);
		}
	}
	public int CostValue(int[] currentX) {
		int cost = 0 ;
       for(int i = 0 ; i < demand ;i ++){
    	   int min = 9999909;
    	   for (int j = 0 ; j < p; j++){
    		   if(currentX[j] == 1){
    			   if(matrix[i][j] < min){
    				   min = matrix[i][j];
    			   }
    		   }
    	   }
    	   cost += min;
       }
       return cost;
    }
	Random r = new Random();
	person pBest, gBest;
	int p, demand;
	int[] fixCost;
	int[][] matrix;
	ArrayList<person> pList = new ArrayList<>();
	public void init(){
		for(int i = 0 ; i < p ; i++){
			person pnew = new person(p);
			pList.add(pnew);
		}
		pBest = pList.get(0);
		
		for(int i = 0 ; i < p; i++ ){
			if(pList.get(i).value < pBest.value){
				pBest = pList.get(i);
			}
		}
		gBest = pBest;
	}
	public int getFiness(int[] perY){
		int cost = 0;
		for(int i = 0 ; i < perY.length; i++){
			if(perY[i] == 1){
				cost += fixCost[i];
			}
		}
		cost += CostValue(perY);
		return cost;
	}
	public void finding(){
		for(int i = 0 ; i < 10000; i++){
			for(int j = 0 ; j < pList.size(); j++){
				pList.get(j).update();
				if(pList.get(j).value < pBest.value){
					pBest = pList.get(j);
				}
			}
			if(pBest.value < gBest.value){
				gBest = pBest;
			}

		}
	}
	public void readData(String fn) throws FileNotFoundException{
		Scanner sn = new Scanner(new File(fn));
		p  = sn.nextInt();
		demand = sn.nextInt();
		fixCost = new int[p];
		//System.out.println ( p +"     " +demand);
		matrix = new int[demand][p];
		for(int i = 0 ; i < p ; i++){
			int k = sn.nextInt();
			fixCost[i] = sn.nextInt();
			
		}
		//System.out.println(Arrays.toString(fixCost));
		for(int i = 0 ; i < demand ; i ++){
			int k = sn.nextInt();
			//System.out.println(k);
			for(int j = 0 ; j < p ; j++){
				int k2 = sn.nextInt();
				//System.out.println(k2 + "		" + k);
				int k3 = sn.nextInt();
				matrix[i][j] = k2;
			}
		}
	}
	public void print(){
		System.out.println(gBest.value);
		System.out.println(Arrays.toString(gBest.y));
	}
	public static void main(String[] args) throws FileNotFoundException{
		PSONew pnew = new PSONew();
		pnew.readData("D:\\datacap\\cap1.txt");
		pnew.init();
		pnew.finding();
		pnew.print();
	}
}
