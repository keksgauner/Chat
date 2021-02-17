package de.byaple.chat.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import de.byaple.chat.Main;
import de.byaple.chat.util.Data;
import de.byaple.chat.util.ScoreboardManager;

public class Chat implements Listener {

	public static ArrayList<Player> timeout = new ArrayList<>();
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) 
	{
		e.setCancelled(true);
		
		Player p = e.getPlayer();
		String message = e.getMessage();
		
		Player player = e.getPlayer();
		String msg = e.getMessage();
		
		
		if (message.startsWith("s ")) {
			if (!(player.hasPermission("rpchat.colorchat") || player.hasPermission("rpchat.*"))) 
			{
				if (msg.startsWith("&")) 
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.chatcolor").replaceAll("%prefix", Data.getPrefix())));

					Chat.shoutMessage(player, msg);
					return;
				} else {
					Chat.shoutMessage(player, msg);
					return;
				}
			} else {

				if (msg.contains("&0") || 
					msg.contains("&1") || 
					msg.contains("&2") || 
					msg.contains("&3") || 
					msg.contains("&4") || 
					msg.contains("&5") || 
					msg.contains("&6") || 
					msg.contains("&7") || 
					msg.contains("&8") || 
					msg.contains("&9") || 
					msg.contains("&a") || 
					msg.contains("&b") || 
					msg.contains("&c") || 
					msg.contains("&d") || 
					msg.contains("&e") || 
					msg.contains("&k") || 
					msg.contains("&l") || 
					msg.contains("&m") || 
					msg.contains("&n") || 
					msg.contains("&o") || 
					msg.contains("&r")
					) 
				{
					if (Chat.timeout.contains(player) && !((player.hasPermission("rpchat.colorchat.bypass") || player.hasPermission("rpchat.*")))) {
			        	player.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.chatcolor_warten").replaceAll("%prefix", Data.getPrefix())));
						
		
						return;
					}
		        Chat.addTimeout(player);
		        msg = ChatColor.translateAlternateColorCodes('&', msg);
				}
				Chat.shoutMessage(player, msg);
				return;
			}
		}
		
		if (!p.hasPermission("rpchat.colorchat")) 
		{
			if (e.getMessage().startsWith("&")) 
			{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.chatcolor").replaceAll("%prefix", Data.getPrefix())));
					talkMessage(p, message);
				return;
			} else {
					talkMessage(p, message);
				return;
			}
		} else {

			if (e.getMessage().contains("&0") || 
					e.getMessage().contains("&1") || 
					e.getMessage().contains("&2") || 
					e.getMessage().contains("&3") || 
					e.getMessage().contains("&4") || 
					e.getMessage().contains("&5") || 
					e.getMessage().contains("&6") || 
					e.getMessage().contains("&7") || 
					e.getMessage().contains("&8") || 
					e.getMessage().contains("&9") || 
					e.getMessage().contains("&a") || 
					e.getMessage().contains("&b") || 
					e.getMessage().contains("&c") || 
					e.getMessage().contains("&d") || 
					e.getMessage().contains("&e") || 
					e.getMessage().contains("&k") || 
					e.getMessage().contains("&l") || 
					e.getMessage().contains("&m") || 
					e.getMessage().contains("&n") || 
					e.getMessage().contains("&o") || 
					e.getMessage().contains("&r")
					) 
				{
				if (timeout.contains(p) && !((p.hasPermission("rpchat.colorchat.bypass") || player.hasPermission("rpchat.*")))) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.chatcolor_warten").replaceAll("%prefix", Data.getPrefix())));
				
					return;
				}
				
			addTimeout(p);
			message = ChatColor.translateAlternateColorCodes('&', message);
			}
				talkMessage(p, message);
			return;
		}
	}
	
	public static void shoutMessage(final Player player, final String message){
		Player[] players = (Player[])Bukkit.getOnlinePlayers().toArray((Object[])new Player[0]);
	    List<Player> playersInRadius = new ArrayList<>();
	    String format = "";
	    Team team = ScoreboardManager.getTeamUser(player);
	    Team fraktion = ScoreboardManager.getFraktionUser(player);
	    
		    format = ChatColor.translateAlternateColorCodes('&', Data.getConfig().getConfig().getString("format.shout")
					.replaceAll("%teamname", team.getName())
					.replaceAll("%color", team.getColor() + "")
					.replaceAll("%prefix", team.getPrefix())
					.replaceAll("%suffix", team.getSuffix())
					
					.replaceAll("%fraktionmname", fraktion.getName())
					.replaceAll("%fcolor", fraktion.getColor() + "")
					.replaceAll("%fprefix", fraktion.getPrefix())
					.replaceAll("%fsuffix", fraktion.getSuffix())
					
					.replaceAll("%playername", player.getName())
					.replaceAll("%customname", player.getCustomName())
					.replaceAll("%displayname", player.getDisplayName())
					);
	    
	    if (Data.getConfig().getConfig().getInt("distance.shout") == 0) {
	      playersInRadius.addAll(Arrays.asList(players));
	    } else {
	      for (Player entityPlayer : players) {
	        if (player.getLocation().distance(entityPlayer.getLocation()) <= Data.getConfig().getConfig().getInt("distance.shout"))
	          playersInRadius.add(entityPlayer); 
	      } 
	    } 
	    for (Player entityPlayer : playersInRadius)
	      entityPlayer.sendMessage(format.replaceAll("%messages", message)); 
    
    }

	public static void talkMessage(final Player player, final String message){
		Player[] players = (Player[])Bukkit.getOnlinePlayers().toArray((Object[])new Player[0]);
	    List<Player> playersInRadius = new ArrayList<>();
	    String format = "";
	    Team team = ScoreboardManager.getTeamUser(player);
	    Team fraktion = ScoreboardManager.getFraktionUser(player);
	    
		    format = ChatColor.translateAlternateColorCodes('&', Data.getConfig().getConfig().getString("format.normal")
					.replaceAll("%teamname", team.getName())
					.replaceAll("%color", team.getColor() + "")
					.replaceAll("%prefix", team.getPrefix())
					.replaceAll("%suffix", team.getSuffix())
					
					.replaceAll("%fraktionname", fraktion.getName())
					.replaceAll("%fcolor", fraktion.getColor() + "")
					.replaceAll("%fprefix", fraktion.getPrefix())
					.replaceAll("%fsuffix", fraktion.getSuffix())
					
					.replaceAll("%playername", player.getName())
					.replaceAll("%customname", player.getCustomName())
					.replaceAll("%displayname", player.getDisplayName())
					);
	    
	    
	    if (Data.getConfig().getConfig().getInt("distance.normal") == 0) {
	      playersInRadius.addAll(Arrays.asList(players));
	    } else {
	      for (Player entityPlayer : players) {
	        if (player.getLocation().distance(entityPlayer.getLocation()) <= Data.getConfig().getConfig().getInt("distance.normal"))
	          playersInRadius.add(entityPlayer); 
	      } 
	    } 
	    for (Player entityPlayer : playersInRadius)
	      entityPlayer.sendMessage(format.replaceAll("%messages", message)); 
	  
    }

	public static void addTimeout(final Player player){
    	
		timeout.add(player);
		
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                	timeout.remove(player);
                }
            }, (int) Data.getConfig().getConfig().getInt("colorcodetimeout") * 20);
        
    }
}