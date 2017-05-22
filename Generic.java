import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Generic {

	class gen{
		int value;
		int [] gen;
		public gen(int p){
			gen = new int[p];
		}
		public int getValue(){
			return value;
		}
		
	}
	int [][] matrix;
	int p, size, totalCost;
	ArrayList<gen> genList = new ArrayList<>();
	ArrayList<gen> genSorted = new ArrayList<>();
	Random r = new Random();
	public void getTotalCost(){
		totalCost = 0;
		for(int i = 0 ; i < genSorted.size(); i++){
			totalCost += genSorted.get(i).value;
		}
	}
	public int select(){
		return r.nextInt(genSorted.size());
	}
	public String gen2String(gen g){
		String str = "";
		for(int i = 0 ; i < g.gen.length; i++){
			if(g.gen[i] < 10){
				str += "0" + Integer.toString(g.gen[i]) + "-";
			}
			
			else{
				str += Integer.toString(g.gen[i]) + "-";
			}
		}
		return str;
	}
	public void mutation(gen g){
		int iReplace = r.nextInt(g.gen.length);
		int gReplace = r.nextInt(size + 1);
		int check = 0;
		for(int i = 0 ; i < g.gen.length; i++){
			if(g.gen[i] == gReplace){
				check = 1;
				break;
			}
		}
		if(check == 0 ){
			g.gen[iReplace] = gReplace;
		}
	}
	public void doCross(gen g1, gen g2){
		ArrayList<Integer> diff1 = new ArrayList<>();
		ArrayList<Integer> diff2 = new ArrayList<>();
		ArrayList<Integer> child1 = new ArrayList<>();
		ArrayList<Integer> child2 = new ArrayList<>();
		
		for(int i = 0 ; i < p; i++){
			int check = 0;
			for(int j = 0; j < p; j++){
				if(g1.gen[i] == g2.gen[j]){
					check = 1;
					child1.add(g1.gen[i]);
					child2.add(g1.gen[i]);
					break;
				}
			}
			if(check == 0 ){
				diff1.add(g1.gen[i]);
			}
			int check1 = 0;
			for(int j = 0; j < p; j++){
				if(g2.gen[i] == g1.gen[j]){
					check1 = 1;
					break;
				}
			}
			if(check1 == 0 ){
				diff2.add(g2.gen[i]);
			}
			
		}		
		int crossLength = diff1.size() / 2;
		if(crossLength < (2 * p / 5)){
			crossLength = diff1.size();
		}
		
//		System.out.println("daylachild" + child1.size());
//		System.out.println("Daylabo");
//		System.out.println(Arrays.toString(g1.gen));
//		System.out.println(Arrays.toString(g2.gen));
		for(int i = 0 ; i < crossLength ; i++){
			if(diff2.size() == 0){
				break;
			}
			child1.add(diff2.get(i));
			child2.add(diff1.get(i));
		}
		for(int i = crossLength ; i < diff1.size() ; i ++){
			child1.add(diff1.get(i));
		}
		for(int i = crossLength ; i < diff2.size() ; i ++){
			child2.add(diff2.get(i));
		}
		//System.out.println(p + "dayla p");
		gen childGen = new gen(p);
		gen childGen2 = new gen(p);

		for(int i  = 0 ; i < p ; i++){
			childGen.gen[i] = child1.get(i);
			childGen2.gen[i] = child2.get(i);
		}
		
		int t = r.nextInt(10);
		if(t < 4){
			mutation(childGen);
		}
		t = r. nextInt(10); 
		if(t < 4){
			mutation(childGen2);
		}
		
		update(childGen);
		update(childGen2);

	}
	public void update(gen childGen) {
		// TODO Auto-generated method stub
		childGen.value = CostValue(childGen.gen, size, matrix);
		if(childGen.value < genSorted.get(0).value){
			gen tmp = genSorted.get(0) ;
			genSorted.set(0, childGen);
			for(int i = 0 ; i < genList.size(); i++){
				if(genList.get(i).value == tmp.value){
					String genCheck = gen2String(genList.get(i));
					int check = 0 ;
					for(int j = 0 ; j < tmp.gen.length ;j++ ){
						if(genCheck.contains(Integer.toString(tmp.gen[j])) == false){
							check = 1;
							break;
						}
					}
					if(check == 0){
						genList.set(i, childGen);
					}
				}
			}
		}
		for(int i = 0 ; i < genSorted.size() ; i ++){
			for(int j = 0 ; j < genSorted.size() ; j ++){
				if(genSorted.get(i).value > genSorted.get(j).value){
					gen temp = genSorted.get(i);
					genSorted.set(i, genSorted.get(j));
					genSorted.set(j, temp);
				}
			}
		}
	}

	public void init() throws IOException{
		for(int i = 0 ; i < 100 ; i++){
			gen g = new gen(p);
			int count = 0;
			ArrayList<Integer> li = new ArrayList<>();
			while(count < p){
				int k = r.nextInt(size) + 1;
				int check = 0;
				for(int h = 0 ; h < li.size() ; h ++){
					if(li.get(h) == k){
						check = 1;
						break;
					}
				}
				if(check == 0){
					li.add(k);
					count++;
				}
			}
			for(int j = 0 ; j < li.size(); j ++){
				g.gen[j] = li.get(j);
			}
			g.value = CostValue(g.gen, size, matrix);
			genList.add(g);
		}
		
		genSorted = genList;
		for(int i = 0 ; i < genSorted.size() ; i ++){
			for(int j = 0 ; j < genSorted.size() ; j ++){
				if(genSorted.get(i).value > genSorted.get(j).value){
					gen temp = genSorted.get(i);
					genSorted.set(i, genSorted.get(j));
					genSorted.set(j, temp);
				}
			}
		}
		System.out.println(genSorted.get(0).value + "daudit"+ genSorted.get(genSorted.size() - 1).value);
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
	public void readData(String fn) throws FileNotFoundException{
		Scanner sn = new Scanner(new File(fn));
		size = sn.nextInt();
		int edge = sn.nextInt();
		 p = sn.nextInt();
		matrix = new int[size + 1][size +1];
		for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                matrix[i][j] = 100000000;
                if (i == j) matrix[i][j] = 0;
            }
        }
		for(int i = 0 ; i < edge ; i ++){
			int k = sn.nextInt();
			int k2 = sn.nextInt();
			int cost = sn.nextInt();
			matrix[k][k2] = cost;
			matrix[k2][k] = cost;
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
	public int test(){
		gen g = new gen(5);
		return g.gen.length;
	}
	public static void main(String[] args) throws IOException{
		Generic ga = new Generic();
		ga.readData("D:\\data\\pmed25.txt");
		long time = System.nanoTime();
		
		ga.init();
		int count = 0;
		for(int i = 0 ; i < 500000 ; i++){
//			if(ga.genSorted.get(0).value == ga.genSorted.get(ga.genSorted.size() - 1).value){
//				count ++;
//			}
//			if(count == 100){
//				ArrayList<gen>  newListt = new ArrayList<>();
//				for(int k = 0 ; k < 10; k++){
//					newListt.add(ga.genSorted.get(100 - k - 1));
//				}
//				ga.init();
//				System.out.println("INIT");
//				for(int k = 0 ; k < 10 ; k ++){
//					ga.update(newListt.get(k));
//				}
//				count = 0;
//			}
			int a = ga.select();
			int b = ga.select();
			ga.doCross(ga.genList.get(a), ga.genList.get(b));
		}
		System.out.println(ga.genSorted.get(0).value);
		System.out.println(ga.genSorted.get(ga.genSorted.size() - 1).value);
		System.out.println("Time : " + (double)(System.nanoTime()-time)/1000000000);
	}
}
