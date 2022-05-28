package Classes;

public class Favorita extends Playlist{
    int playlist_id;

    public Favorita(String titulo, int playlist_id) {
        super(titulo);
        this.playlist_id = playlist_id;
    }

    public Favorita(String titulo) {
        super(titulo);
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }


}
