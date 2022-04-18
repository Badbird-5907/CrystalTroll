package net.badbird5907.crystaltroll;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.UUID;

public final class CrystalTroll extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(event.getEntity() instanceof Player) || event.getDamager().getType() != EntityType.ENDER_CRYSTAL) {
            return;
        }

        EnderCrystal crystal = (EnderCrystal) e.getDamager();
        if(crystal.hasMetadata("player")) {
            Player attacker = Bukkit.getPlayer(UUID.fromString(crystal.getMetadata("player").get(0).asString()));
            event.setCancelled(true);
            Objects.requireNonNull(attacker).damage(e.getDamage());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
            event.getEntity().setMetadata("player", new FixedMetadataValue(this, event.getDamager().getUniqueId().toString()));
        }
    }
}
