package org.lawlsec.Chester.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.lawlsec.Chester.Chester;
import org.lawlsec.Chester.Compatibility.ChestMaster;
import org.lawlsec.Chester.UI.ChestUI;
import org.lawlsec.Chester.Util.UUIDFetcher;

import java.util.UUID;

public class ChesterCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("chester.admin"))
                return true; // Keep the command hidden

        }

        if (!(strings.length >= 1))
            return false;

        if (strings[0].equalsIgnoreCase("reload")) {
            Chester.Get().reloadConfig();
            sender.sendMessage("Config reloaded!");
            return true;
        }

        if (strings[0].equalsIgnoreCase("peek")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You can only peek from in game.");
                return true;
            }

            if (!(strings.length >= 3)) {
                sender.sendMessage("Usage: /chester peek <Player> <Chest>");
                return true;
            }

            Player Sender = (Player) sender;

            long Number;

            try {
                Number = Long.parseLong(strings[2]);

            } catch(Exception e) {
                sender.sendMessage("Invalid chest number");
                return true;
            }

            UUID Peek;

            try {
                Peek = UUIDFetcher.getUUIDOf(strings[1]);

            } catch (Exception e) {
                sender.sendMessage("Invalid player");
                return true;
            }

            ChestUI.Peek(Number, Peek, Sender);
        }

        if (strings[0].equalsIgnoreCase("import")) {
            if (!(strings.length >= 2)) {
                sender.sendMessage("Usage: /chester import <Plugin>");
                return true;
            }

            String Name = strings[1];

            if (Name.equalsIgnoreCase("ChestMaster")) {
                sender.sendMessage(String.format(
                    "Plundering %s's chest stash..", Name
                ));

                ChestMaster Importer = new ChestMaster();

                if (!Importer.Check()) {
                    sender.sendMessage(String.format(
                        "Cannot find any data for %s!", Name
                    ));

                    return true;
                }

                long Imported = Importer.Import();

                sender.sendMessage(String.format(
                    "Plundered %d chests!", Imported
                ));
            }

            sender.sendMessage(String.format(
                "Cannot find %s", Name
            ));

            return true;
        }

        return false;
    }
}