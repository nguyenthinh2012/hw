import java.beans.Expression;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PSO {

	class X{
		int value;
		float[] xVec;
		float[] vVec;
		int[] yVec;
		public X(int size){
			xVec = new float[size];
			vVec = new float[size];
			yVec = new int[size];
			for(int i = 0 ; i < size ; i ++){
				float r1 = r.nextFloat();
				float r2 = r.nextFloat();
				xVec[i] = -10 + 20*r1;
				vVec[i] = -4 + 8 *r2;
				float k = Math.abs(xVec[i] % 2);
				if(k >= 1){
					yVec[i] = 1;
				}else{
					yVec[i] = 0;
				}
			}
		}
		public void update(){
			int size = xVec.length;
			for(int i = 0 ; i < size ; i++){
				float vTemp = vVec[i];
				float w = r.nextFloat();
				float r1 = r.nextFloat();
				float r2 = r.nextFloat();
				if(w < 0.5){
					w += 0.5;
				}
				vVec[i] = vTemp * w + 2 *r1 * (pBest.vec[i] - xVec[i]) + 2 * r2 * (gBest.vec[i] - xVec[i]);
				xVec[i] += vVec[i];
				while(xVec[i] > 1000000 || xVec[i] < -1000000){
				if(xVec[i] > 1000000){
					xVec[i] -= 1000000;
				}
				if(xVec[i] < - 1000000 ){
					xVec[i] += 1000000;
				}
				}
			}
			for(int i = 0 ; i < size; i++){
				float k = Math.abs(xVec[i] % 2);
				if(k >= 1){
					yVec[i] = 1;
				}else{
					yVec[i] = 0;
				}
			}
		}
	}	
	class Best{
		int value;
		float[] vec;
	}
	int m,n;
	int[] fixCost;
	int[][] matrix;
	Best gBest,pBest;
	ArrayList<X> xList;
	public void updateValue(X x){
		x.value = fitness(x);
	}
	public void getBest(ArrayList<X> xList){	
		pBest = new Best();
		pBest.value  = xList.get(0).value;
		pBest.vec = xList.get(0).xVec;
		for(int i = 1 ; i < xList.size(); i++){
			Best tempBest = new Best();
			int value = xList.get(i).value;
			float[] arr = xList.get(i).xVec;
			tempBest.value = value;
			tempBest.vec = arr;
			if(tempBest.value < pBest.value){
				pBest = tempBest;
			}
		}
		if(pBest.value < gBest.value){
			gBest = pBest;
		}
	}

	public int fitness(X x){
		int cost = 0;
		//int size = x.xVec.length;
		for(int i = 0 ; i < m ; i++){
			if(x.yVec[i] == 1){
				cost += fixCost[i];
			}
		}
		for(int i = 0 ; i < n; i++){
			ArrayList<Integer> ai = new ArrayList();
			for(int j = 0 ; j < m ; j ++){
				if(x.yVec[j] == 1){
					ai.add(matrix[i][j]);
				}
			}
			int min = 9999999;
			for(int k = 0 ; k < ai.size(); k++){
				if(ai.get(k) < min){
					min = ai.get(k);
				}
			}
			//System.out.println(min + "  " +i);
			cost += min;
			ai.clear();
		}
		return cost;
	}
	public void ReadData(String fn) throws FileNotFoundException{
		Scanner sn = new Scanner(new File(fn));
		m  = sn.nextInt();
		n = sn.nextInt();
		fixCost = new int[m];
		matrix = new int[n][m];
		for(int i = 0 ; i < m ; i++){
			int k = sn.nextInt();
			fixCost[i] = sn.nextInt();
		}
		//System.out.println(Arrays.toString(fixCost));
		for(int i = 0 ; i < n ; i ++){
			int k = sn.nextInt();
			for(int j = 0 ; j < m ; j++){
				int k2 = sn.nextInt();
				int k3 = sn.nextInt();
				matrix[i][j] = k2;
			}
		}
	} 
	Random r = new Random();
	public void init(){
		xList = new ArrayList<X>();
		for(int i = 0 ; i < m ; i++){
			X  x = new X(m);
			x.value = fitness(x);
			xList.add(x);
		}
		gBest = new Best();
		gBest.value = xList.get(0).value;
		gBest.vec = xList.get(0).xVec;
		getBest(xList);
	}
	public void finding(){
		int count = 0;
		for(int i = 0 ; i < 30; i++){
			ArrayList<X> xListT = new ArrayList<>();
			for(int j = 0 ; j < xList.size(); j++){
				X xTemp = xList.get(j);
				xTemp.update();
				updateValue(xTemp);
				xListT.add(xTemp);
			}
			int temp = gBest.value;
			getBest(xListT);
			if(gBest.value == temp){
				count ++;
			}
			if(count > 100){
				break;
			}
			xList.clear();
			xList = xListT;
		//	print();
		}
	}
	public void print(){
		System.out.println("Best Gobal Value " + gBest.value);
		X xt = xList.get(0);
		xt.xVec = gBest.vec;
		xt.update();
		System.out.println(fitness(xt) + " XTF");
		System.out.println(Arrays.toString(xt.xVec));
		System.out.println(Arrays.toString(xt.vVec));
		System.out.println(Arrays.toString(xt.yVec));
	}
	public void writeFile(String fn) throws IOException{
		Writer w =new PrintWriter( new File(fn));
		for(int i = 0 ; i < n ; i++){
			String str = "";
			for(int j = 0 ; j < m ; j++){
				str += Integer.toString( matrix[i][j]) + " ";
			}
			w.write(str);
			w.flush();
			w.write("\n");
			w.flush();
		}
	}
	public static void main(String[] args) throws IOException {
		PSO p = new PSO();
		p.ReadData("D:\\datacap\\cap.txt");
		p.init();
		p.finding();
		p.print();
		p.writeFile("D:\\datacap\\matrix.txt");
	}
}
