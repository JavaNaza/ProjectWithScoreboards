package me.grantis.mysbproject.handlers;

import me.grantis.mysbproject.MySbProject;
import me.grantis.mysbproject.utils.ConfigFile;
import org.bukkit.entity.Player;

public class PlayerData {

    private ConfigFile playersDataConfig = MySbProject.plugin.getPlayersDataConfig();

    public int deaths;
    public int kills;
    public int bowKills;
    public int points;

    private Player player;

    public PlayerData(Player player) {
        this.player = player;
    }

    public void loadData() {
        if(playersDataConfig.getConfigurationSection("Players." + player.getName()) == null) {
            playersDataConfig.setValue("Players." + player.getName() + ".deaths", 0);
            playersDataConfig.setValue("Players." + player.getName() + ".kills", 0);
            playersDataConfig.setValue("Players." + player.getName() + ".bowKills", 0);
            playersDataConfig.setValue("Players." + player.getName() + ".points", 0);
            playersDataConfig.save();
        }
        deaths = (int) playersDataConfig.getValue("Players." + player.getName() + ".deaths");
        kills = (int) playersDataConfig.getValue("Players." + player.getName() + ".kills");
        bowKills = (int) playersDataConfig.getValue("Players." + player.getName() + ".bowKills");
        points = (int) playersDataConfig.getValue("Players." + player.getName() + ".points");
        playersDataConfig.save();
    }

    public void updateDeaths(int deaths) {
        playersDataConfig.setValue("Players." + player.getName() + ".deaths", deaths);
        playersDataConfig.save();
        this.deaths = deaths;
    }

    public void updateKills(int kills) {
        playersDataConfig.setValue("Players." + player.getName() + ".kills", kills);
        playersDataConfig.save();
        this.kills = kills;
    }

    public void updateBowKills(int bowKills) {
        playersDataConfig.setValue("Players." + player.getName() + ".bowKills", bowKills);
        playersDataConfig.save();
        this.bowKills = bowKills;
    }

    public void updatePoints(int points) {
        playersDataConfig.setValue("Players." + player.getName() + ".points", points);
        playersDataConfig.save();
        this.points = points;
    }
}