package block;

import java.awt.Color;
import java.awt.Graphics;

import game.Pos;
import unit.Unit;

public class Home extends NotTranversable {
	private final static Color house1 = new Color(50, 48, 124);
	private final static Color house2 = new Color(0, 48, 124);
	private char l;
	public Home(Pos pos, char l) {
		super(pos);
		this.l=l;
	}

	@Override
	public boolean isObstacle(Unit unit) {
		return true;
	}
	@Override
	public void paintCompartment(Graphics g, int caseSize, Pos view) {
		if(l=='L')g.setColor(house1); else g.setColor(house2);
		g.fillRect(this.getPos().getX()*caseSize - (view.getX()) * caseSize,this.getPos().getY()*caseSize - (view.getY())* caseSize,caseSize, caseSize);
	}


}
