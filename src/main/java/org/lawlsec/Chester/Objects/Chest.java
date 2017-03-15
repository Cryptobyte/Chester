package org.lawlsec.Chester.Objects;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.inventory.ItemStack;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chester_chests")
public class Chest {

    @Id
    @NotNull
    private int id;

    @Column
    private long Number;

    @Column
    private UUID Owner;

    @Column
    private String Items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumber() {
        return Number;
    }

    public void setNumber(long number) {
        Number = number;
    }

    public UUID getOwner() {
        return Owner;
    }

    public void setOwner(UUID owner) {
        Owner = owner;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }
}
