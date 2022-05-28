package com.example.spotify_prueba;

import Classes.Usuario;
import animatefx.animation.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.ResourceBundle;

public class Registro_Controller implements Initializable {

    ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private ToggleButton genero1;
    @FXML
    private ToggleButton genero2;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField country;
    @FXML
    private TextField email;
    @FXML
    private TextField cp;
    @FXML
    private DatePicker fecha;
    Stage stage = new Stage();
    private static String id;
    String g;

    private static String tipo_plan;

    @FXML
    private Label errorregistro;
    @FXML
    private Label errorUsuarioExiste;
    @FXML
    private Label tarjeta;
    @FXML
    private Label paypal;
    @FXML
    private Label tarjetacentro;
    @FXML
    private Label paypalcentro;
    @FXML
    private Pane tarjetapane;
    @FXML
    private Pane paypalpane;
    @FXML
    private TextField tjnumber;
    @FXML
    private TextField monthc;
    @FXML
    private TextField yearc;
    @FXML
    private TextField cv;
    @FXML
    private TextField email_paypal;
    private static String pago;
    private static String tjnumberst;
    private static String monthcst;
    private static String yearcst;
    private static String cvst;
    private static String email_paypalstr;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirRegistroPremium(MouseEvent event) {
        tipo_plan = "premium";


        if (pago.equals("tarjeta")) {
            try {


            tjnumberst = tjnumber.getText();
            monthcst = monthc.getText();
            yearcst = yearc.getText();
            cvst = cv.getText();

            if(cvst.length()>3){
                System.out.println("codigomal");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El código de seguridad no puede ser más de 3");
                alert.show();
            }else if (tjnumberst.length()!=16){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tu tarjeta no es válida.\nSon 16 números.");
                alert.show();
            }else if (yearcst.length()>4){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El año de caducidad no puede ser mayor que 4");
                alert.show();
            }else if(Year.parse(yearcst).getValue() < 2022){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El año de caducidad debe ser mayor que el año en el que estamos");
                alert.show();
            } else if (Integer.parseInt(monthcst)>12 || Integer.parseInt(monthcst) == 0 ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El mes de caducidad no puede ser mayor a 12");
                alert.show();
            }else {

                Node source = (Node) event.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registro.fxml"));
                    Parent root = null;

                    root = fxmlLoader.load();

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("Spotify");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }catch (RuntimeException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No has rellenado todos los campos");
                alert.show();
            }
        }else if(pago.equals("paypal")){

        email_paypalstr = email_paypal.getText();

        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registro.fxml"));
            Parent root = null;

            root = fxmlLoader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Spotify");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Debes rellenar la forma de pago");
        alert.show();
    }

}



    public void abrirRegistroFree(MouseEvent mouseEvent) throws IOException {
        tipo_plan = "free";

        Node source = (Node) mouseEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registro.fxml"));
        Parent root = null;

        root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();

    }

