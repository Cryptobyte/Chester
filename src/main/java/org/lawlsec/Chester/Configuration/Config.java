package org.lawlsec.Chester.Configuration;

import org.lawlsec.Chester.Chester;

public class Config {
    public static void setupConfig() {
        Chester.Get().getConfig().addDefault("Database.User", "");
        Chester.Get().getConfig().addDefault("Database.Pass", "");
        Chester.Get().getConfig().addDefault("Database.Host", "");
        Chester.Get().getConfig().addDefault("Database.Port", "");
        Chester.Get().getConfig().addDefault("Database.Database", "");
        Chester.Get().getConfig().addDefault("Database.SSL", false);
        Chester.Get().getConfig().addDefault("Restrict.Creative", true);
        Chester.Get().getConfig().addDefault("Restrict.Spectator", true);
        Chester.Get().getConfig().addDefault("Restrict.Adventure", true);
        Chester.Get().getConfig().addDefault("Restrict.Survival", false);
        Chester.Get().getConfig().options().copyDefaults(true);
        Chester.Get().saveConfig();
    }
}
