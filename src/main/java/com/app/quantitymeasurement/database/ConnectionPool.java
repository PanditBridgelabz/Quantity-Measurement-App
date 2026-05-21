package com.app.quantitymeasurement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionPool {

    private final String url;
    private final String user;
    private final String pass;

    public ConnectionPool() {
        this.url = "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1;MODE=MYSQL";
        this.user = "sa";
        this.pass = "";
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ignored) { }
    }

    public Connection acquire() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
