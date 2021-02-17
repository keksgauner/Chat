package de.byaple.chat.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import de.byaple.chat.Main;

public class Data {

	private static ConfigAccessor config = new ConfigAccessor(Main.getInstance(), "" + "config.yml");
	public static ConfigAccessor getConfig() {return config;}
	
	private static ConfigAccessor messages = new ConfigAccessor(Main.getInstance(), "" + "messages.yml");
	public static ConfigAccessor getMessages() {return messages;}

	private static ConfigAccessor teams = new ConfigAccessor(Main.getInstance(), "" + "teams.yml");
	public static ConfigAccessor getTeams() {return teams;}
	

	private static ConfigAccessor join = new ConfigAccessor(Main.getInstance(), "" + "join.yml");
	public static ConfigAccessor getJoin() {return join;}
	
	// Messages Strings
	public static String getPrefix() {return ChatColor.translateAlternateColorCodes('&', getMessages().getConfig().getString("messages.prefix"));}
	public static String getNoPerms() {return ChatColor.translateAlternateColorCodes('&', getMessages().getConfig().getString("messages.noperms").replaceAll("%prefix", getPrefix()));}

	public static List<Team> team;
	public static List<Team> fraktion;
	
	public static void init() {
		
		Data.team = new ArrayList<Team>();
		Data.fraktion = new ArrayList<Team>();
		
 		Data.getConfig().saveDefaultConfig();
 		Data.getMessages().saveDefaultConfig();
 		
 		Data.getTeams().saveDefaultConfig();
 		Data.getJoin().saveDefaultConfig();
 		
 		Data.getConfig().getConfig().options().copyDefaults(true);
 		Data.getMessages().getConfig().options().copyDefaults(true);

 		Data.getTeams().getConfig().options().copyDefaults(true);
 		Data.getJoin().getConfig().options().copyDefaults(true);
 		
 		Data.getConfig().saveConfig();
 		Data.getMessages().saveConfig();
 		
 		Data.getTeams().saveConfig();
 		Data.getJoin().saveConfig();
 		
 		
	}
	
	
	
}
