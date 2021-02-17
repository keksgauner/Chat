package de.byaple.chat.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import de.darkm0ds.chat.Main;
import de.darkm0ds.chat.util.Data;
import de.darkm0ds.chat.util.ScoreboardManager;
import me.lobbysystem.infinitycity.banksystem.BankSystem;
import me.lobbysystem.infinitycity.banksystem.manager.MoneyManager;

public class JoinQuit implements Listener {

	private static List<String> JOIN = Data.getJoin().getConfig().getStringList("join.nachricht");
	
	
	public static void setup() {
		JOIN = Data.getJoin().getConfig().getStringList("join.nachricht");
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ScoreboardManager.setTeam((Player) e.getPlayer());
		if (Data.getConfig().getConfig().getBoolean("tablist")) {
			if (Data.getConfig().getConfig().getBoolean("banksystem")) {

				double balance = 0;
				double cash = 0;
				String IBAN = "";
				
				if (Main.getInstance().getServer().getPluginManager().getPlugin("BankSystem")!=null) {
					MoneyManager money = BankSystem.getBankSystemAPI().getMoneyAPI();
					
					balance = money.getBalance(e.getPlayer().getUniqueId());
					cash = money.getCash(e.getPlayer().getUniqueId());
					IBAN = money.getIBAN(e.getPlayer().getUniqueId());
					
				} 
				
				ScoreboardManager.sendTab((Player) e.getPlayer(), Data.getConfig().getConfig().getString("header").replaceAll("%cash", "" + cash).replaceAll("%balance", "" + balance).replaceAll("%iban", "" + IBAN), Data.getConfig().getConfig().getString("footer").replaceAll("%cash", "" + cash).replaceAll("%balance", "" + balance).replaceAll("%iban", "" + IBAN));
			} else {
				ScoreboardManager.sendTab((Player) e.getPlayer(), Data.getConfig().getConfig().getString("header"), Data.getConfig().getConfig().getString("footer"));
			}
		}

		if (Data.getJoin().getConfig().getBoolean("enabled")) {
			
			if (Data.getJoin().getConfig().getString("costom-join-message") == "") {
				e.setJoinMessage(null);
			} else
			e.setJoinMessage(translator(e.getPlayer(), Data.getJoin().getConfig().getString("costom-join-message")));
			
			for(int i = 0; i < JOIN.size(); i++) {
				e.getPlayer().sendMessage(translator(e.getPlayer(), JOIN.get(i)));
			}
			
			
			
		}
		}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (Data.getJoin().getConfig().getBoolean("enabled")) {
			if (Data.getJoin().getConfig().getString("costom-quit-message") == "") {
				e.setQuitMessage(null);
			} else
			e.setQuitMessage(translator(e.getPlayer(), Data.getJoin().getConfig().getString("costom-quit-message")));
		}
		}
	
	private String translator(Player player, String messages) {
		
		Team team = ScoreboardManager.getTeamUser(player);
	    Team fraktion = ScoreboardManager.getFraktionUser(player);
	    String msg;
	    
		    msg = ChatColor.translateAlternateColorCodes('&', messages
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
		    
		return msg;
	}
}
