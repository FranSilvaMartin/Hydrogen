package dev.fransilva.hydrogen.managers;

import dev.fransilva.hydrogen.Hydrogen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CountdownManager {
    private int save;
    private int countdown = 5;
    private Hydrogen hydrogen;
    private Location location;
    private ConfigManager configManager;

    // Start the countdown
    public CountdownManager(Player player, Hydrogen hydrogen, int cooldown, String message, String cancelMessage) {
        this.configManager = ConfigManager.getInstance();
        this.countdown = cooldown;

        save = Bukkit.getScheduler().scheduleSyncRepeatingTask(hydrogen, new Runnable() {
            @Override
            public void run() {
                if (hydrogen.lista.contains(player.getUniqueId())) {
                    if (countdown >= 1) {
                        player.sendMessage(configManager.getMessage(message).replace("%countdown%", countdown + ""));
                        countdown--;
                    } else if (countdown < 1) {
                        cancel(save);
                        hydrogen.lista.remove(player.getUniqueId());
                        teleport(player);
                        countdown = cooldown;
                    }
                } else {
                    player.sendMessage(configManager.getMessage(cancelMessage));
                    cancel(save);
                    countdown = cooldown;
                }
            }
        }, 0L, 20L);
    }

    private void teleport(Player player) {
        location = configManager.getLocation("config.yml", "spawn");

        if (location != null) {
            player.teleport(location);
            player.sendMessage(configManager.getMessage("teleport_spawn"));
        } else {
            player.sendMessage(configManager.getMessage("spawn_not_found"));
        }
    }

    // Cancel the countdown
    public void cancel(int taskID) {
        Bukkit.getScheduler().cancelTask(save);
    }

    // Get the countdown
    public int getCountdown() {
        return countdown;
    }

    // Set the countdown
    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    // Get the save
    public int getSave() {
        return save;
    }

    // Set the save

    public void setSave(int save) {
        this.save = save;
    }

    // Get the location
    public Location getLocation() {
        return location;
    }

    // Set the location
    public void setLocation(Location location) {
        this.location = location;
    }

    // Get the config manager
    public ConfigManager getConfigManager() {
        return configManager;
    }

    // Set the config manager
    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    // Get the hydrogen
    public Hydrogen getHydrogen() {
        return hydrogen;
    }

    // Set the hydrogen
    public void setHydrogen(Hydrogen hydrogen) {
        this.hydrogen = hydrogen;
    }
}
