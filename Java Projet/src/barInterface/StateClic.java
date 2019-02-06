package barInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;

import game.Game;
import graphic.MyListener;
import unit.EState;
import unit.Unit;

public class StateClic extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Unit u ;
	
	public StateClic (Game g,MyListener listner) {
		super();
		setLayout(new GridLayout (1,5));
		setPreferredSize(new Dimension(50,50));
		ActionButton mine =new ActionButton(EState.Mine,listner,"Mine");
		mine.setBackground(new Color(255, 201, 14));
		ActionButton attack =new ActionButton(EState.Attack,listner,"Attack");
		attack.setBackground(new Color (0,0,128));
		ActionButton sentry =new ActionButton(EState.Sentinel,listner,"Sentinel");
		sentry.setBackground(new Color(0, 64, 64));
		ActionButton normal =new ActionButton(EState.Normal,listner,"Normal");
		normal.setBackground(new Color(98, 11, 19));
		ActionButton addUnit =new ActionButton(EState.newUnit,listner,"New Unit");
		addUnit.setBackground(new Color(98, 11, 19));
		add(mine);
		add(sentry);
		add(attack);
		add(normal);
		add(addUnit);
	}




}
