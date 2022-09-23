package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

import java.io.File;
import java.io.IOException;

public class SetHomeCommand implements CommandExecutor {

    private Player player;
    private World world;
    private Location location;
    private Hydrogen hydrogen;
    private ConfigManager configManager;
    private float x, y, z, pitch, yaw;

    public SetHomeCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender, true)) {

            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;

                world = player.getWorld();
                location = player.getLocation();
                x = location.getBlockX();
                y = location.getBlockY();
                z = location.getBlockZ();
                pitch = location.getPitch();
                yaw = location.getYaw();

                if (args.length == 0) {
                    usageMessage(player);
                    return true;
                }

                String name = args[0].toString();
                String warpName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

                player.sendMessage(TextUtils
                        .colorize("&eEl warp &7(" + warpName + ")&e ha sido creado &7(" + x + ", " + y + ", " + z + ")"));

                configManager.createNewCustomConfig("userdata/" + player.getUniqueId() + ".yml", false);
                configManager.addLocation(configManager.getConfig("userdata/" + player.getUniqueId() + ".yml"), location, "homes." + warpName);
            }
            return true;
        }
        return true;
    }

    private void usageMessage(Player player) {
        player.sendMessage(ChatColor.RED + "Usage: /sethome <name>");
    }
}
