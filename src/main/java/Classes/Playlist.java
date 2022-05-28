package Classes;

import java.time.LocalDate;
import java.util.Date;

public abstract class Playlist {
    private int id;
    private String titulo;
    private int numero_canciones;
    private LocalDate fecha_actual;
    private int usuario_id;


    public Playlist(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Playlist(String titulo) {
        this.titulo = titulo;
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

    public int getNumero_canciones() {
        return numero_canciones;
    }

    public void setNumero_canciones(int numero_canciones) {
        this.numero_canciones = numero_canciones;
    }

    public LocalDate getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(LocalDate fecha_actual) {
        this.fecha_actual = fecha_actual;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
