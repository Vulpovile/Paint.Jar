package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.prop.PropertyManager;
import com.androdome.util.paintdotjar.ui.CanvasContainer;

public class CrashDialog extends JDialog implements ActionListener, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JButton btnAttemptrecovery = new JButton("Attempt Recovery");
	JTextArea textArea = new JTextArea();
	MainInterface main;
	/**
	 * Create the dialog.
	 */
	public CrashDialog(Throwable err, MainInterface intf) {
		super((JDialog)null);
		addWindowListener(this);
		main = intf;
		setModal(true);
		setTitle("Crash!");
		setBounds(100, 100, 660, 505);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		StringWriter sw = new StringWriter();
		err.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		textArea.setEditable(false);
		textArea.setText(exceptionAsString);
		JLabel lblPaintjarCanAttempt = new JLabel("<html>Paint.jar can attempt to save your current files, however this is not guarenteed. To try, press the \"Attempt Recovery\" button</html>");
		lblPaintjarCanAttempt.setBounds(10, 396, 624, 27);
		contentPanel.add(lblPaintjarCanAttempt);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,null, null));
		scrollPane.setBounds(10, 54, 624, 333);
		contentPanel.add(scrollPane);

		scrollPane.setViewportView(textArea);

		JLabel lblpaintjarHasExperienced = new JLabel("<html>Paint.jar has experienced an unrecoverable error and has shut down. Please send the below error log to the developers, or upload it as an issue!</html>");
		lblpaintjarHasExperienced.setBounds(10, 11, 624, 32);
		contentPanel.add(lblpaintjarHasExperienced);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		
		if (intf == null) {
			btnAttemptrecovery.setEnabled(false);
			btnAttemptrecovery.setText("Nothing to Recover");
		}
		btnAttemptrecovery.setActionCommand("OK");
		btnAttemptrecovery.addActionListener(this);
		buttonPane.add(btnAttemptrecovery);
		JButton okButton = new JButton("Exit");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

	}

	public void actionPerformed(ActionEvent e) {
		File recovDir = new File("./recovery/");
		if(!recovDir.isDirectory())
			recovDir.mkdir();
		if(e.getSource() == btnAttemptrecovery)
		{
			if(main != null)
			{
				int i = 0;
				for(CanvasContainer container : main.getOpenCanvases())
				{
					i++;
					int l = 0;
					for(Canvas layer : container.getLayers())
					{
						l++;
						File recovery = new File(recovDir, "i"+i+"l"+l+".png");
						try {
							ImageIO.write(layer.getImage(), "png", recovery);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				JOptionPane.showMessageDialog(null, "Any recovered images will be in the \"recovery\" directory");
			}
		}

		try {
			PropertyManager.saveProperties();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		try {
			PropertyManager.saveProperties();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
	}

	public void windowClosed(WindowEvent e) {
		try {
			PropertyManager.saveProperties();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
