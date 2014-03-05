package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection connect() {
        try {
            String url = "jdbc:mysql://127.0.0.1/db/db.sql";
            String username = "root";
            String password = "root";
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
