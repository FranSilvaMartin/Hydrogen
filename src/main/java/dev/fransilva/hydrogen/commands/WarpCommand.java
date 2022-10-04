package dev.fransilva.hydrogen.commands;

import java.util.ArrayList;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.managers.CountdownManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class WarpCommand implements CommandExecutor {

    private Player player;
    private Hydrogen hydrogen;
    private World world;
    private float x, y, z, pitch, yaw;
    private Location location;
    private int countdown = 5;
    private int save;
    private ConfigManager configManager;

    public WarpCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender, true)) {
            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;
                ArrayList<String> warpList = new ArrayList<String>();

                if (hydrogen.lista.contains(player.getUniqueId())) {
                    return true;
                }

                if (args.length == 0) {
                    ConfigurationSection sec = configManager.getConfig("warps.yml").getConfigurationSection("warps");

                    if (sec != null) {
                        for (String warpName : sec.getKeys(false)) {
                            warpList.add(warpName);
                        }
                    }

                    if (warpList.isEmpty()) {
                        player.sendMessage(TextUtils.colorize("&cNo hay ningun warp asignado, usa /setwarp <name>"));
                    } else {
                        String warps = String.join(", ", warpList);
                        player.sendMessage(TextUtils.colorize("&cWarp list: " + warps));
                    }
                    return true;
                }
                String warpSelected = args[0];

                if (warpExists(warpSelected)) {
                    if (hydrogen.lista.contains(player.getUniqueId())) {
                        return true;
                    }

                    hydrogen.lista.add(player.getUniqueId());
                    location = configManager.getLocation("warps.yml", "warps." + warpSelected);
                    new CountdownManager(hydrogen, player, location, countdown, "warp " + warpSelected);
                } else {
                    player.sendMessage(configManager.getMessage("warp_not_found").replace("%warpname%", warpSelected));
                    return true;
                }
                return true;
            }
        }
        return true;
    }

    public boolean warpExists(String warpName) {
        return configManager.getConfig("warps.yml").contains("warps." + warpName);
    }
}
