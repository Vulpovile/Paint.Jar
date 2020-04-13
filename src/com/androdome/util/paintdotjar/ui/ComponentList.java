package com.androdome.util.paintdotjar.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;

public class ComponentList extends Container implements MouseListener, ComponentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int selectedIndex = -1;
	private Color selectedColor = new Color(121,156,216);
	private ArrayList<Component> list = new ArrayList<Component>();
	public ComponentList()
	{
		this.addComponentListener(this);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {1};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{};
		gridBagLayout.rowWeights = new double[]{};
		super.setLayout(gridBagLayout);
	}
	public final void setLayout(LayoutManager mgr)
	{
		throw new RuntimeException("Cannot set layout of a list");
	}
	public void setListData(ArrayList<Component> list)
	{
		for(Component c : this.list)
		{
			c.removeMouseListener(this);
		}
		this.list = list;
		
		this.removeAll();
		if(list.size() > 0)
			this.selectedIndex = 0;
		else this.selectedIndex = -1;
		for(Component c : list)
		{
			c.setPreferredSize(new Dimension(200, 64));
			c.addMouseListener(this);
			super.add(c, getConstraints());
		}
	}
	public Component add(Component comp)
	{
		list.add(comp);
		comp.setPreferredSize(new Dimension(200, 64));
		
		super.add(comp, getConstraints());
		return comp;
	}
	
	private GridBagConstraints getConstraints() {
		GridBagConstraints consts = new GridBagConstraints();
		consts.fill = GridBagConstraints.HORIZONTAL;
		consts.weighty = 1.0F;
		consts.weightx = 1.0F;
		consts.anchor = GridBagConstraints.SOUTH;
		return consts;
	}
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
	
	public void remove(Component comp)
	{
		list.remove(comp);
		comp.removeMouseListener(this);
		remove(comp);
		if(selectedIndex > list.size()-1)
			selectedIndex--;
		
	}
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		int index = list.indexOf(e.getSource());
		if(index > -1)
		{
			if(this.selectedIndex > -1)
			{
				this.list.get(selectedIndex).setBackground(this.getBackground());
			}
			this.selectedIndex = index;
			this.list.get(selectedIndex).setBackground(selectedColor);
		}
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentResized(ComponentEvent e) {
	}
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
