package com.androdome.util.paintdotjar.ui;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DetatchablePanel extends JPanel implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame containerFrame = null;
	Container parentComponent = null;
	public void detatch()
	{
		if(containerFrame == null)
		{
			parentComponent = this.getParent();
			containerFrame = new JFrame(this.getName());
			containerFrame.addWindowListener(this);
			this.setPreferredSize(this.getSize());
			containerFrame.setLocationRelativeTo(this);
			parentComponent.remove(this);
			containerFrame.add(this);
			containerFrame.pack();
			containerFrame.setVisible(this.isVisible());
		}
	}
	public void attatch()
	{
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
			parentComponent.repaint();
		}
	}
	
	public boolean isAttatched()
	{
		return containerFrame == null;
	}
	
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
}
