package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;

import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.managers.ClipboardImage;
import com.androdome.util.paintdotjar.plugin.PluginManager;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.ui.ColorBar;

public class CreateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtX;
	private JTextField txtY;
	Image image = null;
	JComboBox comboBox = new JComboBox();
	public CreateDialog(final MainInterface mainInterface, final ColorBar colorPanel, final PluginManager manager) {
		setTitle("New File");
		
		image = ClipboardImage.read();
		
		setResizable(false);
		setLocationRelativeTo(mainInterface);
		setSize(389,272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(6, 13, 60, 15);
		contentPane.add(lblWidth);
		
		txtX = new JTextField();
		txtX.setText("800");
		txtX.setBounds(78, 6, 122, 27);
		contentPane.add(txtX);
		txtX.setColumns(10);
		

		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Transparent", "White", "Black", "From Primary", "From Secondary"}));
		comboBox.setBounds(212, 34, 165, 26);
		contentPane.add(comboBox);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(6, 40, 60, 15);
		contentPane.add(lblHeight);
		
		txtY = new JTextField();
		txtY.setText("600");
		txtY.setBounds(78, 33, 122, 27);
		contentPane.add(txtY);
		txtY.setColumns(10);
		final JCheckBox chckbxFromClipboard = new JCheckBox("Create from clipboard");
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CanvasContainer cc;
				try{
					int x = Integer.parseInt(txtX.getText().trim());
					int y = Integer.parseInt(txtY.getText().trim());
					
					if(x > 1 || y > 1)
					{
						cc = new CanvasContainer(x, y, manager, colorPanel);
						if(chckbxFromClipboard.isSelected())
						{
							cc.manager.getSelectedCanvas().getGraphics().drawImage(image, 0, 0, null);
						}
						else if(!comboBox.getSelectedItem().toString().toLowerCase().trim().equals("transparent"))
						{
							Graphics g = cc.manager.getSelectedCanvas().getGraphics();
							String comboValue = comboBox.getSelectedItem().toString().toLowerCase().trim();
							if(comboValue.equals("black"))
								g.setColor(Color.BLACK);
							else if(comboValue.equals("white"))
								g.setColor(Color.WHITE);
							else if(comboValue.equals("from primary"))
								g.setColor(colorPanel.getPrimary());
							else if(comboValue.equals("from secondary"))
								g.setColor(colorPanel.getSecondary());
							else g.setColor(new Color(0,0,0,0));
							g.fillRect(0, 0, cc.manager.getImage().getWidth(), cc.manager.getImage().getHeight());
						}
						mainInterface.addOpenCanvas(cc, true);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Your size has to be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "You can only use integer numbers!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(OutOfMemoryError ex)
				{
					cc = null;
					System.gc();
					JOptionPane.showMessageDialog(null, "You do not have enough memory availible for this operation!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnOk.setBounds(277, 211, 100, 27);
		contentPane.add(btnOk);
		
		JButton btnFuckNo = new JButton("Cancel");
		btnFuckNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFuckNo.setBounds(165, 211, 100, 27);
		contentPane.add(btnFuckNo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "From Clipboard", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 67, 371, 145);
		if(image != null)
			contentPane.add(panel);
		panel.setLayout(null);
		
		chckbxFromClipboard.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(chckbxFromClipboard.isSelected())
				{
					txtX.setEnabled(false);
					txtY.setEnabled(false);
					txtX.setText(image.getWidth(null) + "");
					txtY.setText(image.getHeight(null) + "");
					comboBox.setEnabled(false);
				}
				else
				{
					txtX.setEnabled(true);
					txtY.setEnabled(true);
					comboBox.setEnabled(true);
				}
			}
		});
		
		chckbxFromClipboard.setBounds(16, 19, 163, 18);
		panel.add(chckbxFromClipboard);
		
		JPanel previewPanel = new JPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(image != null)
				{
					g.drawImage(image, 0,0,getWidth(), getHeight(), null);
				}
			}
		};
		previewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		previewPanel.setBounds(250, 19, 104, 104);
		panel.add(previewPanel);
		
		JCheckBox chckbxMergeIntoOne = new JCheckBox("Merge into one layer");
		chckbxMergeIntoOne.setEnabled(false);
		chckbxMergeIntoOne.setBounds(16, 49, 163, 18);
		panel.add(chckbxMergeIntoOne);
		
		JLabel lblBackground = new JLabel("Background");
		lblBackground.setBounds(212, 13, 100, 15);
		contentPane.add(lblBackground);
		
		
	}
}
