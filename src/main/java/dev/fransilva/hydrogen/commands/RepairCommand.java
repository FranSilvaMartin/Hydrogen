package dev.fransilva.hydrogen.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;
import net.md_5.bungee.api.ChatColor;

public class RepairCommand implements CommandExecutor {

	private Player player;
	private ItemStack item;
	private ItemMeta itemMeta;
	private Damageable damageItem;
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
    public RepairCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			player = (Player) sender;
			item = player.getInventory().getItemInMainHand();
			
			if (item == null || item.getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "Debes tener un objeto en la mano");
				return true;
			} else {
				itemMeta = item.getItemMeta();
				damageItem = (Damageable) itemMeta;
				
				if (damageItem.getDamage() >= player.getInventory().getItemInMainHand().getType().getMaxDurability()) {
					player.sendMessage(ChatColor.RED + "Este objeto no puede ser reparado");
				} else {
					damageItem.setDamage(0);
					item.setItemMeta(damageItem);
					player.updateInventory();
					
					if (itemMeta.getDisplayName().equals("")) {
						player.sendMessage(TextUtils.colorize("&eEl Item &7(" + item.getType() + "&7) &ese ha reparado correctamente"));
					} else {
						String itemName = itemMeta.getDisplayName();
						player.sendMessage(TextUtils.colorize("&eEl Item &7(" + itemName.substring(0, itemName.length()-1) + "&7) &ese ha reparado correctamente"));
					}
				}
				return true;
			}
		}

		return false;
	}

}
