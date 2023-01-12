package com.example.demo1;
import java.sql.*;
public class Connexion {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test1";
    static final String USER = "root";
    static final String PASS = "";
    static String sql;
    static ResultSet rs;
    static ResultSet rs1;
    static Connection conn = null;
    static Statement stmt = null;



    public  static void Connection()  throws ClassNotFoundException, SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(DB_URL,USER,PASS);




        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            System.out.println("Connexion échouée");
            e.printStackTrace();
            System.out.println("erreur");

        }

    }
}
