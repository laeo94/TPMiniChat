package barInterface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import graphic.MyListener;
import unit.EState;

public class ActionButton extends JButton{

	private static final long serialVersionUID = 1L;
	private EState state;
	private MyListener listner;
	private ButtonListner buttonlistner = new ButtonListner();

	public ActionButton(EState state, MyListener listner,String name) {
		this.state = state;
		this.listner = listner;
		this.addMouseListener( buttonlistner);
		this.addMouseListener(listner);
		this.addKeyListener(listner);
		setForeground(Color.white);
		setText(name);
	}
	
	public class ButtonListner extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			listner.setButton(takeButton());
		}
	
	}
	public ActionButton takeButton() {
		return this;
	}
	public MyListener getListner() {
		return listner;
	}

	public void setListner(MyListener listner) {
		this.listner = listner;
	}

	public EState getState() {
		return this.state;
	}

}