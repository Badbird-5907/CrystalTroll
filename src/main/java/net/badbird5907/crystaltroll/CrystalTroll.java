package net.badbird5907.crystaltroll;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class CrystalTroll extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
                if (e.getDamager().getType() == EntityType.ENDER_CRYSTAL) {
                    EnderCrystal crystal = (EnderCrystal) e.getDamager();
                    if (crystal.hasMetadata("player")) {
                        Player damager = Bukkit.getPlayer(UUID.fromString(crystal.getMetadata("player").get(0).asString()));
                        event.setCancelled(true);
                        damager.damage(e.getDamage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            event.getEntity().setMetadata("player", new FixedMetadataValue(this, event.getDamager().getUniqueId().toString()));
        }
    }
}
