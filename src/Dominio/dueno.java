package Dominio;

public class dueno {
    private int id_dueño;
    private String nombre;
    private int CI;
    private int Telefono;
    private String Email;
    private String password;

    public dueno() {
    }

    public dueno(int id_dueño, String nombre, int CI, int Telefono, String Email, String password) {
        this.id_dueño = id_dueño;
        this.nombre = nombre;
        this.CI = CI;
        this.Telefono = Telefono;
        this.Email = Email;
        this.password = password;
    }

    public int getId_dueño() {
        return id_dueño;
    }

    public void setId_dueño(int id_dueño) {
        this.id_dueño = id_dueño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCI() {
        return CI;
    }

    public void setCI(int CI) {
        this.CI = CI;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "dueño{" +
                "id_dueño=" + id_dueño +
                ", nombre='" + nombre + '\'' +
                ", CI=" + CI +
                ", telefono=" + Telefono +
                ", email='" + Email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
