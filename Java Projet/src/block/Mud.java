package block;

import java.awt.Color;
import java.awt.Graphics;

import game.Pos;
import unit.Unit;

public class Mud extends  Transversable {
	private final static Color mud = new Color(58, 32, 24);
	public Mud(Pos pos) {
		super(pos);
	}
	
	@Override
	public boolean isObstacle(Unit unit) {
		return false;
	}
	
	@Override
	public void paintCompartment(Graphics g, int caseSize, Pos view) {
		// TODO Auto-generated method stub
		g.setColor(mud);
		g.fillRect(this.getPos().getX()*caseSize - (view.getX()) * caseSize,this.getPos().getY()*caseSize - (view.getY())* caseSize,caseSize, caseSize);
	}

	
}
