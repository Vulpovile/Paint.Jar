package com.androdome.util.paintdotjar.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ComponentList extends Container implements MouseListener, ComponentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int selectedIndex = -1;
	private Color selectedColor = new Color(121,156,216);
	private ArrayList<Component> list = new ArrayList<Component>();
	private ArrayList<ListSelectionListener> listeners = new ArrayList<ListSelectionListener>();
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
	
	public boolean addListSelectionListener(ListSelectionListener lst)
	{
		return listeners.add(lst);
	}
	
	public boolean removeListSelectionListener(ListSelectionListener lst)
	{
		return listeners.remove(lst);
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
		this.list.clear();
		this.removeAll();
		if(list.size() > 0)
			this.selectedIndex = 0;
		else this.selectedIndex = -1;
		for(Component c : list)
		{
			add(c);
		}
		this.list.get(selectedIndex).setBackground(selectedColor);
		for(ListSelectionListener lst : listeners)
		{
			lst.valueChanged(new ListSelectionEvent(this, this.selectedIndex, this.selectedIndex, false));
		}
	}
	public Component add(Component comp)
	{
		list.add(comp);
		comp.setPreferredSize(new Dimension(200, 64));
		comp.setMinimumSize(new Dimension(0, 64));
		comp.addMouseListener(this);
		super.add(comp, getConstraints());
		this.validate();
		this.repaint();
		if(this.getParent() != null)
		{
			this.getParent().validate();
			this.getParent().repaint();
		}
		return comp;
	}
	
	private GridBagConstraints getConstraints() {
		GridBagConstraints consts = new GridBagConstraints();
		consts.fill = GridBagConstraints.HORIZONTAL;
		consts.weighty = 0.0F;
		consts.weightx = 1.0F;
		consts.anchor = GridBagConstraints.SOUTH;
		consts.gridwidth = GridBagConstraints.REMAINDER;
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
		{
			selectedIndex--;
			this.list.get(selectedIndex).setBackground(selectedColor);
			for(ListSelectionListener lst : listeners)
			{
				lst.valueChanged(new ListSelectionEvent(this, this.selectedIndex, this.selectedIndex, false));
			}
		}
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
				for(FocusListener fl : this.list.get(selectedIndex).getFocusListeners())
				{
					fl.focusLost(new FocusEvent(this, FocusEvent.FOCUS_LOST));
				}
			}
			this.selectedIndex = index;
			this.list.get(selectedIndex).setBackground(selectedColor);
			for(ListSelectionListener lst : listeners)
			{
				lst.valueChanged(new ListSelectionEvent(this, this.selectedIndex, this.selectedIndex, false));
			}
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

	public int getCount() {
		return list.size();
	}
}
