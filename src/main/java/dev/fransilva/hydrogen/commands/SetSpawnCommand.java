package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class SetSpawnCommand implements CommandExecutor {

	private Player player;
	private World world;
	private Location location;
	private ConfigManager configManager;
	private Hydrogen hydrogen;
	private float x, y, z, pitch, yaw;
	
    public SetSpawnCommand(Hydrogen plugin) {
		this.configManager = ConfigManager.getInstance();
        this.hydrogen = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			player = (Player) sender;
			
			world = player.getWorld();
			location = player.getLocation();
			x = location.getBlockX();
			y = location.getBlockY();
			z = location.getBlockZ();
			pitch = location.getPitch();
			yaw = location.getYaw();

			player.sendMessage(ChatColor.YELLOW + "El spawn ha sido establecido correctamente. " + ChatColor.GRAY + "(" + x + ", " + y + ", " + z + ")");
			configManager.setData(configManager.getConfig("config.yml"), "spawn.worldName", world.getName());
			configManager.setData(configManager.getConfig("config.yml"), "spawn.x", x);
			configManager.setData(configManager.getConfig("config.yml"), "spawn.y", y);
			configManager.setData(configManager.getConfig("config.yml"), "spawn.z", z);
			configManager.setData(configManager.getConfig("config.yml"), "spawn.pitch", pitch);
			configManager.setData(configManager.getConfig("config.yml"), "spawn.yaw", yaw);

			return true;
		}
		
		return false;
	}
}
