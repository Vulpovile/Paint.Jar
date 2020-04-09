package com.androdome.util.paintdotjar.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class SplitButton extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public SplitButton() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new WeirdLayout());
		
		JButton button = new JButton("New button");
		panel.add(button);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new WeirdLayout());
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setPreferredSize(new Dimension(10, 0));
		panel_1.add(btnNewButton);

	}

}

class WeirdLayout implements LayoutManager
{


	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		
	}

	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return new Dimension(0, 0);
	}

	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return new Dimension(20, 20);
	}

	public void layoutContainer(Container parent) {
		Component[] comps = parent.getComponents();
		if(comps.length > 0)
		{
			comps[0].setLocation(0,0);
			comps[0].setSize(new Dimension(parent.getSize().width, parent.getSize().height));
		}
	}
	
}
