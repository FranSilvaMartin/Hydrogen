package dev.fransilva.hydrogen.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import dev.fransilva.hydrogen.Hydrogen;

public class PlayerMove implements Listener {

	private Hydrogen hydrogen;
	private Player player;
	
    public PlayerMove(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {

		player = event.getPlayer();

		if (!event.getFrom().getBlock().equals(event.getTo().getBlock())) {
			if (hydrogen.lista.contains(player.getUniqueId())) {
				hydrogen.lista.remove(player.getUniqueId());
			}
		}
	}

}
