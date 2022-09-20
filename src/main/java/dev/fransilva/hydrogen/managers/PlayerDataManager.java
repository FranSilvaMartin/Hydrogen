package dev.fransilva.hydrogen.managers;

import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.entity.Player;

public class PlayerDataManager {

    private ConfigManager configManager;

    public PlayerDataManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void createPlayerData(String uuid) {
        configManager.createNewCustomConfig("userdata/" + uuid + ".yml", false);
    }

    public void setPlayerData(String uuid, String path, Object value) {
        configManager.setData(configManager.getConfig("userdata/" + uuid + ".yml"), path, value);
    }

    public Object getPlayerData(String uuid, String path) {
        return configManager.getString("userdata/" + uuid + ".yml", path);
    }

    public void setupPlayerData(Player player) {
        createPlayerData(player.getUniqueId().toString());
        setPlayerData(player.getUniqueId().toString(), "name", player.getName());
        setPlayerData(player.getUniqueId().toString(), "uuid", player.getUniqueId().toString());
        setPlayerData(player.getUniqueId().toString(), "ip", player.getAddress().getAddress().getHostAddress());
        setPlayerData(player.getUniqueId().toString(), "first-join", player.getFirstPlayed());
        setPlayerData(player.getUniqueId().toString(), "last-join", player.getLastPlayed());
        setPlayerData(player.getUniqueId().toString(), "last-location", player.getLocation());
        setPlayerData(player.getUniqueId().toString(), "last-location.world", player.getWorld().getName());
        setPlayerData(player.getUniqueId().toString(), "last-location.x", player.getLocation().getX());
        setPlayerData(player.getUniqueId().toString(), "last-location.y", player.getLocation().getY());
        setPlayerData(player.getUniqueId().toString(), "last-location.z", player.getLocation().getZ());
        setPlayerData(player.getUniqueId().toString(), "last-location.pitch", player.getLocation().getPitch());
        setPlayerData(player.getUniqueId().toString(), "last-location.yaw", player.getLocation().getYaw());
        player.sendMessage(TextUtils.colorize("&3Tus datos han sido cargados correctamente."));
    }
}
