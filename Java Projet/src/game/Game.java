package game;

import unit.Unit;

public class Game {
	private Map map;
	private Player player1;
	private Player player2;
	private boolean partie = true;
	private String winner;

	public Game() {
		map = new Map();
		player1=new Player(this,map.getListHome1());
		player2=new Player(this,map.getListHome2());
		player1.getListUnit().add(new Unit(new Pos(2, 4), this,player1));
		player1.getListUnit().add(new Unit(new Pos(5, 4), this,player1));
		player1.getListUnit().add(new Unit(new Pos(5, 2), this,player1));
		player2.getListUnit().add(new Unit(new Pos(42,20), this,player2));
		player2.getListUnit().add(new Unit(new Pos(39,19), this,player2));
		player2.getListUnit().add(new Unit(new Pos(39,21), this,player2));
		player1.setAdv(player2);
		player2.setAdv(player1);
		winner="";
	}
	
	// Main game with set unit and move unit
	public void gameMain() {
		try {
			Thread.sleep(300);
				for (int i =0;i<player1.getListUnit().size();i++) {
					player1.getListUnit().get(i).move();
				
				}
				for (int i =0;i<player2.getListUnit().size();i++) {
					player2.getListUnit().get(i).move();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	
	
	//Player win in the end of the game
	public void endGame() {
		if(map.getListGoldMine().size()==0) {
			partie=false;
			if(player1.getCoffrer()==player2.getCoffrer()) {
				winner= "Match nul";
				}
			else
			if(player1.getCoffrer()>player2.getCoffrer()) {
				winner= "Player 1 win";
			}else {
				winner="Player 2 win";
			}
			//System.out.println(winner);
		}
		
	}
	public void addObserver(Player p, GameObserver o) {
		for (int i = 0; i < p.getListUnit().size(); i++) {
			p.getListUnit().get(i).register(o);
		}

	}
	/*------------------------------------------Getter && Setter-------------------------------------------------*/
	public Map getMap() {
		return map;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	public String getWinner() {
		return winner;
	}

	public boolean isPartie() {
		return partie;
	}


}