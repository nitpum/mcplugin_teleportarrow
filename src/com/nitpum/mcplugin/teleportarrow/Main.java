package com.nitpum.mcplugin.teleportarrow;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

  @Override
  public void onEnable () {
    getServer().getLogger().info("[TeleportArrow] Enabled !");
    getServer().getPluginManager().registerEvents(this, this);
  }
    
  @Override
  public void onDisable () {
    getServer().getLogger().info("[TeleportArrow] Disabled !");    
  }
  
  @EventHandler
  public void onEntityShootBowEvent (EntityShootBowEvent e) {
    if (e.getEntity() instanceof Player) {
      if (e.getBow().getItemMeta().hasDisplayName()) {
        if (e.getBow().getItemMeta().getDisplayName().toLowerCase().contains("teleport")) {
          e.getProjectile().setCustomName("teleport"); //Set arrow name for easy check  
        }
      }
    }
  }
  
  @EventHandler
  public void onProjectileHitEvent (ProjectileHitEvent e) {
    Projectile proj = e.getEntity();
    if(proj instanceof Arrow) {
      Arrow arrow = (Arrow)proj;
      if (arrow.getCustomName() != null) {
        // Check is teleport arrow
        if (arrow.getCustomName().toLowerCase().equals("teleport")) {
          LivingEntity shooter = (LivingEntity) arrow.getShooter();
          shooter.teleport(arrow.getLocation()); // Teleport shooter to hitted arrow position
          arrow.remove(); // Remove arrow
        }
      }
    }
  }
  
}
