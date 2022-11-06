package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.managers.KitsManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private Player player;
    private Player target;

    private Hydrogen hydrogen;

    public KitCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Create a new KitCommand instance
        KitsManager kitsManager = new KitsManager(hydrogen);


        if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;

                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /gamemode (0-3)");
                    return true;
                }

                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("create")) {
                        kitsManager.createKit(player, args[1]);
                        player.sendMessage(ChatColor.GREEN + "Kit " + args[1] + " created!");
                    } else {
                        kitsManager.giveKit(player, args[0]);
                        player.sendMessage(ChatColor.GREEN + "Kit " + args[0] + " given!");
                    }
                }
            }
        }
        return true;
    }

}
