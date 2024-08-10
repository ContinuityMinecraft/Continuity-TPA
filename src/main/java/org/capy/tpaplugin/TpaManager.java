package org.capy.tpaplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class TpaManager {
    private static TpaManager instance;
    private final Map<String, List<String>> tpaRequests = new HashMap<>();
    private final JavaPlugin plugin = TpaPlugin.getPlugin();

    public static TpaManager getInstance() {
        if (instance == null) {
            instance = new TpaManager();
        }
        return instance;
    }

    public void sendTpaRequest(Player sender, Player target) {
        String targetName = target.getName().toLowerCase();
        String senderName = sender.getName().toLowerCase();

        tpaRequests.computeIfAbsent(targetName, k -> new ArrayList<>()).add(senderName);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            removeRequest(targetName);
            if (!hasRequest(targetName)) {
                target.sendMessage(getPrefix() + getColor() + "The TPA request from " + sender.getName() + " has expired.");
                sender.sendMessage(getPrefix() + getColor() + "Your TPA request to " + target.getName() + " has expired.");
            }
        }, 20L * 60);
    }

    public String getRequestSender(String targetName) {
        List<String> requests = tpaRequests.get(targetName.toLowerCase());
        if (requests == null || requests.isEmpty()) {
            return null;
        }
        return requests.get(0);
    }

    public void removeRequest(String targetName) {
        List<String> requests = tpaRequests.get(targetName.toLowerCase());
        if (requests != null && !requests.isEmpty()) {
            requests.remove(0);
            if (requests.isEmpty()) {
                tpaRequests.remove(targetName.toLowerCase());
            }
        }
    }

    public boolean hasRequest(String targetName) {
        List<String> requests = tpaRequests.get(targetName.toLowerCase());
        return requests != null && !requests.isEmpty();
    }

    public int getDelay() {
        return Utils.getConfig().getInt("delay");
    }

    public String getPrefix() {
        return Utils.getConfig().getString("prefix").replace("&", "§");
    }

    public String getColor() {
        return Utils.getConfig().getString("color").replace("&", "§");
    }

    public String getErrorColor() {
        return Utils.getConfig().getString("error-color").replace("&", "§");
    }
}
