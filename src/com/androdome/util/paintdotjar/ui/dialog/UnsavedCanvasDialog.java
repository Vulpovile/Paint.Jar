package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.ui.ContainerButton;
import com.androdome.util.paintdotjar.ui.ContainerToggleButton;

public class UnsavedCanvasDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public UnsavedCanvasDialog(CanvasContainer[] cc) {
		setTitle("Save and Exit");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTheseCanvasesAre = new JLabel("These canvases are not saved!");
			lblTheseCanvasesAre.setBounds(10, 11, 414, 14);
			contentPanel.add(lblTheseCanvasesAre);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 36, 424, 128);
		contentPanel.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JLabel lblYouCanSave = new JLabel("<html>You can save them all, discard them, cancel, or select which one to save by clicking on them</html>");
		lblYouCanSave.setBounds(10, 168, 424, 45);
		contentPanel.add(lblYouCanSave);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save All");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			JButton btnDiscardAll = new JButton("Discard All");
			btnDiscardAll.setActionCommand("OK");
			buttonPane.add(btnDiscardAll);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		for(CanvasContainer c : cc)
		{
			ContainerButton button = new ContainerButton(c, new Dimension(96, 96));
			panel.add(button);
		}
	}
}
