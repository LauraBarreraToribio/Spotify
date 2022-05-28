package Classes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Artista {

    private int id;
    private String nombre;
    private ImageView imagen;
    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Artista(String nombre, ImageView imagen, String ruta) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.ruta = ruta;

    }

    public Artista(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }
}
