
package graphic;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import block.GoldMine;
import block.Home;
import block.Mud;
import block.Teleporter;
import block.Tree;
import game.Pos;
import game.Game;
import game.Player;

public class InterfaceGame extends JPanel {
	private static final long serialVersionUID = 1L; //Variable utile transferer fichier dans le reseau copie du fichier
	protected MyListener listener;
	private Game game;
	private final static int WIDTH = 800;
	private final static int HEIGHT = 800;
	private Pos view;
	private int width;
	private int height;
	private int caseSize;
	private Player p;
	JFrame frame;
	public InterfaceGame (Game game, Player p, JFrame frame,Pos view,Gui g) {
		super();
		this.game=game;
		this.p=p;
		setSize(WIDTH, HEIGHT);
		this.listener = new MyListener(this,g);
		width = getSize().width;
		height = getSize().height;
		this.view = view;
		this.caseSize = 50;
		this.addMouseListener(listener);
		this.addKeyListener(listener);
		this.frame=frame;
	}
	// Print the unit if it is selected then red else vine
	public void drawUnit(Graphics g) {
		for(int i=0;i<p.getListUnit().size();i++) {
			g.setColor(Color.blue);
			g.drawOval(((p.getListUnit().get(i).getPosActual().getX() - (view.getX())) * caseSize) - 25,((p.getListUnit().get(i).getPosActual().getY() - (view.getY())) * caseSize) - 25, 100, 100);
			g.drawString(p.getListUnit().get(i).getNumberOfLife()+"",(p.getListUnit().get(i).getPosActual().getX() - (view.getX())) * caseSize,(p.getListUnit().get(i).getPosActual().getY() - (view.getY())) * caseSize);
			if(p.getListUnit().get(i).isSelected()) g.setColor(Color.RED); else g.setColor(p.getListUnit().get(i).getColor());
			g.fillOval((p.getListUnit().get(i).getPosActual().getX() - (view.getX())) * caseSize,(p.getListUnit().get(i).getPosActual().getY() - (view.getY())) * caseSize, caseSize, caseSize);
		}
		for (int j =0;j<p.getAdverse().getListUnit().size();j++) {
			g.setColor(Color.red);
			g.drawOval(((p.getAdverse().getListUnit().get(j).getPosActual().getX() - (view.getX())) * caseSize) - 25,((p.getAdverse().getListUnit().get(j).getPosActual().getY() - (view.getY())) * caseSize) - 25, 100, 100);
			g.setColor(p.getAdverse().getListUnit().get(j).getColor());
			g.fillOval((p.getAdverse().getListUnit().get(j).getPosActual().getX() - (view.getX())) * caseSize,(p.getAdverse().getListUnit().get(j).getPosActual().getY() - (view.getY())) * caseSize, caseSize, caseSize);
		}

	}
	
	// Main interface
	public void paintComponent(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);
		paintMap(g);
		drawUnit(g);
		g.setColor(Color.black);
		g.fillRect(600,600,200,100);
		g.setColor(Color.YELLOW);
		g.fillOval(750,620,25,25);
		g.setColor(Color.white);
		g.drawString("Lea and Nayani", 650, 680);
		g.drawString("Coffre : "+p.getCoffrer()+"pieces", 650,630);
		g.drawString("Number pieces :"+game.getMap().getListGoldMine().size(), 650,640);
		g.drawString("Number unit: "+p.getListUnit().size(),650,650);

	}
	// Paint the map
	public void paintMap(Graphics g) {
		for (int i = 0; i <game.getMap().getMapW(); i++) {
			for (int j = 0; j <game.getMap().getMapH(); j++) {
				g.setColor(Color.white);
				g.fillRect((i - view.getX()) * caseSize, (j - view.getY()) * caseSize, caseSize, caseSize);
				g.setColor(Color.black);
				g.drawRect((i - view.getX()) * caseSize, (j - view.getY()) * caseSize, caseSize, caseSize);
			}
		}
		for(GoldMine gold : game.getMap().getListGoldMine()) {
			gold.paintCompartment(g, caseSize, view);
		}
		for(Home home : game.getMap().getListHome1()) {
			home.paintCompartment(g, caseSize, view);
		}
		for(Home home : game.getMap().getListHome2()) {
			home.paintCompartment(g, caseSize, view);
		}
		for(Mud mud:game.getMap().getListMud()) {
			mud.paintCompartment(g, caseSize, view);
		}
		for(Teleporter t: game.getMap().getListTeleporter()) {
			t.paintCompartment(g, caseSize, view);
		}
		for(Tree tr: game.getMap().getListTree()) {
			tr.paintCompartment(g, caseSize, view);
		}

	}

	// Convert pixel into case
	public Pos convertPixelToCase(int x, int y) {
		return new Pos((x / caseSize) + view.getX(), ((y / caseSize) + view.getY()));
	}

	/*---------------------------------------------------------- MAIN GAME --------------------------------*/
	// Main game with set unit and move unit
	public void mainInterface(Gui g) {
		while (game.isPartie()) {
			this.game.gameMain();
		}
		 int result = JOptionPane.showOptionDialog(this,"Winner :"+game.getWinner()+" Rejouer?","fin de partie",
	                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
	                null, null, null);
	        if (result == JOptionPane.YES_OPTION) {
	        	g.getFrame().dispose();
	        	g.getFrame1().dispose();
	        	new Gui();
	        }else {
	        	System.exit(0);
	        }
		
		
	}
	/*------------------------------------------Getter && Setter-------------------------------------------------*/
	public Pos getView() {
		return view;
	}
	public Game getGame() {
		return game;
	}
	public Player getP() {
		return p;
	}

}