package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JList;

import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.plugin.PluginManager;

public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog(PluginManager manager) {
		setModal(true);
		setResizable(false);
		setTitle("About");
		setBounds(100, 100, 417, 455);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPaintjarBuild = new JLabel("Paint.jar Build "+MainInterface.BUILD+" (Plugin API "+PluginManager.API_VERSION+"), Copyright 2020");
			lblPaintjarBuild.setBounds(10, 11, 391, 14);
			contentPanel.add(lblPaintjarBuild);
		}
		{
			JLabel lblCredits = new JLabel("Credits");
			lblCredits.setBounds(10, 86, 46, 14);
			contentPanel.add(lblCredits);
		}
		{
			JLabel lblInstalledPlugins = new JLabel("Installed Plugins");
			lblInstalledPlugins.setBounds(212, 86, 163, 14);
			contentPanel.add(lblInstalledPlugins);
		}
		{
			JLabel lblpaintjarIsA = new JLabel("<html>Paint.jar is a free, open-source paint program inteded to be a cross-platform, easy to use, yet powerful paint application. If you have anything to contribute, feel free to visit the <a href=\"https://github.com/Vulpovile/Paint.Jar\">Github Page</a></html>");
			lblpaintjarIsA.setBounds(10, 29, 391, 46);
			contentPanel.add(lblpaintjarIsA);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 111, 189, 262);
		contentPanel.add(scrollPane);
		
		JTextPane txtpnProgrammingVulopvileStack = new JTextPane();
		txtpnProgrammingVulopvileStack.setEditable(false);
		txtpnProgrammingVulopvileStack.setText("Programming:\r\nVulopvile\r\nStack Overflow\r\n\r\nTesting\r\nidonob");
		scrollPane.setViewportView(txtpnProgrammingVulopvileStack);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(212, 111, 189, 262);
		contentPanel.add(scrollPane_1);
		
		JList list = new JList();
		list.setListData(manager.getPlugins().toArray());
		scrollPane_1.setViewportView(list);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
