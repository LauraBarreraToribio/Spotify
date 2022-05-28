module Spotify.prueba {

    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.controls;

    exports Classes;
    opens Classes to javafx.fxml;
    opens com.example.spotify_prueba;
    exports com.example.spotify_prueba;
}