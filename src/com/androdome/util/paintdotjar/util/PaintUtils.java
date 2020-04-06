package com.androdome.util.paintdotjar.util;

import java.util.HashMap;

public class PaintUtils {
	public static HashMap<String, FileFormatManager> registeredHandlers = new HashMap<String, FileFormatManager>();
	public static HashMap<String, String> registeredDesc = new HashMap<String, String>();
	static
	{
		registeredDesc.put("jpg", "JPEG Image");
		registeredDesc.put("jpeg", "JPEG2000 Image");
		registeredDesc.put("png", "Portable Network Graphics Image");
		registeredDesc.put("gif", "Graphics Interchange Format Image");
		registeredDesc.put("bmp", "Bitmap Image");
		registeredDesc.put("wbmp", "Wireless Application Protocol Bitmap Image");
		registeredDesc.put("ico", "Icon Image");
	}
}
