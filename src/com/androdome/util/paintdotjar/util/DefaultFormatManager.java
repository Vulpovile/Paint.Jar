package com.androdome.util.paintdotjar.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.util.FileFormatManager;


public final class DefaultFormatManager extends FileFormatManager {

	public final String ext;
	public final String desc;
	public static final HashMap<String, String> registeredDesc = new HashMap<String, String>();
	static
	{
		registeredDesc.put("jpg", "JPEG 8.3 Image");
		registeredDesc.put("jpeg", "JPEG Image");
		registeredDesc.put("png", "Portable Network Graphics Image");
		registeredDesc.put("gif", "Graphics Interchange Format Image");
		registeredDesc.put("bmp", "Bitmap Image");
		registeredDesc.put("wbmp", "Wireless Application Protocol Bitmap Image");
		registeredDesc.put("ico", "Icon Image");
	}
	
	public DefaultFormatManager(String ext, String desc)
	{
		super(ext);
		this.ext = ext;
		this.desc = desc;
	}
	
	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		return f.isDirectory() || f.getName().toLowerCase().endsWith(ext);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return desc + " | *." + ext;
	}

	@Override
	public CanvasContainer loadCanvas(File file, MainInterfaceAbstractor mia) {
		// TODO Auto-generated method stub
		try {
			BufferedImage img = ImageIO.read(file);
			if(img == null)
				return null;
			return mia.createCanvas(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveCanvas(CanvasContainer cc, File file,
			MainInterfaceAbstractor mia) {
		try {
			ImageIO.write(cc.manager.getImage(), ext, file);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
