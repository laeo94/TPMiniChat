package block;

import game.Pos;
import unit.Unit;

public abstract class Compartment extends ColorGraphic{
	private Pos pos;
	
	public Compartment (Pos pos) {
		this.pos = pos;
	}

	public Pos getPos() {
		return pos;
	}
	public abstract boolean isObstacle(Unit u);

}
