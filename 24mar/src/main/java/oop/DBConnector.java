package oop;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    static Connection connect;
    static final String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    static final String db_url = "jdbc:mysql://localhost/product_java"; // change url based on your own database
    static final String user = "root";
    static final String password = "";

    public static void initDBConnection()
    {
        try {
            Class.forName(jdbc_driver);
            connect = DriverManager.getConnection(db_url, user, password);

            if (connect != null)
            {
                System.out.println("Connection established");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}