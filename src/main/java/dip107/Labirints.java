package dip107;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


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
		l = new int[rows][cols];
		if (ans == 'n') {
			for (int i=0; i<rows; i++)
				for (int j=0; j<cols; j++)
					l[i][j] = sc.nextInt();
		}
		else {
			generate();
		}

		pprint(null);
		long startTime;
		System.out.print("method number (1-5):");
		int mode = sc.nextInt();
		sc.close();
		Point[] path;
		switch (mode) {
			case 1: //1. algoritms
				startTime = System.nanoTime();
				path = aStar();
				break;
			case 2: //2. algoritms
				startTime = System.nanoTime();
				path = dijkstra();
				break;
			case 3: //3. algoritms
				startTime = System.nanoTime();
				path = floydWarshall();
				break;
			case 4: //4. algoritms
				startTime = System.nanoTime();
				path = depthFirstSearch();
				break;
			case 5: //5. algoritms
				startTime = System.nanoTime();
				path = testArtis();
				break;
			default://netika izvēlēts neviens algoritms
				startTime = System.nanoTime();
				path = new Point[0]; // rezultātu izvade
		}

		// izpildes laika fiksēšana
		long finishTime = System.nanoTime();
		System.out.println((finishTime-startTime)/1000000 + " ms");

		// rezultātu izvade
		System.out.println("results:");
		for (Point p : path) {
			if (p != null) {
				System.out.printf("(%d,%d) ", p.y, p.x);
			}
		}
		System.out.println();
		pprint(path);
	}

	// Ģenerēšanas funkcija
	public static void generate() {
		Random random = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				l[i][j] = 1;
		    }
		}
		l[0][0] = 0;
		l[rows-1][cols-1]=0;
		l[rows-2][cols-1]=0;
		l[rows-1][cols-2]=0;
		int x = 0;
		int y = 0;
		int[][][] directions = {
				  {{0, 2, 0, 1}, {0, -2, 0, -1}, {2, 0, 1, 0}, {-2, 0, -1, 0}},
				  {{0, -2, 0, -1}, {0, 2, 0, 1}, {2, 0, 1, 0}, {-2, 0, -1, 0}},
				  {{2, 0, 1, 0}, {-2, 0, -1, 0}, {0, 2, 0, 1}, {0, -2, 0, -1}},
				  {{-2, 0, -1, 0}, {2, 0, 1, 0}, {0, -2, 0, -1}, {0, 2, 0, 1}}
				};
		Map<String, int[]> parents = new HashMap<>();
		
		outer:
		while (y != rows-1 || x != cols-1) {
			int randDir = random.nextInt(4);
			for (int[] direction : directions[randDir]) {
		    	int ny = y + direction[0];
		    	int nx = x + direction[1];
		    	int sy = y + direction[2];
		    	int sx = x + direction[3];

		    	if (ny < 0 || ny >= rows || nx < 0 || nx >= cols) {
		    		continue;
		    	}

		    	if (l[ny][nx] == 1) {
		    		int[] parent = new int[2];
		    		parent[0]=y;
		    		parent[1]=x;
		    		l[ny][nx] = 0;
		    		l[sy][sx] = 0;
                	x=nx;
                	y=ny;
                	parents.put(y + "," + x, parent);
                	continue outer;
		    	}
        	}
			int[] parent = new int[2];
			parent = parents.get(y + "," + x);
			if (parent == null)
				break outer;
			y = parent[0];
			x = parent[1];
		}
	}

	// Iziešanas algoritmu tips
	public static Point[] aStar() {
	    PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node[3]));
	    queue.offer(new int[] {0, 0, 0, 0});
	    Set<String> visited = new HashSet<>();
	    Map<String, int[]> parents = new HashMap<>();
	    int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	    while (!queue.isEmpty()) {
	        int[] current = queue.poll();

	        if (current[0] == l.length-1 && current[1] == l[0].length-1) {
	        	Point[] way = new Point[l.length * l[0].length];
	            //List<int[]> path = new ArrayList<>();
	            int[] node = current;
	            int i = 0;
	            while (node != null) {
	            	Point p = new Point();
	            	p.y=node[0];
	            	p.x=node[1];
	            	way[i] = p;
	            	i++;
	                node = parents.get(node[0] + "," + node[1]);
	            }
	            Point[] path = new Point[i];
	            i--;
	            for (int j=0;i>=0;i--,j++) {
	            	path[j]=way[i];
	            }
	            return path;
	        }

	        visited.add(current[0] + "," + current[1]);

	        for (int[] direction : directions) {
	            int y = current[0] + direction[0];
	            int x = current[1] + direction[1];

	            if (y >= 0 && y < l.length && x >= 0 && x < l[0].length) {
	                if (l[y][x] == 0 && !visited.contains(y + "," + x)) {
	                    int g = current[2] + 1;
	                    int h = Math.abs(y - l.length - 1) + Math.abs(x - l[0].length - 1);
	                    int f = g + h;

	                    queue.offer(new int[] {y, x, g, f});
	                    visited.add(y + "," + x);
	                    parents.put(y + "," + x, current);
	                }
	            }
	        }
	    }

	    return null;
	}
	
	public static Point[] dijkstra() {
		//int[][] depths = new int[rows][cols];
		/*
		// list of unvisited nodes priority queue?
		PriorityQueue<Point> unvisited = new PriorityQueue<Point>(rows*cols, Comparator.comparing());
		// loop through every position appending to visited
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (l[y][x] == 0) {
					unvisited.add();
				}
			}
		}*/
		//
		/*
		for in.Scan() {
			s = in.Text()
			height = append(height, make([]node, len(s)))
			for i, char := range s {
				if char == 'S' {
					start = pos{x: i, y: count}
					height[start.y][start.x].height = 0
					unvisited = append(unvisited, start)
					continue
				} else if char == 'E' {
					end = pos{x: i, y: count}
					height[end.y][end.x].height = 'z' - 'a'
					height[end.y][end.x].distance = math.MaxInt
					unvisited = append(unvisited, end)
				} else {
					height[count][i].height = int(char - 'a')
					if char == 'a' { //p2
						apos[pos{x: i, y: count}] = true //p2
					} //p2
				}
			}
			count++*/
			// fmt.Println(s)
		return null;
	}

	public static Point[] floydWarshall() {

		int v = rows*cols; // virsotnes
		int[][] dist = new int[v][v]; // distanču starp virsotnēm masīvs
		int[][] next = new int[v][v]; // next hop masīvs

		// dist init
		int INF = v*v; // visām distancēm piešķirt maksimālo vērtību
		for(int i=0; i<v; i++){
			for(int j=0; j<v; j++){
				dist[i][j] = INF;
			}
		}
		int NUL = 0; // piešķirt nulle distanci starp vienādām virsotnēm
		for(int i=0; i<v; i++){
			dist[i][i] = NUL;
		}

		// next init
		int count = 0;
		int[][] dict = new int[rows][cols]; // pirmā palīgvārdnīca ar virsotnēm
		Point[] dict2 = new Point[v]; // otrā palīgvārdnīca ar punktiem
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				dict[i][j] = count; // virsotņu numerācija
				dict2[count] = new Point(j, i); // pirmais x vai y ?
				next[count][count] = count;  // piešķirt ceļus uz virsotnēm pašām pie sevis
				count++;
			}
		}

		// lielo masīvu aizpildīšana pēc labirinta datiem
		count = 0; // virsotņu skaitīšana
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				if(l[i][j]==0){ // ja virsotne nav siena
					//up
					if(i!=0)
						if(l[i-1][j]==0){
							dist[count][count-cols]=1;
							next[count][count-cols]=dict[i-1][j];
						}
					//right
					if(j!=cols-1)
						if(l[i][j+1]==0){
							dist[count][count+1]=1;
							next[count][count+1]=dict[i][j+1];
						}
					//down
					if(i!=rows-1)
						if(l[i+1][j]==0){
							dist[count][count+cols]=1;
							next[count][count+cols]=dict[i+1][j];
						}
					//left
					if(j!=0)
						if(l[i][j-1]==0){
							dist[count][count-1]=1;
							next[count][count-1]=dict[i][j-1];
						}
				}else{ // ja virsotne ir siena
					dist[count][count]=INF;
					next[count][count]=0;
				}
				count++;
		    }
		}

		// risinošā daļa
		for (int k=0; k<v; k++) {
			for (int i=0; i<v; i++) {
				for (int j=0; j<v; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]){
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}

		// rezultātu apkopojums
		Point[] res = new Point[dist[0][v-1]+1]; // punktu masīvs
		int[] path = new int[dist[0][v-1]+1]; // virsotņu masīvs

		int start = 0; // starta virsotne
		int finish = v-1; // finiša virsotne
		if(next[start][finish] == start)
			return res;

		count = 0; // būvēju ceļu no starta līdz finišam
		while(start != finish) {
			start = next[start][finish];
			path[++count] = start;
		}

		count = 0; // pārvēršu ceļu no virsotnēm uz koordinātēm
		for (int p : path)
			res[count++] = dict2[p];

		return res;
	}

