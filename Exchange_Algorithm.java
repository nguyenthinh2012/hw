import javax.swing.text.EditorKit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Exchange_Algorithm {
    public static void main(String[] args) throws IOException {
        // Data input
        int V, E, p = 0;
        int PROBLEM_SIZE = 100;         // 100(pmed1->pmed5), 200(pmed6->pmed10), 300 (pmed11->pmed15)
        int[][] edge = new int[PROBLEM_SIZE + 1][PROBLEM_SIZE + 1];
        for (int i = 0; i <= PROBLEM_SIZE; i++) {
            for (int j = 0; j <= PROBLEM_SIZE; j++) {
                edge[i][j] = 100000000;
                if (i == j) edge[i][j] = 0;
            }
        }

        // Read input
        try {
            FileInputStream fstream = new FileInputStream("D:\\data\\pmed25.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            int lineCount = 1;
            while ((line = br.readLine()) != null) {
                if (lineCount == 1) {
                    line = line.trim();
                    String[] x = line.split(" ");
                    V = Integer.parseInt(x[0]);
                    E = Integer.parseInt(x[1]);
                    p = Integer.parseInt(x[2]);
                    //System.out.println(V + " " + E + " " + p);
                } else {
                    line = line.trim();
                    String[] z = line.split(" ");
                    int x = Integer.parseInt(z[0]);
                    int y = Integer.parseInt(z[1]);
                    int w = Integer.parseInt(z[2]);
                    //System.out.println(x + " " + y + " " + w);
                    edge[x][y] = w;
                    edge[y][x] = w;
                }
                lineCount++;
            }
            in.close();

        } catch (Exception e) {
            System.out.println("Error while reading the file:" + e.getMessage());
            e.printStackTrace();
        }
       
        // Chay Floyd de hoan thanh ma tran canh
        for (int k = 1; k <= PROBLEM_SIZE; k++) {
            for (int i = 1; i <= PROBLEM_SIZE; i++) {
                for (int j = 1; j <= PROBLEM_SIZE; j++) {
                    if (edge[i][j] > edge[i][k] + edge[k][j]) {
                        edge[i][j] = edge[i][k] + edge[k][j];

                    }
                }
            }
        }
        
        for (int i = 1; i <= PROBLEM_SIZE; i++) {
            for (int j = 1; j <= PROBLEM_SIZE; j++) {
                //System.out.print(edge[i][j] + " ");
            }
            //System.out.println();
        }


        // Exchange Algorithm
        // Ta bieu dien loi giai thong qua mot chuoi nhi phan do dai V
        // Random init

        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 1; i <= PROBLEM_SIZE; i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        int[] X = new int[PROBLEM_SIZE + 1];
        for (int i = 1; i <= p; i++) {
            int cur = randomList.get(i - 1);
            X[cur] = 1;
        }
        print(X);
        System.out.println("Init Total Cost : " + CostValue(X, PROBLEM_SIZE, edge));


        long time = System.nanoTime();
        int maxIter = 1000;
        int iterCount = 1;
        while (iterCount <= maxIter) {
            print(X);
            
            System.out.print(" -> " + CostValue(X, PROBLEM_SIZE, edge));
            System.out.println("Time : " + (double)(System.nanoTime()-time)/1000000000);
            System.out.println();
            System.out.println("Step " + iterCount);
            boolean improved = false;
            for (int i = 1; i <= PROBLEM_SIZE; i++) {
                if (X[i] == 1) {
                    for (int j = 1; j <= PROBLEM_SIZE; j++) {
                        if (X[j] != 1) {
                            // Thay i boi j, neu co cai tien => Trao doi va lap lan tiep theo
                            // Neu khong, dung lai
                           // System.out.println("Trao doi " + i + " -> " + j);
                            int[] Y = new int[PROBLEM_SIZE + 1];
                            for (int ii = 1; ii <= PROBLEM_SIZE; ii++) {
                                Y[ii] = X[ii];
                            }
                            Y[i] = 0;
                            Y[j] = 1;
                            if (CostValue(X, PROBLEM_SIZE, edge) > CostValue(Y, PROBLEM_SIZE, edge)) {
                                improved = true;
                                System.out.println("Improved");
                                X = Y;
                            }
                        }
                        if (improved) break;
                    }
                }
                if (improved) break;
            }
            if (!improved) {
                System.out.println("Solution not improved ! Terminating ... ");
                break;
            }
            iterCount++;
        }
        print(X);
        System.out.println("Final Total Cost : " + CostValue(X, PROBLEM_SIZE, edge));
        System.out.println("Time : " + (double)(System.nanoTime()-time)/1000000000);
    }

    public static int CostValue(int[] currentX, int N, int[][] edge) {
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

    public static void print(int[] X) {
        System.out.print("Current solution : ");
        for (int i = 0; i < X.length; i++) {
            if (X[i] == 1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }


}
