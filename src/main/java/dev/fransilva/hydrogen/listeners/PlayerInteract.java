package dev.fransilva.hydrogen.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class PlayerInteract implements Listener {

	private Player player;
	private Sign sign;

	private Inventory inv;
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
	
    public PlayerInteract(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(1).equals(TextUtils.colorize("&0[&3Disposal&0]"))) {
					 inv = Bukkit.createInventory(null, 54, TextUtils.colorize("&0&lDisposal - Trash"));
					 player.openInventory(inv);					 
					 player.sendMessage(TextUtils.colorize("&eLos items han sido depositados correctamente"));
				}
			}
		}
	}
}
