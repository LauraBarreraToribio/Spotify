package Classes;

public abstract class Usuario {
    private int id;
    private String pais;
    private String codigo_postal;
    private String username;
    private String password;
    private String email;
    private String genero;



    public Usuario(int id, String pais, String codigo_postal, String username, String password, String email, String genero, String fecha_nacimiento) {
        this.id = id;
        this.pais = pais;
        this.codigo_postal = codigo_postal;
        this.username = username;
        this.password = password;
        this.email = email;
        this.genero = genero;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Usuario(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    private String fecha_nacimiento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }
}
