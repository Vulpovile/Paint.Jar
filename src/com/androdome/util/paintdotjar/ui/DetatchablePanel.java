package com.androdome.util.paintdotjar.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class DetatchablePanel extends JPanel implements WindowListener, MouseMotionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DetatchablePanel()
	{
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public DetatchablePanel(String string) {
		this();
		this.setName(string);
	}
	Border trueBorder = null;
	JDialog containerFrame = null;
	Container parentComponent = null;
	public Frame getParentFrame()
	{
		Container parentContainer = getParent();
		while(parentContainer != null)
		{
			if(parentContainer instanceof Frame)
				return (Frame)parentContainer;
			parentContainer = parentContainer.getParent();
		}
		return null;
	}
	public void detatch()
	{
		if(containerFrame == null)
		{
			parentComponent = this.getParent();
			containerFrame = new JDialog(this.getParentFrame(), this.getName());
			containerFrame.addWindowListener(this);
			this.setPreferredSize(this.getSize());
			containerFrame.setLocationRelativeTo(this);
			parentComponent.remove(this);
			parentComponent.validate();
			if(parentComponent instanceof JPanel)
				((JPanel)parentComponent).revalidate();
			
			parentComponent.repaint();
			
			containerFrame.add(this);
			containerFrame.pack();
			containerFrame.setVisible(this.isVisible());
			containerFrame.addMouseMotionListener(this);
		}
	}
	public void attatch()
	{
		canAttatch = false;
		if(trueBorder != null)
		{
			this.setBorder(trueBorder);
			trueBorder = null;
		}
		if(containerFrame != null)
		{
			containerFrame.remove(this);
			containerFrame.dispose();
			containerFrame = null;
			
		}
		if(parentComponent != null)
		{
			parentComponent.add(this);
			parentComponent.validate();
			if(parentComponent instanceof JPanel)
				((JPanel)parentComponent).revalidate();
			parentComponent.repaint();
		}
	}
	
	public boolean isAttatched()
	{
		return containerFrame == null;
	}
	private Point movePoint = null;
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent e) {
		attatch();
	}
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		movePoint = new Point(e.getX(), e.getY());
	}
	public void mouseReleased(MouseEvent e) {
		movePoint = null;
		if(!isAttatched() && canAttatch)
		{
			attatch();
		}
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private int threshold = 5;
	boolean canAttatch = false;
	long systemAttTimeout = 0;
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(isAttatched())
		{
			if(x < movePoint.x - threshold || x > movePoint.x + threshold || y < movePoint.y - threshold || y > movePoint.y + threshold)
			{
				Point oldLoc = this.getLocationOnScreen();
				Point oldMV = new Point(movePoint.x, movePoint.y);
				detatch();
				System.out.println(oldLoc.x-containerFrame.getInsets().left);
				System.out.println(oldLoc.x);
				containerFrame.setLocation(oldLoc.x-containerFrame.getInsets().left, oldLoc.y-containerFrame.getInsets().right);
				movePoint = oldMV;
				systemAttTimeout = System.currentTimeMillis();
				try
				{
					Robot robot = new Robot();
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					robot.mousePress(InputEvent.BUTTON1_MASK);
				}
				catch (AWTException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else
		{
			

			int absx = x+this.getLocationOnScreen().x;
			int absy = y+this.getLocationOnScreen().y;
			
            int thisX = containerFrame.getLocation().x;
            int thisY = containerFrame.getLocation().y;

            // Determine how much the mouse moved since the initial click
            int xMoved = e.getX() - movePoint.x;
            int yMoved = e.getY() - movePoint.y;

            // Move window to this position
            int X = thisX + xMoved;
            int Y = thisY + yMoved;
            containerFrame.setLocation(X, Y);
			
			//containerFrame.setLocation(absx-x-containerFrame.getInsets().left, absy-y-containerFrame.getInsets().top);
			if(systemAttTimeout < System.currentTimeMillis()-1000 && parentComponent != null)
			{
				int wx = parentComponent.getLocationOnScreen().x;
				int wy = parentComponent.getLocationOnScreen().y;
				if(absx >= wx && absx <= wx+parentComponent.getWidth() && absy >= wy && absy <= wy+parentComponent.getHeight())
				{
					if(trueBorder == null)
					{
						trueBorder = this.getBorder();
						this.setBorder(new LineBorder(Color.RED));
					}
					canAttatch = true;
				}
				else 
				{
					if(trueBorder != null)
					{
						this.setBorder(trueBorder);
						trueBorder = null;
					}
					canAttatch = false;
				}
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		/*if(movePoint != null)
			mouseDragged(e);*/// Not working...
	}
}
