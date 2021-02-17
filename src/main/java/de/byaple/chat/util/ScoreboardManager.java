package de.byaple.chat.util;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

//import net.minecraft.server.v1_12_R1.ChatMessage;
//import net.minecraft.server.v1_12_R1.IChatBaseComponent;
//import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;

public class ScoreboardManager {
	
	public static Scoreboard sb;
	
	private static List<String> TEAMS = Data.getTeams().getConfig().getStringList("teams.name");
	private static List<String> TEAMS_PREFIX = Data.getTeams().getConfig().getStringList("teams.prefix");
	private static List<String> TEAMS_SUFFIX = Data.getTeams().getConfig().getStringList("teams.suffix");
	private static List<String> TEAMS_COLORS = Data.getTeams().getConfig().getStringList("teams.colors");
	private static List<String> TEAMS_PERMISSIONS_USER = Data.getTeams().getConfig().getStringList("teams.permissions");
	
	private static List<String> FRAKTION = Data.getTeams().getConfig().getStringList("fraktion.name");
	private static List<String> FRAKTION_PREFIX = Data.getTeams().getConfig().getStringList("fraktion.prefix");
	private static List<String> FRAKTION_SUFFIX = Data.getTeams().getConfig().getStringList("fraktion.suffix");
	private static List<String> FRAKTION_COLORS = Data.getTeams().getConfig().getStringList("fraktion.colors");
	private static List<String> FRAKTION_PERMISSIONS_USER = Data.getTeams().getConfig().getStringList("fraktion.permissions");
	
	
	public static void setup() {

		TEAMS = Data.getTeams().getConfig().getStringList("teams.name");
		TEAMS_PREFIX = Data.getTeams().getConfig().getStringList("teams.prefix");
		TEAMS_SUFFIX = Data.getTeams().getConfig().getStringList("teams.suffix");
		TEAMS_COLORS = Data.getTeams().getConfig().getStringList("teams.colors");
		TEAMS_PERMISSIONS_USER = Data.getTeams().getConfig().getStringList("teams.permissions");
		
		FRAKTION = Data.getTeams().getConfig().getStringList("fraktion.name");
		FRAKTION_PREFIX = Data.getTeams().getConfig().getStringList("fraktion.prefix");
		FRAKTION_SUFFIX = Data.getTeams().getConfig().getStringList("fraktion.suffix");
		FRAKTION_COLORS = Data.getTeams().getConfig().getStringList("fraktion.colors");
		FRAKTION_PERMISSIONS_USER = Data.getTeams().getConfig().getStringList("fraktion.permissions");
		
		try {
		sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		if (Data.getConfig().getConfig().getBoolean("debug")) {
			Bukkit.getConsoleSender().sendMessage("Debug ON! bei Chat Plugin - Setzte Teams");
			
		}
		
		// Setzt alle eigenschaften in die Teamliste
		for(int i = 0; i < TEAMS.size(); i++) {
			if(sb.getTeam(TEAMS.get(i)) == null) sb.registerNewTeam(TEAMS.get(i));
			Data.team.add(sb.getTeam(TEAMS.get(i)));
			Data.team.get(i).setColor(ChatColor.valueOf(TEAMS_COLORS.get(i)));
			Data.team.get(i).setPrefix(ChatColor.translateAlternateColorCodes('&', TEAMS_PREFIX.get(i)));
			Data.team.get(i).setSuffix(ChatColor.translateAlternateColorCodes('&', TEAMS_SUFFIX.get(i)));
			if (Data.getConfig().getConfig().getBoolean("debug")) {
			Bukkit.getConsoleSender().sendMessage("Team_User: " + TEAMS.get(i) + ChatColor.valueOf(TEAMS_COLORS.get(i)) + " Farbe " + "�r und PREFIX/SUFFIX " + TEAMS_PREFIX.get(i) + "/" +TEAMS_SUFFIX.get(i) + ";");
			}
		}
	
		for(int i = 0; i < FRAKTION.size(); i++) {
			if(sb.getTeam(FRAKTION.get(i)) == null) sb.registerNewTeam(FRAKTION.get(i));
			Data.fraktion.add(sb.getTeam(FRAKTION.get(i)));
			Data.fraktion.get(i).setColor(ChatColor.valueOf(FRAKTION_COLORS.get(i)));
			Data.fraktion.get(i).setPrefix(ChatColor.translateAlternateColorCodes('&', FRAKTION_PREFIX.get(i)));
			Data.fraktion.get(i).setSuffix(ChatColor.translateAlternateColorCodes('&', FRAKTION_SUFFIX.get(i)));
			if (Data.getConfig().getConfig().getBoolean("debug")) {
			Bukkit.getConsoleSender().sendMessage("Team_User: " + FRAKTION.get(i) + ChatColor.valueOf(FRAKTION_COLORS.get(i)) + " Farbe " + "�r und PREFIX/SUFFIX " + FRAKTION_PREFIX.get(i) + "/" +FRAKTION_SUFFIX.get(i) + ";");
			}
		}
		
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.setScoreboard(sb);
		}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	@SuppressWarnings("deprecation")
	public static Team getTeamUser(Player player) {
		
		// Sucht nach permission um in das richtige team zu setzten
		for(int i = 0; i < TEAMS_PERMISSIONS_USER.size() - 1; i++) {
			if(player.hasPermission(TEAMS_PERMISSIONS_USER.get(i))) {
				if(!Data.team.get(i).hasPlayer(player)) {
					if (Data.getConfig().getConfig().getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " wurde die Permission: " + TEAMS_PERMISSIONS_USER.get(i) + "/"+ TEAMS_PERMISSIONS_USER.size() + " abgefragt.");
						}
					return Data.team.get(i);
				} else {
					if (Data.getConfig().getConfig().getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " wurde die Permission: " + TEAMS_PERMISSIONS_USER.get(i) + "/"+ TEAMS_PERMISSIONS_USER.size() + " abgefragt.");
						}
					return Data.team.get(i);
				}
			}
		}
		
