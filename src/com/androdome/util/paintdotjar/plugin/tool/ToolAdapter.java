package com.androdome.util.paintdotjar.plugin.tool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.androdome.util.paintdotjar.devel.annotation.Preliminary;
import com.androdome.util.paintdotjar.plugin.JavaPlugin;
import com.androdome.util.paintdotjar.ui.CanvasManager;

public abstract class ToolAdapter extends Tool{
	
	public ToolAdapter(JavaPlugin plugin) {
		super(plugin);
	}
	@Override
	public void onSelect(CanvasManager manager){}
	@Override
	public void onDeselect(CanvasManager manager){}
	@Override
	public void onMouseMove(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMouseDrag(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMouseClick(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMousePress(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMouseRelease(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMouseEnter(MouseEvent e, CanvasManager comp){}
	@Override
	public void onMouseExit(MouseEvent e, CanvasManager comp){}
	@Override
	public void onKeyType(KeyEvent e, CanvasManager comp){}
	@Override
	public void onKeyPress(KeyEvent e, CanvasManager comp){}
	@Override
	public void onKeyRelease(KeyEvent e, CanvasManager comp){}
	@Override
	public void onMouseWheelMove(MouseWheelEvent e, CanvasManager comp){}
	@Override
	@Preliminary
	public void onCanvasPaint(Graphics2D g, Rectangle innerFrame, CanvasManager manager){}
	
}
