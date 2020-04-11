package com.androdome.util.paintdotjar.managers;

import java.util.ArrayList;
import java.util.List;

import com.androdome.util.paintdotjar.devel.annotation.Preliminary;
import com.androdome.util.paintdotjar.ui.CanvasContainer;

public class HistoryManager {
	@Preliminary
	private final CanvasContainer canvasContainer;
	private List<HistoryEntry> historyList = new ArrayList<HistoryEntry>();
	public static enum Operations
	{
		DELETE_CANVAS,
		CREATE_CANVAS,
		APPLY_CANVAS,
		CHANGE_CANVAS,
		SWAP_CANVASES,
	}
	@Preliminary
	public HistoryManager(CanvasContainer cc)
	{
		canvasContainer = cc;
	}
	public void pushChange(HistoryEntry entry) {
		if(entry == null)
			return;
		// TODO Auto-generated method stub
		historyList.add(entry);
		if(historyList.size() > 50)
		{
			historyList.remove(0);
		}	
		canvasContainer.setChanged(true);
	}
}