		return Data.team.get(TEAMS_PERMISSIONS_USER.size() - 1);
	}
	
	@SuppressWarnings("deprecation")
	public static Team getFraktionUser(Player player) {
		
		// Sucht nach permission um in das richtige team zu setzten
		for(int i = 0; i < FRAKTION_PERMISSIONS_USER.size() - 1; i++) {
			if(player.hasPermission(FRAKTION_PERMISSIONS_USER.get(i))) {
				if(!Data.fraktion.get(i).hasPlayer(player)) {
					if (Data.getConfig().getConfig().getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " wurde die Permission: " + FRAKTION_PERMISSIONS_USER.get(i) + "/"+ FRAKTION_PERMISSIONS_USER.size() + " abgefragt.");
						}
					return Data.fraktion.get(i);
				} else {
					if (Data.getConfig().getConfig().getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " wurde die Permission: " + FRAKTION_PERMISSIONS_USER.get(i) + "/"+ FRAKTION_PERMISSIONS_USER.size() + " abgefragt.");
						}
					return Data.fraktion.get(i);
				}
			}
		}
		
		return Data.fraktion.get(FRAKTION_PERMISSIONS_USER.size() - 1);
	}
	
	@SuppressWarnings("deprecation")
	public static void setTeam(Player player) {
		
		// Setzt alle ins Richtige Team
		try {
		Team team = getTeamUser(player);
		team.addPlayer(player);
		
		Team fraktion = getTeamUser(player);
		fraktion.addPlayer(player);
		
		// Setzt den besonderen name
		player.setCustomName(
				Data.getConfig().getConfig().getString("customname")
				.replaceAll("%teamname", team.getName())
				.replaceAll("%color", team.getColor() + "")
				.replaceAll("%prefix", team.getPrefix())
				.replaceAll("%suffix", team.getSuffix())
				
				.replaceAll("%fraktionname", fraktion.getName())
				.replaceAll("%fcolor", fraktion.getColor() + "")
				.replaceAll("%fprefix", fraktion.getPrefix())
				.replaceAll("%fsuffix", fraktion.getSuffix())
				
				.replaceAll("%playername", player.getName())
				);
		
		player.setDisplayName(
				Data.getConfig().getConfig().getString("displayname")
				.replaceAll("%teamname", team.getName())
				.replaceAll("%color", team.getColor() + "")
				.replaceAll("%prefix", team.getPrefix())
				.replaceAll("%suffix", team.getSuffix())
				
				.replaceAll("%fraktionname", fraktion.getName())
				.replaceAll("%fcolor", fraktion.getColor() + "")
				.replaceAll("%fprefix", fraktion.getPrefix())
				.replaceAll("%fsuffix", fraktion.getSuffix())
				
				.replaceAll("%playername", player.getName())
				);
		
		if (Data.getConfig().getConfig().getBoolean("debug")) {
		Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " wird die Gruppe: " + team.getName() + " zugewiesen. " + team.getPrefix() + " Farbe");
		}
		
		player.setScoreboard(sb);
		} catch (Exception e) {e.printStackTrace();}
	}
		

	
	@SuppressWarnings("deprecation")
	public static void sendTab(Player player, String head, String foot){
	/*    IChatBaseComponent header = new ChatMessage(ChatColor.translateAlternateColorCodes('&', head.replaceAll("%playername", player.getName()).replaceAll("%displayname", player.getDisplayName())));
	    IChatBaseComponent footer = new ChatMessage(ChatColor.translateAlternateColorCodes('&', foot.replaceAll("%playername", player.getName()).replaceAll("%displayname", player.getDisplayName())));
	    PacketPlayOutPlayerListHeaderFooter tablist = new PacketPlayOutPlayerListHeaderFooter();
	    try {
	            Field headerField = tablist.getClass().getDeclaredField("a");
	            headerField.setAccessible(true);
	            headerField.set(tablist, header);
	            headerField.setAccessible(!headerField.isAccessible());
	            Field footerField = tablist.getClass().getDeclaredField("b");
	            footerField.setAccessible(true);
	            footerField.set(tablist, footer);
	            footerField.setAccessible(!footerField.isAccessible());
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	  
	    CraftPlayer cp = (CraftPlayer) player;
	    cp.getHandle().playerConnection.sendPacket(tablist);*/
	}
	
}
