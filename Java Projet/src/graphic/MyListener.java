package graphic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import barInterface.ActionButton;
import game.GameObserver;
import game.Pos;
import unit.EState;
import unit.Unit;

public class MyListener extends MouseAdapter implements MouseListener, KeyListener {
	private InterfaceGame interfaces;
	private ActionButton button;
	private Gui g ;
	public MyListener(InterfaceGame interfaces, Gui g) {
		button = new ActionButton(null, this, "");
		interfaces.setFocusable(true);
		this.interfaces = interfaces;
		this.g=g;
	}

	/*---------------------------------------------------MouseListener-------------------------------------------*/

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (SwingUtilities.isRightMouseButton(e)) {
			if (this.interfaces.getP().isUniteSelected()) {
				this.interfaces.getP().runListUnit(this.interfaces.convertPixelToCase(x, y));
				this.interfaces.getP().setUniteSelected(false);
			}
			for (Unit unit : interfaces.getP().getListUnit()) {
				if (!unit.isSelected()) {
					this.interfaces.getP().selectUnit(this.interfaces.convertPixelToCase(x, y));
					this.interfaces.getP().setUniteSelected(true);
				}
			}
		}

		if (SwingUtilities.isLeftMouseButton(e)) {
			Pos clic = this.interfaces.convertPixelToCase(x, y);
			for (Unit unit : interfaces.getP().getListUnit()) {
					if (button.getState() == EState.Attack || button.getState() == EState.Mine
							|| button.getState() == EState.Sentinel || button.getState() == EState.Normal) {
						if(button.getState() == EState.Sentinel) {
							if(interfaces.getP().getCoffrer()< 20) {
								this.setButton(button.takeButton());
								JOptionPane.showMessageDialog(null, "Not enought money !");
								return;
							}
						}
							if (button.getState() == EState.Attack) { 
								if(interfaces.getP().getCoffrer()<30) {
								this.setButton(button.takeButton());
								JOptionPane.showMessageDialog(null, "Not enought money !");
								return;
								}
							}
						if (!unit.isSelected()) {
							if (unit.getPosActual().equals(clic)) {
								unit.setSelected(true);
								unit.changeState(button.getState());
								interfaces.getP().setCoffrer(interfaces.getP().getCoffrer() - unit.getState().cost);
								unit.setSelected(false);
								button= new ActionButton(null, this, "");
							}
						}
					ArrayList<GameObserver> o = new ArrayList<>();
					unit.notifyObserver(o);
				}
			
			}
			if (button.getState() == EState.newUnit) {
				if (interfaces.getP().getCoffrer() < Unit.getCost()) {
					mouseReleased(e);
					JOptionPane.showMessageDialog(null, "Not enought money !");
					return;
				}
				interfaces.getP().addUnit();
				ArrayList<GameObserver> o = new ArrayList<>();
				interfaces.getP().getListUnit().get(0).notifyObserver(o);
				interfaces.getGame().addObserver(interfaces.getP(),g);
				button= new ActionButton(null, this, "");
			}
		}
	
	}

	
	

	/*--------------------------------------------KeyListener-------------------------------------------------------*/
	// When the key is pressed then change the view of the map
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP :if (interfaces.getView().getY() > 0) interfaces.getView().setY(interfaces.getView().getY() - 1);break;
		case KeyEvent.VK_DOWN :if (interfaces.getView().getY() < 9) interfaces.getView().setY(interfaces.getView().getY() + 1);break;
		case KeyEvent.VK_LEFT :if (interfaces.getView().getX() > 0) interfaces.getView().setX(interfaces.getView().getX() - 1);break;
		case KeyEvent.VK_RIGHT:if (interfaces.getView().getX() < 32) interfaces.getView().setX(interfaces.getView().getX() + 1);break;
		default: break;
		}
		interfaces.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void setButton(ActionButton button) {
		this.button = button;
	}

	public InterfaceGame getInterface() {
		return interfaces;
	}

}
