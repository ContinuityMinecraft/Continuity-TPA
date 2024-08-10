package org.continuity.tpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.continuity.tpa.TpaManager;

public class TpaCommand implements CommandExecutor {
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

        if (args.length != 1) {
            player.sendMessage(prefix + errorColor + "You have to provide a username to teleport to.");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(prefix + errorColor + args[0] + " is not online.");
            return true;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(prefix + errorColor + "You can't TPA to yourself.");
            return true;
        }

        tpaManager.sendTpaRequest(player, target);
        player.sendMessage(prefix + color + "Sent a TPA request to " + target.getName() + ".");
        target.sendMessage(prefix + color + player.getName() + " wants to teleport to you.");
        return true;
    }
}

