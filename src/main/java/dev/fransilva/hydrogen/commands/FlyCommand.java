package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private ConfigManager configManager;

    public FlyCommand() {
        this.configManager = ConfigManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean status;

        if (CheckUtils.verifyIfIsAPlayer(sender, false)) {
            final Player player = (Player) sender;

            if (CheckUtils.hasPermission(player, command.getName())) {
                switch (args.length) {
                    case 0:
                        status = player.getAllowFlight();
                        player.setAllowFlight(!status);
                        if (status) {
                            player.sendMessage(configManager.getMessage("disable_fly"));
                        } else {
                            player.sendMessage(configManager.getMessage("enable_fly"));
                        }
                        break;
                    case 1:
                        if (CheckUtils.hasPermission(player, command.getName() + ".others")) {
                            Player target = Bukkit.getPlayer(args[0]);

                            if (target != null) {
                                status = target.getAllowFlight();
                                target.setAllowFlight(!status);
                                if (status) {
                                    target.sendMessage(configManager.getMessage("disable_fly"));
                                    player.sendMessage(configManager.getMessage("disable_fly_target", player.getName(), target.getName()));
                                } else {
                                    target.sendMessage(configManager.getMessage("enable_fly"));
                                    player.sendMessage(configManager.getMessage("enable_fly_target", player.getName(), target.getName()));
                                }
                            } else {
                                player.sendMessage(configManager.getMessage("player_not_found"));
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        } else {
            switch (args.length) {
                case 0:
                    sender.sendMessage(configManager.getMessage("must_asign_player"));
                    break;
                case 1:
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        status = target.getAllowFlight();
                        target.setAllowFlight(!status);
                        if (status) {
                            target.sendMessage(configManager.getMessage("disable_fly"));
                            sender.sendMessage(configManager.getMessage("disable_fly_target", sender.getName(), target.getName()));
                        } else {
                            target.sendMessage(configManager.getMessage("enable_fly"));
                            sender.sendMessage(configManager.getMessage("enable_fly_target", sender.getName(), target.getName()));
                        }
                    } else {
                        sender.sendMessage(configManager.getMessage("player_not_found"));
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
