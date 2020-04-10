package com.androdome.util.paintdotjar.managers;

import java.awt.Image;
import java.io.IOException;
import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.managers.HistoryManager.Operations;

public class HistoryEntry {
	public final Operations operation;
	public final int index;
	private final byte[] canvas;
	private byte[] canvas2;
	public int index2;
	public final String message;
	public Image changeIcon = null;
	public HistoryEntry(Operations operation, int index, Canvas canvas, String message) throws IOException
	{
		this.operation = operation;
		this.index = index;
		this.canvas = Canvas.serialize(canvas);
		this.message = message;
	}
	public HistoryEntry(Operations operation, int index, Canvas canvas, String message, Image icon) throws IOException
	{
		this(operation, index, canvas, message);
		this.changeIcon = icon;
	}
	public HistoryEntry(Operations operation, int index, Canvas canvas, int index2, Canvas canvas2, String message) throws IOException
	{
		this(operation, index, canvas, message);
		this.index2 = index2;
		this.canvas2 = Canvas.serialize(canvas2);
	}
	public HistoryEntry(Operations operation, int index, Canvas canvas, int index2, Canvas canvas2, String message, Image icon) throws IOException
	{
		this(operation, index, canvas, index2, canvas2, message);
		this.changeIcon = icon;
	}
	public Canvas getCanvas()
	{
		try
		{
			return Canvas.unserialize(canvas);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public Canvas getCanvas2()
	{
		try
		{
			return Canvas.unserialize(canvas2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
