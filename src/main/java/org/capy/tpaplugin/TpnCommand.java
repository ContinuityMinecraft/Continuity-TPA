package org.capy.tpaplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpnCommand implements CommandExecutor {
    private final TpaManager tpaManager = TpaManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        String prefix = tpaManager.getPrefix();
        String color = tpaManager.getColor();
        String errorColor = tpaManager.getErrorColor();

        Player player = (Player) sender;
        String playerName = player.getName().toLowerCase();

        String requestSenderName = tpaManager.getRequestSender(playerName);
        if (requestSenderName == null) {
            player.sendMessage(prefix + errorColor + "You don't have any incoming TPA requests.");
            return true;
        }

        Player requestSender = Bukkit.getPlayer(requestSenderName);
        if (requestSender == null) {
            player.sendMessage(prefix + errorColor + requestSenderName + " is not online.");
            return true;
        }

        tpaManager.removeRequest(playerName);
        player.sendMessage(prefix + color + "You denied the TPA request.");
        requestSender.sendMessage(prefix + errorColor + "Your TPA request was denied.");
        return true;
    }
}

