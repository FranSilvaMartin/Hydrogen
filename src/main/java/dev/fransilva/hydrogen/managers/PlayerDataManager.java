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

    // Comprueba si el jugador tiene un archivo creado en la carpeta de userData
    public boolean hasPlayerData(Player player) {
        if (configManager.getConfig("userdata/" + player.getUniqueId() + ".yml") != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setupNewPlayerData(Player player) {
        createPlayerData(player.getUniqueId().toString());
        setPlayerData(player.getUniqueId().toString(), "name", player.getName());
        setPlayerData(player.getUniqueId().toString(), "uuid", player.getUniqueId().toString());
        setPlayerData(player.getUniqueId().toString(), "ip", player.getAddress().getAddress().getHostAddress());
        setPlayerData(player.getUniqueId().toString(), "first-join", TextUtils.getCurrentDate());
        setPlayerData(player.getUniqueId().toString(), "last-join", TextUtils.getCurrentDate());
        setPlayerData(player.getUniqueId().toString(), "last-location", player.getLocation());
        setPlayerData(player.getUniqueId().toString(), "last-location.world", player.getWorld().getName());
        setPlayerData(player.getUniqueId().toString(), "last-location.x", player.getLocation().getX());
        setPlayerData(player.getUniqueId().toString(), "last-location.y", player.getLocation().getY());
        setPlayerData(player.getUniqueId().toString(), "last-location.z", player.getLocation().getZ());
        setPlayerData(player.getUniqueId().toString(), "last-location.pitch", player.getLocation().getPitch());
        setPlayerData(player.getUniqueId().toString(), "last-location.yaw", player.getLocation().getYaw());
        player.sendMessage(TextUtils.colorize("&cHas sido agregado en la base de datos, Bienvenido!"));
    }

    //Set the player last join date
    public void setPlayerLastJoin(Player player) {
        setPlayerData(player.getUniqueId().toString(), "last-join", TextUtils.getCurrentDate());
    }

    //Set the player last location
    public void setPlayerLastLocation(Player player) {
        setPlayerData(player.getUniqueId().toString(), "last-location", player.getLocation());
        setPlayerData(player.getUniqueId().toString(), "last-location.world", player.getWorld().getName());
        setPlayerData(player.getUniqueId().toString(), "last-location.x", player.getLocation().getX());
        setPlayerData(player.getUniqueId().toString(), "last-location.y", player.getLocation().getY());
        setPlayerData(player.getUniqueId().toString(), "last-location.z", player.getLocation().getZ());
        setPlayerData(player.getUniqueId().toString(), "last-location.pitch", player.getLocation().getPitch());
        setPlayerData(player.getUniqueId().toString(), "last-location.yaw", player.getLocation().getYaw());
    }

    //Set the player last ip
    public void setPlayerLastIp(Player player) {
        setPlayerData(player.getUniqueId().toString(), "ip", player.getAddress().getAddress().getHostAddress());
    }

    //Set the player name
    public void setPlayerName(Player player) {
        setPlayerData(player.getUniqueId().toString(), "name", player.getName());
    }
}
