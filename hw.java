package test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

public class hw {
public static int p = 123457;
public static int[][] c = new int[5][10000];
	public int hash_fun(int a, int b, int p, int x){
		int y = x % p;
		int hash_val = (a*y + b) % p;
		return hash_val % 10000;
	}
	public static Scanner sn;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		hw h = new hw();
		sn =new Scanner(new File("D:\\HW4-q4\\hash_params.txt"));
		int[] a = new int[5];
		int[] b =new int[5];
		int i = 0;
		while(sn.hasNext()){
			a[i] = sn.nextInt();
			b[i] = sn.nextInt();
			i++;
		}
		sn.close();
		sn = new Scanner(new File("D:\\HW4-q4\\words_stream.txt"));
		float t = 0;
		while(sn.hasNext()){			
			int k = sn.nextInt();					
			for(i = 0 ;i < a.length; i++){				//compute hash value of number in stream
				int temp = h.hash_fun(a[i],b[i],p,k);
				c[i][temp]++;							//update value of matrix c with number in stream
			}
			
		}
		sn.close();
		ArrayList<word> WordFe = new ArrayList();
		sn = new Scanner(new File("D:\\HW4-q4\\counts.txt"));
		while(sn.hasNext()){
			int x = sn.nextInt();
			int fe = sn.nextInt();
			int min = Integer.MAX_VALUE;
			for(i = 0; i < a.length; i++){
				int valueOfFe = c[i][h.hash_fun(a[i], b[i], p, x)];
				if(min < valueOfFe){
					min = valueOfFe;
				}
			}
			int err =	((min - fe)/ fe);
			word w = new word();
			w.error = err;
			w.wordfe =  fe/t;
			WordFe.add(w);
			t++;
		}
		sn.close();
		PrintWriter pw =new PrintWriter(new FileWriter("D:\\out.txt"));
		for(i = 0 ; i < WordFe.size(); i ++){
			String s = WordFe.get(i).error + "	" + WordFe.get(i).wordfe;
			pw.println(s);
		}
		pw.close();
		System.out.println("Done");
	}

}
