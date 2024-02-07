import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public  class connect {


    public static Connection  connect1 () {
         String url = "jdbc:postgresql://localhost/postgres";
        
         String user = "postgres";
         String password = "admin";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static Connection connect2() {
         
     String url = "jdbc:postgresql://localhost/sysapp";
     String user = "postgres";
     String password = "admin";
        


        Connection conn = null ;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }



}