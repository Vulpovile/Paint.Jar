package com.androdome.util.paintdotjar;

import java.awt.image.BufferedImage;

import com.androdome.util.paintdotjar.ui.CanvasContainer;

public final class MainInterfaceAbstractor {
	MainInterface mf;
	MainInterfaceAbstractor(MainInterface mf)
	{
		this.mf = mf;
	}
	/**
	 * Creates a blank {@link CanvasContainer} of default size
	 * @return {@link CanvasContainer}
	 * @deprecated
	 */
	public CanvasContainer createCanvas()
	{
		return new CanvasContainer(mf.getPluginManager(), mf.getColorBar());
	}
	/**
	 * Creates a blank {@link CanvasContainer} with the defined width and height
	 * @param  width   Width of the new canvas
	 * @param  height  Height of the new canvas
	 * @return {@link CanvasContainer}
	 */
	public CanvasContainer createCanvas(int width, int height)
	{
		return new CanvasContainer(width, height, mf.getPluginManager(), mf.getColorBar());
	}
	/**
	 * Creates a {@link CanvasContainer} with a defined image
	 * @param  image Image to be used for the canvas
	 * @return {@link CanvasContainer}
	 */
	public CanvasContainer createCanvas(BufferedImage image)
	{
		return new CanvasContainer(image, mf.getPluginManager(), mf.getColorBar());
	}
	
	public boolean showSaveDialog(CanvasContainer cc)
	{
		return mf.showSaveDialog(cc);
	}
	public boolean saveNoDialog(CanvasContainer cc)
	{
		return mf.saveNoDialog(cc);
	}
	public void showOpenDialog() {
		CanvasContainer cc = mf.showOpenDialog();
		if(cc != null)
			mf.addOpenCanvas(cc, true);
	}
}
