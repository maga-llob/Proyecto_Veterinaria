package Dominio;

import java.time.LocalDateTime;
import java.util.Date;

public class vacuna {
    private int id_vacuna;
    private String tipo;
    private LocalDateTime fecha_hora;
    private Date vencimiento;
    private int id_mascota;
private int id_consulta;

    public vacuna() {
    }

    public vacuna(int id_vacuna, String tipo, LocalDateTime fecha_hora, Date vencimiento, int id_mascota, int id_consulta) {
    this.id_vacuna = id_vacuna;
    this.tipo = tipo;
    this.fecha_hora = fecha_hora;
    this.vencimiento = vencimiento;
    this.id_mascota = id_mascota;
    this.id_consulta = id_consulta;
}

    public int getId_vacuna() {
        return id_vacuna;
    }

    public void setId_vacuna(int id_vacuna) {
        this.id_vacuna = id_vacuna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha_vacuna() {
        return fecha_hora;
    }

    public void setFecha_vacuna(LocalDateTime fecha_vacuna) {
        this.fecha_hora = fecha_vacuna;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getId_mascota() {
    return id_mascota;
}

public void setId_mascota(int id_mascota) {
    this.id_mascota = id_mascota;
}

public int getId_consulta() {
    return id_consulta;
}

public void setId_consulta(int id_consulta) {
    this.id_consulta = id_consulta;
}

    @Override
    public String toString() {
        return "vacuna{" +
                "id_vacuna=" + id_vacuna +
                ", tipo='" + tipo + '\'' +
                ", fecha_hora=" + fecha_hora +
                ", vencimiento=" + vencimiento +
                '}';
    }
}
