package game;

import java.util.ArrayList;

import block.Home;
import unit.Unit;

public class Player {
	private Game game;
	// UNITE
	private ArrayList<Unit> listUnit;
	private boolean isUniteSelected;
	private ArrayList<Pos> zonehome;
	private int coffrer;
	private Player adverse;

	public Player(Game game,ArrayList<Home> home) {
		this.game=game;
		this.listUnit = new ArrayList<>();
		this.isUniteSelected = false;
		posAroundHouseBis(home);
		coffrer = 300;
	}
	// Found in the unit list it is selected then set the objective in the parameter
	public void runListUnit(Pos obj) {
		for (Unit unite : listUnit) {
			if (unite.isSelected()) {
				if(obj.getX()>=45 || obj.getY()>=23) return;
				if (!game.getMap().isObstacle(unite,obj)) {
					if(unite.canMine()) {
						unite.setObjective(obj);
						unite.getState().addObjectif(obj);
						if(unite.isGoldMine()<0) {
							unite.setObjective(null);
							unite.getState().addObjectif(null);
						}

					}else {
						unite.setObjective(obj);
						unite.getState().addObjectif(obj);
					}
				}
			}
		}
	}
	// Select unite
	public void selectUnit(Pos c) {
		for (Unit unit : listUnit) {
			unit.setSelected(false);
		}
		for (Unit unit : listUnit) {
			if (c.equals(unit.getPosActual())) {
				unit.setSelected(true);
				this.setUniteSelected(true);
			}
		}
	}




	public void addUnit() {
		Unit u=new Unit(posAroundHouse(), game,this);
		listUnit.add(u);
		setCoffrer(coffrer - Unit.getCost());
	}

	public Pos posAroundHouse() {
		boolean good;
		for (int j = 0; j < zonehome.size(); j++) {
			good=true;
			for ( Unit u : listUnit) {
				if (u.getPosActual().equals(zonehome.get(j))) {
					good = false;
				}
			}
			if (good)
				return new Pos(zonehome.get(j).getX(),zonehome.get(j).getY());
		}
		return null;
	}

	private void posAroundHouseBis(ArrayList<Home> home) {
		zonehome=new ArrayList<>();
		zonehome.add(new Pos(home.get(0).getPos().getX() - 1, home.get(0).getPos().getY() - 1));
		zonehome.add(new Pos(home.get(0).getPos().getX() - 1, home.get(0).getPos().getY()));
		zonehome.add(new Pos(home.get(0).getPos().getX() - 1, home.get(0).getPos().getY() + 1));
		zonehome.add(new Pos(home.get(0).getPos().getX() - 1, home.get(0).getPos().getY() + 2));
		zonehome.add(new Pos(home.get(0).getPos().getX(), home.get(0).getPos().getY() - 1));
		zonehome.add(new Pos(home.get(0).getPos().getX(), home.get(0).getPos().getY() + 2));
		zonehome.add(new Pos(home.get(0).getPos().getX() + 1, home.get(0).getPos().getY() - 1));
		zonehome.add(new Pos(home.get(0).getPos().getX() + 1, home.get(0).getPos().getY() + 2));
		zonehome.add(new Pos(home.get(0).getPos().getX() + 2, home.get(0).getPos().getY() - 1));
		zonehome.add(new Pos(home.get(0).getPos().getX() + 2, home.get(0).getPos().getY()));
		zonehome.add(new Pos(home.get(0).getPos().getX() + 2, home.get(0).getPos().getY() + 1));
		zonehome.add(new Pos(home.get(3).getPos().getX() + 1, home.get(3).getPos().getY() + 1));
	}

	/*------------------------------------------Getter && Setter-------------------------------------------------*/
	public ArrayList<Unit> getListUnit() {
		return this.listUnit;
	}

	public boolean isUniteSelected() {
		return isUniteSelected;
	}

	public void setUniteSelected(boolean isUniteSelected) {
		this.isUniteSelected = isUniteSelected;
	}

	public int getCoffrer() {
		return coffrer;
	}

	public void setCoffrer(int coffrer) {
		this.coffrer = coffrer;
	}

	public Player getAdverse() {
		return adverse;
	}
	public void setAdv(Player adv) {
		this.adverse = adv;
	}

}
