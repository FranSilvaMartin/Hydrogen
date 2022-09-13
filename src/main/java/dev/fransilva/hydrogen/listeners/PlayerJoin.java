package dev.fransilva.hydrogen.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.fransilva.hydrogen.Hydrogen;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener {
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
	private Player player;
	private String playerName;
	private String message;
	
    public PlayerJoin(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event) {
		player = event.getPlayer();
		playerName = player.getName();
		message = ChatColor.GRAY + "Se ha unido el jugador " + ChatColor.WHITE + playerName;
		
		event.setJoinMessage(message);
    }
}
