package unit;

import game.Pos;
import state.Attacker;
import state.Mine;
import state.Normal;
import state.Sentinel;

public class StateFactory {
	
	public static StateUnit make(EState c,Pos actuel,Pos home) {
		switch (c) {
		case Mine : return new Mine(0,home,actuel);
		case Sentinel : return new Sentinel(20,actuel);
		case Attack :  return new Attacker(30);
		case Normal : return new Normal(0);
		default: return null;
		}
	}
	
	
}
