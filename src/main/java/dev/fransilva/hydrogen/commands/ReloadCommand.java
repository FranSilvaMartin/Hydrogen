package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class ReloadCommand implements CommandExecutor {
	
	private Hydrogen hydrogen;
	private Player player;
	
	public ReloadCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
 
		if (sender instanceof Player) {
			player = (Player) sender;
			player.sendMessage(TextUtils.colorize("&cSe ha recargado la configuración correctamente"));
		}

        ConfigManager.getInstance().reloadConfigs();
        Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&cSe ha recargado la configuración correctamente"));
    
        return true;
    }
}
