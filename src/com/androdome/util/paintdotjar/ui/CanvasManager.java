package com.androdome.util.paintdotjar.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.managers.HistoryEntry;
import com.androdome.util.paintdotjar.managers.HistoryManager;
import com.androdome.util.paintdotjar.plugin.JavaPlugin;

public class CanvasManager {
	//TODO move to managers
	private CanvasContainer cc;
	private HistoryManager historyManager;
	CanvasManager(CanvasContainer cc) {
		this.cc = cc;
		this.historyManager = new HistoryManager(cc);
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
	@Deprecated
	public void applyTemporaryCanvas(){
		applyTemporaryCanvas("Applied Canvas");
	}
	
	public void applyTemporaryCanvas(String message) {
		applyTemporaryCanvas(message, null);
	}
	
	public void applyTemporaryCanvas(String message, Image icon) {
		
		if(cc.tempIndex > -1 && cc.tempIndex < cc.getLayers().size())
		{
			Canvas canvas = cc.getLayers().get(cc.tempIndex);
			if(canvas != null && cc.temporaryCanvas != null)
			{
				try
				{
					historyManager.pushChange(new HistoryEntry(HistoryManager.Operations.APPLY_CANVAS, cc.tempIndex, canvas, message, icon));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "An error occured when trying to save history", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				canvas.apply(cc.temporaryCanvas);
			}
		}
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
	public float getScale()
	{
		return cc.getScale();
	}

	private HashMap<JavaPlugin, HashMap<String, Object>> exdatamap = new HashMap<JavaPlugin, HashMap<String, Object>>();
	/**
	 * Creates a or edits a store value for the plugin in the canvas file for details such as image format and quality
	 * @param plugin
	 * @param key
	 * @param value
	 * @return success
	 */
	public boolean setExtra(JavaPlugin plugin, String key, Object value)
	{
		if(plugin == null)
			return false;
		if(exdatamap.get(plugin) == null)
			exdatamap.put(plugin, new HashMap<String, Object>());
		/*if(exdatamap.get(plugin).get(key) == null && exdatamap.get(plugin).size() >= 40)
		{
			return false;
		}*/
		exdatamap.get(plugin).put(key, value);
		return true;
	}
	/**
	 * Gets a set value if it exists in the data store
	 * @param plugin
	 * @param key
	 * @return value
	 */
	public Object getExtra(JavaPlugin plugin, String key)
	{
		if(plugin == null || exdatamap.get(plugin) == null)
			return null;
		return exdatamap.get(plugin).get(key);
	}
	/**
	 * Deletes a set value in the data store if it exists
	 * @param plugin
	 * @param key
	 * @return
	 */
	public Object clearExtra(JavaPlugin plugin, String key)
	{
		if(plugin == null || exdatamap.get(plugin) == null)
			return null;
		return exdatamap.get(plugin).remove(key);
	}
	/**
	 * Deletes all data in the datastore for a plugin
	 * @param plugin
	 * @param key
	 * @return
	 */
	public void clearAllExtra(JavaPlugin plugin)
	{
		if(plugin != null)
			exdatamap.remove(plugin);
	}
	/**
	 * Deletes all data in the datastore for a plugin
	 * @param plugin
	 * @param key
	 * @return
	 */
	public void purgeExtra()
	{
		exdatamap.clear();
	}


	public HistoryManager getHistoryManager() {
		// TODO Auto-generated method stub
		return historyManager;
	}
	
}
