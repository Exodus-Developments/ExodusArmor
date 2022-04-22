package net.exodusdev.armor.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Armor {
   @Getter private final String identifier;
   @Getter @Setter private List<String> equipCommands;
   @Getter @Setter private List<String> removeCommands;
   @Getter @Setter private Set set;
   @Getter @Setter private ItemStack item;
   @Getter @Setter private Map<PotionEffectType, Integer> effects;
    public Armor(final String identifier) {
        this.identifier = identifier;
        this.equipCommands = new ArrayList<>();
        this.removeCommands = new ArrayList<>();
        this.effects = new HashMap<>();

    }
  public void addEffect(final PotionEffectType potion, final Integer mp) {
        this.effects.put(potion, mp);
  }

}
