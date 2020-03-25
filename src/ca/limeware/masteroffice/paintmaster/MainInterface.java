package ca.limeware.masteroffice.paintmaster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.limeware.masteroffice.paintmaster.plugin.PluginManager;
import ca.limeware.masteroffice.paintmaster.plugin.RegisteredTool;
import ca.limeware.masteroffice.paintmaster.ui.CanvasContainer;
import ca.limeware.masteroffice.paintmaster.ui.ColorBar;
import ca.limeware.masteroffice.paintmaster.ui.dialog.CreateDialog;
import ca.limeware.masteroffice.paintmaster.ui.dialog.LooksAndFeels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class MainInterface extends JFrame implements ActionListener, ChangeListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JMenuItem mntmOpen = new JMenuItem("Open");
	JMenuItem mntmNew = new JMenuItem("New");
	JMenuItem mntmSave = new JMenuItem("Save");
	JMenuItem mntmS2CB = new JMenuItem("Save to clipboard");
	JMenuItem mntmLAF = new JMenuItem("Looks And Feels");
	JMenuItem mntmStandard = new JMenuItem("Standard");
	JMenuItem mntmTiledraw = new JMenuItem("TileDraw");
	JCheckBox chckbxTickMultiples = new JCheckBox("Tick multiples");
	ColorBar colorPanel = new ColorBar();
	PluginManager manager = new PluginManager(this);
	CanvasContainer canvasContainer = new CanvasContainer(manager, colorPanel);
	JPanel toolBox = new JPanel();
	JSlider sliderScale = new JSlider();
	JToolBar toolBarPlugin = new JToolBar();
	JToolBar toolBarTool = new JToolBar();
	int maxOn = 3200;
	int maxOff = 800;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		boolean found = false;
		for(int i = 0; i < laf.length; i++)
		{
			if(laf[i].getName().toLowerCase().contains("nimbus"))
			{
				try {
					UIManager.setLookAndFeel(laf[i].getClassName());
					found = true;
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		}
		if(!found)
		{
			for(int i = 0; i < laf.length; i++)
			{
				if(laf[i].getName().toLowerCase().contains("gtk"))
				{
					try {
						UIManager.setLookAndFeel(laf[i].getClassName());
						found = true;
						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(!found)
		{
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					MainInterface frame = new MainInterface();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainInterface() {
		setTitle("Paint.jar");
		manager.loadPlugins();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 976, 702);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel openPanel = new JPanel();
		openPanel.setBackground(new Color(153, 153, 153));
		openPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(openPanel);
		
		JPanel toolPanel = new JPanel();
		toolPanel.setBorder(null);
		panel.add(toolPanel, BorderLayout.WEST);
		toolPanel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		toolPanel.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		mnFile.add(mntmOpen);
		
		mnFile.add(mntmNew);

		mnFile.add(mntmSave);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnCanvasDispaly = new JMenu("Canvas Dispaly");
		mnView.add(mnCanvasDispaly);
		
		mnCanvasDispaly.add(mntmStandard);
		
		mnCanvasDispaly.add(mntmTiledraw);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mnEdit.add(mntmS2CB);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		
		mnSettings.add(mntmLAF);
		
		JMenu mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);
		
		JToolBar toolBarGeneral = new JToolBar();
		toolPanel.add(toolBarGeneral, BorderLayout.CENTER);
		
		toolPanel.add(toolBarPlugin, BorderLayout.SOUTH);


		panel.add(toolBarTool, BorderLayout.SOUTH);
		

		contentPane.add(toolBox, BorderLayout.WEST);
		toolBox.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		toolBox.setPreferredSize(new Dimension((icoimg+4+5)*2+5,0));
		canvasContainer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		contentPane.add(canvasContainer, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		panel_1.add(colorPanel, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(sliderScale);
		
		
		sliderScale.setValue(100);
		sliderScale.setPaintTicks(true);
		sliderScale.setMinorTickSpacing(10);
		sliderScale.setMajorTickSpacing(100);
		sliderScale.setMinimum(10);
		sliderScale.setMaximum(maxOff);
		sliderScale.setSnapToTicks(true);
		

		panel_2.add(chckbxTickMultiples, BorderLayout.WEST);
		
		txtScale = new JTextField();
		txtScale.setText("100.0%");
		panel_2.add(txtScale, BorderLayout.EAST);
		txtScale.setColumns(6);
		chckbxTickMultiples.addChangeListener(this);
		sliderScale.addChangeListener(this);
		//Listeners
		mntmOpen.addActionListener(this);
		mntmNew.addActionListener(this);
		mntmSave.addActionListener(this);
		mntmS2CB.addActionListener(this);
		mntmLAF.addActionListener(this);
		txtScale.addKeyListener(this);
		mntmStandard.addActionListener(this);
		mntmTiledraw.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmOpen)
		{
			JFileChooser chooser = new JFileChooser();
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				try {
					BufferedImage img = ImageIO.read(chooser.getSelectedFile());
					if(img == null)
					{
						JOptionPane.showMessageDialog(null, "The file has no valid reader associated to it!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					contentPane.remove(canvasContainer);
					canvasContainer = new CanvasContainer(img, manager, colorPanel);
					contentPane.add(canvasContainer);
					contentPane.revalidate();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "The file is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource() == mntmNew)
		{
			CreateDialog dlog = new CreateDialog(this, colorPanel, manager);
			dlog.setModal(true);
			dlog.setVisible(true);
		}
		else if(e.getSource() == mntmSave)
		{
			JFileChooser chooser = new JFileChooser();
			if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				try{
				BufferedImage image = canvasContainer.manager.getImage();
				String file = chooser.getSelectedFile().getCanonicalPath();
				if(!file.toLowerCase().trim().endsWith(".png"))
					file += ".png";
				ImageIO.write(image, "png", new File(file));
				}catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "The file could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource() == mntmS2CB)
		{
			BufferedImage image = canvasContainer.manager.getImage();
			ClipboardImage.write(image);
		}
		else if(e.getSource() == mntmLAF)
		{
			LooksAndFeels dialog = new LooksAndFeels(this);
			dialog.setModal(true);
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
		else if(e.getSource() == mntmStandard)
		{
			canvasContainer.canvasRenderMode = CanvasContainer.CanvasRenderMode.NORMAL;
			canvasContainer.revalidate();
			canvasContainer.repaint();
		}
		else if(e.getSource() == mntmTiledraw)
		{
			canvasContainer.canvasRenderMode = CanvasContainer.CanvasRenderMode.TILEDRAW;
			canvasContainer.revalidate();
			canvasContainer.repaint();
		}
	}

	int icoimg = 32;
	private JTextField txtScale;
	public void retool() {
		toolBox.removeAll();
		clearToolToolbar();
		ButtonGroup bg = new ButtonGroup();
		boolean tExist = false;
		for(RegisteredTool tool : manager.getTools())
		{
			final RegisteredTool t = tool;
			JToggleButton button = new JToggleButton(new ImageIcon(tool.getIcon().getScaledInstance(icoimg,icoimg, Image.SCALE_SMOOTH)));
			if(manager.getTool() == t.getTool())
			{
				button.setSelected(true);
				tExist = true;
			}
			button.setPreferredSize(new Dimension(icoimg+4,icoimg+4));
			button.setToolTipText(t.getName());
			button.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					clearToolToolbar();
					manager.setTool(t.getTool());
				}
			});
			toolBox.add(button);
			bg.add(button);
		}
		if(!tExist)
			manager.setTool(null);
	}

	public CanvasContainer getCanvasContainer() {
		return this.canvasContainer;
	}

	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sliderScale)
		{
			canvasContainer.setScale(sliderScale.getValue()/100.0F);
			canvasContainer.repaint();
		}
		else if(arg0.getSource() == chckbxTickMultiples)
		{
			if(chckbxTickMultiples.isSelected())
			{
				sliderScale.setMajorTickSpacing(100);
				sliderScale.setMinorTickSpacing(100);
				sliderScale.setMinimum(100);
				sliderScale.setMaximum(maxOn);
			}
			else
			{
				sliderScale.setMajorTickSpacing(100);
				sliderScale.setMinorTickSpacing(10);
				sliderScale.setMinimum(10);
				sliderScale.setMaximum(maxOff);
			}
		}
		txtScale.setText((float)sliderScale.getValue() + "%");
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getSource() == txtScale)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				String val = txtScale.getText().trim();
				if(val.endsWith("%"))
				{
					try{
						float perc = Float.parseFloat(val.substring(0, val.length()-1));
						if(perc < 1)
							throw new Exception();//Will change this!
						sliderScale.setValue((int) perc);
						txtScale.setText(perc + "%");
						canvasContainer.setScale(perc/100);
					}
					catch(Exception ex)
					{
						txtScale.setText(canvasContainer.getScale()*100+"%");
					}
				}
				else
				{
					try{
						float perc = Float.parseFloat(val);
						if(perc < 1)
							throw new Exception();//Will change this!
						sliderScale.setValue((int) perc);
						txtScale.setText(perc + "%");
						canvasContainer.setScale(perc/100);
					}
					catch(Exception ex)
					{
						txtScale.setText(canvasContainer.getScale()*100+"%");
					}
				}
				canvasContainer.repaint();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
	public void setCanvasContainer(CanvasContainer newCanvas)
	{
		contentPane.remove(canvasContainer);
		if(newCanvas != null)
			this.canvasContainer = newCanvas;
		else
			canvasContainer = new CanvasContainer(manager, colorPanel);
		contentPane.add(canvasContainer);
		contentPane.revalidate();
	}
	public JToolBar getToolToolbar()
	{
		return toolBarTool;
	}
	public JToolBar getPluginToolbar()
	{
		return toolBarPlugin;
	}
	public void clearToolToolbar()
	{
		toolBarTool.removeAll();
		toolBarTool.revalidate();
		toolBarTool.repaint();
	}
	public void clearPluginToolbar()
	{
		toolBarPlugin.removeAll();
		toolBarPlugin.revalidate();
		toolBarTool.repaint();
	}
}
