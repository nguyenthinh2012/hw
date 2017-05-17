import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class GenericAlgorithm {

	public class Gen{
		int value;
		int[] gen;
		public Gen(int p){
			gen = new int[p];
		}
		public int getValue(){
			return value;
		}
	}
	int size, p, edge;
	int[][] matrix;
	ArrayList<Gen> genList;
	ArrayList<Gen> genSorted;
	public void readData(String fn) throws FileNotFoundException{
		Scanner sn = new Scanner(new File(fn));
		size = sn.nextInt();
		edge = sn.nextInt();
		p = sn.nextInt();
		matrix = new int[size + 1][size +1 ];
		for(int i = 0 ; i < edge ; i++){
			int u = sn.nextInt();
			int v = sn.nextInt();
			int cost = sn.nextInt();
			matrix[u][v] = cost;
			matrix[v][u] = cost;
		}
		for (int k = 1; k <= size; k++) {
            for (int i = 1; i <= size; i++) {
                for (int j = 1; j <= size; j++) {
                    if (matrix[i][j] > matrix[i][k] + matrix[k][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
	}
	public int CostValue(int[] gen, int N, int[][] edge) {
	       int total = 0;
	       int [] currentX = new int[N + 1];
	       for(int i = 0; i < gen.length; i++){
	       	currentX[gen[i]] = 1;
	       }
	       for (int dNode = 1; dNode <= N; dNode++) {
	           int dist2Facility = 10000000;
	           int candidate = -1;
	           if (currentX[dNode] != 1) {
	               for (int canNode = 1; canNode <= N; canNode++) {
	                   if (currentX[canNode] == 1) {
	                       if (edge[dNode][canNode] != 0) {
	                           if (edge[dNode][canNode] < dist2Facility) {
	                               dist2Facility = edge[dNode][canNode];
	                               candidate = canNode;
	                           }
	                       }
	                   }
	               }
	               total += dist2Facility;
	           } else if (currentX[dNode] == 1) {
	               total += 0;
	           }
	            //System.out.println(dNode + " -> " + candidate);

	       }
	       return total;
	    }
	Random r = new Random();
	 public Gen random(){
		 Gen g = new Gen(p);
		 for(int i = 0 ; i < p ; i++){
			 int k = r.nextInt(size) + 1;
			 g.gen[i] = k;
		 }
		 getCost(g);
		 return g;
	 }
	 public void getCost(Gen g){
		 int cost = CostValue(g.gen, size, matrix);
		 g.value = cost;
	 }
	public void sort(){
		for(int i = 0 ; i < genList.size(); i++){
			for(int j = 0 ; j < genList.size() ; j++){
				if(genSorted.get(i).value < genSorted.get(j).value ){
					Gen temp  = genSorted.get(i);
					genSorted.set(i, genSorted.get(j));
					genSorted.set(j, temp);
					
				}
			}
		}
	}
	public void update(Gen g1){
		Gen temp = genSorted.get(0);
		if(g1.value < genSorted.get(0).value){
			genSorted.set(0, g1);
		}
		for(int i = 0; i < genList.size(); i++){
			if(genList.get(i).value == temp.value){
				genList.set(i, g1);
			}
		}
		sort();
	}
	 public void Init(){
		 genList = new ArrayList<>();
		 for(int i = 0 ; i < 100; i ++){
			 Gen g = random();
			 genList.add(g);
		 }
		 genSorted = genList;
		 sort();
	 }
	 
	 int totalCost = 0;
	 public void getTotalCost(){
		 for(int i = 0 ; i< genList.size(); i++){
			 totalCost += genList.get(i).value;
		 }
	 }
	 public int select(){
		 int index = r.nextInt(100);
		 int valueRan = r.nextInt(totalCost) + 1; 
		 int costRandom = 0;
		 while(true){
			  costRandom += genList.get(index).value;
			  if(costRandom > valueRan){
				  break;
			  }
			  else{
				  index++;
			  }
			  if(index >= 100){
				  index = 0;
			  }
		 }
		 return index;
	 }
	 public void mutation(Gen g){
		 int index = r.nextInt(p);
		 int temp = r.nextInt(size);
		 int check = -1;
		 for(int i = 0 ; i < g.gen.length;i++){
			 if(g.gen[i] == check){
				 check = 0;
				 break;
			 }
		 }
		 if(check == -1){
			 g.gen[index] =temp;
		 }
		 g.value = CostValue(g.gen, size, matrix);
	 }
	 public void doCross(){
		int sec1 = select();
		int sec2 = select();
		Gen p1 = genList.get(sec1);
		Gen p2 = genList.get(sec2);
		ArrayList<Integer> diffp1 = new ArrayList<>();
		ArrayList<Integer> child1 = new ArrayList<>();
		ArrayList<Integer> child2 = new ArrayList<>();
		ArrayList<Integer> diffp2 = new ArrayList<>();
		for(int i = 0 ; i < p1.gen.length; i++){
			int check = - 1;
			for(int j = 0 ; j < p1.gen.length; j++){
				if(p1.gen[i] == p2.gen[j]){
					check = 0;
					break;
				}
			}
			if(check ==0 ){
				diffp1.add(p1.gen[i]);
			}
			else{
				child1.add(p1.gen[i]);
			}
		}
		for(int i = 0 ; i < p1.gen.length; i++){
			int check = - 1;
			for(int j = 0 ; j < p1.gen.length; j++){
				if(p2.gen[i] == p1.gen[j]){
					check =0;
					break;
				}
			}
			if(check ==0 ){
				diffp2.add(p2.gen[i]);
			}
			else{
				child2.add(p2.gen[i]);
			}
		}
		int length =0;
		if(diffp2.size() > diffp1.size()){
			length = diffp1.size();
		}
		else {
			length = diffp2.size();
		}		
		int pointCross = length/3;
		if(pointCross >= 1){
			for(int i = pointCross; i < diffp1.size(); i++){
				child1.add(diffp1.get(i));
			}
			for(int i = pointCross; i < diffp2.size(); i++){
				child1.add(diffp2.get(i));
			}
			for(int i = 0 ; i < pointCross ; i++){
				child1.add(diffp2.get(i));
				child2.add(diffp1.get(i));
			}
		}
		else{
			for(int i = length ; i<diffp1.size(); i++){
				child1.add(diffp1.get(i));
			}
			for(int i = length ; i<diffp2.size(); i++){
				child1.add(diffp2.get(i));
			}
			for(int i =0 ; i< length ; i++){
				child1.add(diffp2.get(i));
				child2.add(diffp1.get(i));
			}
		}
		Gen new1 = new Gen(p);
		Gen new2 = new Gen(p);
		System.out.println(child1.size());
		for(int i = 0 ; i < p ; i++){
			new1.gen[i] = child1.get(i);
			new2.gen[i] = child2.get(i);
		}
		new1.value = CostValue(new1.gen, size, matrix);
		new2.value = CostValue(new2.gen, size, matrix);
		int ran = r.nextInt(10);
		if(ran <= 4 ){
			mutation(new1);
		}
		update(new1);
		ran = r.nextInt(10);
		if(ran <=4 ){
			mutation(new2);
		}
		update(new2);
	}
	 public int _do() throws FileNotFoundException{
		int bestvalue = 0;
		readData("D:\\data\\pmed1.txt");
		Init();
		System.out.println(genSorted.get(genSorted.size() - 1).value);
		for(int i = 0 ; i < 100 ; i++){
			getTotalCost();
			doCross();
			System.out.println(genSorted.get(genSorted.size() - 1).value);
			
		}
		bestvalue = genSorted.get(genSorted.size() - 1).value;
		return bestvalue;
		 
	 }
	 public static void main(String[] args) throws FileNotFoundException{
		 GenericAlgorithm GA = new GenericAlgorithm();
		 System.out.print(GA._do());
	 }
}
