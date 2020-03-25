package ca.limeware.masteroffice.paintmaster.plugin.tool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JToolBar;

import ca.limeware.masteroffice.paintmaster.plugin.JavaPlugin;
import ca.limeware.masteroffice.paintmaster.properties.PropertyFilled;
import ca.limeware.masteroffice.paintmaster.ui.CanvasManager;

public abstract class Tool extends PropertyFilled{
	private final JavaPlugin plugin;
	public Tool(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	public final JavaPlugin getPlugin()
	{
		return this.plugin;
	}
	/**
	 * Returns the {@link JToolBar} that can be used by the tool 
	 * This toolbar is cleared every time another tool is selected.
	 */
	public final JToolBar getToolbar() {
		return plugin.getMainInterface().getToolToolbar();
	}
	public abstract void onSelect();
	public abstract void onDeSelect();

	public abstract void onMouseMove(MouseEvent e, CanvasManager manager);
	public abstract void onMouseDrag(MouseEvent e, CanvasManager manager);
	public abstract void onMouseClick(MouseEvent e, CanvasManager manager);
	public abstract void onMousePress(MouseEvent e, CanvasManager manager);
	public abstract void onMouseRelease(MouseEvent e, CanvasManager manager);
	public abstract void onMouseEnter(MouseEvent e, CanvasManager manager);
	public abstract void onMouseExit(MouseEvent e, CanvasManager manager);
	public abstract void onKeyType(KeyEvent e, CanvasManager manager);
	public abstract void onKeyPress(KeyEvent e, CanvasManager manager);
	public abstract void onKeyRelease(KeyEvent e, CanvasManager manager);
	public abstract void onMouseWheelMove(MouseWheelEvent e, CanvasManager manager);
	
}
