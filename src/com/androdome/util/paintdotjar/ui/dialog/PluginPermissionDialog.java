package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.androdome.util.paintdotjar.plugin.JavaPlugin;

public class PluginPermissionDialog extends InformationDialog {

	/**
	 * 
	 */
	public static final Image ICON_WARN;
	public static final Image ICON_STOP;
	public static final Image ICON_INFO;
	public static final Image ICON_OKAY;
	public static final Image ICON_QUESTION;
	static
	{
		Image warnIcon = null;
		Image stopIcon = null;
		Image questionIcon = null;
		Image infoIcon = null;
		Image okayIcon = null;
		try{
			 warnIcon = ImageIO.read(PluginPermissionDialog.class.getResource("/ico/warn.png"));
		}
		catch (IOException e){}
		try{
			stopIcon = ImageIO.read(PluginPermissionDialog.class.getResource("/ico/stop.png"));
		}
		catch (IOException e){}
		try{
			questionIcon = ImageIO.read(PluginPermissionDialog.class.getResource("/ico/question.png"));
		}
		catch (IOException e){}
		try{
			infoIcon = ImageIO.read(PluginPermissionDialog.class.getResource("/ico/info.png"));
		}
		catch (IOException e){}
		try{
			okayIcon = ImageIO.read(PluginPermissionDialog.class.getResource("/ico/okay.png"));
		}
		catch (IOException e){}
		ICON_WARN = warnIcon;
		ICON_STOP = stopIcon;
		ICON_OKAY = okayIcon;
		ICON_INFO = infoIcon;
		ICON_QUESTION = questionIcon;
		
	}
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	@SuppressWarnings("unused")
	private PluginPermissionDialog() {
		this(new JavaPlugin(){
			@Override
			public void init() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void destroy() {
				// TODO Auto-generated method stub
				
			}}, "{DEFAULT ACTION}", null);
	}
	
	public PluginPermissionDialog(JavaPlugin plugin, String action, Image icon) {
		super("Plugin Permissions", "The plugin \"" + plugin.getMainInterface().getName() + "\" wants to " + action + ". <br>Allow it?", ICON_WARN);
		if(icon != null)
			this.setIcon(icon);
		FlowLayout flowLayout = (FlowLayout) getFooterPane().getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		setBounds(100, 100, 603, 435);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getFooterPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Allow");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Deny");
		buttonPane.add(cancelButton);

	}

}
