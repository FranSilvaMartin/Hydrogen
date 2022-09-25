package dev.fransilva.hydrogen.listeners;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.managers.PlayerDataManager;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.fransilva.hydrogen.Hydrogen;
import net.md_5.bungee.api.ChatColor;

public class PlayerQuit implements Listener {
	
	private Player player;
	private String message;

	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
	private PlayerDataManager playerDataManager;
	private ConfigManager configManager;
    public PlayerQuit(Hydrogen plugin) {
        this.hydrogen = plugin;
		this.configManager = ConfigManager.getInstance();
		playerDataManager = new PlayerDataManager(configManager);
    }
	
	@EventHandler
    public void onQuit(PlayerQuitEvent event) {
		
		player = event.getPlayer();
		message = TextUtils.colorize("&cEl jugador ha salido");

		if (!configManager.checkConfigExits("userData/" + player.getUniqueId() + ".yml")) {
			playerDataManager.setupNewPlayerData(player);
		} else {
			playerDataManager.setPlayerLastLocation(player);
		}

		event.setQuitMessage(message);
    }
}
