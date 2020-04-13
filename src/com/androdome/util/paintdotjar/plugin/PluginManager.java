package com.androdome.util.paintdotjar.plugin;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JToolBar;

import com.androdome.util.paintdotjar.MainInterface;
import com.androdome.util.paintdotjar.plugin.event.Event;
import com.androdome.util.paintdotjar.plugin.event.EventListener;
import com.androdome.util.paintdotjar.plugin.tool.Tool;
import com.androdome.util.paintdotjar.util.PaintUtils;



/**
 * PluginManager for the entire application. Loads, unloads, destroys, and registers events for plug-ins.
 */
public final class PluginManager {

	private MainInterface mainui;
	private File pluginDir = new File(PaintUtils.getJarDir(), "plugins/");
	//ClassLoader mainLoader = ClassLoader.getSystemClassLoader();
	public static final float API_VERSION = 1.0F;
	
	private HashMap<String, JavaPlugin> plugins = new HashMap<String, JavaPlugin>();
	private HashMap<JavaPlugin, String> names = new HashMap<JavaPlugin, String>();
	private HashMap<Event.Type, ArrayList<RegisteredEvent>> events = new HashMap<Event.Type, ArrayList<RegisteredEvent>>();
	private ArrayList<RegisteredTool> tools = new ArrayList<RegisteredTool>();
	private Tool selectedTool = null;
	
	/**
	 * Unloads and destroys specified plug-in
	 */
	public boolean unload(JavaPlugin plugin)
	{
		
		for(ArrayList<RegisteredEvent> plg : events.values())
		{
			for(RegisteredEvent event : plg)
			{
				if(event.getPlugin() == plugin)
				{
					plg.remove(event);
				}
			}
		}
		for(RegisteredTool tool : tools)
		{
			if(tool.getPlugin() == plugin)
			{
				tools.remove(tool);
			}
		}
		plugin.destroy();
		String name = names.remove(plugin);
		return plugins.remove(name) != null;
	}
	
	/**
	 * Reloads all plug-ins when called. Will unload all handlers previously registered.
	 */
	public void reload()
	{
		this.destroyPlugins();
		this.loadPlugins();
	}
	
	/**
	 * Registers an event that the plug-in will listen to and returns a {@link RegisteredEvent} object. 
	 * Priorities are the order in which an event will be called upon occurring.<br><br>
	 * Highest recommended is Event.HIGHEST, with Event.REALTIME reserved for 
	 * mission critical plug-ins such as permission managers.
	 */
	public RegisteredEvent registerEvent(Event.Type type, EventListener listener, JavaPlugin plugin, Event.Priority priority)
	{
		if(type != null && listener != null && plugin != null && priority != null)
		{
			RegisteredEvent event = new RegisteredEvent(type, listener, plugin, priority);
			ArrayList<RegisteredEvent> evtList = events.get(type);
			if(evtList == null)
			{
				evtList = new ArrayList<RegisteredEvent>();
				events.put(type, evtList);
			}
			
			for(int i = 0; i < evtList.size(); i++)
			{
				if(evtList.get(i).getPriority().compareTo(priority) > 0)
				{
					evtList.add(i, event);
					return event;
				}
			}
			evtList.add(event);
			return event;
		}
		return null;
	}
	
	
	/**
	 * Unregisters a {@link RegisteredEvent} from the event list and returns true if event was registered.
	 */
	public boolean unregisterEvent(RegisteredEvent event)
	{
		if(event != null)
		{
			ArrayList<RegisteredEvent> evtList = events.get(event.getType());
			if(evtList != null)
			{
				boolean ret = evtList.remove(event);
				if(evtList.size() == 0)
				{
					events.remove(event.getType());
				}
				return ret;
			}
		}
		return false;
	}
	
	/**
	 * Registers a tool that the plug-in provides and returns a {@link RegisteredTool} object. 
	 * {@link javax.awt.Image icon} is required and will be the icon displayed in the toolbox.
	 * The {@link Tool tool}, {@link JavaPlugin plugin}, {@link java.awt.Image icon}, and {@link java.lang.String name} must be valid in order for the tool to be registered.<br><br>
	 */
	public RegisteredTool registerTool(Tool tool, JavaPlugin plugin, Image icon, String name)
	{
		if(tool != null && plugin != null && icon != null)
		{
			RegisteredTool regtool = new RegisteredTool(tool, plugin, icon, name);
			tools.add(regtool);
			tool.setToolHandle(regtool);
			mainui.retool();
			return regtool;
		}
		return null;
	}
	
	/**
	 * Unregisters a {@link RegisteredTool} from the tool list and returns true if the tool was registered.
	 */
	public boolean unregisterTool(RegisteredTool tool)
	{
		if(tool != null)
		{
			return tools.remove(tool);
		}
		return false;
	}
	
	
	
	public boolean processEvent(Event evt)
	{
		if(evt != null)
		{
			ArrayList<RegisteredEvent> evtList = events.get(evt.getType());
			if(evtList != null)
			{
				for(int i = 0; i < evtList.size(); i++)
				{
					evtList.get(i).getListener().handle(evt);
				}
			}
			return !evt.isCancelled();
		}
		else throw new RuntimeException("Event parameter cannot be null");
	}
	
