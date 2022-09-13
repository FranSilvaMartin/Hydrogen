package dev.fransilva.hydrogen.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class SetHomeCommand implements CommandExecutor {

	private Player player;
	private World world;
	private Location location;
	private Hydrogen hydrogen;
	private float x, y, z, pitch, yaw;
	
	public SetHomeCommand(Hydrogen plugin) {
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

			if (args.length == 0) {
				usageMessage(player);
				return true;
			}

			String name = args[0].toString();

			player.sendMessage(TextUtils
					.colorize("&eEl warp &7(" + name + ")&e ha sido creado &7(" + x + ", " + y + ", " + z + ")"));
			
			/*getHomesConfig().set(player.getUniqueId() + "." + name + ".world-name", world.getName());
			getHomesConfig().set(player.getUniqueId() + "." + name + ".x", x);
			getHomesConfig().set(player.getUniqueId() + "." + name + ".y", y);
			getHomesConfig().set(player.getUniqueId() + "." + name + ".z", z);
			getHomesConfig().set(player.getUniqueId() + "." + name + ".pitch", pitch);
			getHomesConfig().set(player.getUniqueId() + "." + name + ".yaw", yaw);
			hydrogen.getCustomConfig().saveConfig();*/
			return true;
		}
		return false;
	}

	private void usageMessage(Player player) {
		player.sendMessage(ChatColor.RED + "Usage: /sethome <name>");
	}

	/*private FileConfiguration getHomesConfig() {
		return hydrogen.getCustomConfig().getHomesConfig();
	}*/
}
