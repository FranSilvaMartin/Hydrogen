package dev.fransilva.hydrogen.commands;

import java.util.ArrayList;

import dev.fransilva.hydrogen.managers.ConfigManager;
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

                if (hydrogen.lista.contains(player.getUniqueId())) {
                    return true;
                }

                if (args.length == 0) {
                    ArrayList<String> list = new ArrayList<String>();

                    ConfigurationSection sec = configManager.getConfig("warps.yml").getConfigurationSection("warps");

                    if (sec != null) {
                        for (String warpName : sec.getKeys(false)) {
                            list.add(warpName);
                        }
                    }

                    if (list.isEmpty()) {
                        player.sendMessage(TextUtils.colorize("&cNo hay ningun warp asignado, usa /setwarp <name>"));
                    } else {
                        String warpList = String.join(", ", list);
                        player.sendMessage(TextUtils.colorize("&cWarp list: " + warpList));
                    }

                    return true;
                }
                String warpSelected = args[0];

                if (hydrogen.lista.contains(player.getUniqueId())) {
                    return true;
                }

                hydrogen.lista.add(player.getUniqueId());
                save = Bukkit.getScheduler().scheduleSyncRepeatingTask(hydrogen.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (hydrogen.lista.contains(player.getUniqueId())) {
                            if (countdown >= 1) {
                                player.sendMessage(
                                        ChatColor.YELLOW + "Teletransportandote al warp " + warpSelected + " en " + ChatColor.GRAY + countdown);
                                countdown--;
                            } else if (countdown < 1) {
                                Bukkit.getScheduler().cancelTask(save);
                                hydrogen.lista.remove(player.getUniqueId());
                                teleport(player, warpSelected);
                                countdown = 5;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Teletransporte cancelado, te has movido.");
                            Bukkit.getScheduler().cancelTask(save);
                            countdown = 5;
                        }
                    }
                }, 0, 20);
            }
            return true;
        }
        return false;
    }

    private void teleport(Player player, String warpSelected) {

        location = configManager.getLocation("warps.yml", "warps." + warpSelected);

        if (world == null) {
            player.sendMessage(ChatColor.RED + "No hay un spawn establecido");
            return;
        }

        if (location != null) {
            player.teleport(location);
            player.sendMessage(ChatColor.YELLOW + "Has sido teletransportado correctamente.");
        } else {
            player.sendMessage(ChatColor.RED + "No hay un spawn establecido");
        }
    }
}
