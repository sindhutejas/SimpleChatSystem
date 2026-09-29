package com.chat;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://db01.dbhost.dev:5051/db_454p5purz";

    private static final String USER =
            "user_454p5purz";

    private static final String PASSWORD =
            "p454p5purz";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

