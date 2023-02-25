package dip107;
import java.util.Scanner;

class Point {
	int x, y;
}
public class Labirints {
	static int[][] l;//Labirints
	static int rows, cols;//Labirinta dimensijas
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("row count: ");
		rows = sc.nextInt();

		System.out.print("column count: ");
		cols = sc.nextInt();

		sc.nextLine();
		System.out.print("Auto fill maze (y/n)?");
		char ans = sc.nextLine().charAt(0);
		if (ans == 'n') {
			l = new int[rows][cols];
			for (int i=0; i<rows; i++)
				for (int j=0; j<cols; j++)
					l[i][j] = sc.nextInt();
		}
		else {
			generate();
		}
		System.out.print("method number (1-3):");
		int mode = sc.nextInt();
		Point[] path;
		switch (mode) {
			case 1: //1. algoritms
				path = aStar();
				break;
			case 2: //2. algoritms
				path = dijkstra();
				break;
			case 3: //3. algoritms
				path = floydWarshall();
				break;
			case 4: //4. algoritms
				path = depthFirstSearch();
				break;
			default://netika izvēlēts neviens algoritms
				path = new Point[0]; // rezultātu izvade
		}
		// rezultātu izvade
		System.out.println("results:");
		for (Point p : path) {
			if (p != null) {
				System.out.printf("(%d,%d) ", p.y, p.x);
			}
		}
	}
	// Ģenerēšanas funkcija
	public static void generate() {
		l = new int[rows][cols];
		//ģenerēt l(abirintu)
	}
	// Iziešanas algoritmu tips
	public static Point[] aStar() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res = new Point[1];
		return res;
	}
	public static Point[] dijkstra() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res = new Point[1];
		return res;
	}
	public static Point[] floydWarshall() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res = new Point[1];
		return res;
	}
	public static Point[] depthFirstSearch() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res = new Point[1];
		return res;
	}
}