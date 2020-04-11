package com.androdome.util.paintdotjar;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


public class KeyboardShortcutListener implements KeyEventDispatcher {
	MainInterface main;
	private long timeout = 0;
	KeyboardShortcutListener(MainInterface mf)
	{
		System.out.println("wtf");
		main = mf;
	}
	public synchronized boolean dispatchKeyEvent(KeyEvent e) {
		if(timeout  < System.currentTimeMillis()-500)
		{
			
			if(e.isControlDown() && e.isAltDown())
			{
				if(e.getKeyCode() == KeyEvent.VK_S)
				{

					timeout = System.currentTimeMillis();
					main.getAbstractor().showSaveDialog(main.getCanvasContainer());
					return true;
				}
			}
			else if(e.isControlDown())
			{
				if(e.getKeyCode() == KeyEvent.VK_S)
				{

					timeout = System.currentTimeMillis();
					main.getAbstractor().saveNoDialog(main.getCanvasContainer());
					return true;
				}
				if(e.getKeyCode() == KeyEvent.VK_O)
				{

					timeout = System.currentTimeMillis();
					main.getAbstractor().showOpenDialog();
					return true;
				}
			}
		}
		return false;
	}



}
