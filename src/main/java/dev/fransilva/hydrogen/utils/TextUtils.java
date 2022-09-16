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
	
}
