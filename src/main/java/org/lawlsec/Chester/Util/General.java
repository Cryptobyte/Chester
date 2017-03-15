package org.lawlsec.Chester.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class General {
    private static ItemStack itemFrom64(String data) throws Exception {
        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            try {
                return (ItemStack) dataInput.readObject();

            } finally { dataInput.close(); }

        } catch (ClassNotFoundException e) {
            throw new Exception("Unable to decode class type.", e);
        }
    }

    private static String itemTo64(ItemStack stack) throws IllegalStateException {
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(stack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    public static String serializeInventory(Inventory v) {
        StringBuilder Serializer = new StringBuilder();

        for (int i = 0; i < v.getSize(); i++) {
            ItemStack Stack = v.getItem(i);

            if (Stack == null)
                continue;

            Serializer.append(String.format(
                "%d#%s@", i, itemTo64(Stack)
            ));
        }

        if (Serializer.length() > 1)
            Serializer.deleteCharAt(Serializer.length() - 1);

        return Serializer.toString();
    }

    public static Inventory unserializeInventory(String data, String Title) throws Exception {
        Inventory Inv = Bukkit.createInventory(null, 54, Title);

        if ((data == null) || (data.equals("")))
            return Inv;

        for (String item : data.split("@")) {
            String[] Item = item.split("#");
            ItemStack Stack = itemFrom64(Item[1]);

            if (Stack != null)
                Inv.setItem(Integer.valueOf(Item[0]), Stack);

        }

        return Inv;
    }
}
