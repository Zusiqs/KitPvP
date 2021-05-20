package me.jeroenyt.kitpvp.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.UserController;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;


public class Board {

    private final KitPvP plugin;
    private final Configuration config;
    private final UserController userController;

    public Board(KitPvP plugin, UserController userController) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.userController = userController;

        new BukkitRunnable()
        {
            public void run()
            {
                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    showScoreboard(all);
                }

            }
        }.runTaskTimer(plugin, 0L, 30L);
    }

    private String getEntryFromScore(Objective o, int score) {
        if (o == null)
            return null;
        if (!hasScoreTaken(o, score))
            return null;
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score)
                return o.getScore(s).getEntry();
        }
        return null;
    }

    private boolean hasScoreTaken(Objective o, int score) {
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score)
                return true;
        }
        return false;
    }

    private void replaceScore(Objective o, int score, String name) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equalsIgnoreCase(name))
                return;
            if (!getEntryFromScore(o, score).equalsIgnoreCase(name))
                o.getScoreboard().resetScores(getEntryFromScore(o, score));
        }
        o.getScore(name).setScore(score);
    }


    private void setupDefaults() {
        if (!config.contains("scoreboard.title")) {
            config.set("scoreboard.title", Utils.format("&c&lKitPvP"));
        }
        if (!config.contains("scoreboard.lines")) {
            List<String> list = new ArrayList<>();
            list.add("   ");
            list.add("&cSpeler informatie »");
            list.add("&fName: &a%player_name%");
            list.add("&fRank: &a%luckperms_prefix%");
            list.add("&fKills: &a%kills%");
            list.add("&fDeaths: &a%deaths%");
            list.add("             ");
            list.add("&cServer informatie »");
            list.add("&fOnline: &a%server_online%");
            list.add("              ");
            list.add("&7play.kitpvp.nl");
            config.set("scoreboard.lines", list);
        }

        plugin.saveConfig();
    }

    private void showScoreboard(Player p) {
        if (p.getScoreboard().equals(p.getServer().getScoreboardManager().getMainScoreboard())) {
            p.setScoreboard(p.getServer().getScoreboardManager().getNewScoreboard());
        }
        Scoreboard score = p.getScoreboard();

        setupDefaults();

        Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") :
                score.getObjective(p.getName());
        objective.setDisplayName(plugin.getConfig().getString(Utils.format("scoreboard.title")));

        int count = 0;
        List<String> lines = config.getStringList("scoreboard.lines");

        for(int i = lines.size()-1; i >= 0; i--) {
            String line = lines.get(i)
                    .replaceAll("%kills%", Integer.toString(userController.getUser(p.getUniqueId()).getKills()))
                    .replaceAll("%deaths%", Integer.toString(userController.getUser(p.getUniqueId()).getDeaths()));

            //set with placeholders
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
                line = PlaceholderAPI.setPlaceholders(p, line);
            }

            replaceScore(objective, count, Utils.format(line));
            count++;
        }
        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR)
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(score);
    }
}
