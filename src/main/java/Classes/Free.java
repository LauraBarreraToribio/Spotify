package Classes;

public class Free extends Usuario {
    public Free(String username, int password) {
        super(username, password);
    }

    public Free(int id, String pais, String codigo_postal, String username, String password, String email, String genero, String fecha_nacimiento) {
        super(id, pais, codigo_postal, username, password, email, genero, fecha_nacimiento);
    }
}
