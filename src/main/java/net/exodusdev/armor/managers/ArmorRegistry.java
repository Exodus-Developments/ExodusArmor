package net.exodusdev.armor.managers;

import lombok.Getter;
import net.exodusdev.armor.objects.Armor;

import java.util.HashMap;
import java.util.Map;

public class ArmorRegistry {
    @Getter private final Map<String, Armor> codeList;

    public ArmorRegistry() {
        this.codeList = new HashMap<>();
    }

    public Armor getArmor(final String name) {
        return this.codeList.get(name);
    }
    public void addArmor(final String name, final Armor code) {
        codeList.put(name, code);
    }
}
