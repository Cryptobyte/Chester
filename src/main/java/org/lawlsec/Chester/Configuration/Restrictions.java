package org.lawlsec.Chester.Configuration;

import org.lawlsec.Chester.Chester;

public class Restrictions {
    public static boolean Creative() {
        return Chester.Get().getConfig().getBoolean("Restrict.Creative", true);
    }

    public static boolean Spectator() {
        return Chester.Get().getConfig().getBoolean("Restrict.Spectator", true);
    }

    public static boolean Adventure() {
        return Chester.Get().getConfig().getBoolean("Restrict.Adventure", true);
    }

    public static boolean Survival() {
        return Chester.Get().getConfig().getBoolean("Restrict.Survival", false);
    }
}
