package ca.limeware.masteroffice.paintmaster.plugin;

import java.awt.Image;

import ca.limeware.masteroffice.paintmaster.plugin.tool.Tool;



public class RegisteredTool {

	private Tool tool;
	private JavaPlugin plugin;
	private Image icon;
	private String name;
	/**
	 * RegisteredEvent class that was returned by the {@link PluginManager}'s registerEvent method
	 * @param name 
	 */
	RegisteredTool(Tool tool, JavaPlugin plugin, Image icon, String name) {
		this.tool = tool;
		this.plugin = plugin;
		this.icon = icon;
	}
	
	public JavaPlugin getPlugin()
	{
		return plugin;
	}
	public Tool getTool()
	{
		return tool;
	}
	public Image getIcon()
	{
		return icon;
	}
	public String getName()
	{
		return name;
	}

}
