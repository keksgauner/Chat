package de.byaple.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.byaple.chat.commands.Broadcast;
import de.byaple.chat.commands.Commands;
import de.byaple.chat.listener.Chat;
import de.byaple.chat.listener.JoinQuit;
import de.byaple.chat.util.Data;
import de.byaple.chat.util.ScoreboardManager;

public class Main extends JavaPlugin {

    private static JavaPlugin plugin;
    private static Main instance;

    public static JavaPlugin getPlugin() { return plugin; }
    public static Main getInstance() { return instance; }


    public void onEnable() {
        plugin = this;
        instance = this;

        Bukkit.getConsoleSender().sendMessage("[RPChat]" + ChatColor.DARK_GREEN + " Das Plugin wurde erfolgreich aktiviert!");

        if (!getDescription().getName().toString().equals("RPChat")) {

            Bukkit.getConsoleSender().sendMessage("[RPChat]" + ChatColor.DARK_RED + " You have changed the name of the RPChat plugin to " + getDescription().getName() + "!");
            Bukkit.getConsoleSender().sendMessage("[RPChat]" + ChatColor.DARK_RED + " The plugin will shut down at every start until you change it again!");

            Bukkit.getServer().getPluginManager().disablePlugin(getPlugin());
        }

        Main.registerEvents(Bukkit.getPluginManager());

        // register Commands
        getCommand("chat").setExecutor(new Commands());
        getCommand("broadcast").setExecutor(new Broadcast());


        Data.init();
        ScoreboardManager.setup();
        JoinQuit.setup();
    }
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[RPChat]" + ChatColor.DARK_RED + " Das Plugin wurde deaktiviert!");
    }

    public static void registerEvents(PluginManager pm) {

        // register Events
        pm.registerEvents(new JoinQuit(), Main.getInstance());
        pm.registerEvents(new Chat(), Main.getInstance());

    }

}

