package Classes;

import java.time.Year;

public class Podcast {

private int id;
private String titulo;
private String descripcion;
private Year anyo;

    public Podcast(int id, String titulo, String descripcion, Year anyo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.anyo = anyo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Year getAnyo() {
        return anyo;
    }

    public void setAnyo(Year anyo) {
        this.anyo = anyo;
    }
}
