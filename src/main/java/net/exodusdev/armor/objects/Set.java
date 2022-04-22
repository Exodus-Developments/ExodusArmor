package net.exodusdev.armor.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Set {
    @Getter private final String identifier;
    @Getter @Setter private Armor chestplate;
    @Getter @Setter private Armor helmet;
    @Getter @Setter private Armor boots;
    @Getter @Setter private Armor leggings;
    @Getter @Setter private Map<PotionEffectType, Integer> effects;
    @Getter @Setter private List<String> equipCommands;
    @Getter @Setter private List<String> removeCommands;
    public Set(final String identifier) {
        this.identifier = identifier;
        this.equipCommands = new ArrayList<>();
        this.removeCommands = new ArrayList<>();
        this.effects = new HashMap<>();
    }
    public void addEffect(final PotionEffectType potion, final Integer mp) {
        this.effects.put(potion, mp);
    }
}
