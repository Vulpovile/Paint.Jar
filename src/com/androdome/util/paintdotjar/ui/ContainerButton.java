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
import javax.swing.JButton;
import javax.swing.JToggleButton;

import com.androdome.util.paintdotjar.Canvas;


public class ContainerButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CanvasContainer canvas;
	public CanvasContainer getCanvasContainer()
	{
		return canvas;
	}
	public ContainerButton(CanvasContainer cc)
	{
		canvas = cc;

	}
	public ContainerButton(CanvasContainer cc, Dimension psize)
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
	}

}
