package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
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
    private Hydrogen hydrogen;

    public SetSpawnCommand(Hydrogen plugin) {
        this.configManager = ConfigManager.getInstance();
        this.hydrogen = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            player = (Player) sender;
            location = player.getLocation();

            player.sendMessage(ChatColor.YELLOW + "El spawn ha sido establecido correctamente. " + ChatColor.GRAY + "(x; " + location.getBlockX() + ", y; " + location.getBlockY() + ", z; " + location.getBlockZ() + ")");
            configManager.addLocation(configManager.getConfig("config.yml"), location, "spawn");
            return true;
        }

        return false;
    }
}