    public void registrarUsuario(ActionEvent actionEvent) throws SQLException, IOException {

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT username from usuario where username LIKE '"+username.getText()+"'");

            if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty() || g.isEmpty() || country.getText().isEmpty()) {
                errorregistro.setVisible(true);
                errorregistro.setText("No has rellenado bien los campos");
                errorregistro.setStyle("-fx-text-fill:red;");
                errorUsuarioExiste.setVisible(false);
                Shake sk = new Shake();
                sk.setNode(errorregistro);
                sk.setSpeed(0.8);
                sk.setCycleCount(2);
                sk.play();
            } else {

                if(rs.next()) {
                    errorUsuarioExiste.setText("Ese usuario ya existe");
                    errorUsuarioExiste.setStyle("-fx-text-fill:red;");
                    errorUsuarioExiste.setVisible(true);
                    Shake sk = new Shake();
                    sk.setNode(errorUsuarioExiste);
                    sk.setSpeed(0.8);
                    sk.setCycleCount(2);
                    sk.play();
                    errorregistro.setVisible(false);
                }else{
                Node source = (Node) actionEvent.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();

                    s.executeUpdate("Insert into usuario(username,password,email,genero,fecha_nacimiento,pais,codigo_postal)" +
                            " values ('" + username.getText() + "','" + password.getText() + "','" + email.getText() + "','" + g + "','" + fecha.getValue() + "','" + country.getText() + "','" + cp.getText() + "')");
                    if (tipo_plan.equals("free")) {
                        agregarFree();
                    } else if (tipo_plan.equals("premium")) {
                        hacerPremium();
                    }
                }
            }
    }

    public void hacerPremium() throws SQLException, IOException {
        
        Connection conexion = Conexion.conexion();
        Statement s = conexion.createStatement();
        
        LocalDate fecha_renovacion = null;
        LocalDate fecha_actual = cogerFechaActual();
        float total = 0;

            if (id.equals("premiumMes")) {
                fecha_renovacion = cogerFechaActual().plusMonths(1);
                total = (float) 5.99;

            } else if(id.equals("premiumAño")){
                fecha_renovacion = cogerFechaActual().plusYears(1);
                total = (float) 69.99;

            }

            s.executeUpdate("Insert into premium(fecha_renovación,usuario_id)" +
                    "values ('"+fecha_renovacion +"',"+cogerIDUsuarioLoggeado()+")");

            s.executeUpdate("Insert into forma_pago values()");

            s.executeUpdate("Insert into suscripcion(fecha_inicio,fecha_fin,premium_usuario_id)" +
                    "values ('"+fecha_actual+"','"+fecha_renovacion+"',"+cogerIDUsuarioLoggeado()+")");


            s.executeUpdate("Insert into pago(fecha,total,forma_pago_id,suscripcion_id)" +
                    "values ('"+fecha_actual+"','"+ total+"',(SELECT max(id) from forma_pago),(SELECT id from suscripcion where premium_usuario_id = "+cogerIDUsuarioLoggeado()+"))");


            if (pago.equals("tarjeta")) {
                Year year = Year.parse(yearcst);
                s.executeUpdate("Insert into tarjeta_credito(numero_tarjeta,mes_caducidad,anyo_caducidad,codigo_seguridad,forma_pago_id)" +
                        "values ('"+tjnumberst+"',"+monthcst+","+year+","+cvst+",(SELECT max(id) from forma_pago))");
            } else if (pago.equals("paypal")){
                s.executeUpdate("Insert into paypal(username_paypal,forma_pago_id)" +
                        " values ('"+email_paypalstr+"',(SELECT max(id) from forma_pago))");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Te has registrado correctamente");
            alert.showAndWait();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inicio.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();



    }

    public void agregarFree() throws SQLException {

            try {
              Connection  conexion = Conexion.conexion();

            Statement s = conexion.createStatement();

            LocalDate fecha_actual = cogerFechaActual();
            s.executeUpdate("Insert into free(fecha_revision,tiempo_publicidad,usuario_id)" +
                    "values ('" + fecha_actual + "','230',(SELECT id from usuario WHERE usuario.username = '" + username.getText() + "'))");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Te has registrado correctamente");
            alert.showAndWait();


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inicio.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Spotify");
            stage.setScene(scene);
            stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }


    @FXML
    private void formasdepago(MouseEvent event) throws IOException {

        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        id = event.getPickResult().getIntersectedNode().getId();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("forma_pago.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();

    }

    public void generoElegido(ActionEvent actionEvent) {
        genero1.setToggleGroup(toggleGroup);
        genero2.setToggleGroup(toggleGroup);

        genero1.setStyle("-fx-border-color:transparent");
        genero2.setStyle("-fx-border-color:transparent");

        ToggleButton selectedToggleButton =
                (ToggleButton) toggleGroup.getSelectedToggle();
        selectedToggleButton.setStyle("-fx-background-color:#4387aa;-fx-border-color:#4387aa;-fx-border-width:1;-fx-border-radius:10;");

        g = selectedToggleButton.getText();

    }

    public void cerrarPlanes(MouseEvent mouseEvent) throws IOException {
        Node source = (Node) mouseEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inicio.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();
    }

    public void atrasRegistro(MouseEvent mouseEvent) throws IOException {
        Node source = (Node) mouseEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("planRegistro.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();


    }

    public LocalDate cogerFechaActual(){

        LocalDate fecha_actual = LocalDate.now();
        return fecha_actual;
    }


    public void tarjeta(MouseEvent mouseEvent) {
        pago = "tarjeta";
        paypalpane.setMouseTransparent(true);
        paypalpane.setVisible(false);
        tarjetapane.setVisible(true);
        tarjeta.setVisible(false);
        tarjetacentro.setVisible(true);
        paypal.setVisible(false);



    }

    public void paypal(MouseEvent mouseEvent) {
        pago = "paypal";
        tarjetapane.setMouseTransparent(true);
        paypalpane.setVisible(true);
        tarjetapane.setVisible(false);
        tarjeta.setVisible(false);
        paypalcentro.setVisible(true);
        paypal.setVisible(false);
    }

    public void cerrarPago(MouseEvent mouseEvent) throws IOException {

        Node source = (Node) mouseEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("planRegistro.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();

    }

    public int cogerIDUsuarioLoggeado() throws SQLException {

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT id from usuario where username LIKE '"+username.getText()+"'");
        int id = 0;
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }

}
