package com.androdome.util.paintdotjar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.androdome.util.paintdotjar.managers.ClipboardImage;
import com.androdome.util.paintdotjar.plugin.PluginManager;
import com.androdome.util.paintdotjar.plugin.RegisteredTool;
import com.androdome.util.paintdotjar.prop.PropertyManager;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.ui.ColorBar;
import com.androdome.util.paintdotjar.ui.ContainerToggleButton;
import com.androdome.util.paintdotjar.ui.dialog.AboutDialog;
import com.androdome.util.paintdotjar.ui.dialog.CrashDialog;
import com.androdome.util.paintdotjar.ui.dialog.CreateDialog;
import com.androdome.util.paintdotjar.ui.dialog.LooksAndFeels;
import com.androdome.util.paintdotjar.ui.dialog.UnsavedCanvasDialog;
import com.androdome.util.paintdotjar.util.FallbackFormatManager;
import com.androdome.util.paintdotjar.util.FileFormatManager;
import com.androdome.util.paintdotjar.util.PaintUtils;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;

public final class MainInterface extends JFrame implements ActionListener, ChangeListener, KeyListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String BUILD = "Alpha 0.0.8";
	private JPanel contentPane;
	private JMenuItem mntmOpen = new JMenuItem("Open");
	private JMenuItem mntmNew = new JMenuItem("New");
	private JMenuItem mntmSave = new JMenuItem("Save");
	private JMenuItem mntmS2CB = new JMenuItem("Save to clipboard");
	private JMenuItem mntmLAF = new JMenuItem("Looks And Feels");
	private JMenuItem mntmStandard = new JMenuItem("Standard");
	private JMenuItem mntmTiledraw = new JMenuItem("TileDraw");
	private JMenuItem mntmAbout = new JMenuItem("About");
	private JCheckBox chckbxTickMultiples = new JCheckBox("Tick multiples");
	private ColorBar colorPanel = new ColorBar();
	private PluginManager manager = new PluginManager(this);
	private CanvasContainer currentCanvas = new CanvasContainer(manager, colorPanel);
	private ArrayList<CanvasContainer> openCanvases = new ArrayList<CanvasContainer>();
	private JPanel toolBox = new JPanel();
	private JSlider sliderScale = new JSlider();
	private JToolBar toolBarPlugin = new JToolBar();
	private JToolBar toolBarTool = new JToolBar();
	private MainInterfaceAbstractor abstractor = new MainInterfaceAbstractor(this);
	private final static int icoimg = 32;
	private JTextField txtScale;
	private final JPanel rightSidePanel = new JPanel();
	private final JButton btnHidePanel = new JButton("");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel openPanel = new JPanel();
	private final JMenuItem mntmSaveAs = new JMenuItem("Save As...");
	private final static int maxOn = 3200;
	private final static int maxOff = 800;
	private final JMenuItem mntmTestDialog = new JMenuItem("Test Dialog");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		
		MainInterface frame = null;
		try{
		System.setProperty("sun.java2d.opengl", "true");
		LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();

		PropertyManager.loadProperties();
		if(PropertyManager.getProperty("frame-decorated", "false").trim().equalsIgnoreCase("true"))
		{
			System.out.println("decorating");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}
		boolean found = false;
		if(!PropertyManager.getProperty("look-and-feel", "null").trim().equalsIgnoreCase("null"))
		{
			try {
				UIManager.setLookAndFeel(PropertyManager.getProperty("look-and-feel"));
				found = true;
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
		if(!found)
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
		//EventQueue.invokeLater(new Runnable() {
			//public void run() {
				//try
				//{
					frame = new MainInterface();
					frame.setVisible(true);
				//}
				//catch (Exception e)
				//{
					//e.printStackTrace();
				//}
			//}
		//});
		}
		catch(Throwable err)
		{
			err.printStackTrace();
			new CrashDialog(err, frame).setVisible(true);
		}
	}

	/**
	 * Create the frame.
	 */
	private MainInterface() {
		//addOpenCanvas(currentCanvas, true);
		setTitle("Paint.jar");
		manager.loadPlugins();
		PaintUtils.setDefault();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 976, 702);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		addOpenCanvas(currentCanvas, true);
		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel toolPanel = new JPanel();
		toolPanel.setBorder(null);
		panel.add(toolPanel, BorderLayout.WEST);
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		toolPanel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		mnFile.add(mntmOpen);
		
		mnFile.add(mntmNew);

		mnFile.add(mntmSave);
		
		mnFile.add(mntmSaveAs);
		
		mnFile.add(mntmAbout);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnCanvasDispaly = new JMenu("Canvas Dispaly");
		mnView.add(mnCanvasDispaly);
		
		mnCanvasDispaly.add(mntmStandard);
		
		mnCanvasDispaly.add(mntmTiledraw);
		mntmTestDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UnsavedCanvasDialog(MainInterface.this.abstractor, openCanvases).setVisible(true);
			}
		});
		
		mnView.add(mntmTestDialog);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mnEdit.add(mntmS2CB);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		
		mnSettings.add(mntmLAF);
		
		JMenu mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);
		
		JToolBar toolBarGeneral = new JToolBar();
		toolPanel.add(toolBarGeneral);
		
		toolPanel.add(toolBarPlugin);


		panel.add(toolBarTool, BorderLayout.SOUTH);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		FlowLayout flowLayout = (FlowLayout) openPanel.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		openPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		openPanel.setBackground(new Color(153, 153, 153));
		scrollPane.setPreferredSize(new Dimension(-1, 90));
		scrollPane.setViewportView(openPanel);
		

		contentPane.add(toolBox, BorderLayout.WEST);
		toolBox.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		toolBox.setPreferredSize(new Dimension((icoimg+4+5)*2+5,0));
		
		
		contentPane.add(currentCanvas, BorderLayout.CENTER);
		
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
		
		contentPane.add(rightSidePanel, BorderLayout.EAST);
		rightSidePanel.setLayout(new BorderLayout(0, 0));
		
		rightSidePanel.add(btnHidePanel, BorderLayout.WEST);
		btnHidePanel.setPreferredSize(new Dimension(10,-1));
		chckbxTickMultiples.addChangeListener(this);
		sliderScale.addChangeListener(this);
		//Listeners
		mntmOpen.addActionListener(this);
		mntmNew.addActionListener(this);
		mntmSave.addActionListener(this);
		mntmSaveAs.addActionListener(this);
		mntmS2CB.addActionListener(this);
		mntmLAF.addActionListener(this);
		txtScale.addKeyListener(this);
		mntmStandard.addActionListener(this);
		mntmTiledraw.addActionListener(this);
		mntmAbout.addActionListener(this);
		this.addWindowListener(this);
	}

	public void requestClose(CanvasContainer canvas) {
		if(canvas.isChanged())
		{
			int retval = JOptionPane.showOptionDialog(null, "This canvas has unsaved changes!\r\nSave them before closing?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Save", "Save As", "Discard", "Cancel"}, null);
			System.out.println(retval);
			if(retval == 1 || (retval == 0 && canvas.getRelatedFile() == null))
			{
				if(!showSaveDialog(canvas))
					return;
			}
			else if(retval == 0)
			{
				if(!saveNoDialog(canvas))
					return;
			}
			else if(retval == 3)
			{
				return;
			}
		}
		if(openCanvases.remove(canvas))
		{
			for(Component c : openPanel.getComponents())
			{
				if(c instanceof ContainerToggleButton)
				{
					ContainerToggleButton btn = (ContainerToggleButton)c;
					if(btn.getCanvasContainer() == canvas)
					{
						openPanel.remove(btn);
						contentPane.revalidate();
						contentPane.repaint();
						break;
					}
				}
			}
			if(openCanvases.size() == 0)
			{
				addOpenCanvas(new CanvasContainer(manager, this.colorPanel), true);
			}
			else if(currentCanvas == canvas)
			{
				this.setSelectedCanvas(openCanvases.get(0));
			}
		}
	}
		
	
	public void addOpenCanvas(final CanvasContainer canvas, boolean switchTo) {
		this.openCanvases.add(canvas);
		final ContainerToggleButton btn = new ContainerToggleButton(canvas, new Dimension(64, 64));
		this.openPanel.add(btn);
		btn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(btn.isOverX())
					requestClose(canvas);
				else setSelectedCanvas(canvas);
				
			}
			
		});
		if(switchTo)
			setSelectedCanvas(canvas);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmOpen)
		{
			JFileChooser chooser = createChooser(true);
			chooser.setMultiSelectionEnabled(true);
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				PropertyManager.setProperty("last-dir", chooser.getCurrentDirectory().getAbsolutePath());
				for(File f : chooser.getSelectedFiles())
				{
					String extension = "";
					if (f.getName().contains("."))
					     extension = f.getName().substring(f.getName().lastIndexOf(".")+1).trim().toLowerCase();
					FileFormatManager ffm = PaintUtils.registeredHandlers.get(extension);
					if(ffm == null)
						ffm = PaintUtils.FALLBACK;
					CanvasContainer cc = ffm.loadCanvas(f, this.abstractor);
					if(cc != null)
					{
						cc.setRelatedFile(f);
						cc.setFormatName(extension);
						addOpenCanvas(cc, true);
					}
					else if(!ffm.doesDisplayError())
					{
						JOptionPane.showMessageDialog(null, "The file is could not load!", "Error", JOptionPane.ERROR_MESSAGE);
					}
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
			if(currentCanvas.getRelatedFile() == null)
				showSaveDialog(currentCanvas);
			else 
				saveNoDialog(currentCanvas);
		}
		else if(e.getSource() == mntmSaveAs)
		{
			showSaveDialog(currentCanvas);
		}
		else if(e.getSource() == mntmS2CB)
		{
			BufferedImage image = currentCanvas.manager.getImage();
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
			currentCanvas.canvasRenderMode = CanvasContainer.CanvasRenderMode.NORMAL;
			currentCanvas.revalidate();
			currentCanvas.repaint();
		}
		else if(e.getSource() == mntmTiledraw)
		{
			currentCanvas.canvasRenderMode = CanvasContainer.CanvasRenderMode.TILEDRAW;
			currentCanvas.revalidate();
			currentCanvas.repaint();
		}
		else if(e.getSource() == mntmAbout)
		{
			new AboutDialog(manager).setVisible(true);
		}
	}
	
	
	public JFileChooser createChooser(boolean containsAll)
	{
		JFileChooser chooser = new JFileChooser();
		if(containsAll)
			chooser.setFileFilter(PaintUtils.getSupportedImagesManager());
		if(PropertyManager.getProperty("last-dir", "").trim()!="")
		{
			chooser.setCurrentDirectory(new File(PropertyManager.getProperty("last-dir")));
		}
		chooser.setAcceptAllFileFilterUsed(false);
		for(String key : PaintUtils.registeredHandlers.keySet())
		{
			FileFormatManager ffm = PaintUtils.registeredHandlers.get(key);
			if(ffm == null)
				continue;
			if(!containsAll && key.trim().equalsIgnoreCase(PaintUtils.DEFAULT_EXT))
				chooser.setFileFilter(ffm);
			else chooser.addChoosableFileFilter(ffm);
		}
		chooser.addChoosableFileFilter(PaintUtils.FALLBACK);
		return chooser;
	}

	boolean showSaveDialog(CanvasContainer cc) {
		JFileChooser chooser = createChooser(false);
		chooser.setSelectedFile(cc.getRelatedFile());
		if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File newFile = chooser.getSelectedFile();
			String ext = "png";
			PropertyManager.setProperty("last-dir", chooser.getCurrentDirectory().getAbsolutePath());
			FileFormatManager ffm = (FileFormatManager)chooser.getFileFilter();
			if(!(ffm instanceof FallbackFormatManager))
			{
				
				if(!newFile.getName().trim().toLowerCase().endsWith("."+ffm.extension))
				{
					newFile = new File(newFile.getAbsoluteFile()+"."+ffm.extension);
				}
				ext = ffm.extension;
			}
			if(ffm.saveCanvas(cc, newFile, this.abstractor))
			{
				cc.setFormatName(ext);
				cc.setChanged(false);
				cc.setRelatedFile(newFile);
				setName();
				return true;
			}
			else if(!ffm.doesDisplayError())
			{
				JOptionPane.showMessageDialog(null, "The file could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return false;
	}
	boolean saveNoDialog(CanvasContainer cc) {
		FileFormatManager ffm = PaintUtils.registeredHandlers.get(cc.getFormatName().trim().toLowerCase());
		if(ffm == null)
		{
			return showSaveDialog(cc);
		}
		else
		{
			if(ffm.saveCanvas(cc, cc.getRelatedFile(), this.abstractor))
			{
				cc.setChanged(false);
				setName();
				return true;
			}
			else if(!ffm.doesDisplayError())
			{
				JOptionPane.showMessageDialog(null, "The file could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return false;
	}

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
		return this.currentCanvas;
	}

	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sliderScale)
		{
			currentCanvas.setScale(sliderScale.getValue()/100.0F);
			currentCanvas.repaint();
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
						currentCanvas.setScale(perc/100);
					}
					catch(Exception ex)
					{
						txtScale.setText(currentCanvas.getScale()*100+"%");
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
						currentCanvas.setScale(perc/100);
					}
					catch(Exception ex)
					{
						txtScale.setText(currentCanvas.getScale()*100+"%");
					}
				}
				currentCanvas.repaint();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
	/*public void setCanvasContainer(CanvasContainer newCanvas)
	{
		contentPane.remove(currentCanvas);
		if(newCanvas != null)
			this.currentCanvas = newCanvas;
		else
			currentCanvas = new CanvasContainer(manager, colorPanel);
		contentPane.add(currentCanvas);
		contentPane.revalidate();
	}*/
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
	public void setSelectedCanvas(CanvasContainer cc)
	{
		if(openCanvases.contains(cc))
		{
			contentPane.remove(currentCanvas);
			for(Component c : openPanel.getComponents())
			{
				if(c instanceof ContainerToggleButton)
				{
					ContainerToggleButton contButton = (ContainerToggleButton)c;
					if(contButton.getCanvasContainer() == cc)
						contButton.setSelected(true);
					else contButton.setSelected(false);
				}
			}
			currentCanvas = cc;
			contentPane.add(currentCanvas);
			contentPane.revalidate();
			contentPane.repaint();
			setName();
		}
	}

	public void setName() {
		String name = "Paint.jar - ";
		if(currentCanvas.getRelatedFile() == null)
			name += "Untitled";
		else name += currentCanvas.getRelatedFile().getName();
		if(currentCanvas.isChanged())
			name += "*";
		this.setTitle(name);
	}

	public ArrayList<CanvasContainer> getOpenCanvases() {
		return this.openCanvases;
	}

	PluginManager getPluginManager() {
		return manager;
	}
	ColorBar getColorBar() {
		return this.colorPanel;
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
		ArrayList<CanvasContainer> unsaved = new ArrayList<CanvasContainer>();
		for(CanvasContainer c : openCanvases)
		{
			if(c.isChanged())
				unsaved.add(c);
		}
		if(unsaved.size() > 0)
		{
			UnsavedCanvasDialog dialog = new UnsavedCanvasDialog(this.abstractor, unsaved);
			dialog.setLocationRelativeTo(this);
			dialog.setModal(true);
			dialog.setVisible(true);
			if(dialog.canDispose())
				dispose();
		}
		else dispose();
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
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
