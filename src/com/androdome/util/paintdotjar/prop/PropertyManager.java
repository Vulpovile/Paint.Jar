package com.androdome.util.paintdotjar.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.androdome.util.paintdotjar.util.PaintUtils;

public final class PropertyManager {
	private static File propFile = new File(PaintUtils.getJarDir(), "pdj.prop");
	private static Properties prop = new Properties();
	public static void saveProperties() throws IOException
	{
		if(!propFile.exists())
			propFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(propFile);
		prop.store(fos, "Paint.JAR Properties");
		fos.close();
	}
	public static void loadProperties() throws IOException
	{
		if(propFile.exists())
		{
			FileInputStream fis = new FileInputStream(propFile);
			prop.load(fis);
			fis.close();
		}
		saveProperties();
	}
	public static String getProperty(String key)
	{
		return prop.getProperty(key);
	}
	public static String getProperty(String key, String value)
	{
		String ret = prop.getProperty(key, value);
		prop.setProperty(key, ret);
		return ret;
	}
	public static String setProperty(String key, String value)
	{
		String ret = String.valueOf(prop.setProperty(key, value));
		try {
			saveProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
