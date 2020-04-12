package com.androdome.util.paintdotjar.ui;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class LayerPanel extends DetatchablePanel {

	/**
	 * Create the panel.
	 */
	public LayerPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(panel, BorderLayout.CENTER);

	}
	
	

}
