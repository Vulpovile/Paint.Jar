package com.androdome.util.paintdotjar.util;

import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;

import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;


public class PaintUtils {
	public final static String DEFAULT_EXT = "png";
	public final static FallbackFormatManager FALLBACK = new FallbackFormatManager();
	public final static File getJarDir()
	{
		try
		{
			File file = new File(PaintUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			return file.getParentFile();
		}
		catch (URISyntaxException e)
		{
			return new File("");
		}
	}
	public static TreeMap<String, FileFormatManager> registeredHandlers = new TreeMap<String, FileFormatManager>(
			new Comparator<String>() {
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					return o1.compareTo(o2);
				}
				});
	public static final void setDefault() {
		for(String key : DefaultFormatManager.registeredDesc.keySet())
		{
			new DefaultFormatManager(key, DefaultFormatManager.registeredDesc.get(key)).registerHandler(key, false);
		}
		new PDJManager().registerHandler("pdj", false);
	}
	public static final FileFormatManager getSupportedImagesManager()
	{
		return new FileFormatManager("."){

			@Override
			public boolean accept(File f) {
				for(FileFormatManager ffm : registeredHandlers.values())
				{
					if(ffm.accept(f))
						return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "All Supported Image Files | *.?";
			}

			@Override
			public CanvasContainer loadCanvas(File file,
					MainInterfaceAbstractor mia) {
				for(FileFormatManager ffm : registeredHandlers.values())
				{
					if(ffm.accept(file))
					{
						return ffm.loadCanvas(file, mia);
					}
				}
				return null;
			}

			@Override
			public boolean saveCanvas(CanvasContainer cc, File file,
					MainInterfaceAbstractor mia) {
				for(FileFormatManager ffm : registeredHandlers.values())
				{
					if(ffm.accept(file))
					{
						return ffm.saveCanvas(cc, file, mia);
					}
				}
				return false;
			}
			
		};
	}
	public static void setAppIcons(JFrame frame, List<Image> images) {

	    try {
	        Class<?> [] types = {java.util.List.class};
	        Method method = Class.forName("java.awt.Window").getDeclaredMethod("setIconImages", types);

	        Object [] parameters = {images};
	        method.invoke(frame, parameters);
	    } catch (Exception e) {
	        frame.setIconImage(images.get(0));
	    }       
	}
}