	public PluginManager(MainInterface ui) {
		this.mainui = ui;
	}

	/**
	 * Returns the {@link MainInterface} instance 
	 */
	public MainInterface getMainInterface() {
		return mainui;
	}
	

	
	/**
	 * Returns the desired plug-in by name if it exists, or null if it doesn't
	 */
	public JavaPlugin getPlugin(String name)
	{
		return this.plugins.get(name);
	}
	
	public void destroyPlugins()
	{
		Collection<JavaPlugin> pluginList = plugins.values();
		JavaPlugin[] pluginArr = new JavaPlugin[pluginList.size()];
		pluginList.toArray(pluginArr);
		for(int i = 0; i < pluginArr.length; i++)
		{
			pluginArr[i].destroy();
		}
		events.clear();
		plugins.clear();
		names.clear();
	}
	public void loadPlugins()
	{
		if(!pluginDir.isDirectory())
		{
			pluginDir.mkdir();
		}
		else
		{
			File[] jars = pluginDir.listFiles();
			ArrayList<URL> urls = new ArrayList<URL>();
			ArrayList<String> mainClass = new ArrayList<String>();
			ArrayList<String> name = new ArrayList<String>();
			if(jars != null)
			for(int i = 0; i < jars.length; i++)
			{
				String pluginName = jars[i].getName();
				try
				{
					if(jars[i].getName().endsWith(".jar"))
					{
						URLClassLoader classLoader = new URLClassLoader(new URL[]{jars[i].toURI().toURL()});
						BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("pluginfo.cfg")));
						pluginName = reader.readLine();
						String mainClassStr = reader.readLine();
						reader.close();
						urls.add(jars[i].toURI().toURL());
						mainClass.add(mainClassStr);
						name.add(pluginName);
					}
				}
				catch (Exception e)
				{
					System.out.println("Failed to load plugin " + pluginName+" (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Error e)
				{
					System.out.println("Plugin " + pluginName+" crashed while attempting to load. (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			URL[] uarr = new URL[urls.size()];
			urls.toArray(uarr);
			URLClassLoader classLoader = new URLClassLoader(uarr);
			for(int i = 0; i < urls.size(); i++)
			{
				try
				{
				Class<?> pluginClass;
				pluginClass = classLoader.loadClass(mainClass.get(i));
				JavaPlugin plugin = (JavaPlugin) pluginClass.newInstance();
				
				/*if(plugin.getAPIVersion() < API_VERSION)
					System.out.println("Older API detected: "+plugin.getAPIVersion()+". Should work with no issue");
				if(plugin.getAPIVersion() > API_VERSION)
					System.out.println("Newer API detected: "+plugin.getAPIVersion()+". An error is likely to occur. You may need to update your program!");
				*/
				plugin.pluginManager = this;
				this.plugins.put(name.get(i), plugin);
				this.names.put(plugin, name.get(i));
				
				}
				catch (Exception e)
				{
					System.out.println("Failed to load plugin " + name.get(i)+" (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Error e)
				{
					System.out.println("Plugin " + name.get(i)+" crashed while attempting to load. (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ArrayList<JavaPlugin> plugins = new ArrayList<JavaPlugin>(this.plugins.values());
			for(int i = 0; i < plugins.size(); i++)
			{
				try{
					plugins.get(i).init();
				}
				catch (Exception e)
				{
					System.out.println("Failed to load plugin " + name.get(i)+" (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Error e)
				{
					System.out.println("Plugin " + name.get(i)+" crashed while attempting to load. (is it out of date?):");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Return an ArrayList of all the names of the plugins.
	 */
	public ArrayList<String> getPlugins() {
		return new ArrayList<String>(this.names.values());
	}

	/**
	 * Returns the plugin's designated {@link File} directory.
	 */
	public File getPluginDirectory() {
		return pluginDir;
	}
	
	/**
	 * Returns the plugin's designated {@link JToolBar}.
	 */
	public JToolBar getToolbar() {
		return getMainInterface().getPluginToolbar();
	}
	
	/**
	 * Clears the plugin's designated {@link JToolBar}.
	 */
	public void clearToolbar() {
		getMainInterface().clearPluginToolbar();
	}


	/**
	 * Gets all registered {@link Tool}s.
	 */
	public ArrayList<RegisteredTool> getTools() {
		// TODO Auto-generated method stub
		return tools;
	}

	/**
	 * Sets the selected {@link Tool}.
	 */
	public void setTool(Tool t) {
		if(this.selectedTool != null)
			this.selectedTool.onDeselect(this.getMainInterface().getCanvasContainer().manager);
		this.selectedTool  = t;
		if(t != null)
			t.onSelect(this.getMainInterface().getCanvasContainer().manager);
	}
	
	/**
	 * Get the currently selected {@link Tool}. 
	 * Returns {@link null} when no tool is selected.
	 */
	public Tool getTool() {
		return this.selectedTool;
	}

	
	
	

}
