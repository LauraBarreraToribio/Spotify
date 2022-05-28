package Classes;

public class Buscado implements Comparable {
    private String nombrebuscado;

    public Buscado(String nombrebuscado) {
        this.nombrebuscado = nombrebuscado;
    }

    public String getNombrebuscado() {
        return nombrebuscado;
    }

    public void setNombrebuscado(String nombrebuscado) {
        this.nombrebuscado = nombrebuscado;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
