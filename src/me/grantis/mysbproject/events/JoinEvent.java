package me.grantis.mysbproject.events;

import me.grantis.mysbproject.MySbProject;
import me.grantis.mysbproject.handlers.PlayerData;
import me.grantis.mysbproject.utils.scoreboards.PreGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PreGameBoard preGameBoard = new PreGameBoard();
        Player player = event.getPlayer();
        MySbProject.joinedPlayers.add(player);
        PlayerData playerData = new PlayerData(player);
        playerData.loadData();
        preGameBoard.loadBoard(player);
    }

}
