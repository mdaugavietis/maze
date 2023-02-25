package dip107;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int[][] l;
		int[][] path = {{0,0},{0,1},{0,2},{1,2}}; // šeit ir jābūt rezultātam
		int r, c, m;
		char ans;
		Scanner sc = new Scanner(System.in);
		System.out.print("row count: ");
		r = sc.nextInt();
		System.out.print("column count: ");
		c = sc.nextInt();
		sc.nextLine();
		System.out.print("Auto fill maze (y/n)?");
		ans = sc.nextLine().charAt(0);
		if (ans == 'n') {
			l = new int[r][c];
			for (int i=0; i<r; i++)
				for (int j=0; j<c; j++)
					l[i][j] = sc.nextInt();
		}
		else {
// automātiski aizpildam masīvu un izvadam to ekrānā
		}
		System.out.print("method number (1-3):");
		m = sc.nextInt();
		switch (m) {
			case 1: //1. algoritms
				break;
			case 2: //2. algoritms
				break;
			case 3: //3. algoritms
				break;
		}
// rezultātu izvade
		System.out.println("results:");
		for (int i=0; i<4; i++)
			System.out.print("(" + path[i][0] + "," + path[i][1] + ") ");
	}
}