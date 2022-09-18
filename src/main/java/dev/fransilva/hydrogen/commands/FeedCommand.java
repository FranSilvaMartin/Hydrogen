package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    private Player player;
    private Player target;
    private ConfigManager configManager;

    public FeedCommand() {
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CheckUtils.hasPermission(sender, command.getName())) {
            if (args.length == 0) {
                if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
                    feedPlayer(sender, null);
                    return true;
                }
                sender.sendMessage(configManager.getMessage("must_asign_player"));
                return true;
            }

            target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                feedPlayer(sender, target);
                return true;
            } else {
                sender.sendMessage(configManager.getMessage("player_not_found"));
                return true;
            }
        }
        return true;
    }

    private void feedPlayer(CommandSender sender, Player target) {
        if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
            player = (Player) sender;
            if (target == null) {
                player.setFoodLevel(20);
                player.sendMessage(configManager.getMessage("feed"));
            } else {
                feedTarget(sender, target);
            }
        } else {
            feedTarget(sender, target);
        }
    }

    private void feedTarget(CommandSender sender, Player target) {
        target.setFoodLevel(20);
        sender.sendMessage(configManager.getMessage("feed_target", null, target.getName()));
        target.sendMessage(configManager.getMessage("feed"));
    }

}
