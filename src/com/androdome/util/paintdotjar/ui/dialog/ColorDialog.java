package com.androdome.util.paintdotjar.ui.dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.androdome.util.paintdotjar.ui.ColorBar;
import com.androdome.util.paintdotjar.ui.ColorWheel;

public class ColorDialog extends JDialog implements ActionListener, ChangeListener, KeyListener, FocusListener, MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtHex;
	private JTextField txtA = new JTextField();
	private JTextField txtR;
	private JTextField txtG;
	private JTextField txtB;
	private JTextField txtDec;
	JSlider sliderRed = new JSlider();
	JSlider sliderGreen = new JSlider();
	JSlider sliderBlue = new JSlider();
	JSlider sliderAlpha = new JSlider();
	JSlider sliderH = new JSlider();
	JSlider sliderS = new JSlider();		
	JSlider sliderV = new JSlider();
	ColorViewPanel panel_1 = new ColorViewPanel();
	ColorBar bar;
	JButton btnPriSec;
	boolean primary = true;

	ColorWheel colorWheel = new ColorWheel(169);
	private JPanel panel;
	private JPanel panel_3;
	private JTextField txtH;
	private JTextField txtS;
	private JTextField txtV;
	private JLabel lblHexRGB;
	private JLabel lblDec_1;
	private JTextField txtRGBDec;
	private JTextField txtRGBHex;
	
	//Really inefficient dialog, TODO spend time improving
	
	public ColorDialog(ColorBar colorBar) {
		
		setResizable(false);
		bar = colorBar;
		setTitle("Colors");
		setBounds(100, 100, 498, 412);
		getContentPane().setLayout(null);
		/*{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 239, 480, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.add(okButton);
		}*/
		
		colorWheel.setBounds(10, 43, 169, 169);
		getContentPane().add(colorWheel);
		sliderRed.setMaximum(255);
		sliderGreen.setMaximum(255);
		sliderAlpha.setMaximum(255);
		
		btnPriSec = new JButton("Primary");
		btnPriSec.setBounds(10, 9, 169, 25);
		getContentPane().add(btnPriSec);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "RGB", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(191, 122, 294, 137);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblRed = new JLabel("Red");
		lblRed.setBounds(14, 21, 40, 15);
		panel.add(lblRed);
		
		JLabel lblGreen = new JLabel("Green");
		lblGreen.setBounds(14, 48, 52, 15);
		panel.add(lblGreen);
		
		JLabel lblBlue = new JLabel("Blue");
		lblBlue.setBounds(14, 75, 40, 15);
		panel.add(lblBlue);
		sliderBlue.setMaximum(255);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "HSV", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(191, 256, 294, 110);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblH = new JLabel("H");
		lblH.setBounds(14, 21, 40, 15);
		panel_2.add(lblH);
		
		JLabel lblS = new JLabel("S");
		lblS.setBounds(14, 48, 52, 15);
		panel_2.add(lblS);
		
		JLabel lblV = new JLabel("V");
		lblV.setBounds(14, 75, 52, 15);
		panel_2.add(lblV);
		sliderH.setMaximum(359);
		

		sliderH.setBounds(66, 18, 168, 20);
		panel_2.add(sliderH);
		

		sliderS.setBounds(66, 45, 168, 20);
		panel_2.add(sliderS);
		

		sliderV.setBounds(66, 72, 168, 20);
		panel_2.add(sliderV);
		
		txtH = new JTextField();
		txtH.setColumns(10);
		txtH.setBounds(246, 14, 33, 23);
		panel_2.add(txtH);
		
		txtS = new JTextField();
		txtS.setColumns(10);
		txtS.setBounds(246, 41, 33, 23);
		panel_2.add(txtS);
		
		txtV = new JTextField();
		txtV.setColumns(10);
		txtV.setBounds(246, 68, 33, 23);
		panel_2.add(txtV);
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "Color", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panel_3.setBounds(191, 13, 294, 110);
		getContentPane().add(panel_3);
		
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				
				
				
		sliderBlue.setBounds(66, 72, 168, 20);
		panel.add(sliderBlue);
		sliderGreen.setBounds(66, 45, 168, 20);
		panel.add(sliderGreen);
		sliderRed.setBounds(66, 18, 168, 20);
		panel.add(sliderRed);
		txtR = new JTextField();
		txtR.setBounds(246, 16, 33, 23);
		panel.add(txtR);
		txtR.setColumns(10);
		txtG = new JTextField();
		txtG.setBounds(246, 43, 33, 23);
		panel.add(txtG);
		txtG.setColumns(10);
			
		txtB = new JTextField();
		txtB.setBounds(246, 70, 33, 23);
		panel.add(txtB);
		txtB.setColumns(10);
		
		lblHexRGB = new JLabel("Hex");
		lblHexRGB.setBounds(14, 102, 40, 15);
		panel.add(lblHexRGB);
		
		lblDec_1 = new JLabel("Dec");
		lblDec_1.setBounds(159, 102, 40, 15);
		panel.add(lblDec_1);
		
		txtRGBDec = new JTextField();
		txtRGBDec.setText("0");
		txtRGBDec.setColumns(10);
		txtRGBDec.setBounds(195, 95, 84, 23);
		panel.add(txtRGBDec);
		
		txtRGBHex = new JTextField();
		txtRGBHex.setText("0");
		txtRGBHex.setColumns(10);
		txtRGBHex.setBounds(66, 95, 84, 23);
		panel.add(txtRGBHex);
		txtA.setBounds(246, 70, 33, 23);
		panel_3.add(txtA);
		txtA.setColumns(10);
		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(14, 75, 40, 15);
		panel_3.add(lblAlpha);
		sliderAlpha.setBounds(66, 74, 168, 20);
		panel_3.add(sliderAlpha);
		
		JLabel lblHex = new JLabel("Hex");
		lblHex.setBounds(14, 21, 40, 15);
		panel_3.add(lblHex);
		txtHex = new JTextField();
		txtHex.setBounds(66, 16, 84, 23);
		panel_3.add(txtHex);
		txtHex.setColumns(10);
		JLabel lblDec = new JLabel("Dec");
		lblDec.setBounds(14, 48, 40, 15);
		panel_3.add(lblDec);
		txtDec = new JTextField();
		txtDec.setBounds(66, 43, 84, 23);
		panel_3.add(txtDec);
		txtDec.setColumns(10);
		panel_1.setBounds(162, 16, 48, 48);
		panel_3.add(panel_1);

		
				
		setColorParam(bar.getPrimary());
		addListeners();
	}

	
	//WINDOW BUILDER STOP PUTTING SHIT IN THESE FUNCTIONS
		public void addListeners()
		{
			txtHex.addKeyListener(this);
			txtDec.addKeyListener(this);
			txtR.addKeyListener(this);
			txtG.addKeyListener(this);
			txtA.addKeyListener(this);
			txtB.addKeyListener(this);
			txtRGBHex.addKeyListener(this);
			txtRGBDec.addKeyListener(this);
			
			
			sliderRed.addMouseListener(this);
			sliderGreen.addMouseListener(this);
			sliderBlue.addMouseListener(this);
			

			txtH.addKeyListener(this);
			txtS.addKeyListener(this);
			txtV.addKeyListener(this);
			
			sliderH.addMouseListener(this);
			sliderS.addMouseListener(this);
			sliderV.addMouseListener(this);

			sliderAlpha.addMouseListener(this);
			btnPriSec.addActionListener(this);
			colorWheel.addMouseListener(this);
			colorWheel.addMouseMotionListener(this);
			addMouseListener(this);
		}
		
		public void setColorParam(Color color)
		{
			//Set ARGB
			txtA.setText(color.getAlpha() + "");
			txtR.setText(color.getRed() + "");
			txtG.setText(color.getGreen() + "");
			txtB.setText(color.getBlue() + "");
			String hex = String.format("%02x%02x%02x%02x", color.getAlpha(),color.getRed(), color.getGreen(), color.getBlue()).toUpperCase();  
			sliderRed.setValue(color.getRed());
			sliderGreen.setValue(color.getGreen());
			sliderBlue.setValue(color.getBlue());
			txtRGBHex.setText(String.format("%02x%02x%02x",color.getRed(), color.getGreen(), color.getBlue()).toUpperCase());
			txtRGBDec.setText(new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()+16777216 +"");
			//set HSV
			float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
			int h = (int) (hsv[0]*360);
			int s = (int) (hsv[1]*100);
			int v = (int) (hsv[2]*100);
			txtH.setText(h + "");
			txtS.setText(s + "");
			txtV.setText(v + "");
			sliderH.setValue(h);
			sliderS.setValue(s);
			sliderV.setValue(v);
			
			
			panel_1.setColor(color);
			txtHex.setText(hex);
			txtDec.setText(color.getRGB() + "");
			
			sliderAlpha.setValue(color.getAlpha());
			panel_1.repaint();
		}
	
	public static Color hex2Rgb(String colorStr) {
		while (colorStr.length() < 8)
		{
			if(colorStr.length() >= 6)
				colorStr = "F" + colorStr;
			else colorStr = "0" + colorStr;
		}
	    return new Color(
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ),
	            Integer.valueOf( colorStr.substring( 6, 8 ), 16 ),
	            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ));
	}
	
	
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(arg0.getSource() == txtHex)
			{
				String hex = txtHex.getText().trim();
				try
				{
					Color col = hex2Rgb(hex);
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtRGBHex)
			{
				String hex = txtRGBHex.getText().trim();
				try
				{
					int color = Integer.parseInt(hex, 16);
					Color colTmp = new Color(color);
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(colTmp.getRed(), colTmp.getGreen(), colTmp.getBlue(), changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtDec)
			{
				try
				{
					int color = Integer.parseInt(txtDec.getText().trim());
					Color col = new Color(color);
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtRGBDec)
			{
				try
				{
					int color = Integer.parseInt(txtRGBDec.getText().trim());
					if(color > -1)
						color = color - 16777216;
					Color colTmp = new Color(color);
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(colTmp.getRed(), colTmp.getGreen(), colTmp.getBlue(), changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtA)
			{
				try
				{
					int color = Integer.parseInt(txtA.getText().trim());
					if(color < 0)
						color = 0;
					else if(color > 255)
						color = 255;
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(changing.getRed(),changing.getGreen(),changing.getBlue(),color);
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtR)
			{
				try
				{
					int color = Integer.parseInt(txtR.getText().trim());
					if(color < 0)
						color = 0;
					else if(color > 255)
						color = 255;
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(color,changing.getGreen(),changing.getBlue(),changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtG)
			{
				try
				{
					int color = Integer.parseInt(txtG.getText().trim());
					if(color < 0)
						color = 0;
					else if(color > 255)
						color = 255;
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(changing.getRed(), color,changing.getBlue(),changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtB)
			{
				try
				{
					int color = Integer.parseInt(txtB.getText().trim());
					if(color < 0)
						color = 0;
					else if(color > 255)
						color = 255;
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color col = new Color(changing.getRed(),changing.getGreen(),color,changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			else if(arg0.getSource() == txtH || arg0.getSource() == txtS || arg0.getSource() == txtV)
			{
				try
				{
					int h = Integer.parseInt(txtH.getText().trim());
					int s = Integer.parseInt(txtS.getText().trim());
					int v = Integer.parseInt(txtV.getText().trim());
					if(s < 0)
						s = 0;
					else if(s > 100)
						s = 100;
					if(v < 0)
						v = 0;
					else if(v > 100)
						v = 100;
					Color changing = bar.getSecondary();
					if(primary)
						changing = bar.getPrimary();
					Color tmpCl = new Color(Color.HSBtoRGB(h/360F, s/100, v/100F));
					Color col = new Color(tmpCl.getRed(), tmpCl.getGreen(), tmpCl.getBlue(), changing.getAlpha());
					if(primary)
						bar.setPrimary(col);
					else bar.setSecondary(col);
				}
				catch(NumberFormatException ex)
				{}
			}
			if(primary)
				setColorParam(bar.getPrimary());
			else setColorParam(bar.getSecondary());
		}
	}

	public void stateChanged(ChangeEvent arg0) {
		// TODO make this useful again
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnPriSec)
		{
			primary = !primary;
			if(primary)
			{
				setColorParam(bar.getPrimary());
				btnPriSec.setText("Primary");
			}
			else
			{
				setColorParam(bar.getSecondary());
				btnPriSec.setText("Secondary");
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == colorWheel)
		{
			int x = arg0.getX();
			int y = arg0.getY();
			Color c = colorWheel.getColorAtPoint(x,y);
			Color changing = bar.getSecondary();
			if(primary)
				changing = bar.getPrimary();
			Color col = new Color(c.getRed(), c.getGreen(), c.getBlue(), changing.getAlpha());
			if(primary)
				bar.setPrimary(col);
			else bar.setSecondary(col);
		}
		if(primary)
			setColorParam(bar.getPrimary());
		else setColorParam(bar.getSecondary());
	}

	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getSource() == sliderRed || arg0.getSource() == sliderGreen || arg0.getSource() == sliderBlue || arg0.getSource() == sliderAlpha)
		{
			if(primary)
			{
				bar.setPrimary(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue(), sliderAlpha.getValue()));
				setColorParam(bar.getPrimary());
			}else{
				bar.setSecondary(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue(), sliderAlpha.getValue()));
				setColorParam(bar.getSecondary());
			}
			bar.repaint();
		}
		else if(arg0.getSource() == sliderH || arg0.getSource() == sliderS || arg0.getSource() == sliderV)
		{
			Color col = bar.getSecondary();
			if(primary)
				col = bar.getPrimary();
			int rgb = Color.HSBtoRGB(sliderH.getValue()/360F, sliderS.getValue()/100F, sliderV.getValue()/100F);
			Color nCol = new Color(rgb);
			Color bCol = new Color(nCol.getRed(), nCol.getGreen(), nCol.getBlue(), col.getAlpha());
			if(primary)
			{
				bar.setPrimary(bCol);
				setColorParam(bar.getPrimary());
			}else{
				bar.setSecondary(bCol);
				setColorParam(bar.getSecondary());
			}
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		if(arg0.getSource() == colorWheel)
		{
			int x = arg0.getX();
			int y = arg0.getY();
			Color c = colorWheel.getColorAtPoint(x,y);
			if(c != null)
			{
				Color changing = bar.getSecondary();
				if(primary)
					changing = bar.getPrimary();
				Color col = new Color(c.getRed(), c.getGreen(), c.getBlue(), changing.getAlpha());
				if(primary)
					bar.setPrimary(col);
				else bar.setSecondary(col);
			}
		}
		recolor();
	}

	public void recolor()
	{
		if(primary)
			this.setColorParam(bar.getPrimary());
		else this.setColorParam(bar.getSecondary());
	}

	public void mouseMoved(MouseEvent e) {
		
	}


	public void focusGained(FocusEvent e) {

	}


	public void focusLost(FocusEvent e) {
		
	}


	public void keyTyped(KeyEvent e) {
		
	}


	public void keyReleased(KeyEvent e) {
		
	}


	public void mousePressed(MouseEvent e) {
		
	}


	public void mouseEntered(MouseEvent e) {
	

	}


	public void mouseExited(MouseEvent e) {


	}
}

class ColorViewPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color color = Color.BLACK;
	public void setColor(Color color)
	{
		this.color = color;
	}
	public void paintComponent(Graphics g)
	{
		  int row;
		  int col;
		  int x;
		  int y;
		  int scale = getWidth()/10;
		  for ( row = 0;  row < 10 + getWidth()%10;  row++ )
		  {
		     for ( col = 0;  col < 10 + getWidth()%10;  col++)
		     {
		        x = col * scale;
		        y = row * scale;
		         if ( (row % 2) == (col % 2) )
		           g.setColor(Color.WHITE);
		        else
		           g.setColor(Color.LIGHT_GRAY);

		        g.fillRect(x, y, scale, scale);
		     }
		  }
		  g.setColor(color);
		  g.fillRect(0, 0, getWidth(), getHeight());
	}
}
