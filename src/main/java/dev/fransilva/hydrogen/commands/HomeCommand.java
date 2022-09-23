package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.managers.CountdownManager;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HomeCommand implements CommandExecutor {

    private Player player;
    private Hydrogen hydrogen;
    private World world;
    private float x, y, z, pitch, yaw;
    private Location location;
    private int countdown = 5;
    private int save;
    private ArrayList<String> list;
    private ConfigManager configManager;

    public HomeCommand(Hydrogen hydrogen) {
        this.configManager = ConfigManager.getInstance();
        this.hydrogen = hydrogen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            player = (Player) sender;


            list = new ArrayList<String>();
            ConfigurationSection sec = configManager.getConfig("userdata/" + player.getUniqueId() + ".yml").getConfigurationSection("homes");

            if (sec != null) {
                for (String warpName : sec.getKeys(false)) {
                    list.add(warpName);
                }
            }

            if (hydrogen.lista.contains(player.getUniqueId())) {
                return true;
            }

            if (args.length == 0) {
                if (list.isEmpty()) {
                    player.sendMessage(TextUtils.colorize("&cNo hay ningun warp asignado, usa /sethome <name>"));
                } else {
                    String warpList = String.join(", ", list);
                    player.sendMessage(TextUtils.colorize("&cHome list: " + warpList));
                }

                return true;
            }
            String warpSelected = args[0];
            String warpSelected2 = warpSelected.substring(0,1).toUpperCase() + warpSelected.substring(1).toLowerCase();

            if (!checkIfHomeExists(warpSelected2)) {
                player.sendMessage(TextUtils.colorize("&cEl home &e" + warpSelected2 + " &cno existe!"));
                return true;
            }

            if (hydrogen.lista.contains(player.getUniqueId())) {
                return true;
            }

            hydrogen.lista.add(player.getUniqueId());

            new CountdownManager(player, hydrogen, countdown, "teleporting_home", "teleport_cancelled");
            return true;
        }

        return false;
    }

    private void teleport(Player player, String warpSelected) {
        location = configManager.getLocation("userdata/" + player.getUniqueId() + ".yml", "homes." + warpSelected);

        if (location != null) {
            player.teleport(location);
            player.sendMessage(configManager.getMessage("teleport_spawn"));
        } else {
            player.sendMessage(configManager.getMessage("spawn_not_found"));
        }
    }

    private boolean checkIfHomeExists(String warpSelected) {
        return configManager.getConfig("userdata/" + player.getUniqueId() + ".yml").contains("homes." + warpSelected);
    }
}
