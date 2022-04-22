package net.exodusdev.armor.listeners;

import net.exodus.commons.Commons;
import net.exodus.commons.abstraction.ExodusListener;
import net.exodus.commons.builders.PlaceholderReplacer;
import net.exodus.commons.listeners.ArmorEquip.ArmorEquipEvent;
import net.exodusdev.armor.ExodusArmor;
import net.exodusdev.armor.objects.Armor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class PlayerListener extends ExodusListener<ExodusArmor> {
    private final ExodusArmor plugin;
    public PlayerListener(final ExodusArmor plugin) {
        super(plugin, Commons.getInstance());
        this.plugin = plugin;
    }
    @EventHandler
    public void onWear(final ArmorEquipEvent event) {
        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR) {
            if (plugin.getUtils().getArmorByItem(event.getNewArmorPiece()) != null) {
                final Armor armor = plugin.getUtils().getArmorByItem(event.getNewArmorPiece());
                final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer();
                final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                placeholderReplacer.addPlaceholder("{player}", event.getPlayer().getName());
                for (final String s : armor.getEquipCommands()) {
                    Bukkit.dispatchCommand(console, placeholderReplacer.parse(s));
                }
                for (Map.Entry<PotionEffectType, Integer> effects : armor.getEffects().entrySet()) {
                    event.getPlayer().addPotionEffect(new PotionEffect(effects.getKey(), Integer.MAX_VALUE, effects.getValue() - 1));
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                final Armor armor = plugin.getUtils().getArmorByItem(event.getNewArmorPiece());
                if(armor!=null&&armor.getSet()!=null&&plugin.getUtils().hasSetOn(event.getPlayer(), armor.getSet())) {
                    plugin.getUtils().activateSet(event.getPlayer(), armor.getSet());
                }
            }, 30);
        }
        if(event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) {
            if (plugin.getUtils().getArmorByItem(event.getOldArmorPiece()) != null) {
                final Armor armor = plugin.getUtils().getArmorByItem(event.getOldArmorPiece());
                   final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer();
                   final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                   placeholderReplacer.addPlaceholder("{player}", event.getPlayer().getName());
                   for (final String s : armor.getRemoveCommands()) {
                       Bukkit.dispatchCommand(console, placeholderReplacer.parse(s));
                   }
                   for (final PotionEffectType effect : armor.getEffects().keySet()) {
                       if (event.getPlayer().hasPotionEffect(effect)) {
                           event.getPlayer().removePotionEffect(effect);
                       }
                   }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                final Armor armor = plugin.getUtils().getArmorByItem(event.getOldArmorPiece());
                if(armor!=null&&armor.getSet()!=null&&plugin.getUtils().hasSetOn(event.getPlayer(), armor.getSet())) {
                    plugin.getUtils().deactivateSet(event.getPlayer(), armor.getSet());
                }
            }, 15);

        }

    }
}
