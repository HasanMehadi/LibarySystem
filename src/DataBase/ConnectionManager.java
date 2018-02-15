package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionManager {

    public static String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static String driverName = "oracle.jdbc.driver.OracleDriver";
    public static String username = "sys as sysdba";
    public static String password = "hasan";
    public static Connection con;

    public static Connection getConnection() {

        try {

            Class.forName(driverName);

            try {

                con = DriverManager.getConnection(url, username, password);


            } catch (SQLException ex) {

                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {

            System.out.println("Driver not found.");
            System.exit(1);

        }
        return con;
    }
    public static void close(){
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("connection cant be closed");

            e.printStackTrace();
        }
    }
}