// Rin versija DFS
// 	public static Point[] depthFirstSearch() {
// 		int x = 0;
// 		int y = 0;
// 		int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
// 		Map<String, int[]> parents = new HashMap<>();
// 		Set<String> visited = new HashSet<>();
		
// 		outer:
// 		while (true) {
// 			if (y == l.length-1 && x == l[0].length-1) {
// 	        	Point[] way = new Point[l.length * l[0].length];
// 	            //List<int[]> path = new ArrayList<>();
// 	            int[] node = new int[2];
// 	            node[0]=y;
// 	            node[1]=x;
// 	            int i = 0;
// 	            while (node != null) {
// 	            	Point p = new Point();
// 	            	p.y=node[0];
// 	            	p.x=node[1];
// 	            	way[i] = p;
// 	            	i++;
// //	            	System.out.println(x);
// //	            	System.out.println(y);
// 	                node = parents.get(node[0] + "," + node[1]);
// 	            }
// 	            Point[] path = new Point[i];
// 	            i--;
// 	            for (int j=0;i>=0;i--,j++) {
// 	            	path[j]=way[i];
// 	            }
// 	            return path;
// 	        }
			
// 			for (int[] direction : directions) {
// 		    	int ny = y + direction[0];
// 		    	int nx = x + direction[1];

