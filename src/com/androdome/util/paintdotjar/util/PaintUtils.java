package com.androdome.util.paintdotjar.util;

import java.util.HashMap;


public class PaintUtils {
	public final static String DEFAULT_EXT = "png";
	public final static FallbackFormatManager FALLBACK = new FallbackFormatManager();
	public static HashMap<String, FileFormatManager> registeredHandlers = new HashMap<String, FileFormatManager>();
	public static final void setDefault() {
		for(String key : DefaultManager.registeredDesc.keySet())
		{
			new DefaultManager(key, DefaultManager.registeredDesc.get(key)).registerHandler(key, false);
		}
	}
}
