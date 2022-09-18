package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class SpawnCommand implements CommandExecutor {

    private Player player;
    private Hydrogen hydrogen;
    private ConfigManager configManager;
    private int countdown = 5;
    private int save;
    private World world;
    private float x, y, z, pitch, yaw;
    private Location location;

    public SpawnCommand(Hydrogen hydrogen) {
        this.configManager = ConfigManager.getInstance();
        this.hydrogen = hydrogen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            player = (Player) sender;

            if (hydrogen.lista.contains(player.getUniqueId())) {
                return true;
            }

            hydrogen.lista.add(player.getUniqueId());
            save = Bukkit.getScheduler().scheduleSyncRepeatingTask(hydrogen, new Runnable() {
                @Override
                public void run() {
                    if (hydrogen.lista.contains(player.getUniqueId())) {
                        if (countdown >= 1) {
                            player.sendMessage(
                                    ChatColor.YELLOW + "Teletransportandote al spawn en " + ChatColor.GRAY + countdown);
                            countdown--;
                        } else if (countdown < 1) {
                            Bukkit.getScheduler().cancelTask(save);
                            hydrogen.lista.remove(player.getUniqueId());
                            teletransporte(player);
                            countdown = 5;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Teletransporte cancelado, te has movido.");
                        Bukkit.getScheduler().cancelTask(save);
                        countdown = 5;
                    }
                }
            }, 0, 1);

            return true;
        }

        return false;
    }

    private boolean teletransporte(Player player) {
        location = configManager.getLocation("config.yml", "spawn");

        if (location != null) {
            player.teleport(location);
            player.sendMessage(ChatColor.YELLOW + "Has sido teletransportado correctamente.");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "No hay un spawn establecido");
            return false;
        }
    }
}
