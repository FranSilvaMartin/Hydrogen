package dev.fransilva.hydrogen.commands;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.CheckUtils;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private Hydrogen hydrogen;
    public FlyCommand(Hydrogen plugin) {
        this.hydrogen = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (CheckUtils.verifyIfIsAPlayer(sender)) return true;
        final Player player = (Player) sender;

        if (CheckUtils.hasPermission(player, "command.fly") || sender.isOp()) {
            boolean status;
            switch (args.length) {
                case 0:
                    status = player.getAllowFlight();
                    player.setAllowFlight(!status);
                    if (status) {
                        player.sendMessage(TextUtils.colorize("disable_fly"));
                    } else {
                        player.sendMessage(TextUtils.colorize("enable_fly"));
                    }
                    break;
                case 1:
                    if (CheckUtils.hasPermission(player, "command.fly.others") || sender.isOp()) {
                        String targetArg = args[0];
                        Player target = Bukkit.getPlayer(targetArg);
                        if (target != null) {
                            status = target.getAllowFlight();
                            target.setAllowFlight(!status);
                            if (status) {
                                target.sendMessage(TextUtils.colorize("disable_fly"));
                                player.sendMessage(TextUtils.colorize("disable_fly_target"));
                            } else {
                                target.sendMessage(TextUtils.colorize("enable_fly"));
                                player.sendMessage(TextUtils.colorize("enable_fly_target"));
                            }
                        } else {
                            player.sendMessage("TextUtils.getPlayerNotFound()");
                        }
                    } else {
                        player.sendMessage("TextUtils.getNoPermission()");
                    }
                    break;
                default:
                    break;
            }
        } else {
            player.sendMessage("TextUtils.getNoPermission()");
        }
        return true;
    }
}
