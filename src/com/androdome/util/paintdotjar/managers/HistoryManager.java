package com.androdome.util.paintdotjar.managers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.ui.CanvasContainer;

public class HistoryManager {
	
	
	public static final byte[] serialize(HistoryManager hm) throws IOException
	{
		if(hm == null)
			return null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(bos);
		ObjectOutputStream dos = new ObjectOutputStream(gz);
		dos.writeObject(hm.historyList);
		dos.close();
		gz.close();
		bos.close();
		return bos.toByteArray();
	}
	@SuppressWarnings("unchecked")
	public static final HistoryManager deserialize(byte[] bytes, CanvasContainer cc) throws IOException, ClassNotFoundException
	{
		if(bytes == null || cc == null)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		GZIPInputStream gz = new GZIPInputStream(bis);
		ObjectInputStream dis = new ObjectInputStream(gz);
		HistoryManager hm = new HistoryManager(cc);
		hm.historyList = (List<HistoryEntry>) dis.readObject();
		dis.close();
		gz.close();
		bis.close();
		return hm;
	}
	
	private final CanvasContainer canvasContainer;
	private List<HistoryEntry> historyList = new ArrayList<HistoryEntry>();
	int histIndex = -1;
	List<Canvas> currentEntry = null;
	public static enum Operations
	{
		DELETE_CANVAS,
		CREATE_CANVAS,
		MERGE_CANVAS,
		CHANGE_CANVAS,
		SWAP_CANVAS,
	}
	public HistoryManager(CanvasContainer cc)
	{
		canvasContainer = cc;
	}
	public void pushChange(HistoryEntry entry) {
		if(entry == null)
			return;
		// TODO Auto-generated method stub
		
		if(histIndex > -1)
		{
			currentEntry = null;
			while(histIndex > -1)
			{
				historyList.remove(historyList.size()-1);
				histIndex--;
			}
		}
		
		historyList.add(entry);
		if(historyList.size() > 50)
		{
			historyList.remove(0);
		}	
		canvasContainer.setChanged(true);
		canvasContainer.getPluginManager().getMainInterface().setHistory();
	}
	public Collection<HistoryEntry> getHistory() {
		// TODO Auto-generated method stub
		return historyList;
	}
	
	public void undo()
	{
		System.out.println("Undoing!!!");
		if(histIndex < historyList.size()-1)
		{
			histIndex++;
			if(currentEntry == null)
			{
				currentEntry = new ArrayList<Canvas>();
				for(Canvas c : canvasContainer.getLayers())
				{
					currentEntry.add(c);
				}
			}
			HistoryEntry he = historyList.get(historyList.size()-1-histIndex);
			switch(he.operation)
			{
				case CHANGE_CANVAS:
					canvasContainer.getLayers().set(he.index, he.getCanvas());
					break;
				case DELETE_CANVAS:
					canvasContainer.getLayers().add(he.index, he.getCanvas());
					break;
				case CREATE_CANVAS:
					canvasContainer.getLayers().remove(he.index);
					break;
				case MERGE_CANVAS:
					canvasContainer.getLayers().set(he.index, he.getCanvas());
					canvasContainer.getLayers().add(he.index2, he.getCanvas2());
					break;
				case SWAP_CANVAS:
					canvasContainer.getLayers().set(he.index, he.getCanvas());
					canvasContainer.getLayers().set(he.index2, he.getCanvas2());
					break;
				default:
					break;
					
			}
			canvasContainer.repaint();
		}
	}
	public void redo()
	{
		if(histIndex > -1)
		{
			histIndex--;
			if(histIndex == -1)
			{
				canvasContainer.getLayers().clear();
				for(Canvas c : currentEntry)
				{
					canvasContainer.getLayers().add(c);
				}
				currentEntry.clear();
				currentEntry = null;
			}
			else
			{
				HistoryEntry he = historyList.get(historyList.size()-1-histIndex);
				switch(he.operation)
				{
					case CHANGE_CANVAS:
						canvasContainer.getLayers().set(he.index, he.getCanvas());
						break;
					case DELETE_CANVAS:
						canvasContainer.getLayers().remove(he.index);
						break;
					case CREATE_CANVAS:
						canvasContainer.getLayers().add(he.index, he.getCanvas());
						break;
					case MERGE_CANVAS:
						Canvas canvas = he.getCanvas();
						canvas.apply(he.getCanvas2());
						canvasContainer.getLayers().set(he.index, canvas);
						canvasContainer.getLayers().remove(he.index2);
						break;
					case SWAP_CANVAS:
						canvasContainer.getLayers().set(he.index, he.getCanvas2());
						canvasContainer.getLayers().set(he.index2, he.getCanvas());
						break;
					default:
						break;
				}
			}
			canvasContainer.repaint();
		}
	}
	public int getHistoryIndex() {
		return histIndex;
	}
}
