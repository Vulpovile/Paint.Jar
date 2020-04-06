package com.androdome.util.paintdotjar.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ComponentList extends Container{
	private int selectedIndex = -1;
	private ArrayList<Component> list = new ArrayList<Component>();
	public ComponentList()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	public void setListData(ArrayList<Component> list)
	{
		this.list = list;
		this.removeAll();
		for(Component c : list)
		{
			JPanel cPanel = new JPanel();
			cPanel.setPreferredSize(new Dimension(-1, 64));
			cPanel.add(c);
			super.add(cPanel);
		}
	}
	public Component add(Component comp)
	{
		list.add(comp);
		JPanel cPanel = new JPanel();
		cPanel.setPreferredSize(new Dimension(-1, 64));
		cPanel.add(comp);
		super.add(cPanel);
		return comp;
	}
	public void remove(Component comp)
	{
		list.remove(comp);
		for(Component c : getComponents())
		{
			if(c instanceof JPanel)
			{
				Arrays.asList(((JPanel)c).getComponents()).contains(comp);
				remove(c);
			}
		}
	}
}
