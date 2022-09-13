package dev.fransilva.hydrogen.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class PlayerChat implements Listener {
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
	private Player player;
	private String message;

    public PlayerChat(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event) {
		message = TextUtils.colorize(event.getMessage());
		player = event.getPlayer();
		event.setFormat(TextUtils.colorize("&7(&c" + player.getName() + "&7) &f" + message));
	}
}
