package com.sanmar.challenge1;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Challenge1 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            for (Player p : getServer().getOnlinePlayers()) {
                if (p != event.getEntity()) {
                    if (p.getHealth() - event.getFinalDamage() > 0) {
                        p.setHealth(p.getHealth() - event.getFinalDamage());
                        p.sendActionBar(
                                Component.text("Урон от другого игрока - " + event.getFinalDamage())
                                        .color(NamedTextColor.RED)
                        );
                        p.playSound(
                                p.getLocation(), // где воспроизводить
                                Sound.ENTITY_PLAYER_HURT, // какой звук
                                1.0f, // громкость
                                1.0f  // питч (высота)
                        );
                    } else if (p.getHealth() - event.getFinalDamage() < 0) {
                        p.setHealth(0);
                        p.sendActionBar(
                                Component.text("Смерть от другого игрока!")
                                        .color(NamedTextColor.BLACK)
                        );
                    }
                }
            }

        }
    }
    @EventHandler
    public void onHealth(EntityRegainHealthEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            for (Player p : getServer().getOnlinePlayers()) {
                if (p != event.getEntity()) {
                    p.setHealth(p.getHealth() + event.getAmount());
                    p.sendActionBar(
                            Component.text("Хил от другого игрока - " + event.getAmount())
                                    .color(NamedTextColor.GREEN)
                    );
                }
            }
        }
    }
}
