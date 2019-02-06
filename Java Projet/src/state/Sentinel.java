package state;

import java.awt.Color;

import game.Pos;
import unit.StateUnit;
import unit.Unit;

public class Sentinel extends StateUnit {
	public static Color sentry = new Color(0, 64, 64);
	Pos actuel;
	Pos objectif;
	public Sentinel(int cost,Pos actuel) {
		super(cost);
		this.actuel= actuel;
	}

	public void move(Unit unit) {
		if (unit.getObjectif() == null) {
			if (unit.getPosActual().equals(actuel)) unit.setObjective(objectif);
			else unit.setObjective(actuel);
		}
		int adv = beInZone(unit);
		if(adv>=0) {
			unit.setObjective(unit.getPosActual());
			unit.getActual().getAdverse().getListUnit().get(adv).setIsattack(true);
			unit.getActual().getAdverse().getListUnit().get(adv).minus();
		}

	}

	@Override
	public boolean canMine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return sentry;
	}


	@Override
	public void addObjectif(Pos obj) {
		// TODO Auto-generated method stub
		objectif = obj;
	}

	@Override
	public boolean canTeleport() {
		// TODO Auto-generated method stub
		return false;
	}

}
