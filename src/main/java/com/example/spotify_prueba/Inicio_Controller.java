package com.example.spotify_prueba;

import animatefx.animation.Shake;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.sql.*;


import java.util.ResourceBundle;


public class Inicio_Controller implements Initializable{



    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorusuario;
    @FXML
    private Label welcome;
    @FXML
    private Label welcome2;
    Stage stage = new Stage();




    public void comprobacionUsuarioClick(ActionEvent event) throws SQLException, IOException, InterruptedException {

            Connection con= Conexion.conexion();
            ResultSet rs = consultaUsuario(con);
            if(rs.next()){
                Node source = (Node) event.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();
                cambiarescena();
            }else{
                errorusuario.setVisible(true);
                Shake sk = new Shake();
                sk.setNode(errorusuario);
                sk.setSpeed(0.8);
                sk.setCycleCount(3);
                sk.play();
            }
        }




   public ResultSet consultaUsuario(Connection conexion) throws SQLException, IOException {

        ResultSet rs = null;
        Statement  s = conexion.createStatement();

        rs = s.executeQuery("SELECT id FROM usuario WHERE username ='" + username.getText() + "' AND password='" + password.getText() + "'");

        return  rs;
    }





    public void cambiarescena() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Spotify-view.fxml"));

        Parent root = fxmlLoader.load();
        Spotify_Controller controller2 = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        controller2.iniciarUsername(username.getText(),stage,this);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        FadeTransition ft = new FadeTransition();
        ft.setNode(welcome);
        ft.setDuration(new Duration(2000));
        ft.setToValue(1.0);
        ft.setFromValue(0.0);
        ft.play();
        FadeTransition ft2 = new FadeTransition();
        ft2.setNode(welcome2);
        ft2.setDuration(new Duration(2000));
        ft2.setToValue(1.0);
        ft2.setFromValue(0.0);
        ft2.play();
        errorusuario.setVisible(false);
    }

    public void quitarerror(MouseEvent mouseEvent) {
        errorusuario.setVisible(false);
    }

    public void abrirPlan(ActionEvent actionEvent) throws IOException {
        Node source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("planRegistro.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();


    }




}
