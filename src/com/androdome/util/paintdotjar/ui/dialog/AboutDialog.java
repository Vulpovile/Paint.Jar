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
import javax.swing.JTextPane;
import javax.swing.JList;

import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.managers.ImageManager;
import com.androdome.util.paintdotjar.plugin.PluginManager;

import javax.swing.SwingConstants;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog(PluginManager manager) {
		setModal(true);
		setResizable(false);
		setTitle("About");
		setBounds(100, 100, 456, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPaintjarBuild = new JLabel("Paint.jar Build "+MainInterface.BUILD+" (Plugin API "+PluginManager.API_VERSION+"), Copyright 2020");
			lblPaintjarBuild.setBounds(92, 11, 348, 14);
			contentPanel.add(lblPaintjarBuild);
		}
		{
			JLabel lblCredits = new JLabel("Credits");
			lblCredits.setBounds(10, 97, 46, 14);
			contentPanel.add(lblCredits);
		}
		{
			JLabel lblInstalledPlugins = new JLabel("Installed Plugins");
			lblInstalledPlugins.setBounds(230, 97, 163, 14);
			contentPanel.add(lblInstalledPlugins);
		}
		{
			JLabel lblpaintjarIsA = new JLabel("<html>Paint.jar is a free, open-source paint program inteded to be a cross-platform, easy to use, yet powerful paint application. If you have anything to contribute, feel free to visit the <a href=\"https://github.com/Vulpovile/Paint.Jar\">Github Page</a></html>");
			lblpaintjarIsA.setBounds(92, 29, 348, 57);
			contentPanel.add(lblpaintjarIsA);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 122, 210, 262);
		contentPanel.add(scrollPane);
		
		JTextPane txtpnProgrammingVulopvileStack = new JTextPane();
		txtpnProgrammingVulopvileStack.setEditable(false);
		txtpnProgrammingVulopvileStack.setText("Programming:\r\nVulopvile\r\n\r\nTesting:\r\nidonob\r\nNukley\r\n\r\nStack Overflow solutions used:\r\n7834768\r\n320542\r\n10773713");
		scrollPane.setViewportView(txtpnProgrammingVulopvileStack);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(230, 122, 210, 262);
		contentPanel.add(scrollPane_1);
		
		JList list = new JList();
		list.setListData(manager.getPlugins().toArray());
		scrollPane_1.setViewportView(list);
		
		JLabel lblPluginDirectory = new JLabel("Plugin Directory:");
		lblPluginDirectory.setBounds(10, 395, 391, 14);
		contentPanel.add(lblPluginDirectory);
		
		JLabel label = new JLabel("<html>"+manager.getPluginDirectory()+"</html>");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(10, 409, 391, 30);
		contentPanel.add(label);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(ImageManager.getImageIconResource("ico/PaintJar64.png"));
		lblIcon.setBounds(10, 11, 72, 72);
		contentPanel.add(lblIcon);
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
