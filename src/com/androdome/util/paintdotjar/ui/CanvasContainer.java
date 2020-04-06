package com.androdome.util.paintdotjar.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.plugin.PluginManager;

public class CanvasContainer extends JComponent implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener{

	private boolean changed = false;
	ArrayList<Canvas> layers = new ArrayList<Canvas>();
	Canvas temporaryCanvas = null;
	File relatedFile = null;
	String formatname = null;
	int tempIndex = -1;
	int width = 800;
	int height = 600;
	int xoffset = 0;
	int yoffset = 0;
	int selectedIndex = 0;
	ColorBar colors;
	Image renderableImage = null;
	private int lastScaleMode = Image.SCALE_FAST;
	private float scale = 1F;
	private PluginManager pluginManager;
	public CanvasManager manager = new CanvasManager(this);
	public enum CanvasRenderMode {
		NORMAL,
		TILEDRAW
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	public float getScale()
	{
		return this.scale;
	}
	
	public CanvasRenderMode canvasRenderMode = CanvasRenderMode.TILEDRAW;
	public ContainerButton containerButton = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private void addListeners()
	{
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}
	
	public CanvasContainer(PluginManager mi, ColorBar color) {
		addListeners();
		colors = color;
		pluginManager = mi;
		layers.add(new Canvas(width, height));
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	
	public CanvasContainer(int width, int height, PluginManager mi, ColorBar color) {
		addListeners();
		pluginManager = mi;
		colors = color;
		this.width = width;
		this.height = height;
		layers.add(new Canvas(width, height));
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	
	public CanvasContainer(BufferedImage img, PluginManager mi, ColorBar color) {
		addListeners();
		pluginManager = mi;
		colors = color;
		this.width = img.getWidth();
		this.height = img.getHeight();
		layers.add(new Canvas(img));
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	
	public void rescaleCanvases()
	{
		if(!temporaryCanvas.sizeEqual(width, height))
		{
			System.out.println("Temp canvas size not equal!");
			temporaryCanvas.resizeImage(width, height, lastScaleMode);
		}
		for(int i = 0; i < layers.size(); i++)
		{
			Canvas canvas = layers.get(i);
			if(!canvas.sizeEqual(width, height))
			{
				System.out.println("Size not equal!");
				canvas.resizeImage(width, height, lastScaleMode);
			}
		}
	}
	public void repaint()
	{
		super.repaint();
		if(containerButton != null)
		containerButton.repaint();
	}
	public void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(this.getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		//g2d.fillRect(x, y, width, height);
		int x = (int) (this.getWidth()/2 - (width*scale)/2 + xoffset);
		int y = (int) (this.getHeight()/2 - (height*scale)/2 + yoffset);
		
		
		if(canvasRenderMode == CanvasRenderMode.TILEDRAW)
		{
			int pscale = 10;
			for(int i = 0; i <= this.getWidth(); i+=pscale)
			{
				for(int j = 0; j <= this.getWidth(); j+=pscale)
				{
					if((i+j)%(pscale*2) == 0)
						g.setColor(new Color(240,240,240));
					else g.setColor(Color.WHITE);
					g.fillRect(i, j, pscale, pscale);
				}
			}
			g.setColor(Color.BLACK);
			for(int i = 0; i < layers.size(); i++)
			{
				Canvas canvas = layers.get(i);
				//g2d.drawImage(canvas.canvasImage, x, y,width,height, this);
				for(int xa = getLowestAfter0(x, (int) (width*scale)); xa <= this.getWidth(); xa+=width*scale)
				{
					for(int ya = getLowestAfter0(y, (int) (height*scale)); ya <= this.getHeight(); ya+=height*scale)
					{
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.canvasOpacity / 255F));
						g2d.drawImage(canvas.canvasImage, xa, ya,(int)(width*scale),(int) (height*scale), this);
						if(tempIndex == i && temporaryCanvas != null)
						{
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temporaryCanvas.canvasOpacity / 255F));
							g2d.drawImage(temporaryCanvas.canvasImage, xa, ya,(int)(width*scale),(int) (height*scale), this);
						}
					}
				}
			}
		}
		else 
		{
			/*int pscale = 10;
			for(int i = x; i <= (int)(width*scale); i+=pscale)
			{
				for(int j = y; j <= (int) (height*scale); j+=pscale)
				{
					if((i+j)%(pscale*2) == 0)
						g.setColor(new Color(240,240,240));
					else g.setColor(Color.WHITE);
					g.fillRect(i, j, pscale, pscale);
				}
			}*/
			/*//Broken!
			int pscale = (int) (width*scale/100);
			boolean offset = false;
			boolean inset = false;
			for(int i = x; i < (int)Math.ceil(width*scale)+x; i+=pscale)
			{
				offset = !offset;
				inset = !inset;
				for(int j = y; j < (int)Math.ceil(height*scale)+y; j+=pscale)
				{
					inset = !inset;
					if(inset ^ offset)
						g.setColor(new Color(240,240,240));
					else g.setColor(Color.WHITE);
					int xpscale = pscale;
					int ypscale = pscale;
					g.fillRect(i, j, xpscale, ypscale); //wtf is going on
				}
			}
			*/
			g.setColor(Color.BLACK);
			for(int i = 0; i < layers.size(); i++)
			{
				Canvas canvas = layers.get(i);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.canvasOpacity / 255F));
				g2d.drawImage(canvas.canvasImage, x, y,(int)(width*scale),(int) (height*scale), this);
				if(tempIndex == i && temporaryCanvas != null)
				{
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temporaryCanvas.canvasOpacity / 255F));
					g2d.drawImage(temporaryCanvas.canvasImage, x, y,(int)(width*scale),(int) (height*scale), this);
				}
			}
		}
		if(renderableImage != null)
			g2d.drawImage(renderableImage, x, y, (int)(width*scale), (int)(height*scale), this);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2d.drawRect(x-1, y-1, (int)(width*scale)+1, (int)(height*scale)+1);
		
	}
	
	private int getLowestAfter0(int a, int gap)
	{
		while(a > 0)
			a-=gap;
		return a;
	}
	
	
	

	public void mouseDragged(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseDrag(e, manager);
	}

	public void mouseMoved(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseMove(e, manager);
	}

	public void mouseClicked(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseClick(e, manager);
	}

	public void mousePressed(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMousePress(e, manager);
	}

	public void mouseReleased(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseRelease(e, manager);
	}

	public void mouseEntered(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseEnter(e, manager);
	}

	public void mouseExited(MouseEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseExit(e, manager);
	}

	public void keyTyped(KeyEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onKeyType(e, manager);
	}

	public void keyPressed(KeyEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onKeyPress(e, manager);
	}

	public void keyReleased(KeyEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onKeyRelease(e, manager);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(pluginManager.getTool() != null)
			pluginManager.getTool().onMouseWheelMove(e, manager);
	}
	public void setRelatedFile(File selectedFile) {
		this.relatedFile = selectedFile;
	}
	public File getRelatedFile() {
		return this.relatedFile;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
		pluginManager.getMainInterface().setName();
	}
	public ArrayList<Canvas> getLayers() {
		// TODO Auto-generated method stub
		return layers;
	}
	public void setFormatName(String formatName)
	{
		formatname = formatName;
	}
	public String getFormatName()
	{
		return formatname;
	}



}
