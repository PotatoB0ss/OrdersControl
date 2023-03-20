package com.example.cemenuta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    protected Connection connect() {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost/application";
        String user = "postgres";
        String password = "koko2022";

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

