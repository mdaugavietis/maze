package dip107;
import java.util.Random;
import java.util.Scanner;

class Point {
	int x, y;

	public Point(){
		x=0;
		y=0;
	}

	public Point(int x, int y){
		this.x=x;
		this.y=y;
	}
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

		System.out.print("method number (1-5):");
		int mode = sc.nextInt();
		sc.close();
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
			case 5: //5. algoritms
				path = testArtis();
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

		// Artis learning to code
		Random rand = new Random();
		int int_random;
		
		// Fill maze with random 0 and 1
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				int_random = rand.nextInt(2);
				l[i][j] = int_random;
			}
		}

		// Reset start and finish
		l[0][0] = 0;
		l[rows-1][cols-1] = 0;

		// Print maze generated
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				System.out.print(l[i][j] + " ");
			}
			System.out.println();
		}
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
		int v = rows*cols;
		int[][] dist = new int[v][v];

		int INF = Integer.MAX_VALUE;
		for(int i=0; i<v; i++){
			for(int j=0; j<v; j++){
				dist[i][j] = INF;
			}
		}

		for(int i=0; i<v; i++)
			dist[i][i] = 0;
		

		for (int k=0; k<v; k++) {
			for (int i=0; i<v; i++) {
				for (int j=0; j<v; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}

		for(int i=0; i<v; i++){
			for(int j=0; j<v; j++){
				if(dist[i][j]==INF)
					System.out.print("X" + "\t");
				else
					System.out.print(dist[i][j] + "\t");
			}
			System.out.println();
		}

		Point[] res = new Point[1];
		return res;
	}
	public static Point[] depthFirstSearch() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res = new Point[1];
		return res;
	}
	public static Point[] testArtis() {
		// atrast ceļu, atgriež ceļa koordinātes
		Point[] res;

		// aprēķinu masīva izmēru - max ceļa garumu
		if(rows > cols){
			res = new Point[(rows/2 + rows%2) * cols];
		}else{
			res = new Point[(cols/2 + cols%2) * rows];
		}
		//System.out.print(res.length);

		res[0] = new Point();

		return res;
	}
}