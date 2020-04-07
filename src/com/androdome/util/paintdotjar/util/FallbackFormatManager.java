package com.androdome.util.paintdotjar.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;

public final class FallbackFormatManager extends FileFormatManager {

	FallbackFormatManager()
	{
		super("png");
	}
	@Override
	public boolean accept(File f) {
		return true;
	}

	@Override
	public String getDescription() {
		return "All Files | *.*";
	}

	@Override
	public CanvasContainer loadCanvas(File file, MainInterfaceAbstractor mia) {
		try {
			return mia.createCanvas(ImageIO.read(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveCanvas(CanvasContainer cc, File file, MainInterfaceAbstractor mia) {
		try {
			return ImageIO.write(cc.manager.getImage(), "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
