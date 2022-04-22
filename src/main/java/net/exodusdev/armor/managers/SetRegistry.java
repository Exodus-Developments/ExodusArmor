package net.exodusdev.armor.managers;

import lombok.Getter;
import net.exodusdev.armor.objects.Set;

import java.util.HashMap;
import java.util.Map;

public class SetRegistry {
    @Getter
    private final Map<String, Set> setList;

    public SetRegistry() {
        this.setList = new HashMap<>();
    }

    public Set getSet(final String name) {
        return this.setList.get(name);
    }
    public void addSet(final String name, final Set code) {
        setList.put(name, code);
    }
}

