package dev.fransilva.hydrogen.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class ClearChatCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
    private Player player;
	
	public ClearChatCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			player = (Player) sender;
			
			for (int x = 0; x < 1000; x++) {
				Bukkit.broadcastMessage("");
			}
			
			Bukkit.broadcastMessage(TextUtils.colorize("&eChat has been cleared by: &f" + player.getName()));
			return true;
		}

		sender.sendMessage(TextUtils.colorize("&cEl comando debe ser ejecutado por un jugador."));
		return true;
	}
}