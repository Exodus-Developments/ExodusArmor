package net.exodusdev.armor.api;

import net.exodus.commons.Utils.NBTEditor;
import net.exodus.commons.builders.PlaceholderReplacer;
import net.exodusdev.armor.ExodusArmor;
import net.exodusdev.armor.objects.Armor;
import net.exodusdev.armor.objects.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class ExodusArmorAPI {
   private final ExodusArmor plugin;
   public ExodusArmorAPI(final ExodusArmor plugin) {
       this.plugin = plugin;
   }
   public Armor getArmorByItem(final ItemStack item) {
       final Armor armor;
       if(NBTEditor.contains(item, "armor")) {
           armor = plugin.armorRegistry().getArmor(NBTEditor.getString(item, "armor").toUpperCase());
       } else {
           armor = null;
       }
       return armor;
   }

    public String getTag(final ItemStack item) {
       return NBTEditor.getString(item, "armor").toUpperCase();
   }
   public boolean hasSetOn(final Player player, final Set set) {
       boolean val = false;
       final ItemStack helmet = player.getInventory().getHelmet();
       final ItemStack chestplate = player.getInventory().getChestplate();
       final ItemStack leggings = player.getInventory().getLeggings();
       final ItemStack boots = player.getInventory().getBoots();
       if(helmet!=null&&chestplate!=null&&leggings!=null&&boots!=null) {
           if (NBTEditor.contains(helmet, "armor") && NBTEditor.contains(chestplate, "armor") && NBTEditor.contains(leggings, "armor") && NBTEditor.contains(boots, "armor")) {
               if (set.getHelmet() != null && set.getChestplate() != null && set.getLeggings() != null && set.getBoots() != null) {
                   if (set.getHelmet().getIdentifier().equalsIgnoreCase(getTag(helmet)) && set.getChestplate().getIdentifier().equalsIgnoreCase(getTag(chestplate)) && set.getLeggings().getIdentifier().equalsIgnoreCase(getTag(leggings)) && set.getBoots().getIdentifier().equalsIgnoreCase(getTag(boots))) {
                       val = true;
                   }
               }
       }

       }
       return val;
   }

   public void activateSet(final Player player, final Set set) {
       final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer();
       final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
       placeholderReplacer.addPlaceholder("{player}", player.getName());
       for (final String s : set.getEquipCommands()) {
           Bukkit.dispatchCommand(console, placeholderReplacer.parse(s));
       }
       for (Map.Entry<PotionEffectType, Integer> effects : set.getEffects().entrySet()) {
           player.addPotionEffect(new PotionEffect(effects.getKey(), Integer.MAX_VALUE, effects.getValue() - 1));
       }
   }
   public void deactivateSet(final Player player, final Set set) {
       final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer();
       final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
       placeholderReplacer.addPlaceholder("{player}", player.getName());
       for (final String s : set.getRemoveCommands()) {
           Bukkit.dispatchCommand(console, placeholderReplacer.parse(s));
       }
       for (final PotionEffectType effect : set.getEffects().keySet()) {
           if(player.hasPotionEffect(effect)) {
               player.removePotionEffect(effect);
           }
       }
   }
}

