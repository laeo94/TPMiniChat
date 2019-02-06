package graphic;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import barInterface.StateClic;
import game.Game;
import game.GameObserver;
import game.Pos;

public class Gui implements GameObserver {
	private JFrame frame;
	private JFrame frame1;
	private InterfaceGame interp1;
	private InterfaceGame interp2;
	private StateClic bar1;
	private StateClic bar2;
	protected Game game;

	public Gui() {
		this.game = new Game();
		this.frame = new JFrame("Strategy game : Player 1");
		this.frame1 = new JFrame("Strategy game : Player 2");
		this.interp1 = new InterfaceGame(this.game, game.getPlayer1(), frame1,new Pos(0,0),this);
		this.interp2 = new InterfaceGame(this.game, game.getPlayer2(), frame,new Pos(31,9),this);
		game.addObserver(game.getPlayer1(),this);
		game.addObserver(game.getPlayer2(),this);
		bar2 = new StateClic(game, interp2.listener);
		bar1 = new StateClic(game, interp1.listener);		
		frame.setSize(interp1.getSize());
		frame1.setSize(interp2.getSize());
		frame.setLocation(0, 0);
		frame1.setLocation(800, 0);
		frame.setResizable(false);
		frame1.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame1.setVisible(true);
		frame.add(interp1, BorderLayout.CENTER);
		frame1.add(interp2, BorderLayout.CENTER);
		frame.add(this.bar1, BorderLayout.SOUTH);
		frame1.add(this.bar2, BorderLayout.SOUTH);
		this.bar1.setFocusable(true);
		this.bar2.setFocusable(true);
		this.interp1.setFocusable(true);
		this.interp2.setFocusable(true);
		this.interp1.requestFocus();
		this.interp2.requestFocus();
		this.bar1.requestFocus();
		this.bar2.requestFocus();
		this.bar1.requestFocusInWindow();
		this.bar2.requestFocusInWindow();
		this.interp1.requestFocusInWindow();
		this.interp2.requestFocusInWindow();
		this.interp1.mainInterface(this);		
		this.interp2.mainInterface(this);
		
	}
	
	
	public JFrame getFrame() {
		return frame;
	}


	public JFrame getFrame1() {
		return frame1;
	}


	@Override
	public void notify(ArrayList<GameObserver> events) {
		interp1.repaint();
		interp2.repaint();
		// TODO Auto-generated method stub
		
	}

}