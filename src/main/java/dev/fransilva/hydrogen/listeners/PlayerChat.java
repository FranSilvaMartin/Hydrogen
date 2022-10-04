package dev.fransilva.hydrogen.listeners;

import dev.fransilva.hydrogen.managers.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChat implements Listener {

    @SuppressWarnings("unused")
    private Hydrogen hydrogen;
    private Player player;
    private String message;
    private ConfigManager configManager;

    public PlayerChat(Hydrogen plugin) {
        this.hydrogen = plugin;
        this.configManager = ConfigManager.getInstance();
    }

    @EventHandler
    public void playerChat(PlayerChatEvent event) {
        message = TextUtils.colorize(event.getMessage());
        player = event.getPlayer();

        LuckPerms api = LuckPermsProvider.get();

        String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "";
        String suffix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getSuffix() != null ? api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getSuffix() : "";
        String group = api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup() != null ? api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup() : "";

        event.setFormat(TextUtils.colorize(configManager.getConfig("config.yml").getString("chat-format")
                .replace("%rank%", prefix)
                .replace("%suffix%", suffix)
                .replace("%player%", player.getName())
                .replace("%message%", message)));
    }

    // Get chat format

}
