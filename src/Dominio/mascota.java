package Dominio;

public class mascota {
    private String nombre;
    private int edad;
    private String especie;
    private String raza;
    private String sexo;
    private double peso;
    private int id_mascota;
    private int id_dueno;

    public mascota() {
    }

    public mascota(String nombre, int edad, String especie, String raza, String sexo, double peso, int id_mascota, int id_dueno) {
    this.nombre = nombre;
    this.edad = edad;
    this.especie = especie;
    this.raza = raza;
    this.sexo = sexo;
    this.peso = peso;
    this.id_mascota = id_mascota;
    this.id_dueno = id_dueno;
}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public int getId_dueno() {
    return id_dueno;
}

public void setId_dueno(int id_dueno) {
    this.id_dueno = id_dueno;
}

    @Override
    public String toString() {
        return "mascota{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", sexo='" + sexo + '\'' +
                ", peso=" + peso +
                ", id_mascota=" + id_mascota +
                '}';
    }
}
