package org.lawlsec.Chester.UI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.lawlsec.Chester.Chester;
import org.lawlsec.Chester.Objects.Chest;
import org.lawlsec.Chester.Storage.Database;
import org.lawlsec.Chester.Util.General;

import java.util.UUID;

public class ChestUI {
    public static void Open(long _Number, Player _Player) {
        Chest _Chest = Database.getChest(_Number, _Player);

        if (_Chest == null) {
            _Chest = Chester.Get().getDatabase().createEntityBean(Chest.class);
            _Chest.setNumber(_Number);
            _Chest.setOwner(_Player.getUniqueId());
            Database.setChest(_Chest);
        }

        Inventory _Inv = null;

        try {
            _Inv = General.unserializeInventory(
                _Chest.getItems(),
                String.format("Chester Chest %d", _Number)
            );

        } catch (Exception e) { e.printStackTrace(); }

        if (_Inv != null)
            _Player.openInventory(_Inv);
    }

    public static void Peek(long _Number, UUID _Player, Player _Peeker) {
        Chest _Chest = Database.getChest(_Number, _Player);

        if (_Chest == null) {
            _Chest = Chester.Get().getDatabase().createEntityBean(Chest.class);
            _Chest.setNumber(_Number);
            _Chest.setOwner(_Player);
            Database.setChest(_Chest);
        }

        Inventory _Inv = null;

        try {
            _Inv = General.unserializeInventory(
                    _Chest.getItems(),
                    String.format("Chester Chest %d", _Number)
            );

        } catch (Exception e) { e.printStackTrace(); }

        if (_Inv != null)
            _Peeker.openInventory(_Inv);
    }
}
