package dev.fransilva.hydrogen.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;
import net.md_5.bungee.api.ChatColor;

public class RenameCommand implements CommandExecutor {
	
	private ItemStack item;
	private ItemMeta itemMeta;
	private String rename;
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
    public RenameCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			item = player.getInventory().getItemInMainHand();
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "Usage: /rename &eGOD");
				return true;
			}
			
			if (item == null || item.getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "Debes tener un objeto en la mano");
				return true;
			} else {
				itemMeta = item.getItemMeta();
				rename = TextUtils.colorize(TextUtils.concatArgs(0, args));
				itemMeta.setDisplayName(rename);
				item.setItemMeta(itemMeta);
				
				player.sendMessage(ChatColor.YELLOW + "El item ha sido renombreado");
				player.updateInventory();
				return true;
			}
		}
		
		return false;
	}

}
