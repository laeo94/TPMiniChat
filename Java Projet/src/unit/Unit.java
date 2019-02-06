package unit;

import java.awt.Color;
import java.util.ArrayList;

import game.Game;
import game.GameObserver;
import game.Player;
import game.Pos;
import state.Normal;

public class Unit {
	private Game game;
	private Path path;
	private ArrayList<Pos> pathpos;
	private Pos posActual;
	private Pos objective;
	private StateUnit state;
	private int startPosCompter = -1;
	private boolean isSelected;
	public static final int INITIAL_NB_LIFE =50;
	private int numberOfLife = INITIAL_NB_LIFE;
	private static int cost = 50;
	private Player actual;
	private boolean isattack;
	private boolean stop=false; //Mud
	private ArrayList<GameObserver> observers;
	public Unit(Pos posActual, Game game, Player actu) {
		this.game = game;
		this.posActual = posActual;
		this.objective = null;
		state = new Normal(0);
		pathpos = new ArrayList<>();
		actual = actu;
		isattack=false;
		observers =  new ArrayList<GameObserver>();
	}
	
	public void register(GameObserver o) {
		observers.add(o);
	}

	public void unregister(GameObserver o) {
		observers.remove(o);
	}

	public void notifyObserver(ArrayList<GameObserver> events) {
		for (GameObserver gameObserver : observers) {
			gameObserver.notify(events);
			
		}
	}
	// Move the unit

	public void move() {
		if (this.objective != null) {

			if (startPosCompter == -1) {
				path = new Path(this.game.getMap(), posActual, objective, this);
				pathpos = path.calculation();
				startPosCompter++;
			}
			if (startPosCompter < pathpos.size()) {
				if(!isMud()) {
					this.posActual = pathpos.get(startPosCompter);
					startPosCompter++;
					stop=false;
				}
			} else {
				this.objective = null;
				startPosCompter = -1;
			}
		}
		ArrayList<GameObserver> o = new ArrayList<>(); //list event 
		notifyObserver(o);
		isTeleporter();
		state.move(this);

	}
	

	public void minus() {
		numberOfLife--;
		if(numberOfLife==0) {
			actual.getListUnit().remove(this);
		}

	}
	public void changeState(EState estate) {
		this.state = StateFactory.make(estate, posActual,actual.posAroundHouse());
	}

	/* Found if the objective is a goldmine  and return the position in the list of GoldMine*/
	public int isGoldMine() {
		if(objective==null) return -1;
		for (int i = 0; i < game.getMap().getListGoldMine().size(); i++) {
			if(game.getMap().getListGoldMine().get(i).getPos().equals(objective)) {
				return i;
			}
		}
		return -1;

	}
	/* Minus the goldmine quantity when mine is arrive in the position of goldmine*/
	public int MinusGoldMine() {
		if(game.getMap().getListGoldMine().get(isGoldMine()).getPos().equals(posActual)) {
			game.getMap().getListGoldMine().get(isGoldMine()).minusPiece();
			if (game.getMap().getListGoldMine().get(isGoldMine()).getQuantity() == 0) {
				game.getMap().getListGoldMine().remove(isGoldMine());
				return -1;
			}

		}
		return 0;
	}

	/*If unit is in position of the teleporter then set the new position of the unit and recalculate the path*/
	public void isTeleporter() {
		for(int i = 0 ; i <game.getMap().getListTeleporter().size() ; i++) {
			if(objective==null) return;
			if(game.getMap().getListTeleporter().get(i).getPos().equals(objective)) {
				if(game.getMap().getListTeleporter().get(i).getPos().equals(posActual)) {
					posActual=game.getMap().getListTeleporter().get(i).getPosfinal();
				}
			}else {
				if(game.getMap().getListTeleporter().get(i).getPos().equals(posActual)) {
					posActual=game.getMap().getListTeleporter().get(i).getPosfinal();
					path = new Path(game.getMap(), posActual,objective,this);
					pathpos=path.calculation();
					startPosCompter=-1;
				}
			}
		}
	}

	/*If unit cross a mud then /2 the time of the unit*/
	public boolean isMud() {
		for(int i = 0 ; i <game.getMap().getListMud().size() ; i++) {
			if(game.getMap().getListMud().get(i).getPos().equals(posActual)) {
				if(stop) return false;
				else{
					stop=true;
					return true;
				}
			}
		}
		return false;
	}

	/*------------------------------------------Getter && Setter-------------------------------------------------*/
	public boolean canMine() {
		return state.canMine();
	}
	public boolean canTeleport() {
		return state.canTeleport();
	}

	public Pos getObjectif() {
		return objective;
	}

	public Pos getPosActual() {
		return posActual;
	}

	public void setObjective(Pos objectif) {
		this.objective = objectif;
	}
	public void setPosActual(Pos posActual) {
		this.posActual=posActual;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean actuel) {
		this.isSelected = actuel;
	}

	public int getNumberOfLife() {
		return numberOfLife;
	}

	public static int getCost() {
		return cost;
	}

	public Game getGame() {
		return game;
	}
	public Color getColor() {
		return state.getColor();
	}
	public StateUnit getState() {
		return state;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setPathpos(ArrayList<Pos> pathpos) {
		this.pathpos = pathpos;
	}

	public Path getPath() {
		return path;
	}

	public void setStartPosCompter(int startPosCompter) {
		this.startPosCompter = startPosCompter;
	}
	public Player getActual() {
		return actual;
	}
	public boolean isIsattack() {
		return isattack;
	}

	public void setIsattack(boolean isattack) {
		this.isattack = isattack;
	}
}
