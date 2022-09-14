package dev.fransilva.hydrogen.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtils {

	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	public static String concatArgs(int start, String[] args) {
		String text = "";
		for (int i = start; i < args.length; i++) {
			text += args[i] + " ";
		}
		return text;
	}

	public static boolean verifyIfIsAPlayer(CommandSender sender) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Utils.getNot_Player()");
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasPermission(Player player, String permission) {
		return player.hasPermission("hydrogen." + permission) || player.hasPermission("hydrogen.*");
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission("hydrogen." + permission) || sender.hasPermission("hydrogen.*");
	}
	
}
