package state;

import java.awt.Color;
import java.util.ArrayList;

import game.Pos;
import unit.StateUnit;
import unit.Unit;

public class Attacker extends StateUnit {
	private final static Color attack = new Color(0, 0, 128);

	public Attacker(int cost) {
		super(cost);
	}

	@Override
	public void move(Unit unit) {
		int adv = beInZone(unit);
		if(adv >= 0) {
			Pos obj = setPosattacker(unit, unit.getActual().getAdverse().getListUnit().get(adv).getPosActual());
			unit.setObjective(obj);
			unit.getActual().getAdverse().getListUnit().get(adv).minus();
			unit.getActual().getAdverse().getListUnit().get(adv).setIsattack(true);
		} else {
			unit.setObjective(unit.getObjectif());
		}
	}

	private Pos setPosattacker(Unit u, Pos adv) {
		ArrayList<Pos> zone = new ArrayList<Pos>();
		zone.add(new Pos(adv.getX() - 1, adv.getY() - 1));
		zone.add(new Pos(adv.getX(), adv.getY() - 1));
		zone.add(new Pos(adv.getX() + 1, adv.getY() - 1));
		zone.add(new Pos(adv.getX() + 1, adv.getY()));
		zone.add(new Pos(adv.getX() + 1, adv.getY() + 1));
		zone.add(new Pos(adv.getX(), adv.getY() + 1));
		zone.add(new Pos(adv.getX() - 1, adv.getY() + 1));
		for (int j = 0; j < zone.size(); j++) {
			if (!u.getGame().getMap().isObstacle(u, zone.get(j))) {
				return zone.get(j);
			}
		}
		return null;
	}

	@Override
	public boolean canMine() {
		return false;
	}

	@Override
	public Color getColor() {
		return attack;
	}

	@Override
	public void addObjectif(Pos obj) {

	}

	@Override
	public boolean canTeleport() {
		// TODO Auto-generated method stub
		return true;
	}
}
