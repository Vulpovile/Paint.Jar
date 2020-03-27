package com.androdome.util.paintdotjar.ui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DetatchablePanel extends JPanel {
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
}
