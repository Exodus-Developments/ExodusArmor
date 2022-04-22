package net.exodusdev.armor.commands;

import net.exodus.commons.Commons;
import net.exodusdev.armor.ExodusArmor;
import net.exodusdev.armor.objects.Armor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArmorCommand implements CommandExecutor {
    private final ExodusArmor plugin;
    public ArmorCommand(final ExodusArmor plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, final String s, final String[] strings) {
       if(strings.length==3) {
           if(strings[0].equalsIgnoreCase("give")) {
               final Player player = Bukkit.getPlayer(strings[1]);
               final Armor armor = plugin.armorRegistry().getArmor(strings[2].toUpperCase());
               if(player != null&&armor!=null) {
                   player.getInventory().addItem(armor.getItem());
               } else {
                   Commons.getInstance().getMessageUtil().sendMessage(player, plugin.getConfig(), "Messages.INVALID", null);
               }
           }
       } else {
            Commons.getInstance().getMessageUtil().sendMessage(commandSender, plugin.getConfig(), "Messages.HELP", null);
        }
        return false;
    }


}



