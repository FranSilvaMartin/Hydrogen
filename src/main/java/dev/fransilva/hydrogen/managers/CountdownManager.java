package dev.fransilva.hydrogen.managers;

import dev.fransilva.hydrogen.Hydrogen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CountdownManager {
    private int save;
    private int countdown = 5;
    private Location location;
    private ConfigManager configManager;

    // Start the countdown
    public CountdownManager(Hydrogen hydrogen, Player player, Location location, int cooldown, String name) {
        this.configManager = ConfigManager.getInstance();
        this.countdown = cooldown;
        this.location = location;

        save = Bukkit.getScheduler().scheduleSyncRepeatingTask(hydrogen, new Runnable() {
            @Override
            public void run() {
                if (hydrogen.lista.contains(player.getUniqueId())) {
                    if (countdown >= 1) {
                        player.sendMessage(configManager.getMessage("teloported").replace("%countdown%", countdown + "").replace("%name%", name));
                        countdown--;
                    } else if (countdown < 1) {
                        cancel(save);
                        hydrogen.lista.remove(player.getUniqueId());
                        teleport(player, location);
                        countdown = cooldown;
                    }
                } else {
                    player.sendMessage(configManager.getMessage("teleported_canceled"));
                    cancel(save);
                    countdown = cooldown;
                }
            }
        }, 0L, 20L);
    }

    private void teleport(Player player, Location location) {
        if (location != null) {
            player.teleport(location);
            player.sendMessage(configManager.getMessage("teleport_successfully"));
        } else {
            player.sendMessage(configManager.getMessage("spawn_not_found"));
        }
    }

    public void cancel(int taskID) {
        Bukkit.getScheduler().cancelTask(save);
    }
}
