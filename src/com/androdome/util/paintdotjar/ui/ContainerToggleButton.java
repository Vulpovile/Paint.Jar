package com.androdome.util.paintdotjar.ui;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.imageio.ImageIO;
import javax.swing.JToggleButton;

import com.androdome.util.paintdotjar.Canvas;


public class ContainerToggleButton extends JToggleButton implements MouseMotionListener, MouseListener {
	/**
	 * 
	 */
	private int crossSize = 10;
	private int crossOffset = 5;
	private boolean isOverArea = false;
	private static final long serialVersionUID = 1L;
	private CanvasContainer canvas;
	private static Image crossImage = null;
	boolean displayCross = true;
	public CanvasContainer getCanvasContainer()
	{
		return canvas;
	}
	public void setCrossDisplayed(boolean isDisplayed)
	{
		displayCross = isDisplayed;
	}
	public ContainerToggleButton(CanvasContainer cc)
	{
		canvas = cc;
		cc.containerButton = this;
		addMouseMotionListener(this);
		addMouseListener(this);
		if(crossImage == null)
		{
			try {
				crossImage = ImageIO.read(this.getClass().getResourceAsStream("/cross.png"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isOverX()
	{
		return isOverArea;
	}
	public ContainerToggleButton(CanvasContainer cc, Dimension psize)
	{
		this(cc);
		this.setPreferredSize(psize);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for(Canvas c : canvas.getLayers())
		{
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, c.getOpacity() / 255F));
			g2d.drawImage(c.getImage(), 6, 6, getWidth()-12, getHeight()-12, this);
		}
		if(displayCross && crossImage != null)
		{
			if(isOverArea)
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			else
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
			g2d.drawImage(crossImage, getWidth()-(crossSize+crossOffset), crossOffset, crossSize, crossSize, this);
		}
	}
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseMoved(MouseEvent e) {
		if(e.getX() > this.getWidth()-(crossSize+crossOffset) && e.getY() < crossSize+crossOffset && e.getX() < getWidth() && e.getY() > 0)
		{
			if(!isOverArea)
			{
				isOverArea = true;
				repaint();
			}
		}
		else if(isOverArea)
		{
			isOverArea = false;
			repaint();
		}
	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		isOverArea = false;
		repaint();
	}

}
