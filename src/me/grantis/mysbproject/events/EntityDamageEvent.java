package me.grantis.mysbproject.events;

import me.grantis.mysbproject.MySbProject;
import me.grantis.mysbproject.handlers.PlayerData;
import me.grantis.mysbproject.utils.ScoreBoardUtil;
import me.grantis.mysbproject.utils.scoreboards.InGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {

            Player player = (Player) event.getDamager();
            PlayerData playerData = new PlayerData(player);
            playerData.loadData();
            InGameBoard playerBoard = new InGameBoard();
            ScoreBoardUtil boardUtil = playerBoard.scoreboard;

            if (event.getEntity() instanceof Player) {
                Player victim = (Player) event.getEntity();
                if (victim.getHealth() <= 0) {
                    playerData.loadData();
                    playerData.updateKills(playerData.kills + 1);
                    playerBoard.updateFilledScores(boardUtil, 5);
                }
            }
        }
    }
}
