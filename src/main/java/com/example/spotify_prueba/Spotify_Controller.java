package com.example.spotify_prueba;
/*
import static com.example.spotify_prueba.Conexion.conexion;

 */

import Classes.*;
import java.text.DecimalFormat;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class Spotify_Controller implements Initializable {

    private Inicio_Controller controller1;
    private Stage stage;
    @FXML
    //label que se inicia con el username//
    private Label user_label;

    //username recibido//
    private static String username;

    @FXML
    private Label id_playlistlabel;

    // labels cancion en reproduccion
    @FXML
    private Label nombre_artista;
    @FXML
    private Label nombre_cancion;

    //diferentes labels de nombres de cada vista
    @FXML
    private Label artista_label;
    @FXML
    private Label altitulo;

    //strings de los labels porque a veces me daban null
    private static String titCancion;
    private static String titulocancion;

    // txtfield nombre de la playlist que vas a crear //
    @FXML
    private TextField ptitulo;

    // corazón de canción fav
    @FXML
    private ImageView corazon;

    // corazón de playlist fav
    @FXML
    private ImageView corazonPlaylist;




    //// INITIALIZE Y RECIBIR PARAMETROS ////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lista_Artistas = FXCollections.observableArrayList();
        todaslasplaylist_lista = FXCollections.observableArrayList();
        playlistsPatrocinadas = FXCollections.observableArrayList();
        cancionesPlaylistPatrocinadas_lista = FXCollections.observableArrayList();
        cancionesPlaylistQueSigues_lista = FXCollections.observableArrayList();
        playlistsQueSigues = FXCollections.observableArrayList();
        amigos_lista = FXCollections.observableArrayList();
        lista_podcasts = FXCollections.observableArrayList();
        listaCapitulosPodcast = FXCollections.observableArrayList();
        lista_seguirUsuario = FXCollections.observableArrayList();
        lista_seguirArtista = FXCollections.observableArrayList();
        playlistslista = FXCollections.observableArrayList();
        canciones_playlist_lista = FXCollections.observableArrayList();
        albums = FXCollections.observableArrayList();
        albumscolaboracion = FXCollections.observableArrayList();
        cancionesfavoritas_lista = FXCollections.observableArrayList();
        filtro = FXCollections.observableArrayList();
    }

    /// método en el que recibo del Inicio cotroller el username ///
    public void iniciarUsername(String text, Stage stage, Inicio_Controller inicio_controller) {
        user_label.setText(text.toUpperCase(Locale.ROOT));
        this.controller1 = inicio_controller;
        this.stage = stage;

        this.username = text;


    }

    /////////////// PANE HOME /////////////////
    @FXML
    private AnchorPane paneHome;

    // tabla patrocinadas //
    @FXML
    private TableView<Patrocinada> tablaPatrocinadas;
    @FXML
    private TableColumn<Patrocinada, String> patrocinadascol;

    ObservableList<Patrocinada> playlistsPatrocinadas;

    // tabla canciones patrocinadas //
    @FXML
    private TableView<Song> cancionesPlaylistPatrocinadas_tabla;
    @FXML
    private TableColumn<Song, String> cancionesPlaylistPatrocinadas_col;

    ObservableList<Song> cancionesPlaylistPatrocinadas_lista;

    // tablas playlists que sigues //
    @FXML
    private TableView<Activa> tablaPlaylistsQueSigues;
    @FXML
    private TableColumn<Activa, String> playlistsQueSiguescol;

    ObservableList<Activa> playlistsQueSigues;

    // tabla canciones playlists que sigues //
    @FXML
    private TableView<Song> cancionesPlaylistQueSigues_tabla;
    @FXML
    private TableColumn<Song, String> cancionesPlaylistQueSigues_col;

    ObservableList<Song> cancionesPlaylistQueSigues_lista;

    public void iniciarHome(MouseEvent mouseEvent) throws SQLException, FileNotFoundException {
        paneArtistas2.setVisible(false);
        paneHome.setVisible(true);
        paneAlbums.setVisible(false);
        paneCancionesPlaylist.setVisible(false);
        panefavoritas.setVisible(false);
        panePodcasts.setVisible(false);
        transicionEscenas(paneHome);

        tablaPlaylistsQueSigues.setVisible(false);
        tablaPatrocinadas.setVisible(false);
        cancionesPlaylistQueSigues_tabla.setVisible(false);
        cancionesPlaylistPatrocinadas_tabla.setVisible(false);
        paneTodasLasPlaylist.setVisible(false);

    }

    @FXML
    private void iniciarPatrocinadas(MouseEvent event) throws SQLException {

        tablaPatrocinadas.setVisible(true);
        transicionEscenas(tablaPatrocinadas);
        patrocinadascol.setCellValueFactory(new PropertyValueFactory<>("titulo"));


        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        Statement s2 = con.createStatement();
        ResultSet rs1 = s.executeQuery("SELECT * from patrocinada");


        while (rs1.next()) {

            int playlist_id = rs1.getInt("playlist_id");
            int patrocinada = rs1.getInt("patrocinada");
            ResultSet rs2 = s2.executeQuery("SELECT titulo from playlist where id = '" + playlist_id + "'");

            while (rs2.next()) {
                String titulo = rs2.getString("titulo");
                Patrocinada p = new Patrocinada(titulo, playlist_id, patrocinada);
                playlistsPatrocinadas.add(p);
            }
            tablaPatrocinadas.setItems(playlistsPatrocinadas);
        }
    }

    public void enseñarCancionesPlaylistPatrocinadas(MouseEvent mouseEvent) {

        try {
            cancionesPlaylistPatrocinadas_tabla.setVisible(true);
            cancionesPlaylistQueSigues_tabla.setVisible(false);
            transicionEscenas(cancionesPlaylistPatrocinadas_tabla);
            cancionesPlaylistPatrocinadas_col.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            cancionesPlaylistPatrocinadas_lista.clear();

            Playlist p = tablaPatrocinadas.getSelectionModel().getSelectedItem();

            Connection con = null;
            try {

                con = Conexion.conexion();
                Statement s1 = con.createStatement();
                ResultSet rs1 = s1.executeQuery("SELECT cancion.titulo from cancion " +
                        "INNER JOIN anyade_cancion_playlist a on a.cancion_id = cancion.id " +
                        "INNER JOIN playlist p on a.playlist_id = p.id " +
                        "INNER JOIN patrocinada on p.id = patrocinada.playlist_id WHERE p.titulo LIKE '" + p.getTitulo() + "'");


                while (rs1.next()) {
                    String tituloa = rs1.getString("titulo");
                    Song c = new Song(tituloa);
                    cancionesPlaylistPatrocinadas_lista.add(c);
                }

                comprobar_si_Playlist_es_Favorita(p.getTitulo());
                cancionesPlaylistPatrocinadas_tabla.setItems(cancionesPlaylistPatrocinadas_lista);

            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch (NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses en los titulos!");
            alert.show();
        }
    }


    public void iniciarPlaylistsQueSigues(MouseEvent mouseEvent) throws SQLException {

        tablaPlaylistsQueSigues.setVisible(true);
        transicionEscenas(tablaPlaylistsQueSigues);
        playlistsQueSiguescol.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from playlist" +
                " INNER JOIN sigue_playlist sp on playlist.id = sp.playlist_id" +
                " INNER JOIN usuario u on sp.usuario_id = u.id where u.id = " + cogerIDUsuarioLoggeado() + " ");

        while (rs.next()) {
            String titulo = rs.getString("titulo");
            Activa a = new Activa(titulo);
            playlistsQueSigues.add(a);
        }
        tablaPlaylistsQueSigues.setItems(playlistsQueSigues);


    }

    public void iniciarCancionesPlaylistsQueSigues(MouseEvent mouseEvent) throws SQLException {

        try {
            String titulo = tablaPlaylistsQueSigues.getSelectionModel().getSelectedItem().getTitulo();

            cancionesPlaylistPatrocinadas_tabla.setVisible(false);
            cancionesPlaylistQueSigues_tabla.setVisible(true);
            transicionEscenas(cancionesPlaylistQueSigues_tabla);
            cancionesPlaylistQueSigues_lista.clear();

            cancionesPlaylistQueSigues_col.setCellValueFactory(new PropertyValueFactory<>("titulo"));

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * from cancion" +
                    " INNER JOIN anyade_cancion_playlist ac on cancion.id = ac.cancion_id" +
                    " INNER JOIN playlist p on ac.playlist_id = p.id WHERE p.titulo LIKE '" + titulo + "'");

            while (rs.next()) {
                String titulo2 = rs.getString("titulo");
                Song c = new Song(titulo2);
                cancionesPlaylistQueSigues_lista.add(c);
            }
            cancionesPlaylistQueSigues_tabla.setItems(cancionesPlaylistQueSigues_lista);
        }catch(NullPointerException n){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses en los títulos!");
            alert.show();
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////// PANE FAVORITAS /////////////////

    @FXML
    private AnchorPane panefavoritas;
    @FXML
    private TableView<Song> tablafavoritas;
    @FXML
    private TableColumn<Song, String> nombrefavoritascol;
    @FXML
    private TableColumn<Song, String> reproduccionesfavoritascol;
    @FXML
    private TableColumn<Song, String> duracionfavoritascol;

    ObservableList<Song> cancionesfavoritas_lista;


    @FXML
    private void iniciarCancionesFavoritas(MouseEvent event) {
        paneAlbums.setVisible(false);
        paneCancionesPlaylist.setVisible(false);
        paneArtistas2.setVisible(false);
        paneHome.setVisible(false);
        panefavoritas.setVisible(true);
        paneTodasLasPlaylist.setVisible(false);
        panePodcasts.setVisible(false);
        transicionEscenas(panefavoritas);

        nombrefavoritascol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        reproduccionesfavoritascol.setCellValueFactory(new PropertyValueFactory<>("numero_reproducciones"));
        duracionfavoritascol.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        cancionesfavoritas_lista.clear();
        Connection con = null;
        try {
            con = Conexion.conexion();
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT c.* from cancion c" +
                    " INNER JOIN guarda_cancion gp on gp.cancion_id = c.id " +
                    " INNER JOIN usuario u on u.id = gp.usuario_id " +
                    " WHERE u.id = " + cogerIDUsuarioLoggeado() + "");
            int i = 0;
            while (rs.next()) {
                i++;
                String nombre = rs.getString("titulo");
                int reproducciones = rs.getInt("numero_reproducciones");
                double duracionsinredondeo = rs.getInt("duracion");
                double duracionenminutos = duracionsinredondeo / 60;
                double duracion = (double) Math.round(duracionenminutos * 100d) / 100;
                Song cancion = new Song(nombre, reproducciones, duracion);
                cancionesfavoritas_lista.add(cancion);
            }
            System.out.println(i);
            tablafavoritas.setItems(cancionesfavoritas_lista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /////////////// PANE ARTISTAS /////////////////
    @FXML
    private AnchorPane paneArtistas2;
    @FXML
    private TableView<Artista> tablaArtistas;
    @FXML
    private TableColumn<Artista, String> nombreArtistacol;
    @FXML
    private TableColumn<Artista, ImageView> imagencol;

    ObservableList<Artista> lista_Artistas;

    @FXML
    private TextField filtrarArtista;

    @FXML
    private ImageView imagenArtista;

    public void iniciarArtistas2(MouseEvent mouseEvent) throws SQLException, FileNotFoundException {
        paneArtistas2.setVisible(true);
        transicionEscenas(paneArtistas2);
        transicionEscenas(tablaArtistas);
        paneAlbums.setVisible(false);
        paneTodasLasPlaylist.setVisible(false);
        paneCancionesPlaylist.setVisible(false);
        panefavoritas.setVisible(false);
        paneHome.setVisible(false);
        panePodcasts.setVisible(false);
        nombreArtistacol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        imagencol.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from artista");

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String ruta = rs.getString("imagen");
            ImageView iv = new ImageView(getClass().getResource("/artistas/" + ruta + "").toExternalForm());
            iv.setFitWidth(150);
            iv.setFitHeight(150);

            Artista a = new Artista(nombre, iv, ruta);
            lista_Artistas.add(a);
        }
        tablaArtistas.setItems(lista_Artistas);

    }

    public void buscarArtistas(KeyEvent keyEvent) throws SQLException {
        lista_Artistas.clear();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String nombreBuscado = filtrarArtista.getText();
            nombreArtistacol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            imagencol.setCellValueFactory(new PropertyValueFactory<>("imagen"));

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT nombre,imagen from artista");

            while (rs.next()) {
                String nombreArtista = rs.getString("nombre");
                String ruta = rs.getString("imagen");
                Image imagen2 = new Image(getClass().getResourceAsStream("/artistas/" + ruta + ""));
                ImageView iv = new ImageView(imagen2);
                iv.setFitWidth(150);
                iv.setFitHeight(150);

                if (nombreArtista.contains(nombreBuscado) || nombreArtista.equalsIgnoreCase(nombreBuscado)) {
                    Artista a = new Artista(nombreArtista, iv, ruta);
                    lista_Artistas.add(a);
                }
            }
            tablaArtistas.setItems(lista_Artistas);


        }
    }

    public void seleccionarArtista(MouseEvent mouseEvent) throws FileNotFoundException {
        try {
            Artista a = tablaArtistas.getSelectionModel().getSelectedItem();
            iniciarAlbums(a);
            paneArtistas2.setVisible(false);
            transicionEscenas(paneAlbums);
            paneAlbums.setVisible(true);

        }catch(NullPointerException n){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses en los titulos!");
            alert.show();
        }
    }

    public void iniciarAlbums(Artista a) throws FileNotFoundException {


        altitulo.setText("");
        artista_label.setText(a.getNombre());

        Image imagen2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/artistas/" + a.getRuta() + "")));
        imagenArtista.setImage(imagen2);

        cancionestabla.setVisible(false);
        albums.clear();
        albumscolaboracion.clear();
        albumscol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        albumscol2.setCellValueFactory(new PropertyValueFactory<>("titulo"));


        try {
            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT titulo from album " +
                    "INNER JOIN artista a ON a.id = album.artista_id WHERE a.nombre = '" + a.getNombre() + "'; ");

            while (rs1.next()) {

                String nombre = rs1.getString("titulo");
                Album album = new Album(nombre);
                albums.add(album);
            }
            ResultSet rs2 = s.executeQuery("SELECT titulo from album INNER JOIN artista a ON a.id = album.artista_id INNER JOIN colaboracion_artistica ca ON ca.artista_id = a.id WHERE ca.artista_colaborador_id = (SELECT id from artista ar where ar.nombre LIKE '" + a.getNombre() + "')");

            while (rs2.next()) {

                String nombre = rs2.getString("titulo");
                Album album = new Album(nombre);
                albumscolaboracion.add(album);
            }
            albumstabla.setItems(albums);
            albumsColaboraciontabla.setItems(albumscolaboracion);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////// PANE ALBUMS ///////////

    @FXML
    private AnchorPane paneAlbums;

    // tabla albums //
    @FXML
    private TableView<Album> albumstabla;
    @FXML
    private TableColumn<Album, String> albumscol;

    ObservableList<Album> albums;

    // tabla colaboracion //
    @FXML
    private TableView<Album> albumsColaboraciontabla;
    @FXML
    private TableColumn<Album, String> albumscol2;

    ObservableList<Album> albumscolaboracion;

    // tabla canciones del album //
    @FXML
    private TableView<Song> cancionestabla;
    @FXML
    private TableColumn<Song, String> titulocol;
    @FXML
    private TableColumn<Song, String> reproduccionescol;
    @FXML
    private TableColumn<Song, Double> duracioncol;

    ObservableList<Song> canciones;


    public void iniciarCancionesAlbum(MouseEvent event) {

        cancionestabla.setVisible(true);

        canciones = FXCollections.observableArrayList();
        canciones.clear();

        titulocol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        reproduccionescol.setCellValueFactory(new PropertyValueFactory<>("numero_reproducciones"));
        duracioncol.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        Album al;
        if (albumstabla.getSelectionModel().isEmpty()) {
            al = this.albumsColaboraciontabla.getSelectionModel().getSelectedItem();

        } else {
            al = this.albumstabla.getSelectionModel().getSelectedItem();
        }

        altitulo.setText(al.getTitulo());


        Connection con = null;
        try {
            con = Conexion.conexion();
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT * from cancion where album_id = (SELECT id from album where titulo = '" + al.getTitulo() + "') ");

            while (rs.next()) {

                String nombre = rs.getString("titulo");
                int reproducciones = rs.getInt("numero_reproducciones");
                double duracionsinredondeo = rs.getInt("duracion");
                double duracionenminutos = duracionsinredondeo / 60;
                double duracion = (double) Math.round(duracionenminutos * 100d) / 100;
                Song cancion = new Song(nombre, reproducciones, duracion);
                canciones.add(cancion);
            }
            cancionestabla.setItems(canciones);
            cancionestabla.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////// PANE PODCASTS ///////////

    @FXML
    private AnchorPane panePodcasts;

    @FXML
    private TableView<Podcast> tablaPodcast;
    @FXML
    private TableColumn<Podcast,String> nombrePodcastcol;
    @FXML
    private TableColumn<Podcast,String> descripcionPodcastcol;
    @FXML
    private TableColumn<Podcast,Year> anyoPodcastcol;

    ObservableList<Podcast> lista_podcasts;

    @FXML
    private TableView<Capitulo> tablaCapituloPodcast;
    @FXML
    private TableColumn<Capitulo, String> tituloCapitulocol;
    @FXML
    private TableColumn<Capitulo, String> descipcionCapitulocol;

    ObservableList<Capitulo> listaCapitulosPodcast;


    public void iniciarPodcasts(MouseEvent mouseEvent) throws SQLException {
        paneArtistas2.setVisible(false);
        paneAlbums.setVisible(false);
        paneTodasLasPlaylist.setVisible(false);
        paneCancionesPlaylist.setVisible(false);
        panefavoritas.setVisible(false);
        paneHome.setVisible(false);
        tablaCapituloPodcast.setVisible(false);

        panePodcasts.setVisible(true);
        transicionEscenas(panePodcasts);

        lista_podcasts.clear();

        nombrePodcastcol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descripcionPodcastcol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        anyoPodcastcol.setCellValueFactory(new PropertyValueFactory<>("anyo"));


        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from podcast");

        while (rs.next()) {
            String nombrePodcast = rs.getString("titulo");
            String descripcion = rs.getString("descripcion");
            int anyo = rs.getInt("anyo");
            Year a = Year.of(anyo);
            int id = rs.getInt("id");

            Podcast p = new Podcast(id,nombrePodcast,descripcion,a);
            lista_podcasts.add(p);
        }
        tablaPodcast.setItems(lista_podcasts);
    }


    public void enseñarCapitulosPodcast(MouseEvent mouseEvent) throws SQLException {


        try {
            Podcast p = tablaPodcast.getSelectionModel().getSelectedItem();
            tablaCapituloPodcast.setVisible(true);
            transicionEscenas(tablaCapituloPodcast);

            listaCapitulosPodcast.clear();

            tituloCapitulocol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            descipcionCapitulocol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));


            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * from capitulo where podcast_id = "+p.getId()+"");

            while (rs.next()) {
                String nombreCapitulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                int id = rs.getInt("id");

                Capitulo c = new Capitulo(id, nombreCapitulo, descripcion);
                listaCapitulosPodcast.add(c);
            }
            tablaCapituloPodcast.setItems(listaCapitulosPodcast);


        }catch(NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses los títulos!");
            alert.show();
        }
    }

    public void seguirPodcast(MouseEvent mouseEvent) throws SQLException {

        try{
            Podcast p = tablaPodcast.getSelectionModel().getSelectedItem();

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            s.executeUpdate("Insert into podcast_usuario(usuario_id,podcast_id) values ("+cogerIDUsuarioLoggeado()+","+p.getId()+")");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Has seguido al podcast "+p.getTitulo()+"");
            alert.show();
        }catch(NullPointerException n){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado ningún podcast");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ya sigues ese podcast");
            alert.show();
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////// PANE PLAYLISTS ///////////

    @FXML
    private AnchorPane paneTodasLasPlaylist;
    @FXML
    private TableView<Activa> tablaTodaslasPlaylist;
    @FXML
    private TableColumn<Activa, String> tituloPlaylistcol;
    @FXML
    private TableColumn<Activa, String> creadorcol;

    ObservableList<Activa> todaslasplaylist_lista;

    @FXML
    private TextField filtrarPlaylist;

    @FXML
    private Label tituloPlaylistfiltro;

    @FXML
    private TextField txt_playlistbuscada;

    ObservableList<Playlist> filtro;

    private static String titulo2;

    public void iniciarPlaylistsActivas(MouseEvent mouseEvent) throws SQLException {
        todaslasplaylist_lista.clear();
        paneArtistas2.setVisible(false);
        panePodcasts.setVisible(false);
        paneCancionesPlaylist.setVisible(false);
        transicionEscenas(tablaTodaslasPlaylist);
        paneAlbums.setVisible(false);

        paneHome.setVisible(false);
        panefavoritas.setVisible(false);

        paneTodasLasPlaylist.setVisible(true);
        todaslasplaylist_lista.clear();

        tituloPlaylistcol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        creadorcol.setCellValueFactory(new PropertyValueFactory<>("propietario"));

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs1 = s.executeQuery("SELECT p.titulo,u.username,p.id from playlist p INNER JOIN usuario u on u.id = usuario_id group by p.titulo");


        while (rs1.next()) {
            String titulo = rs1.getString("titulo");
            String propietario = rs1.getString("username");
            int id = rs1.getInt("id");
            Activa a = new Activa(id,titulo, propietario);
            todaslasplaylist_lista.add(a);

        }
        tablaTodaslasPlaylist.setItems(todaslasplaylist_lista);
    }

    public void buscarPlaylist(KeyEvent keyEvent) throws SQLException {
        todaslasplaylist_lista.clear();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            tituloPlaylistcol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            creadorcol.setCellValueFactory(new PropertyValueFactory<>("propietario"));

            String playlistbuscada = txt_playlistbuscada.getText();


            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT p.titulo,u.username from playlist p INNER JOIN usuario u on u.id = usuario_id group by p.titulo");

            while (rs.next()) {
                String nombrePlaylist = rs.getString("titulo");
                String propietario = rs.getString("username");

                if (nombrePlaylist.contains(playlistbuscada) || nombrePlaylist.equalsIgnoreCase(playlistbuscada)) {
                    Activa a = new Activa(nombrePlaylist, propietario);
                    todaslasplaylist_lista.add(a);
                }
            }
            tablaTodaslasPlaylist.setItems(todaslasplaylist_lista);
        }
    }

    public void filtrarPlaylist(MouseEvent mouseEvent) throws SQLException {
        filtro.clear();
        titulo2 = filtrarPlaylist.getText();

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs1 = s.executeQuery("SELECT titulo from playlist " +
                " WHERE titulo LIKE '"+titulo2+"'");

        while (rs1.next()) {
            String titulo = rs1.getString("titulo");
            Activa p = new Activa(titulo);
            tituloPlaylistfiltro.setText(p.getTitulo());
            filtro.add(p);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////// PANE PLAYLISTS DEL USUARIO ///////////

    @FXML
    private AnchorPane paneCancionesPlaylist;
    @FXML
    private TableView<Song> cancionesPlaylistTabla;
    @FXML
    private TableColumn<Song, String> tituloCancionPlaylist;

    @FXML
    private Label nombre_playlistLabel;

    @FXML
    public void enseñarCancionesPlaylistsPanePlaylist(MouseEvent mouseEvent) {

        try {

            Activa a = tablaTodaslasPlaylist.getSelectionModel().getSelectedItem();
            idActiva = a.getId();
            canciones_playlist_lista.clear();

            paneAlbums.setVisible(false);
            paneArtistas2.setVisible(false);
            paneHome.setVisible(false);
            panePodcasts.setVisible(false);
            panefavoritas.setVisible(false);
            paneTodasLasPlaylist.setVisible(false);
            paneCancionesPlaylist.setVisible(true);
            transicionEscenas(paneCancionesPlaylist);

            tituloCancionPlaylist.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            canciones_playlist_lista.clear();

            nombre_playlistLabel.setText(a.getTitulo());


            Connection con = null;
            try {

                con = Conexion.conexion();
                Statement s = con.createStatement();
                ResultSet rs1 = s.executeQuery("SELECT c.titulo from cancion c " +
                        "INNER JOIN anyade_cancion_playlist an ON c.id = an.cancion_id " +
                        "INNER JOIN usuario u ON an.usuario_id = u.id " +
                        "INNER JOIN playlist p ON an.playlist_id = p.id WHERE u.username LIKE '"+username+"' AND p.id ="+idActiva+"");


                while (rs1.next()) {
                    String tituloa = rs1.getString("titulo");
                    Song c = new Song(tituloa);
                    canciones_playlist_lista.add(c);
                }

                comprobar_si_Playlist_es_Favorita(a.getTitulo());
                cancionesPlaylistTabla.setItems(canciones_playlist_lista);

            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses en los títulos!");
            alert.show();
        }
    }

    public void seguirPlaylist(MouseEvent mouseEvent) throws SQLException {


        Connection con = null;
        con = Conexion.conexion();
        Statement s = con.createStatement();
        Statement s2 = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from playlist " +
                "INNER JOIN sigue_playlist sp on sp.playlist_id = playlist.id " +
                "INNER JOIN usuario u on u.id = sp.usuario_id WHERE playlist.id ="+idActiva+" and u.id="+cogerIDUsuarioLoggeado()+"");

        if (rs.next()) {
            s2.executeUpdate("DELETE FROM sigue_playlist where usuario_id = " + cogerIDUsuarioLoggeado() + " and playlist_id = "+idActiva+"");

            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_love.png")));
            corazonPlaylist.setImage(img2);

        } else {

            s2.executeUpdate("INSERT INTO sigue_playlist(usuario_id,playlist_id) values ("+cogerIDUsuarioLoggeado() +","+idActiva+")");

            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_favorita.png")));
            corazonPlaylist.setImage(img2);
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////// BARRA IZQUIERDA ///////////

    @FXML
    private TableView<Activa> playliststabla;
    @FXML
    private TableColumn<Activa, String> nombrecol;
    ObservableList<Activa> playlistslista;
    ObservableList<Song> canciones_playlist_lista;

    String tituloplaylist;

    static int idActiva;

    @FXML
    public void iniciarPlaylistsDelUsuario(MouseEvent event) {
        playliststabla.setVisible(true);
        playlistslista.clear();
        transicionEscenas(playliststabla);
        Connection con = null;
        try {
            nombrecol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT * from playlist p" +
                    " INNER JOIN activa a on a.playlist_id = p.id WHERE p.usuario_id ="+cogerIDUsuarioLoggeado()+" group by p.id");

            while (rs1.next()) {
                String titulo = rs1.getString("titulo");
                int id = rs1.getInt("id");
                Activa p = new Activa(id, titulo);
                playlistslista.add(p);
                filtro.add(p);
            }
            playliststabla.setItems(playlistslista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enseñarCancionesPlaylistDelUsuario(MouseEvent mouseEvent) {


        tituloCancionPlaylist.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        canciones_playlist_lista.clear();

        try {
            Activa a = playliststabla.getSelectionModel().getSelectedItem();
            idActiva = a.getId();
            transicionEscenas(paneCancionesPlaylist);
            paneCancionesPlaylist.setVisible(true);
            canciones_playlist_lista.clear();
            paneAlbums.setVisible(false);
            paneArtistas2.setVisible(false);
            paneHome.setVisible(false);
            panefavoritas.setVisible(false);
            paneTodasLasPlaylist.setVisible(false);
            panePodcasts.setVisible(false);
            nombre_playlistLabel.setText(a.getTitulo());

            Connection con = null;
            try {

                con = Conexion.conexion();
                Statement s = con.createStatement();
                ResultSet rs1 = s.executeQuery("SELECT c.titulo from cancion c " +
                        "INNER JOIN anyade_cancion_playlist an ON c.id = an.cancion_id " +
                        "INNER JOIN usuario u ON an.usuario_id = u.id " +
                        "INNER JOIN playlist p ON an.playlist_id = p.id " +
                        "INNER JOIN sigue_playlist sp ON sp.playlist_id = p.id WHERE u.username LIKE '"+username+"' AND p.titulo LIKE '" +a.getTitulo() + "' ");

                while (rs1.next()) {
                    String tituloa = rs1.getString("titulo");
                    Song c = new Song(tituloa);
                    canciones_playlist_lista.add(c);
                }

                comprobar_si_Playlist_es_Favorita(titulo2);
                cancionesPlaylistTabla.setItems(canciones_playlist_lista);

            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }

        }catch(NullPointerException n){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("¡No pulses en los titulos!");
            alert.show();
        }
    }

    public void eliminarPlaylist(MouseEvent mouseEvent) throws SQLException {
        try {
            int playlist_id = playliststabla.getSelectionModel().getSelectedItem().getId();
            String titulop = playliststabla.getSelectionModel().getSelectedItem().getTitulo();
            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            try {
                s.executeUpdate("DELETE FROM sigue_playlist where playlist_id = " + playlist_id + " and usuario_id = " + cogerIDUsuarioLoggeado() + "");
                s.executeUpdate("DELETE FROM anyade_cancion_playlist where playlist_id = " + playlist_id + "");
                s.executeUpdate("DELETE FROM activa where playlist_id = " + playlist_id + "");
                s.executeUpdate("INSERT INTO eliminada(fecha_eliminacion,playlist_id) values ('" + cogerFechaActual() + "'," + playlist_id + ")");

            }catch (SQLIntegrityConstraintViolationException e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("La playlist " + titulop + " se ha eliminado con éxito");
                alert.show();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado una Playlist");
            alert.show();
        }
    }

    public void nuevaPlaylist(MouseEvent mouseEvent) throws SQLException, IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crearPlaylist.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();

    }

    public void crearPlaylist(MouseEvent event) throws SQLException {

        tituloplaylist = ptitulo.getText();
        try {
            Connection con = null;
            con = Conexion.conexion();
            Statement s = con.createStatement();
            LocalDate fecha_actual = cogerFechaActual();
            ResultSet rs = s.executeQuery("SELECT playlist_id from activa " +
                    "INNER JOIN playlist p on p.id = activa.playlist_id where p.titulo LIKE '" + tituloplaylist + "' and p.usuario_id = " + cogerIDUsuarioLoggeado() + "");
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Esa playlist ya existe");
                alert.show();

            } else {
                s.executeUpdate("Insert into playlist(titulo,numero_canciones,fecha_creacion,usuario_id) values ('" + tituloplaylist + "',0,'" + fecha_actual + "',(SELECT u.id from usuario u where u.username LIKE '" + username + "'))");
                s.executeUpdate("Insert into activa(es_compartida,playlist_id) values (1,(select id from playlist where titulo LIKE '" + tituloplaylist + "'))");

                Node source = (Node) event.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();

                Activa p = new Activa(tituloplaylist);
                playlistslista.add(p);


            }
        }catch(SQLException a){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esa playlist ya está en eliminadas y no puedes usarla");
            alert.show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////// BARRA DERECHA //////////

    // TABLA FRIENDS //
    @FXML
    private TableView<Usuario_Amigo> amigostabla;
    @FXML
    private TableColumn<Usuario_Amigo, String> amigoscol;
    ObservableList<Usuario_Amigo> amigos_lista;

    /// TABLA DE BUSCAR ///
    @FXML
    private TableView<Buscado> tablaSeguirUsuario;
    @FXML
    private TableColumn<Buscado, String> seguirUsuariocol;
    @FXML
    private TextField txtfield_nombrebuscado;
    ObservableList<Buscado> lista_seguirUsuario;

    @FXML
    private TableView<Buscado> tablaSeguirArtista;
    @FXML
    private TableColumn<Buscado, String> seguirArtistacol;

    ObservableList<Buscado> lista_seguirArtista;


    @FXML
    private void iniciarAmigos(MouseEvent event) {
        amigos_lista.clear();
        try {
            amigostabla.setVisible(true);
            amigoscol.setCellValueFactory(new PropertyValueFactory<>("username"));

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT username from usuario  " +
                    "INNER JOIN sigue_usuario su ON su.usuario_seguido_id = usuario.id WHERE su.usuario_id = " + cogerIDUsuarioLoggeado() + "");

            while (rs1.next()) {
                String username = rs1.getString("username");
                Usuario_Amigo u = new Usuario_Amigo(username);
                amigos_lista.add(u);

            }
            amigostabla.setItems(amigos_lista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dejardeseguirUsuario(MouseEvent mouseEvent) throws SQLException {
        try {
            Usuario_Amigo u = amigostabla.getSelectionModel().getSelectedItem();
            Connection con = Conexion.conexion();
            Statement s = con.createStatement();

            s.executeUpdate("DELETE FROM sigue_usuario where usuario_id = " + cogerIDUsuarioLoggeado() + " and usuario_seguido_id = (SELECT id from usuario where username LIKE '" + u.getUsername() + "')");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Has dejado de seguir a " + u.getUsername() + "");
            alert.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado ningún amigo");
            alert.show();
        }

    }

    /// SEGUIR AMIGOS Y ARTISTAS ///
    public void buscarUsuarioyArtista(KeyEvent event) throws SQLException {
        tablaSeguirUsuario.setVisible(true);
        tablaSeguirArtista.setVisible(true);
        if (event.getCode() == KeyCode.ENTER) {
            seguirUsuariocol.setCellValueFactory(new PropertyValueFactory<>("nombrebuscado"));
            seguirArtistacol.setCellValueFactory(new PropertyValueFactory<>("nombrebuscado"));
            if (!lista_seguirUsuario.isEmpty()) {
                lista_seguirUsuario.clear();

            }
            if (!lista_seguirArtista.isEmpty()) {
                lista_seguirArtista.clear();
            }

            String nombrebuscado = txtfield_nombrebuscado.getText();

            ArrayList<Buscado> artista_buscado_lista = new ArrayList<>();
            ArrayList<Buscado> usuario_buscado_lista = new ArrayList<>();

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT nombre from artista");
            while (rs.next()) {
                String nombre_artista = rs.getString("nombre");
                Buscado u = new Buscado(nombre_artista);
                artista_buscado_lista.add(u);
            }
            for (Buscado b : artista_buscado_lista) {
                if (b.getNombrebuscado().equalsIgnoreCase(nombrebuscado) || b.getNombrebuscado().contains(nombrebuscado)) {
                    lista_seguirArtista.add(b);
                }
            }

            Statement s2 = con.createStatement();
            ResultSet rs2 = s2.executeQuery("SELECT username from usuario");
            while (rs2.next()) {
                String nombre_usuario = rs2.getString("username");
                Buscado u = new Buscado(nombre_usuario);
                usuario_buscado_lista.add(u);
            }

            for (Buscado b : usuario_buscado_lista) {
                if (b.getNombrebuscado().equalsIgnoreCase(nombrebuscado) || b.getNombrebuscado().contains(nombrebuscado)) {
                    lista_seguirUsuario.add(b);
                }
            }
        }
        tablaSeguirArtista.setItems(lista_seguirArtista);

        tablaSeguirUsuario.setItems(lista_seguirUsuario);
    }

    public void seguirUsuario(MouseEvent mouseEvent) throws SQLException {

        try {
            Buscado b1 = tablaSeguirUsuario.getSelectionModel().getSelectedItem();


            Connection con = Conexion.conexion();
            Statement s = con.createStatement();

            s.executeUpdate("INSERT INTO sigue_usuario(usuario_id,usuario_seguido_id) values (" + cogerIDUsuarioLoggeado() + ",(SELECT id from usuario where username LIKE '" + b1.getNombrebuscado() + "')) ");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Has seguido a " + b1.getNombrebuscado() + "");
            alert.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado nada");
            alert.show();
        } catch (SQLIntegrityConstraintViolationException s) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ya sigues a ese usuario");
            alert.show();
        }
    }

    public void seguirArtista(MouseEvent mouseEvent) throws SQLException {
        try {
            Buscado b2 = tablaSeguirArtista.getSelectionModel().getSelectedItem();

            Connection con = Conexion.conexion();
            Statement s = con.createStatement();

            s.executeUpdate("INSERT INTO sigue_artista(usuario_id,artista_id) values (" + cogerIDUsuarioLoggeado() + ",(SELECT id from artista where nombre LIKE '" + b2.getNombrebuscado() + "')) ");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Has seguido a " + b2.getNombrebuscado() + "");
            alert.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado nada");
            alert.show();
        } catch (SQLIntegrityConstraintViolationException s) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ya sigues a ese artista");
            alert.show();
        }

    }





    /////// INICIAR UNA CANCION //////

    public void playcancion(MouseEvent mouseEvent) throws IOException, SQLException, InterruptedException {

        try {

            if (paneCancionesPlaylist.isVisible()) {
                titCancion = this.cancionesPlaylistTabla.getSelectionModel().getSelectedItem().getTitulo();
            } else if (paneAlbums.isVisible()) {
                titCancion = this.cancionestabla.getSelectionModel().getSelectedItem().getTitulo();
            } else if (cancionesPlaylistPatrocinadas_tabla.isVisible()) {
                titCancion = this.cancionesPlaylistPatrocinadas_tabla.getSelectionModel().getSelectedItem().getTitulo();
            } else if (cancionesPlaylistQueSigues_tabla.isVisible()) {
                titCancion = this.cancionesPlaylistQueSigues_tabla.getSelectionModel().getSelectedItem().getTitulo();
            } else if (tablafavoritas.isVisible()) {
                titCancion = this.tablafavoritas.getSelectionModel().getSelectedItem().getTitulo();
            }

            corazon.setVisible(true);
            cambiarLabeldeCancion(titCancion);

            comprobar_si_Cancion_es_Favorita(titCancion);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("letra.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setTitle("Spotify");
            stage.setScene(scene);
            stage.show();
            iniciarLetra();
        }catch(NullPointerException n){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("¡No pulses en los titulos!");
                alert.show();
            }
        }


    //método para cambiar el nombre de la canción puesta de la barra de play//

    public void cambiarLabeldeCancion(String titcancion) throws SQLException {
        nombre_cancion.setText(titcancion);
        Connection con = null;
        con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT nombre from artista " +
                "INNER JOIN album al ON artista.id = al.artista_id " +
                "INNER JOIN cancion c ON c.album_id = al.id " +
                "WHERE c.titulo LIKE '" + titcancion + "'");

        if (rs.next()) {
            String artista = rs.getString("nombre");
            nombre_artista.setText(artista);
        }


    }


    public void iniciarLetra() throws SQLException, IOException, InterruptedException {
        cambiaraLetra();
    }

    // ABRIMOS EL CONTROLADOR LETRA_CONTROLLER //

    public void cambiaraLetra() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("letra.fxml"));

        Parent root = fxmlLoader.load();
        Letra_Controller controller2 = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Spotify");
        stage.setScene(scene);
        controller2.iniciarCancion(titCancion, stage, this);
        stage.show();

    }



    // método para cambiar el corazón de favorita //

    public void añadir_o_quitarCancionaFavorita(MouseEvent mouseEvent) throws FileNotFoundException, SQLException {

        Connection con = null;
        con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from cancion  " +
                "INNER JOIN guarda_cancion gp ON cancion.id = gp.cancion_id " +
                "INNER JOIN usuario on usuario.id = gp.usuario_id" +
                " where cancion.id = (SELECT id from cancion where titulo LIKE '" + nombre_cancion.getText() + "') and usuario.id = " + cogerIDUsuarioLoggeado() + "");

        if (rs.next()) {
            s.executeUpdate("DELETE FROM guarda_cancion WHERE usuario_id = " + cogerIDUsuarioLoggeado() + " and cancion_id = (select id from cancion where titulo LIKE '" + nombre_cancion.getText() + "') ");
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_love.png")));
            corazon.setImage(img2);

        } else {
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_favorita.png")));
            corazon.setImage(img2);
            s.executeUpdate("INSERT INTO guarda_cancion(usuario_id,cancion_id) " +
                    "VALUES(" + cogerIDUsuarioLoggeado() + ",(select id from cancion where titulo LIKE '" + nombre_cancion.getText() + "'))");

        }

    }

    // COMPROBACIONES  //

    private void comprobar_si_Cancion_es_Favorita(String titCancion) throws SQLException, FileNotFoundException {
        Connection con = null;
        con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from cancion  " +
                "INNER JOIN guarda_cancion gp ON cancion.id = gp.cancion_id " +
                "INNER JOIN usuario on usuario.id = gp.usuario_id" +
                " where cancion.id = (SELECT id from cancion where titulo LIKE '" + titCancion + "') and usuario.id = " + cogerIDUsuarioLoggeado() + "");

        if (rs.next()) {
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_favorita.png")));
            corazon.setImage(img2);
        } else {
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_love.png")));
            corazon.setImage(img2);

        }
    }

    public void comprobarCancion_a_anyadir(MouseEvent mouseEvent) throws SQLException, IOException {
        titulocancion = nombre_cancion.getText();
        if (titulocancion.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No hay una canción puesta");
            alert.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("añadirAPlaylist.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setTitle("Spotify");
            stage.setScene(scene);
            stage.show();
        }
    }

    private void comprobar_si_Playlist_es_Favorita(String titPlayist) throws SQLException, FileNotFoundException {
        Connection con = null;
        con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from playlist " +
                "INNER JOIN sigue_playlist sp on sp.playlist_id = playlist.id " +
                "INNER JOIN usuario u on u.id = sp.usuario_id WHERE playlist.id ="+idActiva+" and u.id = " + cogerIDUsuarioLoggeado() + "");

        if (rs.next()) {
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_favorita.png")));
            corazonPlaylist.setImage(img2);
        } else {
            Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagenes/ic_love.png")));
            corazonPlaylist.setImage(img2);

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // BOTON AÑADIR A PLAYLIST //
    public void añadirAPlaylist(MouseEvent event) throws SQLException {

        try {
            Connection con = Conexion.conexion();
            Statement s = con.createStatement();

            s.executeUpdate("INSERT into anyade_cancion_playlist(playlist_id,cancion_id,usuario_id,fecha_anyadida) " +
                    "values ((SELECT id from playlist where titulo LIKE '" + titulo2 + "'),(select id from cancion where titulo LIKE '" + titulocancion + "')," + cogerIDUsuarioLoggeado() + ",'0000-00-00 00:00:00')");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Has añadido '" + titulocancion + "' a '" + titulo2 + "'");
            alert.show();

        }catch(SQLIntegrityConstraintViolationException s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No has seleccionado una playlist");
            alert.show();

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void transicionEscenas(Node node) {

        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();

    }

    public LocalDate cogerFechaActual() {

        LocalDate fecha_actual = LocalDate.now();
        return fecha_actual;
    }

    public int cogerIDUsuarioLoggeado() throws SQLException {

        Connection con = Conexion.conexion();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT id from usuario where username LIKE '" + username + "'");
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public void cerrarSesion(MouseEvent mouseEvent) throws IOException {

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
}


