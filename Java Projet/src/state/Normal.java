package state;

import java.awt.Color;

import game.Pos;
import unit.StateUnit;
import unit.Unit;

public class Normal extends StateUnit{
	private final static Color vin = new Color(98, 11, 19);
	
	public Normal(int cost) {
		super(cost);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canMine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return vin;
	}

	@Override
	public void move(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObjectif(Pos obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean canTeleport() {
		// TODO Auto-generated method stub
		return true;
	}
}
