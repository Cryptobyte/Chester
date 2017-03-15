package org.lawlsec.Chester.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.lawlsec.Chester.Objects.Chest;
import org.lawlsec.Chester.Storage.Database;
import org.lawlsec.Chester.Util.General;

public class InventoryListener implements Listener{
    @EventHandler
    public void InventoryClosed(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().contains("Chester Chest")) {
            int Num = Integer.valueOf(e.getInventory().getTitle().split(" ")[2]);

            Chest _Chest = Database.getChest(
                Num, (Player)e.getPlayer()
            );

            String Items = General.serializeInventory(e.getInventory());

            /*
             * Don't keep empty chest records
             */
            if (Items.length() == 0) {
                Database.delChest(_Chest);
                return;
            }

            assert _Chest != null;
            _Chest.setItems(Items);

            Database.updChest(_Chest);
        }
    }
}
