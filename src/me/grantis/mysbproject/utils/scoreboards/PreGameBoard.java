package me.grantis.mysbproject.utils.scoreboards;

import me.grantis.mysbproject.MySbProject;
import me.grantis.mysbproject.handlers.PlayerData;
import me.grantis.mysbproject.utils.ConfigFile;
import me.grantis.mysbproject.utils.ScoreBoardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;

import java.util.concurrent.ThreadLocalRandom;

public class PreGameBoard {

    private ConfigFile mainConfig = MySbProject.plugin.getMainConfig();

    private Player player;
    private String websiteText = "www.webiste.com";
    private int num = 0;
    private int timeLeft = 60;

    private void loadGears(Player player) {
        this.player = player;
        websiteText = "www.webiste.com";
        num = 0;
        timeLeft = 60;
    }

    public void loadBoard(Player player) {
        loadGears(player);
        ScoreBoardUtil scoreboard = new ScoreBoardUtil("PreGameBoard");
        scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
        scoreboard.setDisplayName("&5&lPre-Game");
        updateTimeLeft(60);
        arrangeBoard(scoreboard);
        scoreboard.display(player);

    }

    private void createFilledScores(ScoreBoardUtil board) {
        if(!board.scoreExists("websiteScore")) {
            ScoreBoardUtil.BoardScore websiteScore1 = new ScoreBoardUtil.BoardScore("websiteScore", websiteText, 1);
            board.addScore(websiteScore1);
        }
        if(!board.scoreExists("timeleftScore")) {
            ScoreBoardUtil.BoardScore timeLeftScore4 = new ScoreBoardUtil.BoardScore("timeleftScore", "&c&lTime left&f: " + timeLeft, 4);
            board.addScore(timeLeftScore4);
        }
        if(!board.scoreExists("playersjoinedScore")) {
            ScoreBoardUtil.BoardScore playersjoinedScore3 = new ScoreBoardUtil.BoardScore("playersjoinedScore", "&6Players joined&f:" +
                    MySbProject.joinedPlayers.size() + "/" + mainConfig.getValue("Game.maxPlayers"), 3);
            board.addScore(playersjoinedScore3);
        }
    }

    private void createEmptyScores(ScoreBoardUtil board) {
        if(!board.scoreExists("emptyScore2")) {
            ScoreBoardUtil.BoardScore emptyScore2 = new ScoreBoardUtil.BoardScore("emptyScore2", "&chey", 2);
            board.addScore(emptyScore2);
        }
        if(!board.scoreExists("emptyScore5")) {
            ScoreBoardUtil.BoardScore emptyScore5 = new ScoreBoardUtil.BoardScore("emptyScore5", "&c", 5);
            board.addScore(emptyScore5);
        }
    }

    private void updateFilledScores(ScoreBoardUtil board) {
        createFilledScores(board);
        board.getScore("websiteScore").setDisplayName(websiteText);
        board.getScore("timeleftScore").setDisplayName("&c&lTime left&f: " + timeLeft);
        board.getScore("playersjoinedScore").setDisplayName("&6Players joined&f: " + MySbProject.joinedPlayers.size() + "/" +
                mainConfig.getValue("Game.maxPlayers"));
    }

    private void updateTimeLeft(final int time) {
        timeLeft = time;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(timeLeft == 0) {
                    InGameBoard inGameBoard = new InGameBoard();
                    inGameBoard.loadBoard(player);
                    cancel();
                }
                else {
                    timeLeft--;
                }
            }
        }.runTaskTimer(MySbProject.plugin, 0, 20);
    }

    private void updateEmptyScores(ScoreBoardUtil board) {
        createEmptyScores(board);
        board.getScore("emptyScore5").setDisplayName("&c");
        board.getScore("emptyScore2").setDisplayName("§r");
    }

    private void arrangeBoard(ScoreBoardUtil board) {
        new BukkitRunnable() {
            String buf = websiteText;
            @Override
            public void run() {
                websiteText = (num == 0 ? "&1" : num == 1 ? "&2" : num == 2 ? "&3" : "") + buf;
                num = (num == 0 ? 1 : num == 1 ? 2 : num == 2 ? 0 : null);
                updateFilledScores(board);
                updateEmptyScores(board);
                board.update();
            }
        }.runTaskTimer(MySbProject.plugin, 0, 10);
    }
}