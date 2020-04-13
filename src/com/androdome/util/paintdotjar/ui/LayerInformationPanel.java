package com.androdome.util.paintdotjar.ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import com.androdome.util.paintdotjar.Canvas;

import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;

import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LayerInformationPanel extends JPanel implements MouseListener, KeyListener, FocusListener {
	public JTextField textField = new JTextField();
	boolean removed = true;
	JLabel lblLayername;
	Canvas thisCanvas;
	public LayerInformationPanel(final CanvasContainer cc, int layerIndex, Canvas canvas) {
		thisCanvas = canvas;
		setLayout(new BorderLayout(0, 0));
		
		lblLayername = new JLabel(canvas.getName());
		lblLayername.addMouseListener(this);
		textField.addKeyListener(this);
		textField.addFocusListener(this);
		this.addFocusListener(this);
		add(lblLayername, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		add(panel, BorderLayout.WEST);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel panel_2 = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(thisCanvas.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setPreferredSize(new Dimension(60, 60));
		panel_2.setOpaque(false);
		panel.add(panel_2);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long timeout = 0;
	public void mouseClicked(MouseEvent e) {
		for(MouseListener l : this.getMouseListeners())
		{
			l.mouseClicked(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
		}
		if(timeout > System.currentTimeMillis()-500 && removed)
		{
			removed = false;
			remove(lblLayername);
			textField.setText(thisCanvas.getName());
			add(textField);
			repaint();
			revalidate();
		}
		else
		{
			timeout = System.currentTimeMillis();
		}
		
	}

	public void mousePressed(MouseEvent e) {
		for(MouseListener l : this.getMouseListeners())
		{
			l.mousePressed(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
		}
	}

	public void mouseReleased(MouseEvent e) {
		for(MouseListener l : this.getMouseListeners())
		{
			l.mouseReleased(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
		}
	}

	public void mouseEntered(MouseEvent e) {
		for(MouseListener l : this.getMouseListeners())
		{
			l.mouseEntered(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
		}
	}

	public void mouseExited(MouseEvent e) {
		for(MouseListener l : this.getMouseListeners())
		{
			l.mouseExited(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !removed)
		{
			removed = true;
			thisCanvas.setName(textField.getText());
			remove(textField);
			lblLayername.setText(thisCanvas.getName());
			add(lblLayername);
			repaint();
			revalidate();
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent e) {
		if(!removed)
		{
			removed = true;
			thisCanvas.setName(textField.getText());
			remove(textField);
			lblLayername.setText(thisCanvas.getName());
			add(lblLayername);
			repaint();
			revalidate();
		}
	}

}
