package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.fransilva.hydrogen.Hydrogen;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private ConfigManager configManager;
    private Player player;

    public ReloadCommand() {
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CheckUtils.hasPermission(sender, command.getName())) {
            configManager.reloadConfigs();
            sender.sendMessage(configManager.getMessage("reloaded_config"));
        }
        return true;
    }
}
