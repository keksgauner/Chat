package de.byaple.chat.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.darkm0ds.chat.Main;
import de.darkm0ds.chat.listener.JoinQuit;
import me.lobbysystem.infinitycity.banksystem.BankSystem;
import me.lobbysystem.infinitycity.banksystem.manager.MoneyManager;

public class Util {
	public static void reload() {

		Data.getConfig().reloadConfig();
		Data.getMessages().reloadConfig();
		Data.getTeams().reloadConfig();

		Data.init();
		ScoreboardManager.setup();
		JoinQuit.setup();
		try {
		for (Player all : Bukkit.getOnlinePlayers()) {
			Bukkit.getScheduler().cancelAllTasks();
			Bukkit.getScheduler().cancelTasks(Main.getPlugin());;
			ScoreboardManager.setTeam(all.getPlayer());

			if (Data.getConfig().getConfig().getBoolean("tablist")) {
				if (Data.getConfig().getConfig().getBoolean("banksystem")) {
					MoneyManager money = BankSystem.getBankSystemAPI().getMoneyAPI();
					
					double balance = money.getBalance(all.getPlayer().getUniqueId());
					double cash = money.getCash(all.getPlayer().getUniqueId());
					String IBAN = money.getIBAN(all.getPlayer().getUniqueId());
					
					ScoreboardManager.sendTab(all.getPlayer(), Data.getConfig().getConfig().getString("header").replaceAll("%balance", "" + balance).replaceAll("%cash", "" + cash).replaceAll("%iban", "" + IBAN), Data.getConfig().getConfig().getString("footer").replaceAll("%cash", "" + cash).replaceAll("%balance", "" + balance).replaceAll("%iban", "" + IBAN));
				} else {
					ScoreboardManager.sendTab(all.getPlayer(), Data.getConfig().getConfig().getString("header"), Data.getConfig().getConfig().getString("footer"));
				}
			
			}
				all.setScoreboard(ScoreboardManager.sb);
		}
		} catch (Exception e) {e.printStackTrace();}
	}
}
