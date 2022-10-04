package dev.fransilva.hydrogen.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.fransilva.hydrogen.utils.CheckUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.fransilva.hydrogen.utils.TextUtils;

public class LoreCommand implements CommandExecutor {

    private Player player;
    private ItemStack item;
    private ItemMeta itemMeta;
    private List<String> lore;
    private HashMap<Integer, String> loreDelete;
    private String text = "";
    private String textUpdate = "";
    private String defaultColor = "&7";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        lore = new ArrayList<String>();


        if (CheckUtils.verifyIfIsAPlayer(sender, true)) {
            if (CheckUtils.hasPermission(sender, command.getName())) {
                player = (Player) sender;
                item = player.getInventory().getItemInMainHand();

                if (args.length == 0) {
                    usageMessage(player);
                    return true;
                }

                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(TextUtils.colorize("&cDebes tener un objeto en la mano"));
                    return true;
                } else {
                    itemMeta = item.getItemMeta();
                    lore = itemMeta.getLore();
                    text = TextUtils.concatArgs(0, args);
                    textUpdate = TextUtils.concatArgs(2, args);

                    if (lore == null) {
                        lore = new ArrayList<String>();
                    }

                    if (args[0].equalsIgnoreCase("add") && args.length >= 2) {
                        text = defaultColor + TextUtils.concatArgs(1, args);
                        lore.add(TextUtils.colorize(text));
                        updateLore(item, itemMeta, lore, player);
                        player.sendMessage(TextUtils.colorize("&eLore agregado al item"));
                        return true;

                    } else if (args[0].equalsIgnoreCase("update") && args.length >= 3) {
                        int index = Integer.parseInt(args[1]);
                        if (lore.size() <= index) {
                            player.sendMessage(TextUtils.colorize("&cNo existe esta linea en el lore"));
                            return true;
                        } else {
                            textUpdate = defaultColor + textUpdate;
                            lore.set(index, TextUtils.colorize(textUpdate));
                            updateLore(item, itemMeta, lore, player);
                            player.sendMessage(TextUtils.colorize("&eActualizado el lore del item en la línea " + args[1]));
                            return true;
                        }

                    } else if (args[0].equalsIgnoreCase("clear")) {
                        lore.clear();
                        updateLore(item, itemMeta, lore, player);
                        player.sendMessage(TextUtils.colorize("&eEl lore ha sido borrado"));
                        return true;

                    } else if (args[0].equalsIgnoreCase("delete")) {

                        int line = Integer.parseInt(args[1]);

                        loreDelete = convertListToHashMap(lore);
                        loreDelete.remove(line);
                        lore.clear();
                        lore.addAll(loreDelete.values());

                        updateLore(item, itemMeta, lore, player);
                        player.sendMessage(TextUtils.colorize("&eLa linea &7(" + line + ")" + " &eha sido modificada"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("insert")) {
                        if (lore != null) {
                            int line = Integer.parseInt(args[1]);
                            text = defaultColor + TextUtils.concatArgs(2, args);
                            lore.add(line, TextUtils.colorize(text));

                            updateLore(item, itemMeta, lore, player);
                            player.sendMessage(TextUtils.colorize("&eLa linea &7(" + line + ")" + " &eha sido modificada"));
                        } else {
                            player.sendMessage(TextUtils.colorize("&cEl item no tiene lore"));
                        }
                        return true;
                    } else {
                        usageMessage(player);
                        return true;
                    }

                }
            }
        }

        return true;
    }

    private void usageMessage(Player player) {
        player.sendMessage(TextUtils.colorize("&cUsage: lore command"));
        player.sendMessage(TextUtils.colorize("&c  - /lore add <text>"));
        player.sendMessage(TextUtils.colorize("&c  - /lore update <line> <text>"));
        player.sendMessage(TextUtils.colorize("&c  - /lore insert <line> <text>"));
        player.sendMessage(TextUtils.colorize("&c  - /lore delete <line>"));
        player.sendMessage(TextUtils.colorize("&c  - /lore clear"));
    }

    private static HashMap<Integer, String> convertListToHashMap(List<String> List) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        int cont = 0;

        for (String line : List) {
            hashMap.put(cont, line);
            cont++;
        }

        return hashMap;
    }

    private void updateLore(ItemStack item, ItemMeta itemMeta, List<String> Lore, Player player) {
        itemMeta.setLore(Lore);
        item.setItemMeta(itemMeta);
        player.updateInventory();
    }


}
