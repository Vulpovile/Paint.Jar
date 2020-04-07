package com.androdome.util.paintdotjar.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import com.androdome.util.paintdotjar.Canvas;

public class CanvasManager {

	private CanvasContainer cc;
	CanvasManager(CanvasContainer cc) {
		this.cc = cc;
	}

	
	public Canvas getSelectedCanvas() {
		return cc.getLayers().get(cc.selectedIndex);
	}
	
	public int getSelectedIndex() {
		return cc.selectedIndex;
	}
	
	public void setSelectedCanvas(Canvas canvas) {
		int idx = cc.getLayers().indexOf(canvas);
		if(idx > -1)
			cc.selectedIndex = idx;
	}
	
	public void setSelectedCanvas(int index) {
		if(cc.getLayers().size() > index)
			cc.selectedIndex = index;
	}
	
	public Canvas getTemporaryCanvas(int index)
	{
		if(cc.getLayers().size() > index)
		{
			cc.temporaryCanvas = new Canvas(cc.width, cc.height);
			cc.tempIndex = index;
			return cc.temporaryCanvas;
		}
		return null;
	}
	
	public Canvas getTemporaryCanvasClone(int index)
	{
		if(cc.getLayers().size() > index)
		{
			cc.temporaryCanvas = new Canvas(cc.getLayers().get(index));
			cc.tempIndex = index;
			return cc.temporaryCanvas;
		}
		return null;
	}
	
	public void applyTemporaryCanvas() {
		if(cc.tempIndex > -1 && cc.tempIndex < cc.getLayers().size())
		{
			Canvas canvas = cc.getLayers().get(cc.tempIndex);
			if(canvas != null && cc.temporaryCanvas != null)
			{
				canvas.apply(cc.temporaryCanvas);
			}
		}
		cc.setChanged(true);
		cc.tempIndex = -1;
		cc.temporaryCanvas = null;
	}
	
	public void voidTemporaryCanvas() {
		cc.tempIndex = -1;
		cc.temporaryCanvas = null;
	}
	
	public Point getLocationOnGraphics(Point point)
	{
		//if(canvasRenderMode == CanvasRenderMode.NORMAL)
		//{
			float x = (cc.getWidth()/cc.getScale()/2 - (cc.width)/2 + cc.xoffset);
			float y = (cc.getHeight()/cc.getScale()/2 - (cc.height)/2 + cc.yoffset);
			return new Point((int)Math.floor(point.x/cc.getScale() - x), (int)Math.floor(point.y/cc.getScale() - y));
		//}
		/*else
		{
			int x = (int) (this.getWidth()/2 - (width*scale)/2 + xoffset);
			int y = (int) (this.getHeight()/2 - (height*scale)/2 + xoffset);
			Point p = new Point(point.x - x, point.y - y);
			if(p.x > width)
				p.x = (int) (p.x % width);
			if(p.y > height)
				p.y = (int) (p.x % height);
			return p;
		}*/
		
	}


	public void repaint() {
		cc.repaint();
	}

	public Color getPrimary()
	{
		return cc.colors.getPrimary();
	}
	public Color getSecondary()
	{
		return cc.colors.getSecondary();
	}
	/**
	 * Retuns a volatile image if successful, or null if not.
	 * @return {@link VolatileImage} or {@link null}
	 */
	public VolatileImage createRenderableVolatileImage()
	{
		VolatileImage vi = null;
		try {
			vi = cc.createVolatileImage(cc.getWidth(), cc.getHeight(), new ImageCapabilities(true));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		cc.renderableImage = vi;
		return vi;
	}
	
	/**
	 * Retuns an {@link Image} object
	 * @return {@link Image}
	 */
	public Image createRenderableImage()
	{
		BufferedImage bi = new BufferedImage(cc.getWidth(), cc.getHeight(), BufferedImage.TYPE_INT_ARGB);
		cc.renderableImage = bi;
		return bi;
	}
	
	/**
	 * Destroys the current {@link Image} or {@link VolatileImage} object
	 */
	public void destroyRenderableImage()
	{
		cc.renderableImage = null;
	}

	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(cc.width, cc.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		for(int i = 0; i < cc.getLayers().size(); i++)
		{
			g.drawImage(cc.getLayers().get(i).getImage(), 0, 0, null);
		}
		return image;
	}


	public void setSecondary(Color c) {
		cc.colors.setSecondary(c);
		cc.colors.recolorDialog();
	}
	public void setPrimary(Color c) {
		cc.colors.setPrimary(c);
		cc.colors.recolorDialog();
	}


	
}
