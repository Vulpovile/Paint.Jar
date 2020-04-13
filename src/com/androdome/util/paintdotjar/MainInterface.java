package com.androdome.util.paintdotjar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import java.util.Collections;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.androdome.util.paintdotjar.managers.ClipboardImage;
import com.androdome.util.paintdotjar.managers.HistoryEntry;
import com.androdome.util.paintdotjar.managers.ImageManager;
import com.androdome.util.paintdotjar.plugin.PluginManager;
import com.androdome.util.paintdotjar.plugin.RegisteredTool;
import com.androdome.util.paintdotjar.prop.PropertyManager;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.ui.ColorBar;
import com.androdome.util.paintdotjar.ui.ComponentList;
import com.androdome.util.paintdotjar.ui.ContainerToggleButton;
import com.androdome.util.paintdotjar.ui.DetatchablePanel;
import com.androdome.util.paintdotjar.ui.LayerInformationPanel;
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
import javax.swing.KeyStroke;

import java.awt.event.InputEvent;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JList;

public final class MainInterface extends JFrame implements ActionListener, ChangeListener, KeyListener, WindowListener, ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String BUILD = "Alpha 0.0.8";
	private JPanel contentPane;
	private JMenuItem mntmOpen = new JMenuItem("Open");
	private JMenuItem mntmNew = new JMenuItem("New");
	private JMenuItem mntmSave = new JMenuItem("Save");
	private JMenuItem mntmSaveAll = new JMenuItem("Save All");
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
	private final JButton btnNew = new JButton("");
	private final JButton btnOpen = new JButton("");
	private final JButton btnSave = new JButton("");
	private final JButton btnSaveAll = new JButton("");
	private final DetatchablePanel layerPanel = new DetatchablePanel("Layers");
	private final ComponentList layerList = new ComponentList();
	private final JPanel panelHistoryContainer = new JPanel();
	private final DetatchablePanel historyPanel = new DetatchablePanel("History");
	private final JScrollPane historyScrollPane = new JScrollPane();
	private final JPanel layerButtonBar = new JPanel();
	private final JPanel historyButtonBar = new JPanel();
	final int ico_size = 17;
	private final JButton btnNewLayer = new JButton(ImageManager.getScaledImageIconResource("ico/layers/new.png", ico_size, ico_size, Image.SCALE_SMOOTH));
	private final JButton btnCloneLayer = new JButton(ImageManager.getScaledImageIconResource("ico/layers/clone.png", ico_size, ico_size, Image.SCALE_SMOOTH));
	private final JButton btnDeleteLayer = new JButton(ImageManager.getScaledImageIconResource("ico/layers/delete.png", ico_size, ico_size, Image.SCALE_SMOOTH));
	private final JList historyList = new JList();
	private final JButton btnUndo = new JButton(ImageManager.getScaledImageIconResource("ico/history/undo.png", ico_size, ico_size, Image.SCALE_SMOOTH));
	private final JButton btnRedo = new JButton(ImageManager.getScaledImageIconResource("ico/history/redo.png", ico_size, ico_size, Image.SCALE_SMOOTH));
	
	
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
		
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(ImageManager.getImageResource("ico/PaintJar32.png"));
		icons.add(ImageManager.getImageResource("ico/PaintJar64.png"));
		icons.add(ImageManager.getImageResource("ico/PaintJar128.png"));
		historyScrollPane.setPreferredSize(new Dimension(200, -1));
		PaintUtils.setAppIcons(this, icons);
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
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		mntmOpen.setIcon(ImageManager.getScaledImageIconResource("ico/file/open.png", 16, 16, Image.SCALE_SMOOTH));
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		
		
		mnFile.add(mntmOpen);
		mntmNew.setIcon(ImageManager.getScaledImageIconResource("ico/file/new.png", 16, 16, Image.SCALE_SMOOTH));
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		
		mnFile.add(mntmNew);
		mntmSave.setIcon(ImageManager.getScaledImageIconResource("ico/file/save.png", 16, 16, Image.SCALE_SMOOTH));
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

		mnFile.add(mntmSave);
		mntmSaveAs.setIcon(ImageManager.getScaledImageIconResource("ico/file/save.png", 16, 16, Image.SCALE_SMOOTH));
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		
		mnFile.add(mntmSaveAs);
		mntmSaveAll.setIcon(ImageManager.getScaledImageIconResource("ico/file/saveall.png", 16, 16, Image.SCALE_SMOOTH));
		mntmSaveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		
		mnFile.add(mntmSaveAll);
		mntmAbout.setIcon(ImageManager.getScaledImageIconResource("ico/layers/unknown.png", 16, 16, Image.SCALE_SMOOTH));
		
		mnFile.add(mntmAbout);
		
		JMenu mnView = new JMenu("View");
		mnView.setMnemonic('V');
		menuBar.add(mnView);
		
		JMenu mnCanvasDispaly = new JMenu("Canvas Dispaly");
		mnView.add(mnCanvasDispaly);
		//mntmStandard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		
		mnCanvasDispaly.add(mntmStandard);
		//mntmTiledraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		
		mnCanvasDispaly.add(mntmTiledraw);
		mntmTestDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UnsavedCanvasDialog(MainInterface.this.abstractor, openCanvases).setVisible(true);
			}
		});
		
		mnView.add(mntmTestDialog);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic('E');
		menuBar.add(mnEdit);
		
		mnEdit.add(mntmS2CB);
		
		JMenu mnSettings = new JMenu("Settings");
		mnSettings.setMnemonic('E');
		menuBar.add(mnSettings);
		
		
		mnSettings.add(mntmLAF);
		
		JMenu mnPlugins = new JMenu("Plugins");
		mnPlugins.setMnemonic('P');
		menuBar.add(mnPlugins);
		
		JToolBar toolBarGeneral = new JToolBar();
		toolPanel.add(toolBarGeneral);
		btnNew.setToolTipText("New");
		btnNew.setIcon(ImageManager.getImageIconResource("ico/file/new.png"));
		btnNew.setMnemonic('N');
		
		toolBarGeneral.add(btnNew);
		btnOpen.setToolTipText("Open");
		btnOpen.setIcon(ImageManager.getImageIconResource("ico/file/open.png"));
		btnOpen.setMnemonic('O');
		
		toolBarGeneral.add(btnOpen);
		btnSave.setToolTipText("Save");
		btnSave.setIcon(ImageManager.getImageIconResource("ico/file/save.png"));
		btnSave.setMnemonic('S');
		
		toolBarGeneral.add(btnSave);
		btnSaveAll.setToolTipText("Save All");
		btnSaveAll.setIcon(ImageManager.getImageIconResource("ico/file/saveall.png"));
		btnSaveAll.setMnemonic('A');
		
		toolBarGeneral.add(btnSaveAll);
		
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
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		
		panelBottom.add(colorPanel, BorderLayout.WEST);
		
		JPanel panelScale = new JPanel();
		panelBottom.add(panelScale, BorderLayout.EAST);
		panelScale.setLayout(new BorderLayout(0, 0));
		panelScale.add(sliderScale);
		
		
		sliderScale.setValue(100);
		sliderScale.setPaintTicks(true);
		sliderScale.setMinorTickSpacing(10);
		sliderScale.setMajorTickSpacing(100);
		sliderScale.setMinimum(10);
		sliderScale.setMaximum(maxOff);
		sliderScale.setSnapToTicks(true);
		

		panelScale.add(chckbxTickMultiples, BorderLayout.WEST);
		
		txtScale = new JTextField();
		txtScale.setText("100.0%");
		panelScale.add(txtScale, BorderLayout.EAST);
		txtScale.setColumns(6);
		
		contentPane.add(rightSidePanel, BorderLayout.EAST);
		rightSidePanel.setLayout(new BorderLayout(0, 0));
		
		
		rightSidePanel.add(btnHidePanel, BorderLayout.WEST);
		btnHidePanel.setPreferredSize(new Dimension(20,-1));
		final int ICON_SIZE = 10;
		btnHidePanel.setIcon(ImageManager.getScaledImageIconResource("ico/right.png", ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
		
		

		JPanel panelLayerContainer = new JPanel();
		JScrollPane layerScrollPane = new JScrollPane();
		final JPanel panelRight = new JPanel();
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		btnHidePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panelRight.isVisible())
					btnHidePanel.setIcon(ImageManager.getScaledImageIconResource("ico/left.png", ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
				else btnHidePanel.setIcon(ImageManager.getScaledImageIconResource("ico/right.png", ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
				panelRight.setVisible(!panelRight.isVisible());
				rightSidePanel.revalidate();
				rightSidePanel.repaint();
			}
		});
		
		rightSidePanel.add(panelRight, BorderLayout.EAST);
		panelRight.setLayout(new BorderLayout(0, 0));
		panelRight.add(splitPane, BorderLayout.CENTER);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(panelLayerContainer);
		panelLayerContainer.setLayout(new BorderLayout(0, 0));
		layerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelLayerContainer.add(layerPanel);
		layerPanel.setLayout(new BorderLayout(0, 0));
		JLabel lblLayers = new JLabel("Layers              ");
		lblLayers.setIcon(ImageManager.getScaledImageIconResource("ico/layers/clone.png", 16, 16, Image.SCALE_SMOOTH));
		
		layerPanel.add(lblLayers, BorderLayout.NORTH);
		
		layerPanel.add(layerScrollPane, BorderLayout.CENTER);
		
		
		layerScrollPane.setViewportView(layerList);
		
		layerPanel.add(layerButtonBar, BorderLayout.SOUTH);
		final Dimension PREF_BSIZE = new Dimension(25, 25);
		btnNewLayer.setPreferredSize(PREF_BSIZE);
		layerButtonBar.add(btnNewLayer);
		btnCloneLayer.setPreferredSize(PREF_BSIZE);
		layerButtonBar.add(btnCloneLayer);
		btnDeleteLayer.setPreferredSize(PREF_BSIZE);
		layerButtonBar.add(btnDeleteLayer);
		
		splitPane.setRightComponent(panelHistoryContainer);
		panelHistoryContainer.setLayout(new BorderLayout(0, 0));
		historyPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		panelHistoryContainer.add(historyPanel, BorderLayout.CENTER);
		historyPanel.setLayout(new BorderLayout(0, 0));
		

		JLabel lblHistory = new JLabel("History");
		lblHistory.setIcon(ImageManager.getScaledImageIconResource("ico/history/clock.png", 16, 16, Image.SCALE_SMOOTH));
		historyPanel.add(lblHistory, BorderLayout.NORTH);
		
		historyPanel.add(historyScrollPane, BorderLayout.CENTER);
		
		
		
		historyList.setCellRenderer(new DefaultListCellRenderer() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        if(value instanceof HistoryEntry)
		        {
		        	HistoryEntry he = (HistoryEntry)value;
		        	label.setText(he.message);
		        	if(he.changeIcon != null)
		        	{
		        		label.setIcon(new ImageIcon(he.changeIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		        	}
		        	else
		        	{
		        		label.setIcon(ImageManager.getScaledImageIconResource("ico/unknown.png", 16, 16, Image.SCALE_SMOOTH));
		        	}
		        }
		        return label;
		    }
		});
		
		
		historyScrollPane.setViewportView(historyList);
		
		historyPanel.add(historyButtonBar, BorderLayout.SOUTH);
		btnUndo.setPreferredSize(new Dimension(25, 25));
		
		historyButtonBar.add(btnUndo);
		btnRedo.setPreferredSize(new Dimension(25, 25));
		
		historyButtonBar.add(btnRedo);
		
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
		mntmSaveAll.addActionListener(this);
		btnNew.addActionListener(this);
		btnSave.addActionListener(this);
		btnOpen.addActionListener(this);
		btnSaveAll.addActionListener(this);
		layerList.addListSelectionListener(this);
		btnNewLayer.addActionListener(this);
		this.addWindowListener(this);
		//KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyboardShortcutListener(this));
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
		if(e.getSource() == mntmOpen || e.getSource() == btnOpen)
		{
			CanvasContainer cc = showOpenDialog();
			if(cc != null)
				addOpenCanvas(cc, true);
		}
		else if(e.getSource() == mntmNew || e.getSource() == btnNew)
		{
			CreateDialog dlog = new CreateDialog(this, colorPanel, manager);
			dlog.setModal(true);
			dlog.setVisible(true);
		}
		else if(e.getSource() == mntmSave || e.getSource() == btnSave)
		{
			if(currentCanvas.getRelatedFile() == null)
				showSaveDialog(currentCanvas);
			else 
				saveNoDialog(currentCanvas);
		}
		else if(e.getSource() == btnSaveAll)
		{
			for(CanvasContainer cc : openCanvases)
			{
				if(cc.getRelatedFile() == null)
					showSaveDialog(cc);
				else 
					saveNoDialog(cc);
			}
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
		else if(e.getSource() == btnNewLayer)
		{
			Canvas canvas = new Canvas(currentCanvas.manager.getWidth(),currentCanvas.manager.getHeight());
			canvas.setName("Layer " + currentCanvas.getLayers().size());
			currentCanvas.manager.addLayer(canvas);
			refillLayers();
			this.layerList.validate();
			this.layerList.repaint();
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

	CanvasContainer showOpenDialog()
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
					return cc;
				}
				else if(!ffm.doesDisplayError())
				{
					JOptionPane.showMessageDialog(null, "The file is could not load!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return null;
	}
	
	boolean showSaveDialog(CanvasContainer cc) {
		while(true)
		{
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
				if(cc.getLayers().size() > 1 && !ffm.supportsLayers)
				{
					int res = JOptionPane.showOptionDialog(null, "Your image has layers but the format you have chosen does not support layers.\r\nDo you want to flatten the image, or save as a different format?", "Format Issue", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Flatten and Save", "Save as other format", "Cancel"}, 0);
					if(res == 1)
						continue;
					else if(res == 2)
						return false;
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
	}
	boolean saveNoDialog(CanvasContainer cc) {
		FileFormatManager ffm = null;
		if(cc.getFormatName() != null)
			ffm = PaintUtils.registeredHandlers.get(cc.getFormatName().trim().toLowerCase());
		if(ffm == null || cc.getRelatedFile() == null)
		{
			return showSaveDialog(cc);
		}
		else
		{
			if(cc.getLayers().size() > 1 && !ffm.supportsLayers)
			{
				int res = JOptionPane.showOptionDialog(null, "Your image has layers but the format you have chosen does not support layers.\r\nDo you want to flatten the image, or save as a different format?", "Format Issue", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Flatten and Save", "Save as other format", "Cancel"}, 0);
				if(res == 1)
					showSaveDialog(cc);
				else if(res == 2)
					return false;
			}
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
			refillLayers();
			setHistory();
		}
	}

	private void refillLayers() {
		ArrayList<Component> lip = new ArrayList<Component>();
		for(Canvas c : currentCanvas.getLayers())
		{
			lip.add(new LayerInformationPanel(currentCanvas, currentCanvas.getLayers().indexOf(c), c));
		}
		Collections.reverse(lip);
		//TODO add layer list
		this.layerList.setListData(lip);
	}
	
	public void setHistory()
	{
		this.historyList.setListData(currentCanvas.manager.getHistoryManager().getHistory().toArray());
	}

	public void setName() {
		String name = "Paint.jar - ";
		if(currentCanvas.getRelatedFile() == null)
			name += "Untitled";
		else name += currentCanvas.getRelatedFile().getName();
		if(currentCanvas.isChanged())
		{
			name += "*";
			this.btnSave.setEnabled(true);
		}
		else this.btnSave.setEnabled(false);
		this.btnSaveAll.setEnabled(false);
		for(CanvasContainer c : openCanvases)
		{
			if(c.isChanged())
			{
				this.btnSaveAll.setEnabled(true);
				break;
			}
		}
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
		
	}

	public void windowClosing(WindowEvent e) {

		try {
			PropertyManager.saveProperties();
		} catch (IOException e1) {
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
		System.exit(0);
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowActivated(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		
	}

	public MainInterfaceAbstractor getAbstractor() {
		return abstractor;
	}

	public void repaintList() {
		if(layerList != null)
		this.layerList.repaint();
	}

	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == this.layerList)
		{
			if(this.layerList.getSelectedIndex() > -1)
			{
				
				this.currentCanvas.manager.setSelectedCanvas((this.layerList.getCount()-1)-this.layerList.getSelectedIndex());
			}
		}
	}
}
