package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private Player player;
    private Player target;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;

                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /gamemode (0-3)");
                    return true;
                }

                if (CheckUtils.hasPermission(sender, command.getName() + ".target")) {
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            player.sendMessage("Has modificado el modo de juego de " + ChatColor.GRAY + target.getName());
                            gamemodeChanger(target, args[0]);
                        } else {
                            player.sendMessage(ChatColor.RED + "Jugador no encontrado");
                            return true;
                        }
                    } else {
                        gamemodeChanger(player, args[0]);
                    }
                }
            }
        }
        return true;
    }

    private void enviarMensaje(Player player, String args) {
        player.sendMessage(ChatColor.YELLOW + "Tu modo de juego ha sido cambiado a " + ChatColor.GRAY + args);
    }

    private void gamemodeChanger(Player player, String args) {
        switch (args.toUpperCase()) {
            case "0":
            case "SURVIVAL":
                player.setGameMode(GameMode.SURVIVAL);
                enviarMensaje(player, "SURVIVAL");
                break;
            case "1":
            case "CREATIVE":
                player.setGameMode(GameMode.CREATIVE);
                enviarMensaje(player, "CREATIVE");
                break;
            case "2":
            case "ADVENTURE":
                player.setGameMode(GameMode.ADVENTURE);
                enviarMensaje(player, "ADVENTURE");
                break;
            case "3":
            case "SPECTATOR":
                player.setGameMode(GameMode.SPECTATOR);
                enviarMensaje(player, "SPECTATOR");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /gamemode <0-3> <player>");
                break;
        }
    }

}
