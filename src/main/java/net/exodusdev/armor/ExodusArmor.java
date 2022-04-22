package net.exodusdev.armor;

import net.exodus.commons.Commons;
import net.exodus.commons.builders.ItemBuilder;
import net.exodusdev.armor.commands.ArmorCommand;
import net.exodusdev.armor.listeners.PlayerListener;
import net.exodusdev.armor.managers.ArmorRegistry;
import net.exodusdev.armor.managers.SetRegistry;
import net.exodusdev.armor.objects.Armor;
import net.exodusdev.armor.api.ExodusArmorAPI;
import net.exodusdev.armor.objects.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class ExodusArmor extends JavaPlugin {
    public static ExodusArmor plugin;
    private ArmorRegistry armorRegistry;
    private SetRegistry setRegistry;
    private ExodusArmorAPI exodusArmorApi;

    public void onEnable() {
        init();
        registerListeners();
        getCommand("ExodusArmor").setExecutor(new ArmorCommand(this));
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        loadSets();
        loadArmors();
    }

    public void onDisable() {
        saveDefaultConfig();
    }

    public void loadArmors() {
        final ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("Armors");
        for (String code : configurationSection.getKeys(false)) {
            final Armor armor = new Armor(code);
            final String path = "Armors."+code+".";
            armor.setEquipCommands(getConfig().getStringList(path+"Equip-Commands"));
            armor.setRemoveCommands(getConfig().getStringList(path+"Remove-Commands"));
            for (final String effectKeys : getConfig().getStringList(path+"Effects")) {
                final String[] effect = effectKeys.split(";");

                armor.addEffect(PotionEffectType.getByName(effect[0]), Integer.parseInt(effect[1]));
            }
            final ItemBuilder itemBuilder = Commons.getInstance().getUtils().getItemFromConfig(getConfig(), path+"Item");
            itemBuilder.addNBTTag("armor", code.toLowerCase());
            armor.setItem(itemBuilder.parse());
            armor.setSet(setRegistry.getSet(getConfig().getString(path+"Set").toUpperCase()));
            this.armorRegistry.addArmor(code.toUpperCase(), armor);
        }
    }
    public void loadSets() {
        final ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("Sets");
        for (final String code : configurationSection.getKeys(false)) {
            final Set set = new Set(code);
            final String path = "Sets."+code+".";
            set.setEquipCommands(getConfig().getStringList(path+"Equip-Commands"));
            set.setRemoveCommands(getConfig().getStringList(path+"Remove-Commands"));
            set.setHelmet(plugin.armorRegistry().getArmor(path+"Helmet".toUpperCase()));
            set.setChestplate(plugin.armorRegistry().getArmor(path+"Chestplate".toUpperCase()));
            set.setLeggings(plugin.armorRegistry().getArmor(path+"Leggings".toUpperCase()));
            set.setBoots(plugin.armorRegistry().getArmor(path+"Boots".toUpperCase()));
            for (final String effectKeys : getConfig().getStringList(path+"Effects")) {
                final String[] effect = effectKeys.split(";");

                set.addEffect(PotionEffectType.getByName(effect[0]), Integer.parseInt(effect[1]));
            }
            this.setRegistry.addSet(code.toUpperCase(), set);
        }
    }


    public void init() {
        ExodusArmor.plugin = this;
        this.armorRegistry = new ArmorRegistry();
        this.setRegistry = new SetRegistry();
        this.exodusArmorApi = new ExodusArmorAPI(this);
    }
   public void registerListeners() {
        new PlayerListener(this);
   }
    public ExodusArmor getInstance() {
        return plugin;
    }
    public SetRegistry getSetRegistry() {
        return setRegistry;
    }
    public ArmorRegistry armorRegistry() {
        return armorRegistry;
    }
    public ExodusArmorAPI getUtils() {
        return exodusArmorApi;
    }
}
