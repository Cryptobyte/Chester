package org.lawlsec.Chester.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQL {
    /**
     * Get a connection to the database.
     * It is your job to close this connection!
     *
     * @return Connection to Database
     */
    public static Connection getConnection() {
        if ((org.lawlsec.Chester.Configuration.Database.getHost() == null) ||
            (org.lawlsec.Chester.Configuration.Database.getPort() == null) ||
            (org.lawlsec.Chester.Configuration.Database.getDatabase() == null) ||
            (org.lawlsec.Chester.Configuration.Database.getUser() == null) ||
            (org.lawlsec.Chester.Configuration.Database.getPass() == null))
        {

            System.out.println("Config variables missing!");
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection(
                    String.format(
                            "jdbc:mysql://%s:%s/%s?useSSL=%s",
                            org.lawlsec.Chester.Configuration.Database.getHost(),
                            org.lawlsec.Chester.Configuration.Database.getPort(),
                            org.lawlsec.Chester.Configuration.Database.getDatabase(),
                            String.valueOf(org.lawlsec.Chester.Configuration.Database.isSSL()).toLowerCase()
                    ),
                    org.lawlsec.Chester.Configuration.Database.getUser(),
                    org.lawlsec.Chester.Configuration.Database.getPass()
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            System.out.println("jdbc driver not found!");
            return null;
        }
    }
}
