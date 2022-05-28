package com.example.spotify_prueba;
import java.sql.*;

public class Conexion {

    static Connection conexion() throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33006/spotify", "root",
                "dbrootpass");

        return con;
    }
}


