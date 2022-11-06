package dev.fransilva.hydrogen.managers;

import dev.fransilva.hydrogen.Hydrogen;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class KitsManager {

    private Hydrogen plugin;

    /**
     * Our constructor.
     *
     * @param plugin
     */
    public KitsManager(Hydrogen plugin) {
        this.plugin = plugin;
    }

    /**
     * This is the method used to create kits.
     *
     * @param p       ~ The player who created the kit.
     * @param kitName ~ The string which will from hereafter be the name of the kit.
     */
    public void createKit(Player p, String kitName) {
        FileConfiguration conf = ConfigManager.getInstance().getConfig("config.yml");
        PlayerInventory inv = p.getInventory();

        // Now, before creating a kit, we need to make sure that the kit does not already exist.
        if (conf.getConfigurationSection("kits." + kitName) != null) {
            p.sendMessage(kitName + " already exists!");
            return;
        }

        // We'll be using this path a lot to set items and stuff for the kits.
        String path = "kits." + kitName + ".";
        // Create a new Configuration Section
        conf.createSection("kits." + kitName);

        /*
         * This is our for loop. It iterates through all the main inventory slots (Slots 0 - 35).
         * If a player has an item there, it will set it in the config.
         */
        for (int i = 0; i < 36; i++) {
            ItemStack is = inv.getItem(i);

            // If null or air, continue, because we don't care anymore since that just wastes space in the config.
            if (is == null || is.getType() == Material.AIR)
                continue;

            // For each number, representing the inventory slot, we'll need to set several things such as the material.
            String slot = path + "items." + i;
            // Material.GOLD_INGOT ==> gold_ingot - This is unnecessary but looks better in the config and easier to read (in my opinion)
            conf.set(slot + ".type", is.getType().toString().toLowerCase());
            conf.set(slot + ".amount", is.getAmount());

            // Do not go any further if the item does not have any item meta.
            if (!is.hasItemMeta())
                continue;

            /*
             * Now, we are just going to be checking individually if there is a name, lore, etc..
             */
            if (is.getItemMeta().hasDisplayName())
                conf.set(slot + ".name", is.getItemMeta().getDisplayName());

            if (is.getItemMeta().hasLore())
                conf.set(slot + ".lore", is.getItemMeta().getLore());

            // We are going to be adding enchantments as a stringlist.
            if (is.getItemMeta().hasEnchants()) {
                Map<Enchantment, Integer> enchants = is.getEnchantments();
                List<String> enchantList = new ArrayList<String>();
                for (Enchantment e : is.getEnchantments().keySet()) {
                    int level = enchants.get(e);
                    enchantList.add(e.getName().toLowerCase() + ":" + level);
                }
                conf.set(slot + ".enchants", enchantList);
            }
            // While 'continue;' isn't always necessary, I feel it's good practice to put it.
            continue;
        }

        conf.set(path + "armor.helmet", inv.getHelmet() != null ? inv
                .getHelmet().getType().toString().toLowerCase() : "air");
        conf.set(path + "armor.chestplate", inv.getChestplate() != null ? inv
                .getChestplate().getType().toString().toLowerCase() : "air");
        conf.set(path + "armor.leggings", inv.getLeggings() != null ? inv
                .getLeggings().getType().toString().toLowerCase() : "air");
        conf.set(path + "armor.boots", inv.getBoots() != null ? inv
                .getBoots().getType().toString().toLowerCase() : "air");

        ConfigManager.getInstance().saveData(conf);

        // Therefore, Operators have all kits by default.


        //NOTE: For the purpose of this tutorial, I will not be covering per-kit permissions unless I get many requests to add it.
    }

    /*
     * This method has deprecation warnings: p.updateInventory() is the only deprecated method.
     */
    @SuppressWarnings("deprecation")
    public void giveKit(Player p, String kitName) {
        FileConfiguration config = ConfigManager.getInstance().getConfig("config.yml");
        // Sanity-checks
        if (config.getConfigurationSection("kits." + kitName) == null) {
            p.sendMessage(kitName + " does not exist!");
            return;
        }

        // Clear the player's inventory so they don't have extra items
        // p.getInventory().clear();
        // Again, we need this path.
        String path = "kits." + kitName + ".";
        // Because it exists, we can use the configuration section.
        ConfigurationSection s = config.getConfigurationSection(path + "items");
        // Loop through the different keys directly next in line, so to speak.
        for (String str : s.getKeys(false)) {
            int slot = Integer.parseInt(str);
            // Return (continue) if the slot is an invalid number.
            if (0 > slot && slot > 36)
                return;

            // Just cast some variables for easy use. Our null checks are later on.
            String string = path + "items." + slot + ".";
            String type = config.getString(string + "type");
            String name = config.getString(string + "name");
            List<String> lore = config.getStringList(string + "lore");
            List<String> enchants = config.getStringList(string + "enchants");
            int amount = config.getInt(string + "amount");

            // If you remember, we converted the name of the material to
            // lowercase. Because it's an enum we need to reconvert it.
            ItemStack is = new ItemStack(Material.matchMaterial(type.toUpperCase()), amount);
            ItemMeta im = is.getItemMeta();

            // Continue if there is no itemMeta.
            if (im == null)
                continue;

            if (name != null)
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            if (lore != null)
                im.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore.toString())));

            if (enchants != null) {
                // We attached enchantments to their levels - Now we need to
                // splice it. So we need to loop through each string list.
                for (String s1 : enchants) {
                    String[] indiEnchants = s1.split(":");
                    im.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
                }
            }

            is.setItemMeta(im);
            p.getInventory().addItem(is);
        }

        // We also need to give them armor.
        String helmet = config.getString(path + "armor.helmet").toUpperCase();
        String chestplate = config.getString(path + "armor.chestplate").toUpperCase();
        String leggings = config.getString(path + "armor.leggings").toUpperCase();
        String boots = config.getString(path + "armor.boots").toUpperCase();

        // Comprueba si el usuario tiene armadura puesta y si la tiene pues se la agregamos al inventario
        if (p.getInventory().getHelmet() != null) {
            p.getInventory().addItem(new ItemStack(helmet != null ? Material.matchMaterial(helmet) : Material.AIR));
        } else {
            p.getInventory().setHelmet(new ItemStack(helmet != null ? Material.matchMaterial(helmet) : Material.AIR));
        }
        if (p.getInventory().getChestplate() != null) {
            p.getInventory().addItem(new ItemStack(chestplate != null ? Material.matchMaterial(chestplate) : Material.AIR));
        } else {
            p.getInventory().setChestplate(new ItemStack(chestplate != null ? Material.matchMaterial(chestplate) : Material.AIR));
        }
        if (p.getInventory().getLeggings() != null) {
            p.getInventory().addItem(new ItemStack(leggings != null ? Material.matchMaterial(leggings) : Material.AIR));
        } else {
            p.getInventory().setLeggings(new ItemStack(leggings != null ? Material.matchMaterial(leggings) : Material.AIR));
        }
        if (p.getInventory().getBoots() != null) {
            p.getInventory().addItem(new ItemStack(boots != null ? Material.matchMaterial(boots) : Material.AIR));
        } else {
            p.getInventory().setBoots(new ItemStack(boots != null ? Material.matchMaterial(boots) : Material.AIR));
        }

        p.updateInventory();
    }

    public Hydrogen getPlugin() {
        return plugin;
    }

}
