package Classes;

import java.sql.Date;

public class Patrocinada extends Playlist{
    private int playlist_id;
    private Date fecha_inicio;
    private Date fecha_fin;
    private int patrocinada = 1;


    public Patrocinada(String titulo, int playlist_id, int patrocinada) {
        super(titulo);
        this.playlist_id = playlist_id;
        this.patrocinada = patrocinada;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getPatrocinada() {
        return patrocinada;
    }

    public void setPatrocinada(int patrocinada) {
        this.patrocinada = patrocinada;
    }
}


