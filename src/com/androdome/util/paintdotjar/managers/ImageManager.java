package com.androdome.util.paintdotjar.managers;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {
	private static HashMap<String, Image> __StaticImageMap = new HashMap<String, Image>();
	public static Image getImageResource(String resource)
	{
		Image res = __StaticImageMap.get(resource);
		if(res != null)
			return res;
		else
		{
			try
			{
				res = ImageIO.read(ImageManager.class.getResource("/"+resource));
				if(res != null)
				{
					__StaticImageMap.put(resource, res);
				}
				return res;
			}
			catch (IOException e)
			{

				e.printStackTrace();
				return null;
			}
		}
	}
	public static ImageIcon getImageIconResource(String resource)
	{
		Image img = getImageResource(resource);
		if(img == null)
			return null;
		return new ImageIcon(img);
	}
	public static Image dropImageResource(String resource)
	{
		return __StaticImageMap.remove(resource);
	}
}
