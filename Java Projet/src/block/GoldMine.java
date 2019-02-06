package block;

import java.awt.Color;
import java.awt.Graphics;

import game.Pos;
import unit.Unit;

public  class GoldMine extends NotTranversable{
	private int quantity;

	public GoldMine(Pos pos, int quantity) {
		super(pos);
		this.quantity=quantity;
	}


	public int minusPiece() {
		return quantity--;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public boolean isObstacle(Unit unit) {
		return true;
	}

	@Override
	public void paintCompartment(Graphics g, int caseSize, Pos view) {
		// TODO Auto-generated method stub
		g.setColor(Color.yellow);
		g.fillOval(this.getPos().getX()*caseSize+8 - (view.getX()) * caseSize,this.getPos().getY()*caseSize+8 - (view.getY())* caseSize,caseSize-25, caseSize-25);
		g.setColor(Color.BLACK);
		g.drawString(this.getQuantity()*10+"",this.getPos().getX()*caseSize+20 - (view.getX()) * caseSize,this.getPos().getY()*caseSize+20 - (view.getY())* caseSize);
	}


}
