package com.androdome.util.paintdotjar.plugin.tool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.androdome.util.paintdotjar.plugin.JavaPlugin;
import com.androdome.util.paintdotjar.ui.CanvasManager;

public abstract class ToolAdapter extends Tool{
	
	public ToolAdapter(JavaPlugin plugin) {
		super(plugin);
	}
	public void onSelect(){}
	public void onDeSelect(){}
	public void onMouseMove(MouseEvent e, CanvasManager comp){}
	public void onMouseDrag(MouseEvent e, CanvasManager comp){}
	public void onMouseClick(MouseEvent e, CanvasManager comp){}
	public void onMousePress(MouseEvent e, CanvasManager comp){}
	public void onMouseRelease(MouseEvent e, CanvasManager comp){}
	public void onMouseEnter(MouseEvent e, CanvasManager comp){}
	public void onMouseExit(MouseEvent e, CanvasManager comp){}
	public void onKeyType(KeyEvent e, CanvasManager comp){}
	public void onKeyPress(KeyEvent e, CanvasManager comp){}
	public void onKeyRelease(KeyEvent e, CanvasManager comp){}
	public void onMouseWheelMove(MouseWheelEvent e, CanvasManager comp){}
	
}
