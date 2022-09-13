package dev.fransilva.hydrogen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.fransilva.hydrogen.Hydrogen;

public class HealCommand implements CommandExecutor {
	
	private Player player;
	private Player target;
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
    public HealCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			player = (Player) sender;
			
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.YELLOW + "Has sido curado correctamente");
				player.setHealth(20);
				return true;
			} 
			
			target = Bukkit.getPlayer(args[0]);
			
			if (target != null) {
				target.setHealth(20);
				player.sendMessage(ChatColor.YELLOW + "Has curado a " + ChatColor.GRAY + target.getName());
				target.sendMessage(ChatColor.YELLOW + "Has sido curado");
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "Jugador no encontrado");
				return true;
			}
		}
		
		return true;
	}

}
