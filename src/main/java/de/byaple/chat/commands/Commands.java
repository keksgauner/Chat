package de.byaple.chat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.byaple.chat.util.Data;
import de.byaple.chat.util.Util;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
					sender.sendMessage("Help: /Chat reload");
					return false;
			} else
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					Util.reload();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.reload").replaceAll("%prefix", Data.getPrefix())));
					return false;
				}
			}
			return false;
		}
		
		Player player = (Player) sender;
		if (args.length == 0) {
			if (!(player.hasPermission("rpchat.reload") || player.hasPermission("rpchat.*"))) {
				player.sendMessage(Data.getNoPerms());
				return true;
			}
			
			sender.sendMessage("Help: /Chat reload");
			return false;
		} else
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				

				if (!(player.hasPermission("rpchat.reload") || player.hasPermission("rpchat.*"))) {
					player.sendMessage(Data.getNoPerms());
					return true;
				}
				
				Util.reload();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Data.getMessages().getConfig().getString("messages.reload").replaceAll("%prefix", Data.getPrefix())));
				return true;
			}
		}
		
		
		
		
		
		return false;
	}

}
