package Classes;

public class Song {

    private int id;
    private String titulo;
    private double duracion;
    private String ruta;
    private int numero_reproducciones;
    private String album_id;

    public Song(String titulo) {
        this.titulo = titulo;
    }

    public Song(String titulo, int numero_reproducciones, double duracion) {
        this.titulo = titulo;
        this.numero_reproducciones = numero_reproducciones;
        this.duracion = duracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getNumero_reproducciones() {
        return numero_reproducciones;
    }

    public void setNumero_reproducciones(int numero_reproducciones) {
        this.numero_reproducciones = numero_reproducciones;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }
}
