package com.sanmar.challenge1;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
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

import java.util.HashMap;
import java.util.Map;

public final class Challenge1 extends JavaPlugin implements Listener {

    private LangManager lang;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        lang = new LangManager(this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            for (Player p : getServer().getOnlinePlayers()) {
                if (p != event.getEntity()) {
                    if (p.getHealth() - event.getFinalDamage() > 0) {
                        p.setHealth(p.getHealth() - event.getFinalDamage());

                        Map<String, String> placeholders = new HashMap<>();
                        placeholders.put("amount", String.valueOf(event.getFinalDamage()));

                        p.sendActionBar(
                                Component.text(lang.getMessage(placeholders.toString(), "damage"))
                                        .color(NamedTextColor.RED)
                        );
                        p.playSound(
                                p.getLocation(),
                                Sound.ENTITY_PLAYER_HURT,
                                1.0f,
                                1.0f
                        );
                    } else {
                        p.setHealth(0);
                        p.sendActionBar(
                                Component.text(lang.getMessage("death"))
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

                    Map<String, String> placeholders = new HashMap<>();
                    placeholders.put("amount", String.valueOf(event.getAmount()));

                    p.sendActionBar(
                            Component.text(lang.getMessage(placeholders.toString(), "heal"))
                                    .color(NamedTextColor.GREEN)
                    );
                }
            }
        }
    }

    @EventHandler
    public void onTick(ServerTickStartEvent e){
        for (Player p : getServer().getOnlinePlayers()){
            p.setMaxHealth(getConfig().getDouble("max_hp"));
        }
    }
}
