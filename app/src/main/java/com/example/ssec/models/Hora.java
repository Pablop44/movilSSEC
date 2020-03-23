package com.example.ssec.models;

public class Hora {
    private String hora;
    private String disponible;

    public Hora(String hora, String disponible) {
        this.hora = hora;
        this.disponible = disponible;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
}
