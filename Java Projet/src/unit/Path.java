package unit;

import java.util.ArrayList;
import java.util.Collections;

import game.Map;
import game.Pos;

public class Path {
	private boolean[][] file; // position accessible
	private Pos[][] pred; // Predecessor from the position start until the end
	private int[][] dist; // Distance Array
	private int xSize;
	private int ySize;
	private Pos start;
	private Pos end;
	private ArrayList<Pos> listPos = new ArrayList<>();

	public Path(Map m, Pos start, Pos end, Unit unit) {
		this.xSize = m.getMapW();
		this.ySize = m.getMapH();
		this.start = start;
		this.end = end;
		this.file = new boolean[xSize][ySize];
		this.pred = new Pos[xSize][ySize];
		this.dist = new int[xSize][ySize];
		for(int i=0;i<xSize-1;i++) {
			for(int j=0;j<ySize-1;j++) {
				if(!m.isObstacle(unit,new Pos(i,j))) {
					this.file[i][j]=true;
					listPos.add(new Pos(i, j));
				}else {
					this.file[i][j] = false;
				}
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		
		dist[start.getX()][start.getY()] = 0;
	}



	// Create listPos of neighbor
	private ArrayList<Pos> neighbor(Pos s1, ArrayList<Pos> listPos) {
		ArrayList<Pos> neighbors = new ArrayList<>();

		if (s1.getY() <= this.ySize && this.file[(s1.getX())][(s1.getY() + 1)]) {
			neighbors.add(new Pos(s1.getX(), s1.getY() + 1));
		}

		if (s1.getX() > 0 && this.file[s1.getX() - 1][s1.getY()]) {
			neighbors.add(new Pos(s1.getX() - 1, s1.getY()));
		}

		if (s1.getX() <= this.xSize && this.file[s1.getX() + 1][s1.getY()]) {
			neighbors.add(new Pos(s1.getX() + 1, s1.getY()));
		}

		if (s1.getY() > 0 && this.file[s1.getX()][s1.getY() - 1]) {
			neighbors.add(new Pos(s1.getX(), s1.getY() - 1));
		}

		return neighbors;
	}

	private int searchMin() {
		int pathMinIdx = 0;
		int minDist = dist[listPos.get(0).getX()][listPos.get(0).getY()];

		int i = 0;
		for (Pos p : listPos) {
			if (dist[p.getX()][p.getY()] < minDist) {
				pathMinIdx = i;
				minDist = dist[p.getX()][p.getY()];
			}
			i++;
		}

		return pathMinIdx;
	}

	/*
	 * From the start position, we add this to the listPos. While the listPos isn't
	 * null we keep our first position in memory and delete the position of the
	 * listPos and put in false the position that we are currently
	 */

	private void dijkstra() {
		while (listPos.size() > 0) {

			int pathMinIdx = searchMin();
			Pos s1 = listPos.get(pathMinIdx);
			listPos.remove(pathMinIdx);

			ArrayList<Pos> neighbors = this.neighbor(s1, listPos);
			for (Pos s2 : neighbors) {
				if (dist[s2.getX()][s2.getY()] > dist[s1.getX()][s1.getY()] + 1) {
					dist[s2.getX()][s2.getY()] = dist[s1.getX()][s1.getY()] + 1;
					pred[s2.getX()][s2.getY()] = s1;
				}
			}
		}

	}
	
	/* Calculate the shortest path */
	public ArrayList<Pos> calculation() {
		dijkstra();
		ArrayList<Pos> listp = new ArrayList<>();
		Pos s = this.end;
		while (s.getX() != start.getX() || s.getY() != start.getY()) {
			listp.add(new Pos(s.getX(), s.getY()));
			s = this.pred[s.getX()][s.getY()];
		}

		Collections.reverse(listp);

		return listp;

	}
}
