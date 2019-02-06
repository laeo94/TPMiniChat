package block;

import java.awt.Color;
import java.awt.Graphics;

import game.Pos;
import unit.Unit;

public class Tree extends NotTranversable{
	private final static Color green = new Color(74, 151, 55);
	public Tree(Pos pos) {
		super(pos);
	}
	
	@Override
	public boolean isObstacle(Unit unit) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void paintCompartment(Graphics g, int caseSize,Pos view) {
		// TODO Auto-generated method stub
		g.setColor(green);
		g.fillRect(this.getPos().getX()*caseSize - (view.getX()) * caseSize,this.getPos().getY()*caseSize - (view.getY())* caseSize,caseSize, caseSize);
	}
	


}

