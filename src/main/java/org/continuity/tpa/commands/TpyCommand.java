package org.continuity.tpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.continuity.tpa.TpaManager;
import org.continuity.tpa.TpaPlugin;

public class TpyCommand implements CommandExecutor {
    private final Plugin plugin = TpaPlugin.getPlugin();
    private final TpaManager tpaManager = TpaManager.getInstance();;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName().toLowerCase();

        String requestSenderName = tpaManager.getRequestSender(playerName);
        if (requestSenderName == null) {
            player.sendMessage(tpaManager.getPrefix() + tpaManager.getErrorColor() + "You don't have any incoming TPA requests.");
            return true;
        }

        Player requestSender = Bukkit.getPlayer(requestSenderName);
        if (requestSender == null) {
            player.sendMessage(tpaManager.getPrefix() + tpaManager.getErrorColor() + "The player who sent the request is not online.");
            return true;
        }

        // Remove the request before proceeding
        tpaManager.removeRequest(playerName);

        player.sendMessage(tpaManager.getPrefix() + tpaManager.getColor() + "You accepted the TPA request.");
        requestSender.sendMessage(tpaManager.getPrefix() + tpaManager.getColor() + "Your TPA request was accepted.");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (requestSender.isOnline() && player.isOnline()) {
                requestSender.teleport(player.getLocation());
                requestSender.sendMessage(tpaManager.getPrefix() + tpaManager.getColor() + "Teleporting you to " + player.getName() + ".");
            } else {
                requestSender.sendMessage(tpaManager.getPrefix() + tpaManager.getErrorColor() + "Teleporting failed.");
            }
        }, tpaManager.getDelay() * 20L);

        return true;
    }
}
