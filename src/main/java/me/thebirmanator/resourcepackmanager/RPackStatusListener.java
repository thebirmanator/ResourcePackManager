package me.thebirmanator.resourcepackmanager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RPackStatusListener implements Listener {

    @EventHandler
    public void onUpdate(PlayerResourcePackStatusEvent event) {
        ResourcePackManager main = ResourcePackManager.getInstance();
        Player player = event.getPlayer();
        switch (event.getStatus()) {
            case ACCEPTED:
                player.sendMessage(main.getPrefix() + "Sending you to safety to load the resource pack.");
                List<ItemStack> hotbar = Arrays.asList(player.getInventory().getStorageContents()).subList(0, 8);
                // does not have previously saved data: if they do, they are already in the box, and it will be overwritten
                if(PlayerData.getData(player) == null) {
                    new PlayerData(player.getUniqueId(), player.getLocation(), hotbar);
                }
                for(int i = 0; i < 9; i++) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
                player.teleport(main.getSafetyBox());
                break;
            case DECLINED:
                player.sendMessage(main.getPrefix() + ChatColor.RED + "Warning! " + ChatColor.RESET +
                        "You really need to download this resource pack. Things will not render correctly. If you " +
                        "accidentally declined it, please set " + ChatColor.GREEN + "Server Resource Pack" + ChatColor.RESET +
                        " to " + ChatColor.GREEN + "" +
                        "Enabled" + ChatColor.RESET + " in the server edit screen. " +
                        "Alternatively, you can download it for yourself at " + ChatColor.AQUA + main.getResourcePackURL());
                break;
            case SUCCESSFULLY_LOADED:
                player.sendMessage(main.getPrefix() + "Resource pack successfully loaded.");
                PlayerData data = PlayerData.getData(player);
                player.teleport(data.getLocation());
                for(int i = 0; i < data.getHotbar().size(); i++) {
                    player.getInventory().setItem(i, data.getHotbar().get(i));
                }
                data.remove();
                break;
            case FAILED_DOWNLOAD:
                player.sendMessage(main.getPrefix() + "Failed to download the resource pack. Let's try it again.");
                player.setResourcePack(main.getResourcePackURL());
                break;
            default:
                player.sendMessage(main.getPrefix() + "How the hell did you even get here?");
                break;
        }
    }
}
