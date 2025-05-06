/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.working;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kairav
 */
public class DB {

    // Fields to store the database connection and prepared statement
    private static Connection conn;             
    private static PreparedStatement ps;       

    // Initializes the database connection Loads the JDBC driver and connects to the database
    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        String connectionUrl = "jdbc:mysql://localhost:3306/elmstreet";
        
        conn = DriverManager.getConnection(connectionUrl, "root", "KAIRAVcr7");
    }

    // Reads data from the database using the provided SQL query Returns: ResultSet - the result set containing the data retrieved by the query
    public static ResultSet read(String query) throws SQLException {
        ps = conn.prepareStatement(query);
        System.out.println(query);
        ResultSet rs = ps.executeQuery();  
        return rs;  
    }

    // Updates the database using the provided SQL query
    public static void update(String query) throws SQLException {
        ps = conn.prepareStatement(query);
        ps.executeUpdate();  
        ps.close();  
    }

}

