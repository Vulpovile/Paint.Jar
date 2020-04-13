package com.androdome.util.paintdotjar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

public class Canvas{
	
	public static byte[] serialize(Canvas canvas) throws IOException
	{
		if(canvas == null)
			return null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(bos);
		DataOutputStream dos = new DataOutputStream(gz);
		dos.writeUTF(canvas.canvasName);
		dos.writeShort(canvas.canvasOpacity);
		ImageIO.write(canvas.canvasImage, "png", dos);
		dos.close();
		gz.close();
		bos.close();
		return bos.toByteArray();
	}
	
	public static Canvas unserialize(byte[] serialized) throws IOException
	{
		if(serialized == null)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
		GZIPInputStream gz = new GZIPInputStream(bis);
		DataInputStream dis = new DataInputStream(gz);
		String name = dis.readUTF();
		short opacity = dis.readShort();
		BufferedImage image = ImageIO.read(dis);
		dis.close();
		gz.close();
		bis.close();
		Canvas canvas = new Canvas(image);
		canvas.canvasOpacity = opacity;
		canvas.canvasName = name;
		return canvas;
	}
	
	private BufferedImage canvasImage;
	private short canvasOpacity = 255;
	private String canvasName = "Background";
	private boolean isVisible = true;;
	public Canvas(int w, int h)
	{
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
		canvasImage = config.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics g = canvasImage.getGraphics();
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, w, h);
		//canvasImage.setRGB(w-1, h-1, (new Color(0.0f, 0.0f, 0.0f, 1.0f)).getRGB());
	}
	public void setOpacity(short opacity)
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
	private static BufferedImage deepCopy(BufferedImage bi) {
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
	public String getName() {
		// TODO Auto-generated method stub
		return canvasName;
	}
	public void setName(String name) {
		// TODO Auto-generated method stub
		canvasName = name;
	}

	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return isVisible ;
	}
}
