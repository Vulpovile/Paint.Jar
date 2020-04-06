package com.androdome.util.paintdotjar.util.defaultmanagers;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.util.FileFormatManager;

public class PNGManager extends FileFormatManager {

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		return f.isDirectory() || f.getName().endsWith(".png");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Portable Network Graphcis | *.png";
	}

	@Override
	public CanvasContainer loadCanvas(File f, MainInterfaceAbstractor mia) {
		// TODO Auto-generated method stub
		try {
			return mia.createCanvas(ImageIO.read(f));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean saveCanvas(CanvasContainer cc, File f, MainInterfaceAbstractor mia) {
		// TODO Auto-generated method stub
		return false;
	}

}
