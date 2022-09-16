package dev.fransilva.hydrogen.utils;

import dev.fransilva.hydrogen.managers.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckUtils {

    public static boolean verifyIfIsAPlayer(CommandSender sender, boolean sendMessage) {
        if (!(sender instanceof Player)) {
            if (sendMessage) {
                sender.sendMessage(ConfigManager.getInstance().getMessage("no_player"));
            }
            return false;
        }
        return true;
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission("hydrogen.command." + permission) || !sender.hasPermission("hydrogen.command.*")) {
            sender.sendMessage(ConfigManager.getInstance().getMessage("no_permission"));
            return false;
        }

        return sender.hasPermission("hydrogen.command." + permission) || sender.hasPermission("hydrogen.command.*");
    }
}
