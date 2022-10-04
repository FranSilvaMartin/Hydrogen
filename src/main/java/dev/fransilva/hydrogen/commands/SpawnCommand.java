package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.managers.CountdownManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class SpawnCommand implements CommandExecutor {

    private Player player;
    private Hydrogen hydrogen;
    private ConfigManager configManager;
    private int countdown = 5;
    private int save;
    private World world;
    private float x, y, z, pitch, yaw;
    private Location location;

    public SpawnCommand(Hydrogen hydrogen) {
        this.configManager = ConfigManager.getInstance();
        this.hydrogen = hydrogen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender, true)) {
            player = (Player) sender;

            if (CheckUtils.hasPermission(player, command.getName())) {
                if (hydrogen.lista.contains(player.getUniqueId())) {
                    return true;
                }

                hydrogen.lista.add(player.getUniqueId());
                location = configManager.getLocation("config.yml","spawn");
                new CountdownManager(hydrogen, player, location, countdown, "spawn");
                return true;
            }
        }
        return true;
    }
}
