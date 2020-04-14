package com.androdome.util.paintdotjar.ui;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.androdome.util.paintdotjar.ui.dialog.ColorDialog;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ColorBar extends JPanel implements ActionListener, MouseListener, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private ColorDialog dialog = null;
	private ColorChoice panel;
	private JPanel colorButtonPanel = new JPanel();
	private Color primary = Color.BLACK;
	private Color secondary = Color.WHITE;
	private Color[][] colors;
	private JButton btnColorDialog = new JButton("^");
	
	
	public ColorBar() {
		initColors();
		setLayout(new BorderLayout(0, 0));
		
		panel = new ColorChoice(this);
		add(panel, BorderLayout.WEST);
		
		add(colorButtonPanel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		add(panel_1, BorderLayout.EAST);
		btnColorDialog.addActionListener(this);
		
		panel_1.add(btnColorDialog);
		panel.repaint();
		
		
	}

	private void initColors() {
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/initcolor.png"));
			colors = new Color[img.getHeight()][img.getWidth()];
			colorButtonPanel.setLayout(new GridLayout(img.getHeight(),img.getWidth()));
			for(int y = 0; y < img.getHeight(); y++)
			{
				for(int x = 0; x < img.getWidth(); x++)
				{
					colors[y][x] = new Color(img.getRGB(x, y));
				}
			}
			for(int y = 0; y < colors.length; y++)
			{
				for(int x = 0; x < colors[y].length; x++)
				{
					JButton btn = new JButton();
					btn.setPreferredSize(new Dimension(14,14));
					btn.setBackground(colors[y][x]);
					colorButtonPanel.add(btn);
					btn.addMouseListener(this);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Color getPrimary() {
		return primary;
	}

	public Color getSecondary() {
		return secondary;
	}

	public Frame getParentFrame()
	{
		Container parentContainer = getParent();
		while(parentContainer != null)
		{
			if(parentContainer instanceof Frame)
				return (Frame)parentContainer;
			parentContainer = parentContainer.getParent();
		}
		return null;
	}
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnColorDialog && dialog == null)
		{
			dialog = new ColorDialog(this);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(btnColorDialog);
			//dialog.setAlwaysOnTop(true);
			dialog.addWindowListener(this);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			setPrimary(((Component) arg0.getSource()).getBackground());
		}
		if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			setSecondary(((Component) arg0.getSource()).getBackground());
		}
		recolorDialog();
		panel.revalidate();
	}

	public void mouseEntered(MouseEvent arg0) {
		
		
	}

	public void mouseExited(MouseEvent arg0) {
	
		
	}

	public void mousePressed(MouseEvent arg0) {
		
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
		
	}

	public void setPrimary(Color color) {
		this.primary = color;
		repaint();
	}

	public void setSecondary(Color color) {
		this.secondary = color;
		repaint();
	}
	
	public void recolorDialog()
	{
		if(dialog != null)
			dialog.recolor();
	}

	public void windowActivated(WindowEvent arg0) {
		
		
	}

	public void windowClosed(WindowEvent arg0) {

	}

	public void windowClosing(WindowEvent arg0) {
		if(arg0.getSource() == dialog)
			dialog = null;
	}

	public void windowDeactivated(WindowEvent arg0) {
		
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		
		
	}

	public void windowIconified(WindowEvent arg0) {
		
		
	}

	public void windowOpened(WindowEvent arg0) {
		
		
	}

	public void swap() {
		Color temp = primary;
		primary = secondary;
		secondary = temp;
		recolorDialog();
		repaint();
	}

}

class ColorChoice extends JPanel implements ComponentListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColorBar bar;
	ColorChoice(ColorBar bar)
	{
		this.bar = bar;
		this.addComponentListener(this);
		this.addMouseListener(this);
		setPreferredSize(new Dimension(28,28));
	}
	
	public void paintComponent(Graphics g)
	{
		this.setPreferredSize(new Dimension(this.getHeight(), this.getHeight()));
		g.setColor(bar.getSecondary());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(bar.getSecondary().brighter());
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(3));

		g.drawLine(0, 0, getWidth(), 0);
		g.drawLine(0, 0, 0, getHeight());
		g.setColor(bar.getSecondary().darker());
		g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
		g.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);
		
		g.setColor(bar.getPrimary());
		g.fillRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
	}

	public void componentResized(ComponentEvent e) {
		this.setPreferredSize(new Dimension(this.getHeight(), this.getHeight()));
	}

	public void componentMoved(ComponentEvent e) {
		this.setPreferredSize(new Dimension(this.getHeight(), this.getHeight()));
		
	}

	public void componentShown(ComponentEvent e) {
		this.setPreferredSize(new Dimension(this.getHeight(), this.getHeight()));
		
	}

	public void componentHidden(ComponentEvent e) {
		
	}

	public void mouseClicked(MouseEvent arg0) {
		bar.swap();
		revalidate();
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
