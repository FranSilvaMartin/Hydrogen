package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class SetSpawnCommand implements CommandExecutor {

    private Player player;
    private Location location;
    private ConfigManager configManager;
    private double x,y,z;
    private String message;

    public SetSpawnCommand() {
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender, true)) {
            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;
                location = player.getLocation();
                x = location.getBlockX();
                y = location.getBlockY();
                z = location.getBlockZ();

                message = configManager.getMessage("setspawn");
                message = message.replace("%blockX%", x + "");
                message = message.replace("%blockY%", y + "");
                message = message.replace("%blockZ%", z + "");

                player.sendMessage(message);
                configManager.addLocation(configManager.getConfig("config.yml"), location, "spawn");
            }
            return true;
        }

        return true;
    }
}
