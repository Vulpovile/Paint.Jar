package com.androdome.util.paintdotjar.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;

public abstract class FileFormatManager extends FileFilter{
	/**
	 * Return codes for the registerHandler function<br>
	 */
	public static enum RegisterCode{
		/**
		 * Registration was successful, no further actions needed
		 */
		SUCCESS,
		/**
		 * Registration failed because another handler was already registered
		 */
		ALREADY_REGISTERED,
		/**
		 * Registration failed because the extension was not valid
		 */
		INVALID,
		/**
		 * Registration failed for unknown reasons
		 */
		FAIL
	}
	/**
	 * Registers this as a handler for the extension. <b>Extensions DO NOT contain the periods!</b>
	 * @param ext The file extension
	 * @param force Whether the handler should be set regardless of whether it is already registered
	 * @return {@link RegisterCode} 
	 */
	public final RegisterCode registerHandler(String ext, boolean force)
	{
		try{
			ext = ext.toLowerCase();
			if(ext.contains("."))
				return RegisterCode.INVALID;
			if(force)
			{
				PaintUtils.registeredHandlers.put(ext, this);
				return RegisterCode.SUCCESS;
			}
			else
			{
				if(PaintUtils.registeredHandlers.get(ext) != null)
					return RegisterCode.ALREADY_REGISTERED;
				PaintUtils.registeredHandlers.put(ext, this);
				return RegisterCode.SUCCESS;
			}
		} catch (Exception ex)
		{
			return RegisterCode.FAIL; //When would this ever be called?
		}
	}
	@Override
	public abstract boolean accept(File f);

	@Override
	public abstract String getDescription();
	/**
	 * Called when the program needs to load a canvas of this type.<br>
	 * Expected to return a CanvasContainer on success and null on failure.<br>
	 * Not recommended to just return a blank canvas on failure<br>
	 * @param file
	 * @param mia
	 * @return {@link CanvasContainer} or {@link null} on error
	 */
	public abstract CanvasContainer loadCanvas(File file, MainInterfaceAbstractor mia);
	/**
	 * Called when the program needs to save a canvas of this type.<br>
	 * Expected to return true on success and false on failure.<br>
	 * @param cc
	 * @param file
	 * @param mia
	 * @return {@link CanvasContainer} or {@link null} on error
	 */
	public abstract boolean saveCanvas(CanvasContainer cc, File file, MainInterfaceAbstractor mia);

}
