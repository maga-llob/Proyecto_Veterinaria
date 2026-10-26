package Dominio;

public class veterinario {
    private int id_veterinario;
    private String nombre;
    private String password;
    private String especialidad;
    private String estado;

    public veterinario() {
    }

    public veterinario(int id_veterinario, String nombre, String password, String especialidad) {
        this.id_veterinario = id_veterinario;
        this.nombre = nombre;
        this.password = password;
        this.especialidad = especialidad;
    }

    public int getId_veterinario() {
        return id_veterinario;
    }

    public void setId_veterinario(int id_veterinario) {
        this.id_veterinario = id_veterinario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
    return estado;
    }

    public void setEstado(String estado) {
    this.estado = estado;
    }

    @Override
    public String toString() {
        return "veterinario{" +
                "id_veterinario=" + id_veterinario +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", especialidad='" + especialidad + '\'' +
                '}';
    }
}
