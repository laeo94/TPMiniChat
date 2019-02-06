package block;

import game.Pos;
import unit.Unit;

public abstract class NotTranversable extends Compartment  {

	public NotTranversable(Pos pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}
	public abstract boolean isObstacle(Unit unit);

}
