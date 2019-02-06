package game;

import java.util.ArrayList;

public interface GameObserver {
	public void notify (ArrayList<GameObserver> events);
}
