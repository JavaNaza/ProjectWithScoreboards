package me.grantis.mysbproject;

import me.grantis.mysbproject.events.EntityDamageEvent;
import me.grantis.mysbproject.events.JoinEvent;
import me.grantis.mysbproject.events.QuitEvent;
import me.grantis.mysbproject.utils.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class MySbProject extends JavaPlugin {

    public static MySbProject plugin;
    private ConfigFile playersDataConfig;
    private ConfigFile mainConfig;

    public static ArrayList<Player> joinedPlayers = new ArrayList<Player>();

    @Override
    public void onEnable() {
        plugin = this;
        loadConfigFiles();
        registerEvents();
    }

    @Override
    public void onDisable() {
        reloadConfigFiles();
    }

    public void loadConfigFiles() {
        playersDataConfig = new ConfigFile(plugin, "playersdata.yml");
        mainConfig = new ConfigFile(plugin, "config.yml");

        playersDataConfig.load();
        mainConfig.load();

        if(playersDataConfig.getConfigurationSection("Players") == null) {
            playersDataConfig.createSection("Players");
            playersDataConfig.save();
        }

        if(mainConfig.getConfigurationSection("Game.maxPlayers") == null) {
            mainConfig.setValue("Game.maxPlayers", 5);
            mainConfig.save();
        }
    }

    public void reloadConfigFiles() {
        playersDataConfig.reload();
        mainConfig.reload();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), plugin);
        getServer().getPluginManager().registerEvents(new QuitEvent(), plugin);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), plugin);
    }

    public void registerCommands() {

    }

    public ConfigFile getPlayersDataConfig() {
        return playersDataConfig;
    }

    public ConfigFile getMainConfig() {
        return mainConfig;
    }

}
