package Dominio;

import java.time.LocalDateTime;

public class consulta {
    private int id_consulta;
    private String procedimiento;
    private LocalDateTime fecha_hora;
    private int id_mascota;
private int id_veterinario;

    public consulta() {
    }

    public consulta(int id_consulta, String procedimiento, LocalDateTime fecha_hora, int id_mascota, int id_veterinario) {
    this.id_consulta = id_consulta;
    this.procedimiento = procedimiento;
    this.fecha_hora = fecha_hora;
    this.id_mascota = id_mascota;
    this.id_veterinario = id_veterinario;
}

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getId_mascota() {
    return id_mascota;
}

public void setId_mascota(int id_mascota) {
    this.id_mascota = id_mascota;
}

public int getId_veterinario() {
    return id_veterinario;
}

public void setId_veterinario(int id_veterinario) {
    this.id_veterinario = id_veterinario;
}
    

    @Override
    public String toString() {
        return "consulta{" +
                "id_consulta=" + id_consulta +
                ", procedimiento='" + procedimiento + '\'' +
                ", fecha_hora='" + fecha_hora + '\'' +
                '}';
    }
}
