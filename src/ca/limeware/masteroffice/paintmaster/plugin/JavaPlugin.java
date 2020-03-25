package ca.limeware.masteroffice.paintmaster.plugin;

import java.io.File;

import ca.limeware.masteroffice.paintmaster.MainInterface;


public abstract class JavaPlugin {
	PluginManager pluginManager;
	/**
	 * Returns the {@link PluginManager}
	 */
	public final PluginManager getManager()
	{
		return pluginManager;
	}
	/**
	 * returns the {@link PluginManager}'s {@link MainInterface} instance
	 */
	public final MainInterface getMainInterface()
	{
		return pluginManager.getMainInterface();
	}

	/**
	 * Called when the plug-in is first initialized by the {@link PluginManager}
	 */
	public abstract void init();
	public abstract void destroy();
	public File getPluginDirectory() {
		return pluginManager.getPluginDirectory();
	}
	
	

}
