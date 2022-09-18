package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class ClearChatCommand implements CommandExecutor {

    private ConfigManager configManager;

    public ClearChatCommand() {
        this.configManager = ConfigManager.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CheckUtils.hasPermission(sender, command.getName())) {
            for (int x = 0; x < 950; x++) {
                Bukkit.broadcastMessage("");
            }
            Bukkit.broadcastMessage(configManager.getMessage("chat_cleaned", sender.getName()));
        }
        return true;
    }
}