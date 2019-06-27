package me.thebirmanator.resourcepackmanager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class ResourcePackManager extends JavaPlugin {

    private static ResourcePackManager instance;

    private String prefix = ChatColor.AQUA + "" + ChatColor.BOLD + "RESOURCEPACK" + ChatColor.DARK_GRAY + " ⎜ " + ChatColor.RESET;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // if there's data, load it
        if(getConfig().getConfigurationSection("data") != null) {
            Set<String> stringUUIDS = getConfig().getConfigurationSection("data").getKeys(false);
            for(String s : stringUUIDS) {
                PlayerData.loadFromConfig(s);
            }
        }

        getServer().getPluginManager().registerEvents(new RPackStatusListener(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "ResourcePackManager enabled!");
    }

    public void onDisable() {
        for(PlayerData data : PlayerData.getAllData()) {
            data.saveToConfig();
        }
    }

    public static ResourcePackManager getInstance() {
        return instance;
    }

    public Location getSafetyBox() {
        String[] split = getConfig().getString("send-to").split("\\|");
        World world = getServer().getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        Location location = new Location(world, x, y, z);
        return location;
    }

    public String getResourcePackURL() {
        return getConfig().getString("resource-pack-url");
    }

    public String getPrefix() {
        return prefix;
    }
}
