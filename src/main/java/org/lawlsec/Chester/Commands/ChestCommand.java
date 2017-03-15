package org.lawlsec.Chester.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.lawlsec.Chester.Configuration.Restrictions;
import org.lawlsec.Chester.UI.ChestUI;

public class ChestCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            System.out.println("Chester can only be used by players");
            return true;
        }

        Player Sender = (Player) sender;

        if (strings.length != 1) {
            showUsage(Sender);
            return true;
        }

        if (isChesterRestricted(Sender))
            return true;

        long Number;

        try {
            Number = Long.parseLong(strings[0]);

        } catch(Exception e) {
            Sender.sendMessage("Invalid chest number");
            return true;
        }

        if (Number <= 0) {
            Sender.sendMessage("Invalid chest number");
            return true;
        }

        if (Sender.hasPermission("chester.unlimited")) {
            ChestUI.Open(Number, Sender);
            return true;
        }

        if (getMaxChests(Sender) >= Number) {

            ChestUI.Open(Number, Sender);
            return true;

        } else {
            Sender.sendMessage(String.format("You don't have %d chests", Number));
            return true;
        }
    }

    private void showUsage(Player Sender) {
        Sender.sendMessage(String.format(
            "Usage: /chest # (1-%d)",
            getMaxChests(Sender)
        ));
    }

    private long getMaxChests(Player Sender) {
        String[] Perms = getPerm(Sender, "chester.");

        if (Perms == null)
            return 0;

        long MaxChe = 0;
        for (String Perm : Perms) {
            long CurChe = Long.parseLong(Perm);

            if (CurChe > MaxChe)
                MaxChe = CurChe;

        }

        return MaxChe;
    }

    private String[] getPerm(Player Sender, String perm) {
        for (PermissionAttachmentInfo perms : Sender.getEffectivePermissions()) {
            if (perms.getPermission().startsWith(perm))
                return perms.getPermission().split("\\.");

        }

        return null;
    }

    private boolean isChesterRestricted(Player Sender) {
        switch (Sender.getGameMode()) {
            case CREATIVE:
                return Restrictions.Creative();
            case SPECTATOR:
                return Restrictions.Spectator();
            case ADVENTURE:
                return Restrictions.Adventure();
            case SURVIVAL:
                return Restrictions.Survival();
            default:
                return false;
        }
    }
}
