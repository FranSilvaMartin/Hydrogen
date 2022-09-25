package dev.fransilva.hydrogen.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

	public static String getPrefix() {
		return colorize("&8[&bHydrogen&8] &7");
	}

	public static final String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
}
