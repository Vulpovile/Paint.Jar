package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.ui.ContainerButton;
import com.androdome.util.paintdotjar.ui.ContainerToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UnsavedCanvasDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	boolean canDispose = false;
	private ArrayList<CanvasContainer> canvasContainer;
	JPanel panel = new JPanel();
	/**
	 * Create the dialog.
	 * @param mainInterface 
	 * @param canvasContainer
	 */
	public UnsavedCanvasDialog(final MainInterfaceAbstractor mainInterface, ArrayList<CanvasContainer> ccList) {
		this.canvasContainer = ccList;
		setTitle("Save and Exit");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTheseCanvasesAre = new JLabel("These canvases have unsaved changes!");
			lblTheseCanvasesAre.setBounds(10, 11, 414, 14);
			contentPanel.add(lblTheseCanvasesAre);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 36, 424, 128);
		contentPanel.add(scrollPane);
		

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
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for(CanvasContainer c : canvasContainer)
						{
							boolean result;
							if(c.getRelatedFile() != null)
							{
								result = mainInterface.saveNoDialog(c);	
							}
							else
								result = mainInterface.showSaveDialog(c);
							if(result)
							{
								canDispose = true;
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			JButton btnDiscardAll = new JButton("Discard All");
			btnDiscardAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canDispose = true;
					dispose();
				}
			});
			btnDiscardAll.setActionCommand("OK");
			buttonPane.add(btnDiscardAll);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		for(final CanvasContainer c : canvasContainer)
		{
			final ContainerButton button = new ContainerButton(c, new Dimension(96, 96));
			if(c.getRelatedFile() != null)
				button.setToolTipText(c.getRelatedFile().getName());
			else button.setToolTipText("Untitled");
			button.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					boolean result = mainInterface.showSaveDialog(c);
					if(result)
					{
						canvasContainer.remove(c);
						panel.remove(button);
						panel.revalidate();
						panel.repaint();
						if(canvasContainer.size() == 0)
						{
							canDispose = true;
							dispose();
						}
					}
				}});
			panel.add(button);
		}
	}
	public boolean canDispose() {
		// TODO Auto-generated method stub
		return canDispose;
	}
}
