package dev.fransilva.hydrogen;

import dev.fransilva.hydrogen.commands.*;
import dev.fransilva.hydrogen.listeners.*;
import dev.fransilva.hydrogen.managers.ConfigManager;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Hydrogen extends JavaPlugin {

    public List<UUID> lista = new ArrayList<UUID>();
    private Hydrogen instance;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.getInstance().setPlugin(this);
        ConfigManager.getInstance().setupConfig();

        registerListeners();
        registerCommands();

        Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&cHydrogen - Activado"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&cHydrogen - Desactivado"));
        saveConfig();
    }

    private void registerCommands() {
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("rename").setExecutor(new RenameCommand(this));
        getCommand("lore").setExecutor(new LoreCommand(this));
        getCommand("repair").setExecutor(new RepairCommand(this));
        getCommand("clearchat").setExecutor(new ClearChatCommand(this));
        getCommand("reloadconfig").setExecutor(new ReloadCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("antonio123").setExecutor(new HomeCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new SignChange(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
    }

    public Hydrogen getInstance() {
        return instance;
    }
}
