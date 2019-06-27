package me.thebirmanator.resourcepackmanager;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PlayerData {

    private static Set<PlayerData> allData = new HashSet<>();
    private static FileConfiguration config = ResourcePackManager.getInstance().getConfig();

    private UUID uuid;
    private Location loc;
    private List<ItemStack> hotbar;

    public PlayerData(UUID uuid, Location loc, List<ItemStack> hotbar) {
        this.uuid = uuid;
        this.loc = loc;
        this.hotbar = hotbar;

        allData.add(this);
    }

    public Location getLocation() {
        return loc;
    }

    public List<ItemStack> getHotbar() {
        return hotbar;
    }

    public void saveToConfig() {
        config.set("data." + uuid.toString() + ".loc", loc);
        config.set("data." + uuid.toString() + ".hotbar", hotbar);
        ResourcePackManager.getInstance().saveConfig();
    }

    public static void loadFromConfig(String stringUUID) {
        UUID uuid = UUID.fromString(stringUUID);
        Location loc = config.getObject("data." + stringUUID + ".loc", Location.class);
        List<ItemStack> hotbar = config.getObject("data." + stringUUID + ".hotbar", List.class);
        new PlayerData(uuid, loc, hotbar);
    }

    public void remove() {
        allData.remove(this);
        config.set("data." + uuid, null);
        ResourcePackManager.getInstance().saveConfig();
    }

    public static Set<PlayerData> getAllData() {
        return allData;
    }

    public static PlayerData getData(Player player) {
        for(PlayerData data : allData) {
            if(player.getUniqueId().equals(data.uuid)) {
                return data;
            }
        }
        return null;
    }
}
