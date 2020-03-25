package com.androdome.util.paintdotjar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Canvas {
	public BufferedImage canvasImage;
	public int canvasOpacity = 255;
	public Canvas(int w, int h)
	{
		canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = canvasImage.getGraphics();
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, w, h);
		//canvasImage.setRGB(w-1, h-1, (new Color(0.0f, 0.0f, 0.0f, 1.0f)).getRGB());
	}
	public void setOpacity(int opacity)
	{
		if(opacity < 0)
			opacity = 0;
		else if(opacity > 255)
			opacity = 255;
		canvasOpacity = opacity;
	}
	public int getOpacity()
	{
		return canvasOpacity;
	}
	public Canvas(BufferedImage img) {
		canvasImage = img;
	}
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public Canvas(Canvas canvas) {
		canvasImage = deepCopy(canvas.canvasImage);
		canvasOpacity = canvas.canvasOpacity;
	}
	public void apply(Canvas canvas)
	{
		System.out.println("Applying!");
		Graphics2D g = (Graphics2D) canvasImage.getGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.canvasOpacity / 255F));
		if(!canvas.sizeEqual(canvasImage.getWidth(), canvasImage.getHeight()))
		{
			System.out.println("Not equal!!");
			canvas.resizeImage(canvasImage.getWidth(), canvasImage.getHeight(), BufferedImage.SCALE_DEFAULT);
		}
		g.drawImage(canvas.getImage(), 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	public void plotPixel(int x, int y, Color c)
	{
		Graphics g = canvasImage.getGraphics();
		g.drawLine(x, y, x, y);
	}
	public Graphics getGraphics()
	{
		return canvasImage.getGraphics();
	}

	public boolean sizeEqual(int width, int height) {
		return canvasImage.getHeight() == height && canvasImage.getWidth() == width;
	}
	
	public void resizeImage(int width, int height, int mode)
	{
		canvasImage = (BufferedImage) canvasImage.getScaledInstance(width, height, mode);	
	}

	public BufferedImage getImage() {
		return canvasImage;
	}
}
