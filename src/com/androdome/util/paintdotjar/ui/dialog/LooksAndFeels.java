package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JCheckBox;

import com.androdome.util.paintdotjar.MainInterface;

public class LooksAndFeels extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
	public String[] names = new String[laf.length];
	/**
	 * Create the dialog.
	 */
	MainInterface mintf;
	public LooksAndFeels(MainInterface intf) {
		mintf = intf;
		for(int i = 0; i < names.length; i++)
		{
			names[i] = laf[i].getName();
		}
		setBounds(100, 100, 216, 409);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChooseATheme = new JLabel("Choose a theme:");
		lblChooseATheme.setBounds(10, 11, 180, 14);
		contentPanel.add(lblChooseATheme);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 180, 276);
		contentPanel.add(scrollPane);
		
		final JList list = new JList();
		list.setListData(names);
		list.setSelectedValue(UIManager.getLookAndFeel().getName(), true);
		scrollPane.setViewportView(list);
		
		final JCheckBox chckbxDecorateFrame = new JCheckBox("Decorate Frame");
		chckbxDecorateFrame.setSelected(JFrame.isDefaultLookAndFeelDecorated());
		chckbxDecorateFrame.setBounds(10, 311, 180, 23);
		contentPanel.add(chckbxDecorateFrame);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						if(list.getSelectedIndex() != -1)
						{
							LookAndFeelInfo slaf = laf[list.getSelectedIndex()];
							try {
								UIManager.setLookAndFeel(slaf.getClassName());
								SwingUtilities.updateComponentTreeUI(mintf);
								JFrame.setDefaultLookAndFeelDecorated(chckbxDecorateFrame.isSelected());
								JDialog.setDefaultLookAndFeelDecorated(chckbxDecorateFrame.isSelected());
								mintf.dispose();
								mintf.setVisible(true);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InstantiationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnsupportedLookAndFeelException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						dispose();
					}
					
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}
}
