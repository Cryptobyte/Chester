package org.lawlsec.Chester.Storage;

import org.bukkit.entity.Player;
import org.lawlsec.Chester.Chester;
import org.lawlsec.Chester.Objects.Chest;

import java.util.List;
import java.util.UUID;

public class Database {
    public static Chest getChest(long Number, Player _Player) {
        List<Chest> Chests = Chester.Get().getDatabase()
            .find(Chest.class)
                .where()
                    .eq("Owner", _Player.getUniqueId())
                    .eq("Number", Number)
            .findList();

        if (Chests.isEmpty())
            return null;

        return Chests.get(0);
    }

    public static Chest getChest(long Number, UUID _Player) {
        List<Chest> Chests = Chester.Get().getDatabase()
                .find(Chest.class)
                .where()
                .eq("Owner", _Player)
                .eq("Number", Number)
                .findList();

        if (Chests.isEmpty())
            return null;

        return Chests.get(0);
    }

    public static void delChest(Chest _Chest) {
        Chester.Get().getDatabase()
            .delete(_Chest);
    }

    public static void setChest(Chest _Chest) {
        Chester.Get().getDatabase()
            .save(_Chest);
    }

    public static void updChest(Chest _Chest) {
        Chester.Get().getDatabase()
            .update(_Chest);
    }
}
