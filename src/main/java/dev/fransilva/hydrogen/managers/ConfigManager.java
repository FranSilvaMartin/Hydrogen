package dev.fransilva.hydrogen.managers;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static ConfigManager single_inst = null;
    private ArrayList<FileConfiguration> customConfigs = new ArrayList<FileConfiguration>();
    private ArrayList<String> configNames = new ArrayList<String>();
    private Hydrogen hydrogen;

    private String selectedLanguage = "lang_es";

    public void setPlugin(Hydrogen hydrogen) {
        this.hydrogen = hydrogen;
    }

    public FileConfiguration getConfig(String name) {
        if (customConfigs.size() > 0) {
            for (FileConfiguration conf : customConfigs) {
                if (conf.getName().equalsIgnoreCase(name)) {
                    return conf;
                }
            }
        }

        return createNewCustomConfig(name);
    }

    public void reloadConfigs() {
        customConfigs.clear();
        configNames.clear();

        setupConfig();
    }

    private FileConfiguration createNewCustomConfig(String name) {
        FileConfiguration fileConfiguration;
        File configFile = new File(hydrogen.getDataFolder(), name);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            hydrogen.saveResource(name, false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(configFile);
            customConfigs.add(fileConfiguration);
            configNames.add(name);
            return fileConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setData(FileConfiguration conf, String path, Object data) {
        conf.set(path, data);
        return saveData(conf);
    }

    private boolean saveData(FileConfiguration conf) {
        try {
            conf.save(new File(hydrogen.getDataFolder(), configNames.get(customConfigs.indexOf(conf))));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getString(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, "dummy string");
        }
        return conf.getString(path);
    }

    public String getMessage(String path, String player, String target) {
        String fileName = "langs/" + selectedLanguage + ".yml";
        FileConfiguration conf = getConfig("langs/" + selectedLanguage + ".yml");
        path = "messages." + path;

        // Agrega un mensaje por defecto si no existe el path
        if (!conf.contains(path)) {
            setData(conf, path, TextUtils.colorize("&cDefault message, check langs folder &7(" + selectedLanguage + ".yml" + ")"));
        }
        String message = TextUtils.colorize(conf.getString((path)));

        if (player != null) {
            message = message.replace("%player%", player);
        }

        if (target != null) {
            message = message.replace("%target%", target);
        }

        return message;
    }

    public String getMessage(String path, String player) {
        return getMessage(path, player, null);
    }

    public String getMessage(String path) {
        return getMessage(path, null, null);
    }

    /**
     * Obtiene el int de un parametro de la configuracion
     *
     * @param fileName Nombre del archivo
     * @param path     Parametro
     * @return Devuelve un entero
     */
    public int getInt(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        //Create dummy if not available
        if (!conf.contains(path)) {
            setData(conf, path, 1);
        }
        return conf.getInt(path);
    }

    public double getDouble(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        if (!conf.contains(path)) {
            setData(conf, path, 1.0);
        }
        return conf.getDouble(path);
    }

    public boolean getBoolean(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        if (!conf.contains(path)) {
            setData(conf, path, false);
        }
        return conf.getBoolean(path);
    }

    public List<?> getList(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        if (!conf.contains(path)) {
            setData(conf, path, new ArrayList<Location>().add(new Location(Bukkit.getWorld("world"), 10, 10, 10)));
        }
        return conf.getList(path);
    }

    public boolean addLocation(FileConfiguration conf, Location location, String path) {
        conf.set(String.format("%s.world", path), location.getWorld().getName());
        conf.set(String.format("%s.x", path), location.getX());
        conf.set(String.format("%s.y", path), location.getY());
        conf.set(String.format("%s.z", path), location.getZ());
        conf.set(String.format("%s.pitch", path), location.getPitch());
        conf.set(String.format("%s.yaw", path), location.getYaw());

        return saveData(conf);
    }

    public Location getLocation(String fileName, String path) {
        FileConfiguration conf = getConfig(fileName);

        String worldName = getString(String.valueOf(conf), String.format("%s.world", path));
        Bukkit.getServer().createWorld(new WorldCreator(worldName));

        World world = Bukkit.getWorld(worldName);
        int x = getInt(String.valueOf(conf), String.format("%s.x", path));
        int y = getInt(String.valueOf(conf), String.format("%s.y", path));
        int z = getInt(String.valueOf(conf), String.format("%s.z", path));

        return new Location(world, x, y, z);
    }

    public static ConfigManager getInstance() {
        if (single_inst == null) {
            single_inst = new ConfigManager();
        }
        return single_inst;
    }

    public void setupConfig() {
        createNewCustomConfig("config.yml");
        createNewCustomConfig("warps.yml");
        createNewCustomConfig("permissions.yml");
        createNewCustomConfig("langs/lang_es.yml");
        createNewCustomConfig("langs/lang_en.yml");
        createNewCustomConfig("homes.yml");
        selectedLanguage = getConfig("config.yml").getString("language");
    }
}
