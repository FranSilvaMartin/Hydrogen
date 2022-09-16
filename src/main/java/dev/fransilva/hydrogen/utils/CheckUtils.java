package dev.fransilva.hydrogen.utils;

import dev.fransilva.hydrogen.managers.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckUtils {

    public static boolean verifyIfIsAPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getInstance().getMessage("no_player"));
            return false;
        }
        return true;
    }

    public static boolean hasPermission(Player player, String permission) {

        if (!player.hasPermission("hydrogen.command." + permission) || !player.hasPermission("hydrogen.command.*")) {
            player.sendMessage(ConfigManager.getInstance().getMessage("no_permission"));
            return false;
        }

        return player.hasPermission("hydrogen.command." + permission) || player.hasPermission("hydrogen.command.*");
    }
}
