package dev.fransilva.hydrogen.listeners;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.managers.PlayerDataManager;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.fransilva.hydrogen.Hydrogen;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {

    @SuppressWarnings("unused")
    private Hydrogen hydrogen;
    private Player player;
    private String playerName;
    private String message;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;

    public PlayerJoin(Hydrogen plugin) {
        this.hydrogen = plugin;
        this.configManager = ConfigManager.getInstance();
        this.playerDataManager = new PlayerDataManager(configManager);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        player = event.getPlayer();
        playerName = player.getName();
        message = ChatColor.GRAY + "Se ha unido el jugador " + ChatColor.WHITE + playerName;

        event.setJoinMessage(message);
    }

    @EventHandler
    public void firstJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        if (!player.hasPlayedBefore()) {
            Location location = configManager.getLocation("config.yml", "spawn");

            if (location != null) {
                player.teleport(location);
            }
        }

        if (!configManager.checkConfigExits("userData/" + player.getUniqueId() + ".yml")) {
            playerDataManager.setupNewPlayerData(player);
        } else {
            player.sendMessage(TextUtils.colorize("&eTus datos han sido cargados correctamente."));
            playerDataManager.setPlayerLastLocation(player);
            playerDataManager.setPlayerLastJoin(player);
            playerDataManager.setPlayerName(player);
            playerDataManager.setPlayerLastIp(player);
        }


    }
}
