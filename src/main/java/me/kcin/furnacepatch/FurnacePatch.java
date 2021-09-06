package me.kcin.furnacepatch;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

public final class FurnacePatch extends JavaPlugin implements Listener {

    private String bypassPermission = null;
    private boolean doMessages = false;
    private String cancelMessage = null;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        bypassPermission = getConfig().getString("messageOnCancel.bypassPermission");
        doMessages = getConfig().getBoolean("messageOnCancel.enabled");
        cancelMessage = getConfig().getString("messageOnCancel.message");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() { }

    @EventHandler
    public void onRecipeClick(PlayerRecipeBookClickEvent event) {
        Player player = event.getPlayer();
        InventoryView inv = player.getOpenInventory();
        if (inv.getType() == InventoryType.FURNACE || inv.getType() == InventoryType.BLAST_FURNACE || inv.getType() == InventoryType.SMOKER) {
            if (inv.getTopInventory().getItem(0) != null) {
                if (player.hasPermission(bypassPermission)) {
                    return;
                }
                event.setCancelled(true);
                if (doMessages) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cancelMessage));
                }
            }
        }
    }

}
