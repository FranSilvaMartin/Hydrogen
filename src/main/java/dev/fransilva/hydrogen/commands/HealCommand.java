package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

import java.lang.annotation.Target;

public class HealCommand implements CommandExecutor {

    private Player player;
    private Player target;

    private ConfigManager configManager;

    public HealCommand() {
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CheckUtils.hasPermission(sender, command.getName())) {
            if (args.length == 0) {
                if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
                    healPlayer(sender, null);
                    return true;
                }
                sender.sendMessage(configManager.getMessage("must_asign_player"));
                return true;
            }

            target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                healPlayer(sender, target);
                return true;
            } else {
                sender.sendMessage(configManager.getMessage("player_not_found"));
                return true;
            }
        }
        return true;
    }

    private void healPlayer(CommandSender sender, Player target) {
        if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
            player = (Player) sender;
            if (target == null) {
                player.setHealth(20);
                player.sendMessage(configManager.getMessage("heal"));
            } else {
                healTarget(sender, target);
            }
        } else {
            healTarget(sender, target);
        }
    }

    private void healTarget(CommandSender sender, Player target) {
        target.setHealth(20);
        sender.sendMessage(configManager.getMessage("heal_target", null, target.getName()));
        target.sendMessage(configManager.getMessage("heal"));
    }

}
