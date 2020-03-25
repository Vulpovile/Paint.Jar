package com.androdome.util.paintdotjar.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ColorWheel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	int rad = 0;
	
	public static int RGBtoHEX(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            if (hex.length() == 5)
                hex = "0" + hex;
            if (hex.length() == 4)
                hex = "00" + hex;
            if (hex.length() == 3)
                hex = "000" + hex;
        }
        hex = "#" + hex;
        return Integer.decode(hex);
    }
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(wheel, 0, 0, this.getWidth(), this.getHeight(), this);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawArc(0,0, getWidth()-1, getHeight()-1, 0, 360);


		
		
	}
	
	public ColorWheel(int rad) {
		this.rad = rad;
        wheel = new BufferedImage(rad, rad, BufferedImage.TYPE_INT_RGB);

        // Center Point (MIDDLE, MIDDLE)
        int centerX = wheel.getWidth() / 2;
        int centerY = wheel.getHeight() / 2;
        int radius = (wheel.getWidth() / 2) * (wheel.getWidth() / 2);

        // Red Source is (RIGHT, MIDDLE)
        int redX = wheel.getWidth();
        int redY = wheel.getHeight() / 2;
        int redRad = wheel.getWidth() * wheel.getWidth();

        // Green Source is (LEFT, MIDDLE)
        int greenX = 0;
        int greenY = wheel.getHeight() / 2;
        int greenRad = wheel.getWidth() * wheel.getWidth();

        // Blue Source is (MIDDLE, BOTTOM)
        int blueX = wheel.getWidth() / 2;
        int blueY = wheel.getHeight();
        int blueRad = wheel.getWidth() * wheel.getWidth();

        for (int i = 0; i < wheel.getWidth(); i++) {
            for (int j = 0; j < wheel.getHeight(); j++) {
                int a = i - centerX;
                int b = j - centerY;

                int distance = a * a + b * b;
                if (distance < radius) {
                    int rdx = i - redX;
                    int rdy = j - redY;
                    int redDist = (rdx * rdx + rdy * rdy);
                    int redVal = (int) (255 - ((redDist / (float) redRad) * 256));

                    int gdx = i - greenX;
                    int gdy = j - greenY;
                    int greenDist = (gdx * gdx + gdy * gdy);
                    int greenVal = (int) (255 - ((greenDist / (float) greenRad) * 256));

                    int bdx = i - blueX;
                    int bdy = j - blueY;
                    int blueDist = (bdx * bdx + bdy * bdy);
                    int blueVal = (int) (255 - ((blueDist / (float) blueRad) * 256));

                    Color c = new Color(redVal, greenVal, blueVal);

                    float hsbVals[] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

                    Color highlight = Color.getHSBColor(hsbVals[0], hsbVals[1], 1);

                    wheel.setRGB(i, j, RGBtoHEX(highlight));
                } else {
                    wheel.setRGB(i, j, RGBtoHEX(this.getBackground()));
                }
            }
        }
	}
	BufferedImage wheel;

	public Color getColorAtPoint(int x, int y) {
		// TODO Auto-generated method stub
		if(x > 0 && y > 0 && x < wheel.getWidth() && y < wheel.getHeight())
		{
			Color wheelColor = new Color(wheel.getRGB(x, y));
			if(wheelColor.getRed() != getBackground().getRed())
			return wheelColor;
			else return null;
		}
		else return null;
	}

}
