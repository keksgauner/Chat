package de.byaple.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.darkm0ds.chat.util.Data;
import de.darkm0ds.chat.util.ScoreboardManager;

public class Broadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		
		if (!(player.hasPermission("rpchat.broadcast") || player.hasPermission("rpchat.*"))) {
			player.sendMessage(Data.getNoPerms());
			return true;
		}

		
		String msg = "";
        for (int i = 0; i < args.length; i++) {
        msg = msg + args[i] + " ";
        }
		
        String format = "";
        

	    Team team = ScoreboardManager.getTeamUser(player);
	    Team fraktion = ScoreboardManager.getFraktionUser(player);
        
		    format = ChatColor.translateAlternateColorCodes('&', Data.getConfig().getConfig().getString("broadcast")
		    		
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
					.replaceAll("%messages", msg))
					;
        
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
		
		
		
		
		return false;
	}
}