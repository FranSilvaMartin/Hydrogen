package dev.fransilva.hydrogen.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportManager {

    public TeleportManager(Player player, Location location) {
        if (location != null) {
            player.teleport(location);
        } else {
            player.sendMessage("not_found");
        }
    }
}
