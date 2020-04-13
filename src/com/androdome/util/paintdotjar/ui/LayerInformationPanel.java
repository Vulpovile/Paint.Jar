package com.androdome.util.paintdotjar.ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

import com.androdome.util.paintdotjar.Canvas;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;

public class LayerInformationPanel extends JPanel {
	public LayerInformationPanel(final CanvasContainer cc, int layerIndex, final Canvas canvas) {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblLayername = new JLabel(canvas.getName());
		add(lblLayername, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		add(panel, BorderLayout.WEST);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel panel_2 = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(canvas.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setPreferredSize(new Dimension(60, 60));
		panel_2.setOpaque(false);
		panel.add(panel_2);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