// 		    	if (ny < 0 || ny >= rows || nx < 0 || nx >= cols) {
// 		    		continue;
// 		    	}

// 		    	if (l[ny][nx] == 0 && !visited.contains(ny + "," + nx)) {
// 		    		int[] parent = new int[2];
// 		    		parent[0]=y;
// 		    		parent[1]=x;
// 		    		visited.add(ny + "," + nx);
//                 	x=nx;
//                 	y=ny;
//                 	parents.put(y + "," + x, parent);
//                 	continue outer;
// 		    	}
//         	}
// 			int[] parent = new int[2];
// 			parent = parents.get(y + "," + x);
// 			if (parent == null)
// 				return null;
// 			y = parent[0];
// 			x = parent[1];
// 		}
// 	}

	public static Point[] depthFirstSearch() {

		List<Point> currentPath = new ArrayList<>();
		currentPath.add(new Point(0, 0));
		Set<Point> visited = new HashSet<>();
		visited.add(new Point(0, 0));
		return dfsHelper(currentPath, visited);
	}

	private static Point[] dfsHelper(List<Point> currentPath, Set<Point> visited) {
		Point current = currentPath.get(currentPath.size() - 1);
		int x = current.x;
		int y = current.y;

		if (x == l.length - 1 && y == l[0].length - 1) { // atrada beigas
				return currentPath.toArray(new Point[currentPath.size()]);
		}

		int[][] directions = { { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 } };
		for (int[] direction : directions) {
				int nextX = x + direction[0];
				int nextY = y + direction[1];
				if (nextX >= 0 && nextX < l.length && nextY >= 0 && nextY < l[0].length) {
						if (l[nextY][nextX] == 0 && !visited.contains(new Point(nextX, nextY))) {
								visited.add(new Point(nextX, nextY));
								currentPath.add(new Point(nextX, nextY));
								Point[] result = dfsHelper(currentPath, visited);
								if (result != null) {
										return result; // found path, return it
								}
								currentPath.remove(currentPath.size() - 1);
						}
				}
		}

		return null; // no path found in this branch
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

	// Pretty printing for debugging
	public static void pprint(Point[] path) {
		char[][] out = new char[rows][cols];
		if (path != null) {
			for (Point p: path) {
				out[p.y][p.x] = '+';
			}
		}
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (out[y][x] != 0) {
					System.out.print('+');
				} else if (l[y][x] == 0) {
					System.out.print('.');
				} else if (l[y][x] == 1) {
					System.out.print('#');
				}
				System.out.print(' ');
			}
			System.out.println();
		}
	}
}