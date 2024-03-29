package com.androdome.util.paintdotjar.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
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
import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.plugin.PluginManager;

public class CanvasContainer extends JComponent implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener{

	public static final int INIT_WIDTH = 800;
	public static final int INIT_HEIGHT = 600;
	private boolean changed = false;
	private ArrayList<Canvas> layers = new ArrayList<Canvas>();
	Canvas temporaryCanvas = null;
	private File relatedFile = null;
	private String formatname = null;
	int tempIndex = -1;
	int width = INIT_WIDTH;
	int height = INIT_HEIGHT;
	int xoffset = 0;
	int yoffset = 0;
	int selectedIndex = 0;
	ColorBar colors;
	Image renderableImage = null;
	private int lastScaleMode = Image.SCALE_FAST;
	private float scale = 1F;
	private PluginManager pluginManager;
	private Shape selection = null;
	public CanvasManager manager = new CanvasManager(this);

	
	public enum CanvasRenderMode {
		NORMAL,
		TILEDRAW
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public CanvasContainer(PluginManager mi, ColorBar color) {
		this(INIT_WIDTH, INIT_HEIGHT, mi, color);
	}
	
	private void initialConfig(PluginManager mi, ColorBar color)
	{
		this.setDoubleBuffered(false);
		addListeners();
		pluginManager = mi;
		colors = color;
	}
	
	public CanvasContainer(int width, int height, PluginManager mi, ColorBar color) {

		initialConfig(mi, color);
		this.width = width;
		this.height = height;
		layers.add(new Canvas(width, height));
	}
	
	public CanvasContainer(BufferedImage img, PluginManager mi, ColorBar color) {
		initialConfig(mi, color);
		this.width = img.getWidth();
		this.height = img.getHeight();
		layers.add(new Canvas(img));
	}
	
	public int getImageWidth()
	{
		return width;
	}
	public int getImageHeight()
	{
		return height;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
		pluginManager.getMainInterface().reScrollbar();
	}
	public float getScale()
	{
		return this.scale;
	}
	
	public CanvasRenderMode canvasRenderMode = CanvasRenderMode.TILEDRAW;
	public ContainerToggleButton containerButton = null;
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
		pluginManager.getMainInterface().repaintList();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
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
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.getOpacity() / 255F));
						g2d.drawImage(canvas.getImage(), xa, ya,(int)(width*scale),(int) (height*scale), this);
						if(tempIndex == i && temporaryCanvas != null)
						{
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temporaryCanvas.getOpacity() / 255F));
							g2d.drawImage(temporaryCanvas.getImage(), xa, ya,(int)(width*scale),(int) (height*scale), this);
						}
					}
				}
			}
		}
		else 
		{

			int pscale = 10;
			g.setClip(x-1, y-1, (int)(width*scale)+2, (int)(height*scale)+2);
			for(int i = 0; i <= this.width*scale; i+=pscale)
			{
				for(int j = 0; j <= this.height*scale; j+=pscale)
				{
					if((i+j)%(pscale*2) == 0)
						g.setColor(new Color(240,240,240));
					else g.setColor(Color.WHITE);
					g.fillRect(i+x-1, j+y-1, pscale, pscale);
				}
			}
			
			g.setColor(Color.BLACK);
			for(int i = 0; i < layers.size(); i++)
			{
				Canvas canvas = layers.get(i);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.getOpacity() / 255F));
				g2d.drawImage(canvas.getImage(), x, y,(int)(width*scale),(int) (height*scale), this);
				if(tempIndex == i && temporaryCanvas != null)
				{
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temporaryCanvas.getOpacity() / 255F));
					g2d.drawImage(temporaryCanvas.getImage(), x, y,(int)(width*scale),(int) (height*scale), this);
				}
			}
		}
		if(renderableImage != null)
			g2d.drawImage(renderableImage, x, y, (int)(width*scale), (int)(height*scale), this);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2d.drawRect(x-1, y-1, (int)(width*scale)+1, (int)(height*scale)+1);
		if(selection != null)
		{
			g2d.draw(selection);
		}
		if(this.pluginManager.getTool() != null)
		{
			this.pluginManager.getTool().onCanvasPaint(g2d, new Rectangle(x, y, (int)(width*scale), (int)(height*scale)), this.manager);
		}
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

	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

	void addLayer(Canvas canvas) {
		this.layers.add(canvas);
	}

	public Canvas removeLayer(int lIndex) {
		return this.layers.remove(lIndex);
	}

	public void setXOffset(int i) {
		this.xoffset = i;
	}
	public void setYOffset(int i) {
		this.yoffset = i;
	}

	



}
