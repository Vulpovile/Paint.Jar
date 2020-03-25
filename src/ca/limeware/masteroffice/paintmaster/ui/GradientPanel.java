package ca.limeware.masteroffice.paintmaster.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GradientPanel extends JPanel {

	short direction = 0x0001;
	Color otherBG = this.getBackground();
	public GradientPanel()
	{
		this.setOpaque(false);
		otherBG = this.getBackground();
	}
	public Color getBackground()
	{
		return otherBG;
	}
	public void setBackground(Color bg)
	{
		otherBG = bg;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void paintComponent(Graphics g)
	{
		
		switch(direction)
		{
			//case 0x0001:
				//for(int i = this.getWidth(); i < )
			
		}
		super.paintComponent(g);
	}

}
