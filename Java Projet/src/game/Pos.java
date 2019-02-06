package game;

public class Pos {
	private int x ;
	private int y ;
	public Pos () {
		this.x = 0;
		this.y = 0;
	 }
	public Pos (int x, int y) {
		 this.setX(x);
		 this.setY(y);
		 
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Pos p) {
		if(x==p.x && y==p.y) return true;
		return false;
	}
	
}
