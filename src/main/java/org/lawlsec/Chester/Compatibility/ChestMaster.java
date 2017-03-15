package org.lawlsec.Chester.Compatibility;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.lawlsec.Chester.Chester;
import org.lawlsec.Chester.Objects.Chest;
import org.lawlsec.Chester.Storage.Database;
import org.lawlsec.Chester.Util.General;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.sql.*;
import java.util.UUID;

import static org.lawlsec.Chester.Storage.SQL.getConnection;

/**
 * Adapted from source of ChestMaster
 * https://goo.gl/eOog4t
 *
 * @ dev of ChestMaster:
 * https://cdn.meme.am/cache/instances/folder287/67039287.jpg
 */
public class ChestMaster {
    private static boolean isSQLite = false;

    private static ItemStack itemFrom64(String data) throws IOException {
        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            try {

                return (ItemStack) dataInput.readObject();

            } finally { dataInput.close(); }

        } catch (ClassNotFoundException e) { throw new IOException("Unable to decode class type.", e); }
    }

    private static Inventory unserializeInventory(String data) throws IOException {
        Inventory v = Bukkit.createInventory(null, 54);
        String[] dataArray = data.split("@");

        for (String s : dataArray) {
            s.replace("@", "");
            ItemStack i = itemFrom64(s);

            if (i != null)
                v.addItem(i);

        }

        return v;
    }

    public Connection getDatabase() {
        String DBFile = Chester.Get().getDataFolder() + "/Import.db";

        if (new File(DBFile).exists()) {
            try {
                isSQLite = true;
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:" + DBFile);

            } catch (Exception e) { }
        }

        isSQLite = false;
        return getConnection();
    }

    public long Import() {
        long Processed = 0;

        try {
            PreparedStatement Statement = getDatabase().prepareStatement(
                "SELECT * FROM chests;"
            );

            ResultSet Results = Statement.executeQuery();

            while (Results.next()) {
                Inventory Inv;

                try {
                    Inv = unserializeInventory(Results.getString("inventory"));

                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                Chest _Chest = Chester.Get().getDatabase().createEntityBean(Chest.class);
                _Chest.setNumber(Results.getInt("number"));
                _Chest.setOwner(UUID.fromString(Results.getString("uuid")));
                _Chest.setItems(General.serializeInventory(Inv));

                Database.setChest(_Chest);

                Processed++;
            }

            if (isSQLite) {
                new File(
                    Chester.Get().getDataFolder() + "/Import.db"

                ).delete();
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return Processed;
    }

    /**
     * Check if plugin data exists
     * @return Plugin data found
     */
    public boolean Check() {
        try {
            PreparedStatement Statement = getDatabase().prepareStatement(
                "SELECT COUNT(*) FROM chests;"
            );

            ResultSet Results = Statement.executeQuery();

            while (Results.next())
                return Results.getInt(1) != 0;

        } catch (SQLException e) { return false; }

        return false;
    }
}
