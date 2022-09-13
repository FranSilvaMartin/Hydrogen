package dev.fransilva.hydrogen.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class WarpCommand implements CommandExecutor {

	private Player player;
	private Hydrogen hydrogen;
	private World world;
	private float x, y, z, pitch, yaw;
	private Location location;
	private int countdown = 5;
	private int save;

	public WarpCommand(Hydrogen plugin) {
		this.hydrogen = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			player = (Player) sender;

			if (hydrogen.lista.contains(player.getUniqueId())) {
				return true;
			}

			if (args.length == 0) {
				ArrayList<String> list = new ArrayList<String>();

				/*ConfigurationSection sec = getWarpConfig().getConfigurationSection("warps");

				if (sec != null) {
					for (String warpName : sec.getKeys(false)) {
						list.add(warpName);
					}
				}*/

				if (list.isEmpty()) {
					player.sendMessage(TextUtils.colorize("&cNo hay ningun warp asignado, usa /setwarp <name>"));
				} else {
					String warpList = String.join(", ", list);
					player.sendMessage(TextUtils.colorize("&cWarp list: " + warpList));
				}

				return true;
			}
			String warpSelected = args[0];
			
			if (hydrogen.lista.contains(player.getUniqueId())) {
				return true;
			}

			hydrogen.lista.add(player.getUniqueId());
			save = Bukkit.getScheduler().scheduleSyncRepeatingTask(hydrogen.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (hydrogen.lista.contains(player.getUniqueId())) {
						if (countdown >= 1) {
							player.sendMessage(
									ChatColor.YELLOW + "Teletransportandote al warp " + warpSelected + " en " + ChatColor.GRAY + countdown);
							countdown--;
						} else if (countdown < 1) {
							Bukkit.getScheduler().cancelTask(save);
							hydrogen.lista.remove(player.getUniqueId());
							teletransporte(player, warpSelected);
							countdown = 5;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Teletransporte cancelado, te has movido.");
						Bukkit.getScheduler().cancelTask(save);
						countdown = 5;
					}
				}
			}, 0, 20);
			
			return true;
		}

		return false;
	}
	
	private boolean teletransporte(Player player, String warpSelected) {
		
		/*world = Bukkit.getWorld(getWarpConfig().getString("warps." + warpSelected + ".world-name"));
		x = (float) getWarpConfig().getDouble("warps." + warpSelected + ".x");
		y = (float) getWarpConfig().getDouble("warps." + warpSelected + ".y");
		z = (float) getWarpConfig().getDouble("warps." + warpSelected + ".z");
		yaw = (float) getWarpConfig().getDouble("warps." + warpSelected + ".yaw");
		pitch = (float) getWarpConfig().getDouble("warps." + warpSelected + ".pitch");*/

		if (world == null) {
			player.sendMessage(ChatColor.RED + "No hay un spawn establecido");
			return false;
		}
		
		location = new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);

		if (location != null) {
			player.teleport(location);
			player.sendMessage(ChatColor.YELLOW + "Has sido teletransportado correctamente.");
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "No hay un spawn establecido");
			return false;
		}
	}
	
	/*private FileConfiguration getWarpConfig() {
		return hydrogen.getCustomConfig().getWarpsConfig();
	}*/
}
