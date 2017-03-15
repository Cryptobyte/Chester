package org.lawlsec.Chester;

import org.bukkit.plugin.java.JavaPlugin;
import org.lawlsec.Chester.Commands.ChestCommand;
import org.lawlsec.Chester.Commands.ChesterCommand;
import org.lawlsec.Chester.Configuration.Config;
import org.lawlsec.Chester.Listeners.InventoryListener;
import org.lawlsec.Chester.Objects.Chest;

import javax.persistence.PersistenceException;
import java.util.LinkedList;
import java.util.List;

public class Chester extends JavaPlugin {
    private static Chester Instance;

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> classes = new LinkedList<Class<?>>();
        classes.add(Chest.class);
        return classes;
    }

    @Override
    public void onEnable() {
        Instance = this;

        Config.setupConfig();

        checkDDL();

        getCommand("chest").setExecutor(new ChestCommand());
        getCommand("chester").setExecutor(new ChesterCommand());

        Chester.Get().getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public void onDisable() { }

    private void checkDDL() {
        try {
            this.getDatabase().find(Chest.class).findRowCount();

        } catch (PersistenceException e){
            this.installDDL();
        }
    }

    public static Chester Get() {
        return Instance;
    }
}
