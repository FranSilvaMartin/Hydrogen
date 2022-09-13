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

public class SetWarpCommand implements CommandExecutor {

	private Player player;
	private World world;
	private Location location;
	private Hydrogen hydrogen;
	private float x, y, z, pitch, yaw;
	
	public SetWarpCommand(Hydrogen plugin) {
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

			/*hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".world-name", world.getName());
			hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".x", x);
			hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".y", y);
			hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".z", z);
			hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".pitch", pitch);
			hydrogen.getCustomConfig().getWarpsConfig().set("warps." + name + ".yaw", yaw);

			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".world-name", world.getName());
			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".x", x);
			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".y", y);
			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".z", z);
			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".pitch", pitch);
			hydrogen.getCustomConfig().getConfig().set("warps." + name + ".yaw", yaw);
			hydrogen.getCustomConfig().saveConfig(hydrogen.getCustomConfig().getWarpsFile(), hydrogen.getCustomConfig().getWarpsConfig());
			hydrogen.getCustomConfig().saveConfig(hydrogen.getCustomConfig().getDefaultFile(), hydrogen.getCustomConfig().getConfig());*/
			return true;
		}

		return false;
	}

	private void usageMessage(Player player) {
		player.sendMessage(ChatColor.RED + "Usage: /setwarp <name>");
	}

}
