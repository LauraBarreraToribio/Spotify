package Classes;

import java.util.Date;

public class Activa extends Playlist {
    private boolean es_compartida;
    private String propietario;


    public Activa(String titulo) {
        super(titulo);

    }

    public Activa(int id, String titulo) {
        super(id, titulo);
    }

    public Activa(int id, String titulo, String propietario) {
        super(id, titulo);
        this.propietario = propietario;
    }

    public Activa(String titulo, String propietario) {

        super(titulo);
        this.propietario = propietario;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Activa(String titulo, int usuario_id) {
        super(titulo);
    }


    public boolean isEs_compartida() {
        return es_compartida;
    }

    public void setEs_compartida(boolean es_compartida) {
        this.es_compartida = es_compartida;
    }

}
