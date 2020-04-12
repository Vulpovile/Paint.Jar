package com.androdome.util.paintdotjar.ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class LayerInformationPanel extends JPanel {
	public LayerInformationPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
			}
		};
		panel.setPreferredSize(new Dimension(64, 64));
		add(panel, BorderLayout.WEST);
		
		JLabel lblLayername = new JLabel("LayerName");
		add(lblLayername, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton btnAction = new JButton("Action 1");
		panel_1.add(btnAction);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
