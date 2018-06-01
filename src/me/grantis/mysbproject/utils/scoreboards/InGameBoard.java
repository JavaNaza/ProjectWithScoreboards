package me.grantis.mysbproject.utils.scoreboards;

import me.grantis.mysbproject.MySbProject;
import me.grantis.mysbproject.handlers.PlayerData;
import me.grantis.mysbproject.utils.ScoreBoardUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class InGameBoard {

    public ScoreBoardUtil scoreboard;

    private PlayerData playerData;
    private void loadGears(Player player) {
        this.playerData = new PlayerData(player);
        this.playerData.loadData();
    }

    public void loadBoard(Player player) {
        loadGears(player);

        scoreboard = new ScoreBoardUtil("InGameBoard");
        scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
        scoreboard.setDisplayName("&a&lIn-Game");
        arrangeBoard(scoreboard);
        scoreboard.display(player);
    }

    private void createEmptyScores(ScoreBoardUtil board) {
        if(!board.scoreExists("emptyScore2")) {
            ScoreBoardUtil.BoardScore emptyScore2 = new ScoreBoardUtil.BoardScore("emptyScore2", "§r", 2);
            board.addScore(emptyScore2);
        }
        if(!board.scoreExists("emptyScore6")) {
            ScoreBoardUtil.BoardScore emptyScore6 = new ScoreBoardUtil.BoardScore("emptyScore6", "§r§r", 6);
            board.addScore(emptyScore6);
        }
    }

    public void updateEmptyScores(ScoreBoardUtil board) {
        createEmptyScores(board);
        board.getScore("emptyScore2").setDisplayName("§r§r");
        board.getScore("emptyScore6").setDisplayName("§r");
    }

    private void createFilledScores(ScoreBoardUtil board) {
        if(!board.scoreExists("pointsScore")) {
            ScoreBoardUtil.BoardScore pointsScore1 = new ScoreBoardUtil.BoardScore("pointsScore", "&b&lPoints&f: " +
                    playerData.points, 1);
            board.addScore(pointsScore1);
        }
        if(!board.scoreExists("deathsScore")) {
            ScoreBoardUtil.BoardScore deathsScore3 = new ScoreBoardUtil.BoardScore("deathsScore", "&cDeaths&f: " + playerData.deaths, 3);
            board.addScore(deathsScore3);
        }
        if(!board.scoreExists("bowKillsScore")) {
            ScoreBoardUtil.BoardScore bowKillsScore4 = new ScoreBoardUtil.BoardScore("bowKillsScore", "&dBow kills&f: "
                    + playerData.bowKills, 4);
            board.addScore(bowKillsScore4);
        }
        if(!board.scoreExists("totalKillsScore")) {
            ScoreBoardUtil.BoardScore totalKillsScore5 = new ScoreBoardUtil.BoardScore("totalKillsScore", "&5Total kills&f: "
                    + playerData.kills, 5);
            board.addScore(totalKillsScore5);
        }
    }

    public void updateFilledScores(ScoreBoardUtil board) {
        createFilledScores(board);
        board.getScore("pointsScore").setDisplayName("&b&lPoints&f: " + playerData.points);
        board.getScore("deathsScore").setDisplayName("&cDeaths&f: " + playerData.deaths);
        board.getScore("bowKillsScore").setDisplayName("&dBow kills&f: " + playerData.bowKills);
        board.getScore("totalKillsScore").setDisplayName("&5Total kills&f: " + playerData.kills);
    }

    public void updateFilledScores(ScoreBoardUtil board, int scoreToUpdate) {
        if(scoreToUpdate == 5) {
            new BukkitRunnable() {
                String displayName = "&5Total kills&f: " + playerData.kills;
                int timesChanged = 0;
                @Override
                public void run() {

                    board.getScore("pointsScore").setDisplayName("&b&lPoints&f: " + playerData.points);
                    board.getScore("deathsScore").setDisplayName("&cDeaths&f: " + playerData.deaths);
                    board.getScore("bowKillsScore").setDisplayName("&dBow kills&f: " + playerData.bowKills);
                    displayName = (displayName.equals("&5Total kills&f: " + playerData.kills) ? "&aTotal kills&f: " + playerData.kills : "&5Total kills&f: " + playerData.kills);
                    board.getScore("totalKillsScore").setDisplayName(displayName);
                    timesChanged++;
                    if(timesChanged == 10) {
                        cancel();
                    }

                }
            }.runTaskTimer(MySbProject.plugin, 0, 13);
        }
    }

    private void arrangeBoard(ScoreBoardUtil board) {
        updateEmptyScores(board);
        updateFilledScores(board);
        board.update();
    }

}
