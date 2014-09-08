package database;

import java.awt.List;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sql {
    public static Connection connect(){
        Connection conn = null;
        File s = new File(sql.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String appLocation = s.getPath();
        if(appLocation.lastIndexOf(".jar")== -1) {
            appLocation = "";
        } else {
            appLocation = appLocation.substring(0, appLocation.lastIndexOf("\\")+1);
        }
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+appLocation+"databases\\Filmai_v1.sqlite");
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return conn;
    }
    public static void getMovies(List list, Connection conn){
        Statement stmt = null;
        try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT PAVADINIMAS FROM Filmai;");
                while(rs.next()){
                    list.add(rs.getString(1));
                }
                rs.close();
            } catch (Exception e) {
                System.out.println("No result: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(sql.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
}
