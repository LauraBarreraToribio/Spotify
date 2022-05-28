package com.example.spotify_prueba;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Letra_Controller implements Initializable {

    private Spotify_Controller controller1;
    private Stage stage;



    @FXML
    private TextArea txtarea;


    public void iniciarCancion(String text, Stage stage, Spotify_Controller spotify_controller) {

        this.controller1 = spotify_controller;
        this.stage = stage;


        Scanner scanner = null;

        try {
            Connection con = Conexion.conexion();
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT letra.ruta from letra " +
                    "INNER JOIN cancion ON letra.cancion_id = cancion.id" +
                    " where cancion.titulo = '" + text + "'");

            if (rs.next()) {
                String ruta = rs.getString("ruta");

                File file = new File("Spotify\\src\\main\\resources"+ruta+"");

                BufferedReader br = new BufferedReader(new FileReader(file));

                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    this.txtarea.setWrapText(true);
                    this.txtarea.appendText(strCurrentLine + "\n");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException o) {
            o.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}