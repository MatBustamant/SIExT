/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author oveja
 */
public class BasedeDatos {
     private static String url = "jdbc:sqlite:bd.db";
     
     public static Connection getConnection() throws SQLException {
         Connection conexion = null;
         conexion = DriverManager.getConnection(url);
         return conexion;
     }

}
