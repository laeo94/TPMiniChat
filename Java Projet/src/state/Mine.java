package state;

import java.awt.Color;

import block.GoldMine;
import game.Pos;
import unit.StateUnit;
import unit.Unit;

public class Mine extends StateUnit {
	public final static Color Harvester = new Color(255, 201, 14);
	private Pos actuel;
	private Pos objective;
	private Pos home;
	private boolean havepiece;
	public Mine(int cost,Pos home,Pos actuel) {
		super(cost);
		this.actuel=actuel;
		this.home = home;
		this.objective = home;
		havepiece=false;
		// TODO Auto-generated constructor stub
	}

	public void move(Unit u) {
		if (u.getObjectif() == null) {
			if (u.getPosActual().equals(actuel)|| u.getPosActual().equals(home)) {
				u.setObjective(objective);
				if(u.isGoldMine()<0) u.setObjective(null);
			} else {
				home=u.getActual().posAroundHouse();
				u.setObjective(home);
			}
		} else {
			if(u.getObjectif().equals(home)) {
				if(u.isIsattack() && havepiece) {
					 u.getGame().getMap().getListGoldMine().add(new GoldMine(u.getPosActual(),1));
					 havepiece=false;
					 u.setIsattack(false);
				 }
			}
			
			isInHome(u);
			if (u.isGoldMine()>=0) {
				u.MinusGoldMine();
				u.setIsattack(false);
				havepiece=true;
			}
		}
	}

	private void isInHome(Unit u) {
		if(u.getPosActual().equals(home) && havepiece) {
			u.getActual().setCoffrer(u.getActual().getCoffrer()+10);
			u.getGame().endGame();
			havepiece=false;
		}
	}
	

	
	@Override
	public boolean canMine() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Harvester;
	}

	
	@Override
	public void addObjectif(Pos obj) {
		objective = obj;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canTeleport() {
		// TODO Auto-generated method stub
		return true;
	}


}