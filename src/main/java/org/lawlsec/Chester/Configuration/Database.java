package org.lawlsec.Chester.Configuration;

import org.lawlsec.Chester.Chester;

public class Database {
    public static String getUser() {
        return Chester.Get().getConfig().getString("Database.User");
    }

    public static String getPass() {
        return Chester.Get().getConfig().getString("Database.Pass");
    }

    public static String getHost() {
        return Chester.Get().getConfig().getString("Database.Host");
    }

    public static String getPort() {
        return Chester.Get().getConfig().getString("Database.Port");
    }

    public static String getDatabase() {
        return Chester.Get().getConfig().getString("Database.Database");
    }

    public static boolean isSSL() {
        return Chester.Get().getConfig().getBoolean("Database.SSL");
    }
}
