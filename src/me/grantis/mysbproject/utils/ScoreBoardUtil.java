package me.grantis.mysbproject.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardUtil {

    private Scoreboard scoreboard;
    private Objective objective;
    private String objectiveName;
    private DisplaySlot slot;
    private List<BoardScore> scores;
    private String displayName;

    public ScoreBoardUtil(String objectiveName) {
        this(objectiveName, objectiveName);
    }

    public ScoreBoardUtil(String objectiveName, String displayName) {
        this(objectiveName, displayName, DisplaySlot.SIDEBAR);
    }

    public ScoreBoardUtil(String objectiveName, String displayName, DisplaySlot slot) {
        this.scores = new ArrayList<>();
        this.objectiveName = objectiveName;
        this.displayName = displayName;
        this.slot = slot;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(objectiveName, "dummy");
        this.setDisplayName(displayName);
    }

    public void display(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void displayOnline() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            this.display(player);
        }
    }

    public void update() {
        this.clear(false);
        this.setScores();
    }

    public void setDisplayName(Object name) {
        this.objective.setDisplayName(toColor(name+""));
    }

    public void setDisplaySlot(DisplaySlot slot) {
        this.objective.setDisplaySlot(slot);
    }

    public void deleteScore(String name) {
        this.scores.remove(getScore(name));
    }

    private void displayScore(BoardScore score) {
        org.bukkit.scoreboard.Score display = this.objective.getScore(toColor(score.getDisplayName()));
        display.setScore(score.getValue());
    }

    private void setScores() {
        for (int x = 0; x < scores.size(); x++) {
            this.displayScore(scores.get(x));
        }
    }

    public void addScore(BoardScore score) {
        if(scoreExists(score.getName())) try {
            throw new IOException("Score id name already exists");
        } catch (IOException e) {
            e.printStackTrace();
        } else {
            this.displayScore(score);
            this.scores.add(score);
        }
    }

    public BoardScore getScore(String name) {
        try {
            return this.getRawScore(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BoardScore getRawScore(String name) throws IOException {
        for (int x = 0; x < scores.size(); x++) {
            if(scores.get(x).getName().equals(name)) return scores.get(x);
        }
        throw new IOException("Score does not exists");
    }

    public boolean scoreExists(String name) {
        for (int x = 0; x < scores.size(); x++) {
            if(name.equals(scores.get(x).getName())) return true;
        }
        return false;
    }


    public void clear(boolean removeValues) {
        for(String score : this.scoreboard.getEntries()) {
            this.scoreboard.resetScores(score);
        }
        if(removeValues) this.scores.clear();
    }

    public void hide(Player player) {
        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
    }

    private String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public static class BoardScore {

        private String name;
        private String displayName;
        private int value;

        public BoardScore(String name, String displayName, int value) {
            this.name = name;
            this.displayName = displayName;
            this.value = value;
        }

        public BoardScore(String name) {
            this(name, 0);
        }

        public BoardScore(String name, int value) {
            this(name, name, value);
        }

        public String getName() {
            return name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public BoardScore setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public int getValue() {
            return value;
        }

        public BoardScore setValue(int value) {
            this.value = value;
            return this;
        }
    }
}