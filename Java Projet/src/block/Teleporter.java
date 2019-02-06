package block;

import java.awt.Color;
import java.awt.Graphics;

import game.Pos;
import unit.Unit;

public class Teleporter extends Transversable {
	private Pos posfinal;

	public Teleporter(Pos pos, Pos posfinal) {
		super(pos);
		this.posfinal = posfinal;
	}

	public Pos getPosfinal() {
		return posfinal;
	}

	public void move(Unit unit) {
		unit.setObjective(posfinal);
	}

	public boolean isObstacle(Unit unit) {
		if(unit.canTeleport()) return false;
		return true;
		}

	@Override
	public void paintCompartment(Graphics g, int caseSize, Pos view) {
		// TODO Auto-generated method stub
		g.setColor(Color.GRAY);
		g.fillOval(this.getPos().getX()*caseSize - (view.getX()) * caseSize,this.getPos().getY()*caseSize - (view.getY())* caseSize,caseSize, caseSize);
		g.drawOval(posfinal.getX()*caseSize - (view.getX()) * caseSize,posfinal.getY()*caseSize - (view.getY())* caseSize,caseSize, caseSize);
	}


}
