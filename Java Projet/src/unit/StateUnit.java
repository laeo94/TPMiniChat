package unit;

import java.awt.Color;

import game.Pos;

public abstract class StateUnit {
    public int cost;
    public StateUnit(int cost) {
    	this.cost = cost;
    	
    }
    
    
    /*Return the position of the adv in the list if he is in the zone of the unit*/
    public int beInZone(Unit u) {
		Pos topLeft = new Pos(u.getPosActual().getX()-3,u.getPosActual().getY()-3);
		Pos bottomRight = new Pos (u.getPosActual().getX()+3,u.getPosActual().getY()+3);
		for(int i =0;i<u.getActual().getAdverse().getListUnit().size();i++) {
			Unit unit =u.getActual().getAdverse().getListUnit().get(i);
			if(unit.getPosActual().getX()>=topLeft.getX() && unit.getPosActual().getY()>=topLeft.getY()) {
				if(unit.getPosActual().getX()<=bottomRight.getX() && unit.getPosActual().getY()<=bottomRight.getY()) {
					return i;
				}
			}
		}
		return -1;
	}
    public abstract void addObjectif (Pos obj);
    public abstract void move (Unit unit);
    public abstract boolean canMine();
    public abstract boolean canTeleport();
    public abstract Color getColor() ;
}
