import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MyOpic {
	int pnum, pmed, edg;
	int[][] matrix;
	int[] solution;
	public void readData(String fn) throws FileNotFoundException{
		
		Scanner in = new Scanner(new File(fn));
		pnum = in.nextInt();
		edg = in.nextInt();
		pmed = in.nextInt();
		matrix = new int[pnum + 1][pnum + 1];
		solution = new int [pnum + 1];
		for(int i = 0 ; i <= pnum; i ++)
			for(int j = 0 ; j <= pnum; j++){
				matrix[i][j] = 1000000;
			}
		while(in.hasNext()){
			int i = in.nextInt();
			int j = in.nextInt();
			int k = in.nextInt();
			matrix[i][j] = k;
			matrix[j][i] = k;
		}
		
		for (int k = 1; k <= pnum; k++) {
            for (int i = 1; i <= pnum; i++) {
                for (int j = 1; j <= pnum; j++) {
                    if (matrix[i][j] > matrix[i][k] + matrix[k][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];

                    }
                }
            }
        }
		
//		System.out.println(Arrays.deepToString(edge));
//		System.out.println("Read data complete");
	//	System.out.println(Arrays.deepToString(matrix));
	}

	public int getMin(int[] arr){
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0 ; i < arr.length; i ++){
			if(min > arr[i] && solution[i] == 0){
				min = arr[i];
				index = i;
			}
		}
		return index;
	}
	public void myOpic(){
		int count = 0;
		while(count < pmed){
			int[] arr = new int[pnum + 1];
			Arrays.fill(arr, 1000000);
			for(int i = 1 ; i <= pnum ; i++){
				if( solution[i] == 0){
					solution[i] = 1;				
					arr[i] = CostValue(solution, pnum, matrix);
					solution[i] = 0;
				}
			}
			solution[getMin(arr)] = 1;
			count++;
		}
	}
    public int CostValue(int[] currentX, int N, int[][] edge) {
        int total = 0;
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

    public void print(){
    	for(int i = 0 ; i <= pnum; i ++){
    		if(solution[i] == 1){
    			System.out.println(i);
    		}
    	}
    	//System.out.println(Arrays.toString(solution));
    	System.out.println("  CostValue " + CostValue(solution, pnum, matrix));
    }
	public static void main(String[] args) throws IOException{
		MyOpic my = new MyOpic();
		//for(int i = 1 ; i <= 40 ; i++){
        long time = System.nanoTime();
		my.readData("D:\\data\\pmed40.txt");
		my.myOpic();
		//System.out.print(i);
		my.print();
        System.out.println("Time : " + (double)(System.nanoTime()-time)/1000000000);

	//}
	}
}
