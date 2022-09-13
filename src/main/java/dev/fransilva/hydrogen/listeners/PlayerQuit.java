package dev.fransilva.hydrogen.listeners;

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
	
    public PlayerQuit(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
    public void onJoin(PlayerQuitEvent event) {
		
		player = event.getPlayer();
		message = ChatColor.GRAY + "Se ha unido el jugador " + ChatColor.WHITE + player.getName();
		
		event.setQuitMessage(message);
    }
}
